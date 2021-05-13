package BLL.DataNodes;

import BLL.DataGenerator;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.BorderPane;

import java.io.File;

public class LineChartNode implements IDataNode {
    /**
     * Takes the LineChart from the DataGenerator to make a Barchart
     *
     * @param pane the BorderPane of the PickerStageController
     * @param file the file you want to generate data from
     * @return A Node with the given LineChart
     */
    @Override
    public Node getData(BorderPane pane, File file) {
        LineChart lineChart = DataGenerator.getLineChart(file.getPath());

        //Makes it follow the panes width
        pane.widthProperty().addListener((observableValue, bounds, t1) -> {
            lineChart.setPrefWidth(t1.doubleValue());
        });
        //Makes it follow the panes height
        pane.heightProperty().addListener((observableValue, bounds, t1) -> {
            lineChart.setPrefHeight(t1.doubleValue());
        });

        //sets initial height and width
        lineChart.setPrefWidth(pane.getWidth());
        lineChart.setPrefHeight(pane.getHeight());

        lineChart.setAccessibleText(ViewType.LineChart.name()  + String.format("=\"%s\"", file.getPath()));
        return lineChart;
    }
}
