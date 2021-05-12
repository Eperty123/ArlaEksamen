package BLL.DataNodes;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import java.io.File;

public class HTTPNode implements IDataNode {

    /**
     * Add a WebView that loads a file or url.
     *
     * @param pane The BorderPane responsible for this WebView.
     * @param file The url or file to load into the WebView.
     * @return Returns the WebView.
     */
    @Override
    public Node getData(BorderPane pane, File file) {

        WebView webView = new WebView();
        webView.getEngine().load(file.toURI().toString());

        //Makes it follow the panes width
        pane.widthProperty().addListener((observableValue, bounds, t1) -> {
            webView.setPrefWidth(t1.doubleValue());
        });
        //Makes it follow the panes height
        pane.heightProperty().addListener((observableValue, bounds, t1) -> {
            webView.setPrefHeight(t1.doubleValue());
        });

        //sets initial height and width
        webView.setPrefWidth(pane.getWidth());
        webView.setPrefHeight(pane.getHeight());

        webView.setAccessibleText(ViewType.HTTP.name() + String.format("=\"%s\"", file.getAbsolutePath()));
        return webView;
    }
}
