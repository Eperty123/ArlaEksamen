package BLL;

import DAL.Parser.CSVParser;
import DAL.Parser.XLSXParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;

import java.util.List;


public class DataGenerator {


    /**
     * This method creates a BarChart with String values on the x-axis, and Number values on the y-axis.
     * @param filename file to be parsed by getParsedData() method.
     * @return BarChart
     */
    public static BarChart<String, Number> getBarChart(String filename){

        XYChart.Series<String, Number> series = getXYSeries(getParsedData(filename));

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle("Units Produced Pr. Hour");
        barChart.getData().add(series);

        return barChart;
    }



    /**
     * Created and returns a PieChart using the getPieChartData() method.
     * @param filepath file to create chart from.
     * @return PieChart - Department vs. Proficiency
     */
    public static PieChart getPieChart(String filepath){

        PieChart pieChart = new PieChart(getPieChartData(filepath));
        pieChart.setTitle("Department Proficiency");

        return pieChart;
    }

    /**
     * Method creates an list of PieChart data to instantiate a PieChart.
     * @param filepath file to be parsed by getParsedData() method.
     * @return ObservableList of PieChart data.
     */
    private static ObservableList<PieChart.Data> getPieChartData(String filepath) {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        List<String[]> data = getParsedData(filepath);

        for(int i = 1; i < data.size(); i++){
            pieData.add(new PieChart.Data(data.get(i)[0], Integer.parseInt(data.get(i)[1])));
        }
        return pieData;
    }

    /**
     * Returns a list of String[]'s containing row data from either .csv or .xlxs files.
     * It does this by calling the getCSVData or the getXLXSData depending on the file type,
     * which is determined by the isCSV() method.
     * @param filepath file to be parsed
     * @return String[] with row data. [0] is x-axis info, [1] is y-axis info.
     */
    private static List<String[]> getParsedData(String filepath) {

        return isCSV(filepath) ? getCSVData(filepath) : getXLXSData(filepath);
    }

    /**
     * Returns a list of String[]'s containing row data from the parsed .csv-file.
     * @param filepath string containing filepath of the file.
     * @return
     */
    private static List<String[]> getCSVData(String filepath) {

        return CSVParser.parseFile(filepath).getParsedData();
    }

    /**
     * Returns a list of String[]'s containing row data from the parsed .xlsx-file.
     * @param filepath string containing filepath of the file.
     * @return
     */
    private static List<String[]> getXLXSData(String filepath){

        return XLSXParser.parseFile(filepath).getParsedData();
    }

    /**
     * Method determines if the file passed is a .csv, based on the file ending.
     * @param filepath filename to be analyzed.
     * @return true if the filepath is ending with .csv, otherwise return is false.
     */
    private static boolean isCSV(String filepath){
        String[] tokens = filepath.split("\\.");
        return tokens[tokens.length-1].equalsIgnoreCase("csv");
    }

    /**
     * Method creates an XYChart.Series<String, Number>, which is the used by the getBarChart() method,
     * to create a BarChart.
     * @param data a list of String[]'s with row data. [0] is x-axis info. [1] is y-axis info.
     * @return the XYChart.Series
     */
    private static XYChart.Series<String, Number> getXYSeries(List<String[]> data) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Units produced pr. Hour");
        for(int i = 1; i < data.size(); i++){
            try {
                series.getData().add(new XYChart.Data(data.get(i)[0], Integer.parseInt(data.get(i)[1])));
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
            }
        }
        return series;
    }


}






