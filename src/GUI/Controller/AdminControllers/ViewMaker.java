package GUI.Controller.AdminControllers;

import BLL.DataGenerator;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import java.io.File;

public class ViewMaker {

    /**
     * Switches between the different ViewTypes to call the right method
     *
     * @param pickerStageController the controller
     * @param viewType              The ViewType you want
     * @param file                  the file with our markdown
     */
    public static void callProperMethod(PickerStageController pickerStageController, ViewType viewType, File file) {
        switch (viewType) {
            case HTTP, PDF -> {
                pickerStageController.setContent(ViewMaker.getHTTP(pickerStageController.getRoot(), file));
            }
            case BarChart -> {
                pickerStageController.setContent(ViewMaker.getBarChart(pickerStageController.getRoot(), file));
            }
            case PieChart -> {
                pickerStageController.setContent(ViewMaker.getPieChart(pickerStageController.getRoot(), file));
            }
            case Image -> {
                pickerStageController.setContent(ViewMaker.getImage(pickerStageController.getRoot(), file));
            }
            default -> {
                System.out.println("Option not implemented yet");
            }
        }
    }

    /**
     * Takes the BarChart from the DataGenerator to make a Barchart
     *
     * @param pane the BorderPane of the PickerStageController
     * @param file the file you want to generate data from
     * @return A Node with the given BarChart
     */
    private static Node getBarChart(BorderPane pane, File file) {
        BarChart<String, Number> bc = DataGenerator.getBarChart(file.getAbsolutePath());

        //Makes it follow the panes width
        pane.widthProperty().addListener((observableValue, bounds, t1) -> {
            bc.setPrefWidth(t1.doubleValue());
        });
        //Makes it follow the panes height
        pane.heightProperty().addListener((observableValue, bounds, t1) -> {
            bc.setPrefHeight(t1.doubleValue());
        });
        //sets initial height and width
        bc.setPrefWidth(pane.getWidth());
        bc.setPrefHeight(pane.getHeight());
        bc.setAccessibleText(ViewType.BarChart.name() + String.format("=\"%s\"", file.getAbsolutePath()));

        return bc;
    }

    /**
     * Takes the PieChart from the DataGenerator to make a Barchart
     *
     * @param pane the BorderPane of the PickerStageController
     * @param file the file you want to generate data from
     * @return A Node with the given PieChart
     */
    private static Node getPieChart(BorderPane pane, File file) {
        PieChart pieChart = DataGenerator.getPieChart(file.getAbsolutePath());

        //Makes it follow the panes width
        pane.widthProperty().addListener((observableValue, bounds, t1) -> {
            pieChart.setPrefWidth(t1.doubleValue());
        });
        //Makes it follow the panes height
        pane.heightProperty().addListener((observableValue, bounds, t1) -> {
            pieChart.setPrefHeight(t1.doubleValue());
        });

        //sets initial height and width
        pieChart.setPrefWidth(pane.getWidth());
        pieChart.setPrefHeight(pane.getHeight());

        pieChart.setAccessibleText(ViewType.PieChart.name() + String.format("=\"%s\"", file.getAbsolutePath()));
        return pieChart;
    }

    /**
     * Add a WebView that loads a file or url.
     *
     * @param pane The BorderPane responsible for this WebView.
     * @param file The url or file to load into the WebView.
     * @return Returns the WebView.
     */
    private static Node getHTTP(BorderPane pane, File file) {
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

    /**
     * Adds a Image to the view
     *
     * @param pane The PickerStage's BorderPane
     * @param file the file of the image
     * @return A Node with the given Image
     */
    private static Node getImage(BorderPane pane, File file) {
        ImageView imageView = new ImageView(String.format("file:/%s", file.getAbsolutePath()));
        imageView.setPreserveRatio(true);


        //Makes it follow the panes width
        pane.widthProperty().addListener((observableValue, bounds, t1) -> {
            imageView.setFitWidth(t1.doubleValue());
        });

        //Makes it follow the panes height
        pane.heightProperty().addListener((observableValue, bounds, t1) -> {
            imageView.setFitHeight(t1.doubleValue());
        });

        //sets initial height and width
        imageView.setFitWidth(pane.getWidth());
        imageView.setFitHeight(pane.getHeight());

        imageView.setAccessibleText(ViewType.Image + String.format("=\"%s\"", file.getAbsolutePath()));
        return imageView;
    }
}
