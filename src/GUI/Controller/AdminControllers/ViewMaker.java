package GUI.Controller.AdminControllers;

import BLL.DataGenerator;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewMaker {

    /**
     * Switches between the different ViewTypes to call the right method
     * @param pickerStageController the controller
     * @param viewType The ViewType you want
     * @param file the file with our markdown
     */
    public static void callProperMethod(PickerStageController pickerStageController, String viewType, File file) {
        switch (viewType) {
            case "HTTP" -> {
                pickerStageController.setContent(ViewMaker.getHTTP(pickerStageController.getRoot(), file));
            }
            case "BarChart" -> {
                pickerStageController.setContent(ViewMaker.getBarChart(pickerStageController.getRoot(), file));
            }
            case "PieChart" -> {
                pickerStageController.setContent(ViewMaker.getPieChart(pickerStageController.getRoot(), file));
            }
            case "Image" -> {
                pickerStageController.setContent(ViewMaker.getImage(pickerStageController.getRoot(), file));
            }
            default -> {
                System.out.println("Option not implemented yet");
            }
        }
    }

    //TODO datagenerator does not give the right data
    public static Node getBarChart(BorderPane pane, File file) {
        Axis<String> xAxis = new CategoryAxis();
        xAxis.setLabel("xAxis");
        Axis<Number> yAxis = new NumberAxis();
        yAxis.setLabel("yAxis");

        BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);

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

        XYChart.Series series = DataGenerator.getBarChartSeries(file.getAbsolutePath());
        bc.getData().add(series);
        bc.setAccessibleText(ViewType.BarChart.name() + String.format("=\"%s\"", file.getAbsolutePath()));

        return bc;
    }

    //TODO Piechar does not get the right data
    public static Node getPieChart(BorderPane pane, File file) {
        PieChart pieChart = new PieChart();

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

        DataGenerator.getPieChartData(file.getAbsolutePath()).forEach(d -> pieChart.getData().add(new PieChart.Data(d.getName(), d.getPieValue())));
        pieChart.setAccessibleText(ViewType.PieChart.name() + String.format("=\"%s\"", file.getAbsolutePath()));
        return pieChart;
    }

    //TODO implement this
    public static Node getHTTP(BorderPane borderPane, File file) {
        return null;
    }

    public static Node getImage(BorderPane pane, File file) {
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
