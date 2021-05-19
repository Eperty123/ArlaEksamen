package BLL.DataNodes;

import BE.PDFDisplayer;
import GUI.Controller.PopupControllers.WarningController;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.IOException;

public class PdfNode implements IDataNode {
    @Override
    public Node getData(BorderPane pane, File file) {
        PDFDisplayer pdfViewer = new PDFDisplayer();
        try {
            var pdfNode = (WebView) pdfViewer.toNode();
            pdfViewer.loadPDF(file);
            //Makes it follow the panes width
            pane.widthProperty().addListener((observableValue, bounds, t1) -> {
                pdfNode.setPrefWidth(t1.doubleValue());
            });
            //Makes it follow the panes height
            pane.heightProperty().addListener((observableValue, bounds, t1) -> {
                pdfNode.setPrefHeight(t1.doubleValue());
            });

            //sets initial height and width
            pdfNode.setPrefWidth(pane.getWidth());
            pdfNode.setPrefHeight(pane.getHeight());

            pdfNode.setAccessibleText(ViewType.PDF.name() + String.format("=\"%s\"", file.getAbsolutePath()));
            return pdfNode;
        } catch (IOException e) {
            WarningController.createWarning("Oh no! Something went wrong trying to read the PDF file." +
                    " The file may be corrupted or lost. " +
                    "Please try again. If the problem persists, please contact an IT-Administrator");
        }
        return null;
    }

    @Override
    public Node getData(BorderPane pane, String uri) {
        return getData(pane, new File(uri));
    }
}
