package BLL;

import BE.CSVColumnData;
import BE.CSVData;
import BE.CSVRowData;
import BE.IColumnData;
import DAL.Parser.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.Chart;
import javafx.scene.chart.XYChart;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
        /*for(IColumnData column : csvData.getAllColumnData().values()) {
            if(column.getColumnName().equals("Time")){
                xAxisValue = Integer.parseInt(column.getColumnValue());
            } else{
                yAxisValue = Integer.parseInt(column.getColumnValue());
                series.getData().add(new XYChart.Data<>(xAxisValue,yAxisValue));
            }
        }

         */
        return series;


    }








    public static void main(String[] args) throws IOException, CsvException {

        FileReader fileReader = new FileReader("src/Resources/BarChart_mockData.csv");
        com.opencsv.CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader csvReader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();
        List<String[]> allData = csvReader.readAll();

        for(int i = 0; i < allData.size(); i++){
            for(int j = 0; j < allData.get(i).length; j++){
                System.out.print(allData.get(i)[j] +" ");
            }
            System.out.println();
        }

    }

}






