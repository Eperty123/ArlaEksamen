package BLL.DataNodes;

import BLL.DataGenerator;
import javafx.scene.Node;
import javafx.scene.chart.BubbleChart;
import javafx.scene.layout.BorderPane;

import java.io.File;

public class BubbleChartNode implements IDataNode {
    /**
     * Takes the BubbleChart from the DataGenerator to make a Barchart
     *
     * @param pane the BorderPane of the PickerStageController
     * @param file the file you want to generate data from
     * @return A Node with the given BubbleChart
     */
    @Override
    public Node getData(BorderPane pane, File file) {
        BubbleChart bubbleChart = DataGenerator.getBubbleChart(file.getPath());

        //Makes it follow the panes width
        pane.widthProperty().addListener((observableValue, bounds, t1) -> {
            bubbleChart.setPrefWidth(t1.doubleValue());
        });
        //Makes it follow the panes height
        pane.heightProperty().addListener((observableValue, bounds, t1) -> {
            bubbleChart.setPrefHeight(t1.doubleValue());
        });

        //sets initial height and width
        bubbleChart.setPrefWidth(pane.getWidth());
        bubbleChart.setPrefHeight(pane.getHeight());

        bubbleChart.setAccessibleText(ViewType.BubbleChart.name() + String.format("=\"%s\"", file.getPath()));
        return bubbleChart;
    }

    @Override
    public Node getData(BorderPane pane, String uri) {
        return this.getData(pane, new File(uri));
    }
}
