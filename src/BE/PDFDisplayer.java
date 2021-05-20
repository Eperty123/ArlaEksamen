package BE;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.scene.Parent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Author: Dansoft Owner
 * Version: 0.8
 * GitHub: https://github.com/Dansoftowner/PDFViewerFX
 */
public class PDFDisplayer {

    private static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool(runnable -> {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t;
    });

    private final ObjectProperty<Consumer<Task<String>>> onLoaderTaskPresent =
            new SimpleObjectProperty<>();

    private boolean pdfJsLoaded;

    private WebView webView;
    private String loadScript;
    private String toExecuteWhenPDFJSLoaded = "";

    public PDFDisplayer() {
    }

    public PDFDisplayer(File file) throws IOException {
        loadPDF(file);
    }

    public PDFDisplayer(URL url) throws IOException {
        loadPDF(url);
    }

    public PDFDisplayer(InputStream inputStream) throws IOException {
        loadPDF(inputStream);
    }

    /**
     * Load a pdf from a File.
     *
     * @param file The File to load a pdf from.
     * @throws IOException
     */
    public void loadPDF(File file) throws IOException {
        loadPDF(new BufferedInputStream(new FileInputStream(file)));
    }

    /**
     * Load a pdf from the specified URL.
     *
     * @param url The URL to load.
     * @throws IOException
     */
    public void loadPDF(URL url) throws IOException {
        loadPDF(new BufferedInputStream(url.openConnection().getInputStream()));
    }

    /**
     * Load a pdf from an InputStream.
     *
     * @param inputStream The InputStream to use.
     */
    public void loadPDF(InputStream inputStream) {
        if (inputStream == null)
            return;

        Task<String> task = buildLoadingTask(inputStream);

        final Consumer<Task<String>> onLoaderTaskPresent = this.onLoaderTaskPresent.get();
        if (onLoaderTaskPresent != null) {
            Platform.runLater(() -> onLoaderTaskPresent.accept(task));
        }
        THREAD_POOL.submit(task);
    }

    /**
     * Create a task to convert the pdf file into a Stream.
     *
     * @param inputStream The InputStream of the pdf.
     * @return
     */
    private Task<String> buildLoadingTask(InputStream inputStream) {
        final Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                    long contentSize = inputStream.available();
                    long onePercent = contentSize / 100;

                    int allReadBytesCount = 0;

                    byte[] buf = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buf)) >= 0) {
                        allReadBytesCount += bytesRead;
                        outputStream.write(buf, 0, bytesRead);

                        if (onePercent > 0) {
                            double percent = allReadBytesCount / (double) onePercent;
                            updateProgress(percent, 100d);
                        }

                        if (this.isCancelled()) {
                            return null;
                        }
                    }

                    byte[] data = outputStream.toByteArray();
                    String base64 = Base64.getEncoder().encodeToString(data);

                    //JS Function declaration
                    return "openFileFromBase64('" + base64 + "');";
                } finally {
                    inputStream.close();
                }
            }
        };
        task.valueProperty().addListener((observable, oldValue, js) -> {
            if (js != null) {
                try {
                    webView.getEngine().executeScript(js);
                } catch (Exception ex) {
                    if (!pdfJsLoaded) loadScript = js;
                }
            }
        });
        return task;
    }

    @SuppressWarnings("all")
    public void setSecondaryToolbarToggleVisibility(boolean value) {
        setVisibilityOf("secondaryToolbarToggle", value);

        String js;
        if (value) {
            js = new StringBuilder()
                    .append("var element = document.getElementsByClassName('verticalToolbarSeparator')[0];")
                    .append("element.style.display = 'inherit';")
                    .append("element.style.visibility = 'inherit';")
                    .toString();
        } else {
            js = new StringBuilder()
                    .append("var element = document.getElementsByClassName('verticalToolbarSeparator')[0];")
                    .append("element.style.display = 'none';")
                    .append("element.style.visibility = 'hidden';")
                    .toString();
        }

        try {
            webView.getEngine().executeScript(js);
        } catch (Exception ex) {
            if (!pdfJsLoaded) toExecuteWhenPDFJSLoaded += js;
        }
    }

    @SuppressWarnings("all")
    public void setVisibilityOf(String id, boolean value) {
        String css;
        if (value) {
            css = new StringBuilder()
                    .append("document.getElementById('" + id + "').style.display = 'inherit';")
                    .append("document.getElementById('" + id + "').style.visibility = 'inherit';")
                    .toString();
        } else {
            css = new StringBuilder()
                    .append("document.getElementById('" + id + "').style.display = 'none';")
                    .append("document.getElementById('" + id + "').style.visibility = 'hidden';")
                    .toString();
        }

        try {
            webView.getEngine().executeScript(css);
        } catch (Exception ex) {
            if (!pdfJsLoaded) this.toExecuteWhenPDFJSLoaded += css;
        }
    }

    /**
     * Get the current page number.
     *
     * @return The current page number.
     */
    public int getActualPageNumber() {
        try {
            return (int) webView.getEngine().executeScript("PDFViewerApplication.page;");
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Get the total pages of the pdf.
     *
     * @return
     */
    public int getTotalPageCount() {
        try {
            return (int) webView.getEngine().executeScript("PDFViewerApplication.pagesCount;");
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Navigate to the specified page index.
     *
     * @param pageNum The page index to navigate to.
     */
    public void navigateByPage(int pageNum) {
        String jsCommand = "goToPage(" + pageNum + ");";
        try {
            webView.getEngine().executeScript(jsCommand);
        } catch (Exception ex) {
            if (!pdfJsLoaded) toExecuteWhenPDFJSLoaded += jsCommand;
        }
    }

    /**
     * Execute the specified JavaScript.
     *
     * @param js The JavaScript to execute.
     */
    public void executeScript(String js) {
        try {
            this.webView.getEngine().executeScript(js);
        } catch (Exception ex) {
            if (!pdfJsLoaded) toExecuteWhenPDFJSLoaded += String.format("%s;", js);
        }
    }

    /**
     * Create a WebView to show the pdf in.
     *
     * @return The created WebView.
     */
    private WebView createWebView() {
        WebView webView = new WebView();
        webView.setContextMenuEnabled(false);

        WebEngine engine = webView.getEngine();
        // The files below must exist! Or pdf viewer no more!
        String url = new File("src/Resources/pdfjs_2.7.570/web/viewer.html").toURI().toString();

        engine.setJavaScriptEnabled(true);
        engine.load(url);
        System.out.println(url);


        engine.getLoadWorker()
                .stateProperty()
                .addListener(
                        new ChangeListener<>() {
                            @Override
                            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                                JSObject window = (JSObject) engine.executeScript("window");
                                window.setMember("java", null);
                                engine.executeScript("console.log = function(message){ try {java.log(message);} catch(e) {} };");

                                if (newValue == Worker.State.SUCCEEDED) {
                                    try {
                                        pdfJsLoaded = true;

                                        if (loadScript != null) {
                                            engine.executeScript(loadScript);
                                        }

                                        engine.executeScript(toExecuteWhenPDFJSLoaded);
                                        toExecuteWhenPDFJSLoaded = null;
                                        observable.removeListener(this);
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        });
        return webView;
    }

    /**
     * Create a Node of the WebView to be able to put in any scene.
     *
     * @return Returns a usable Node to be used in a scene.
     */
    public Parent toNode() {
        if (webView == null)
            return webView = createWebView();
        else
            return webView;
    }
}
