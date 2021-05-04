package GUI.Controller;

import BLL.DataGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChartTestController implements Initializable {

    private DataGenerator dataGenerator = new DataGenerator();

    @FXML
    private PieChart chart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }






    public void loadChart(ActionEvent actionEvent) {
        CategoryAxis xAxis    = new CategoryAxis();
        xAxis.setLabel("Time");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Units produced");

        String filenameXLSX = "src/Resources/Excel_mockData.xlsx";
        String filenameCSV = "src/Resources/BarChart_mockData.csv";

        chart.setData(dataGenerator.getPieChartData(filenameCSV));


    }


}
