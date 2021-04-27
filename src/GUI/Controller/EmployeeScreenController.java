package GUI.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class EmployeeScreenController implements Initializable {
    @FXML
    private BarChart<String,Number> barChart;
    @FXML
    private PieChart pieChart;
    @FXML
    private AreaChart<String,Number> areaChart;
    @FXML
    private BubbleChart<String,Number> bubbleChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
            BAR CHART
         */
        XYChart.Series series1 = new XYChart.Series();
        series1.getData().add(new XYChart.Data("Test 1", 100));
        series1.getData().add(new XYChart.Data("Test 2", 160));
        series1.getData().add(new XYChart.Data("Test 3", 34));
        series1.getData().add(new XYChart.Data("Test 4", 720));
        barChart.getData().addAll(series1);

        /*
            PIE CHART
         */
        PieChart.Data slice1 = new PieChart.Data("Test 1", 100);
        PieChart.Data slice2 = new PieChart.Data("Test 2", 740);
        PieChart.Data slice3 = new PieChart.Data("Test 3", 345);
        pieChart.getData().addAll(slice1,slice2,slice3);

        /*
            AREA CHART
         */
        XYChart.Series series3 = new XYChart.Series();
        series3.getData().add(new XYChart.Data("Test 1", 23));
        series3.getData().add(new XYChart.Data("Test 2", 986));
        series3.getData().add(new XYChart.Data("Test 3", 1231));
        series3.getData().add(new XYChart.Data("Test 4", 342));
        areaChart.getData().add(series3);

        /*
            BUBBLE CHART
         */
        XYChart.Series series4 = new XYChart.Series();
        series4.setName("work");

        series4.getData().add(new XYChart.Data(10,30,4));
        series4.getData().add(new XYChart.Data(25,40,5));
        series4.getData().add(new XYChart.Data(40,50,9));
        series4.getData().add(new XYChart.Data(55,60,7));
        series4.getData().add(new XYChart.Data(70,70,9));
        series4.getData().add(new XYChart.Data(85,80,6));

        bubbleChart.getData().add(series4);
    }
}
