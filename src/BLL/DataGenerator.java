package BLL;

import DAL.Parser.CSVParser;
import DAL.Parser.XLSXParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.List;


public class DataGenerator {


    public XYChart.Series<String, Integer> getBarChartSeries(String filename){
        
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("BarChart");

        List<String[]> data = getParsedData(filename);

        for(int i = 1; i < data.size(); i++){
            series.getData().add(new XYChart.Data(data.get(i)[0], Integer.parseInt(data.get(i)[1])));
        }
        return series;
    }

    public ObservableList<PieChart.Data> getPieChartData(String filename){

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        List<String[]> data = getParsedData(filename);

        for(int i = 1; i < data.size(); i++){
            pieData.add(new PieChart.Data(data.get(i)[0], Integer.parseInt(data.get(i)[1])));
        }
        return pieData;
    }


    private List<String[]> getParsedData(String filename) {

        return isCSV(filename) ? getCSVData(filename) : getXLXSData(filename);
    }

    private List<String[]> getCSVData(String filename) {

        return CSVParser.parseFile(filename).getParsedData();
    }

    private List<String[]> getXLXSData(String filename){
        return XLSXParser.parseFile(filename).getParsedData();
    }

    private boolean isCSV(String filename){
        String[] tokens = filename.split("\\.");
        return tokens[tokens.length-1].equalsIgnoreCase("csv");
    }

}






