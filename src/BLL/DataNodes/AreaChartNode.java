package BLL.DataNodes;

import BLL.DataGenerator;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.layout.BorderPane;

import java.io.File;

public class AreaChartNode implements IDataNode {
    /**
     * Takes the AreaChart from the DataGenerator to make a Barchart
     *
     * @param pane the BorderPane of the PickerStageController
     * @param file the file you want to generate data from
     * @return A Node with the given AreaChart
     */
    @Override
    public Node getData(BorderPane pane, File file) {
        AreaChart areaChart = DataGenerator.getAreaChart(file.getPath());

        //Makes it follow the panes width
        pane.widthProperty().addListener((observableValue, bounds, t1) -> {
            areaChart.setPrefWidth(t1.doubleValue());
        });
        //Makes it follow the panes height
        pane.heightProperty().addListener((observableValue, bounds, t1) -> {
            areaChart.setPrefHeight(t1.doubleValue());
        });

        //sets initial height and width
        areaChart.setPrefWidth(pane.getWidth());
        areaChart.setPrefHeight(pane.getHeight());

        areaChart.setAccessibleText(ViewType.AreaChart.name() + String.format("=\"%s\"", file.getPath()));
        return areaChart;
    }

    @Override
    public Node getData(BorderPane pane, String uri) {
        return this.getData(pane, new File(uri));
    }
}
