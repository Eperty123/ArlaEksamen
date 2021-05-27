package BLL.DataNodes;

import BLL.DataGenerator;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;

import java.io.File;

public class PieChartNode implements IDataNode{
    /**
     * Takes the PieChart from the DataGenerator to make a Barchart
     *
     * @param pane the BorderPane of the PickerStageController
     * @param file the file you want to generate data from
     * @return A Node with the given PieChart
     */
    @Override
    public Node getData(BorderPane pane, File file) {
        PieChart pieChart = DataGenerator.getPieChart(file.getPath());

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

        pieChart.setAccessibleText(ViewType.PieChart.name() + String.format("=\"%s\"", file.getPath()));
        return pieChart;
    }

    @Override
    public Node getData(BorderPane pane, String uri) {
        return this.getData(pane, new File(uri));
    }
}
