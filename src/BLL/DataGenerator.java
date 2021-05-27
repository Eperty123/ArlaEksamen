package BLL;

import DAL.Parser.CSVParser;
import DAL.Parser.XLSXParser;
import GUI.Controller.PopupControllers.WarningController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;

import java.util.List;


public class DataGenerator {


    /**
     * This method creates a BarChart with String values on the x-axis, and Number values on the y-axis.
     *
     * @param filename file to be parsed by getParsedData() method.
     * @return BarChart
     */
    public static BarChart<String, Number> getBarChart(String filename) {

        final String seriesHeader = "Units Produced Pr. Hour";
        XYChart.Series<String, Number> series = DataGenerator.getXYSeriesStringNumber(DataGenerator.getParsedData(filename), seriesHeader);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(seriesHeader);
        barChart.getData().add(series);

        return barChart;
    }


    /**
     * Created and returns a PieChart using the getPieChartData() method.
     *
     * @param filepath file to create chart from.
     * @return PieChart - Department vs. Proficiency
     */
    public static PieChart getPieChart(String filepath) {

        PieChart pieChart = new PieChart(DataGenerator.getPieChartData(filepath));
        pieChart.setTitle("Department Proficiency");

        return pieChart;
    }

    /**
     * Method creates an list of PieChart data to instantiate a PieChart.
     *
     * @param filepath file to be parsed by getParsedData() method.
     * @return ObservableList of PieChart data.
     */
    private static ObservableList<PieChart.Data> getPieChartData(String filepath) {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        List<String[]> data = DataGenerator.getParsedData(filepath);

        for (int i = 1; i < data.size(); i++) {
            pieData.add(new PieChart.Data(data.get(i)[0], Integer.parseInt(data.get(i)[1])));
        }
        return pieData;
    }

    /**
     * This method creates a ScatterChart with String values on the x-axis, and Number values on the y-axis.
     *
     * @param filename file to be parsed by getParsedData() method.
     * @return ScatterChart
     */
    public static ScatterChart<String, Number> getScatterChart(String filename) {

        final String seriesHeader = "Temperature Pr. Hour";
        XYChart.Series<String, Number> series = DataGenerator.getXYSeriesStringNumber(DataGenerator.getParsedData(filename), seriesHeader);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        ScatterChart<String, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);

        // TODO: Add proper title.
        scatterChart.setTitle("Max Temperature");
        scatterChart.getData().add(series);

        return scatterChart;
    }

    /**
     * This method creates a BubbleChart with String values on the x-axis, and Number values on the y-axis.
     *
     * @param filename file to be parsed by getParsedData() method.
     * @return BubbleChart
     */
    public static BubbleChart<Number, Number> getBubbleChart(String filename) {

        final String seriesHeader = "Duration Per. Hour";
        XYChart.Series<Number, Number> series = DataGenerator.getXYSeriesNumberNumber(DataGenerator.getParsedData(filename), seriesHeader);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        BubbleChart<Number, Number> bubbleChart = new BubbleChart(xAxis, yAxis);

        // TODO: Add proper title.
        bubbleChart.setTitle("Total Duration");
        bubbleChart.getData().add(series);

        return bubbleChart;
    }

    /**
     * This method creates a AreaChart with String values on the x-axis, and Number values on the y-axis.
     *
     * @param filename file to be parsed by getParsedData() method.
     * @return AreaChart
     */
    public static AreaChart<String, Number> getAreaChart(String filename) {

        final String seriesHeader = "Sales Pr. Hour";
        XYChart.Series<String, Number> series = DataGenerator.getXYSeriesStringNumber(DataGenerator.getParsedData(filename), seriesHeader);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        AreaChart<String, Number> areaChart = new AreaChart<>(xAxis, yAxis);

        // TODO: Add proper title.
        areaChart.setTitle("Total Sales");
        areaChart.getData().add(series);

        return areaChart;
    }

    /**
     * This method creates a LineChart with String values on the x-axis, and Number values on the y-axis.
     *
     * @param filename file to be parsed by getParsedData() method.
     * @return LineChart
     */
    public static LineChart<String, Number> getLineChart(String filename) {

        final String seriesHeader = "Attendance pr. Day";
        XYChart.Series<String, Number> series = DataGenerator.getXYSeriesStringNumber(DataGenerator.getParsedData(filename), seriesHeader);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

        // TODO: Add proper title.
        lineChart.setTitle("Total Attendance");
        lineChart.getData().add(series);

        return lineChart;
    }

    /**
     * This method creates a StackedBarChart with String values on the x-axis, and Number values on the y-axis.
     *
     * @param filename file to be parsed by getParsedData() method.
     * @return StackedBarChart
     */
    public static StackedBarChart<String, Number> getStackedBarChart(String filename) {

        final String seriesHeader = "Sales Pr. Hour";
        XYChart.Series<String, Number> series = DataGenerator.getXYSeriesStringNumber(DataGenerator.getParsedData(filename), seriesHeader);

        // Hardcoded mock data to show.
        XYChart.Series<String, Number> series1 = DataGenerator.getXYSeriesStringNumber(DataGenerator.getParsedData("src/Resources/StackedBarChart_mockData1.csv"), seriesHeader + "1");
        XYChart.Series<String, Number> series2 = DataGenerator.getXYSeriesStringNumber(DataGenerator.getParsedData("src/Resources/StackedBarChart_mockData2.csv"), seriesHeader + "2");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<>(xAxis, yAxis);

        // TODO: Add proper title.
        stackedBarChart.setTitle("Total Sales");
        stackedBarChart.getData().add(series);
        stackedBarChart.getData().add(series1);
        stackedBarChart.getData().add(series2);

        return stackedBarChart;
    }

    /**
     * This method creates a StackedAreaChart with String values on the x-axis, and Number values on the y-axis.
     *
     * @param filename file to be parsed by getParsedData() method.
     * @return StackedAreaChart
     */
    public static StackedAreaChart<String, Number> getStackedAreaChart(String filename) {

        final String seriesHeader = "Sales Pr. Hour";
        XYChart.Series<String, Number> series = DataGenerator.getXYSeriesStringNumber(DataGenerator.getParsedData(filename), seriesHeader);

        // Hardcoded mock data to show.
        XYChart.Series<String, Number> series1 = DataGenerator.getXYSeriesStringNumber(DataGenerator.getParsedData("src/Resources/StackedAreaChart_mockData1.csv"), seriesHeader + "1");
        //XYChart.Series<String, Number> series2 = getXYSeriesStringNumber(getParsedData("src/Resources/StackedAreaChart_mockData2.csv"), seriesHeader + "2");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        StackedAreaChart<String, Number> stackedAreaChart = new StackedAreaChart<>(xAxis, yAxis);

        // TODO: Add proper title.
        stackedAreaChart.setTitle("Total Sales");
        stackedAreaChart.getData().add(series);
        stackedAreaChart.getData().add(series1);
        //stackedAreaChart.getData().add(series2);

        return stackedAreaChart;
    }


    /**
     * Returns a list of String[]'s containing row data from either .csv or .xlxs files.
     * It does this by calling the getCSVData or the getXLXSData depending on the file type,
     * which is determined by the isCSV() method.
     *
     * @param filepath file to be parsed
     * @return String[] with row data. [0] is x-axis info, [1] is y-axis info.
     */
    private static List<String[]> getParsedData(String filepath) {

        return DataGenerator.isCSV(filepath) ? DataGenerator.getCSVData(filepath) : DataGenerator.getXLXSData(filepath);
    }

    /**
     * Returns a list of String[]'s containing row data from the parsed .csv-file.
     *
     * @param filepath string containing filepath of the file.
     * @return
     */
    private static List<String[]> getCSVData(String filepath) {

        return CSVParser.parseFile(filepath).getParsedData();
    }

    /**
     * Returns a list of String[]'s containing row data from the parsed .xlsx-file.
     *
     * @param filepath string containing filepath of the file.
     * @return
     */
    private static List<String[]> getXLXSData(String filepath) {

        return XLSXParser.parseFile(filepath).getParsedData();
    }

    /**
     * Method determines if the file passed is a .csv, based on the file ending.
     *
     * @param filepath filename to be analyzed.
     * @return true if the filepath is ending with .csv, otherwise return is false.
     */
    private static boolean isCSV(String filepath) {
        String[] tokens = filepath.split("\\.");
        return tokens[tokens.length - 1].equalsIgnoreCase("csv");
    }

    /**
     * Method creates an XYChart.Series<String, Number>, which is the used by the getBarChart() method,
     * to create a BarChart.
     *
     * @param data a list of String[]'s with row data. [0] is x-axis info. [1] is y-axis info.
     * @return the XYChart.Series
     */
    private static XYChart.Series<String, Number> getXYSeriesStringNumber(List<String[]> data, String title) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        //series.setName("Units produced pr. Hour");
        series.setName(title);
        for (int i = 1; i < data.size(); i++) {
            try {
                series.getData().add(new XYChart.Data(data.get(i)[0], Double.parseDouble(data.get(i)[1])));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                WarningController.createWarning("Oh no! Something went wrong trying to create the chart data." +
                        " Please try again. If the problem persists, please contact an IT-Administrator");
            }
        }
        return series;
    }

    /**
     * Method creates an XYChart.Series<String, Number>, which is the used by the getBarChart() method,
     * to create a BarChart.
     *
     * @param data a list of String[]'s with row data. [0] is x-axis info. [1] is y-axis info.
     * @return the XYChart.Series
     */
    private static XYChart.Series<Number, Number> getXYSeriesNumberNumber(List<String[]> data, String title) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        //series.setName("Units produced pr. Hour");
        series.setName(title);
        for (int i = 1; i < data.size(); i++) {
            try {
                series.getData().add(new XYChart.Data(Double.parseDouble(data.get(i)[0]), Double.parseDouble(data.get(i)[1])));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                WarningController.createWarning("Oh no! Something went wrong trying to create the chart data." +
                        " Please try again. If the problem persists, please contact an IT-Administrator");
            }
        }
        return series;
    }
}






