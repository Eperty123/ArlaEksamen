package BLL;

import DAL.Parser.CSVParser;
import DAL.Parser.XLSXParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;

import java.util.List;


public class DataGenerator {


    public static BarChart getBarChart(String filename){

        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Units produced pr. Hour");

        List<String[]> data = getParsedData(filename);

        for(int i = 1; i < data.size(); i++){
            try {
                series.getData().add(new XYChart.Data(data.get(i)[0], Integer.parseInt(data.get(i)[1])));
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
            }
        }

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle("Units Produced Pr. Hour");
        barChart.getData().add(series);

        return barChart;
    }

    public static PieChart getPieChartData(String filename){

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        List<String[]> data = getParsedData(filename);

        for(int i = 1; i < data.size(); i++){
            pieData.add(new PieChart.Data(data.get(i)[0], Integer.parseInt(data.get(i)[1])));
        }

        PieChart pieChart = new PieChart(pieData);
        pieChart.setTitle("Department Proficiency");

        return pieChart;
    }


    private static List<String[]> getParsedData(String filename) {

        return isCSV(filename) ? getCSVData(filename) : getXLXSData(filename);
    }

    private static List<String[]> getCSVData(String filename) {

        return CSVParser.parseFile(filename).getParsedData();
    }

    private static List<String[]> getXLXSData(String filename){

        return XLSXParser.parseFile(filename).getParsedData();
    }

    private static boolean isCSV(String filename){
        String[] tokens = filename.split("\\.");
        return tokens[tokens.length-1].equalsIgnoreCase("csv");
    }

    /*
    public static void main(String[] args) {
        DataGenerator dataGenerator = new DataGenerator();

        String filename = "src/Resources/BarChart_mockData.csv";
        List<String[]> data = dataGenerator.getParsedData(filename);

        for (String[] datum : data) {
            System.out.println(datum[0] + " " + datum[1]);
        }
    }
     */

}






