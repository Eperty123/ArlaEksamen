package BLL.DataNodes;

import BLL.DataGenerator;
import javafx.scene.Node;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.layout.BorderPane;

import java.io.File;

public class StackedAreaChartNode implements IDataNode {
    /**
     * Takes the StackedAreaChart from the DataGenerator to make a Barchart
     *
     * @param pane the BorderPane of the PickerStageController
     * @param file the file you want to generate data from
     * @return A Node with the given StackedAreaChart
     */
    @Override
    public Node getData(BorderPane pane, File file) {
        StackedAreaChart stackedAreaChart = DataGenerator.getStackedAreaChart(file.getPath());

        //Makes it follow the panes width
        pane.widthProperty().addListener((observableValue, bounds, t1) -> {
            stackedAreaChart.setPrefWidth(t1.doubleValue());
        });
        //Makes it follow the panes height
        pane.heightProperty().addListener((observableValue, bounds, t1) -> {
            stackedAreaChart.setPrefHeight(t1.doubleValue());
        });

        //sets initial height and width
        stackedAreaChart.setPrefWidth(pane.getWidth());
        stackedAreaChart.setPrefHeight(pane.getHeight());

        stackedAreaChart.setAccessibleText(ViewType.StackedAreaChart.name() + String.format("=\"%s\"", file.getPath()));
        return stackedAreaChart;
    }

    @Override
    public Node getData(BorderPane pane, String uri) {
        return getData(pane, new File(uri));
    }
}
