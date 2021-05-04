package BLL;

import DAL.Parser.CSVParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.List;


public class DataGenerator {


    public XYChart.Series<String, Integer> getBarChartSeries(){

        // TODO figure out filename
        String filename = "Filename";
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName(filename);

        List<String[]> csvData = getCSVDate(filename);

        for(int i = 1; i < csvData.size(); i++){
            series.getData().add(new XYChart.Data(csvData.get(i)[0], Integer.parseInt(csvData.get(i)[1])));
        }
        return series;
    }




    public ObservableList<PieChart.Data> getPieChartData(){

        String filename = "Filename";
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        List<String[]> csvData = getCSVDate(filename);

        for(int i = 1; i < csvData.size(); i++){
            pieData.add(new PieChart.Data(csvData.get(i)[0], Integer.parseInt(csvData.get(i)[1])));
        }
        return pieData;
    }





    private List<String[]> getCSVDate(String filename) {
        // TODO implement filename
        CSVParser csvParser = new CSVParser();
        csvParser.loadFile("src/Resources/BarChart_mockData.csv");
        return csvParser.getParsedData();
    }






}






