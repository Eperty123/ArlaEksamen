package BLL.DataNodes;

import BLL.DataGenerator;
import javafx.scene.Node;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.FileNotFoundException;

public class StackedBarChartNode implements IDataNode {
    /**
     * Takes the StackedBarChart from the DataGenerator to make a Barchart
     *
     * @param pane the BorderPane of the PickerStageController
     * @param file the file you want to generate data from
     * @return A Node with the given StackedBarChart
     */
    @Override
    public Node getData(BorderPane pane, File file) throws FileNotFoundException {
        StackedBarChart stackedBarChart = DataGenerator.getStackedBarChart(file.getPath());

        //Makes it follow the panes width
        pane.widthProperty().addListener((observableValue, bounds, t1) -> {
            stackedBarChart.setPrefWidth(t1.doubleValue());
        });
        //Makes it follow the panes height
        pane.heightProperty().addListener((observableValue, bounds, t1) -> {
            stackedBarChart.setPrefHeight(t1.doubleValue());
        });

        //sets initial height and width
        stackedBarChart.setPrefWidth(pane.getWidth());
        stackedBarChart.setPrefHeight(pane.getHeight());

        stackedBarChart.setAccessibleText(ViewType.StackedBarChart.name() + String.format("=\"%s\"", file.getPath()));
        return stackedBarChart;
    }

    @Override
    public Node getData(BorderPane pane, String uri) throws FileNotFoundException {
        return this.getData(pane, new File(uri));
    }
}
