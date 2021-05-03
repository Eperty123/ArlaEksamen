package BLL;


import BE.CSVRowData;

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



    public XYChart.Series<String, Integer> getBarChart(){
        // TODO figure out filename
        String filename = "Filename";
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName(filename);

        CSVParser csvParser = new CSVParser();
        csvParser.loadFile("src/Resources/BarChart_mockData.csv");
        var csvData = csvParser.getParsedData();

        for(int i = 1; i < csvData.size(); i++){
            series.getData().add(new XYChart.Data(csvData.get(i)[0], Integer.parseInt(csvData.get(i)[1])));
        }
        return series;
    }









}






