package BLL.DataNodes;

import BLL.DataGenerator;
import javafx.scene.Node;
import javafx.scene.chart.ScatterChart;
import javafx.scene.layout.BorderPane;

import java.io.File;

public class ScatterChartNode implements IDataNode {
    /**
     * Takes the ScatterChart from the DataGenerator to make a ScatterChart
     *
     * @param pane the BorderPane of the PickerStageController
     * @param file the file you want to generate data from
     * @return A Node with the given PieChart
     */
    @Override
    public Node getData(BorderPane pane, File file) {
        ScatterChart scatterChart = DataGenerator.getScatterChart(file.getPath());

        //Makes it follow the panes width
        pane.widthProperty().addListener((observableValue, bounds, t1) -> {
            scatterChart.setPrefWidth(t1.doubleValue());
        });
        //Makes it follow the panes height
        pane.heightProperty().addListener((observableValue, bounds, t1) -> {
            scatterChart.setPrefHeight(t1.doubleValue());
        });

        //sets initial height and width
        scatterChart.setPrefWidth(pane.getWidth());
        scatterChart.setPrefHeight(pane.getHeight());

        scatterChart.setAccessibleText(ViewType.ScatterChart.name() + String.format("=\"%s\"", file.getPath()));
        return scatterChart;
    }
}
