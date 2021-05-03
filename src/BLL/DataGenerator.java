package BLL;

import BE.CSVColumnData;
import BE.CSVData;
import BE.IColumnData;
import DAL.Parser.CSVParser;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.Chart;
import javafx.scene.chart.XYChart;

public class DataGenerator {



    // x = time
    // y = enheder pr. time




    public XYChart.Series<Integer, Integer> getBarChart(){
        String filename = "Filename";
        XYChart.Series<Integer, Integer> series = new XYChart.Series<>();
        series.setName(filename);

        CSVParser csvParser = new CSVParser();
        csvParser.loadFile("src/Resources/BarChart_mockData.csv");
        var csvData = csvParser.getParsedData();


        int xAxisValue = 0;
        int yAxisValue = 0;
        for(IColumnData column : csvData.getAllColumnData()) {
            if(column.getColumnName().equals("Time")){
                xAxisValue = Integer.parseInt(column.getColumnValue());
            } else{
                yAxisValue = Integer.parseInt(column.getColumnValue());
                series.getData().add(new XYChart.Data<>(xAxisValue,yAxisValue));
            }
        }
        return series;
    }








    public static void main(String[] args) {



        CSVParser csvParser = new CSVParser();
        csvParser.loadFile("src/Resources/BarChart_mockData.csv");
        var csvData = csvParser.getParsedData();

        for(IColumnData column : csvData.getAllColumnData()) {

            System.out.println(column.getColumnName());
            System.out.println(column.getColumnValue());

        }


    }



}
