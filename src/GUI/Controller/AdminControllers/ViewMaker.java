package GUI.Controller.AdminControllers;

import BLL.DataGenerator;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.image.ImageView;

import java.io.File;

public class ViewMaker {

//TODO datagenerator does not give the right data
    public static Node getBarChart(File file) {
        Axis<String> xAxis = new CategoryAxis();
        xAxis.setLabel("xAxis");
        Axis<Number> yAxis = new NumberAxis();
        yAxis.setLabel("yAxis");
        BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        XYChart.Series series = DataGenerator.getBarChartSeries(file.getAbsolutePath());
        bc.getData().add(series);
        bc.setAccessibleText(ViewType.BarChart.name() + String.format("=\"%s\"", file.getAbsolutePath()));

        return bc;
    }

    //TODO Piechar does not get the right data
    public static Node getPieChart(File file) {
        PieChart pieChart = new PieChart();
        DataGenerator.getPieChartData(file.getAbsolutePath()).forEach(d -> pieChart.getData().add(new PieChart.Data(d.getName(), d.getPieValue())));
        pieChart.setAccessibleText(ViewType.PieChart.name() + String.format("=\"%s\"", file.getAbsolutePath()));
        return pieChart;
    }

    //TODO implement this
    public static Node getHTTP(File file){
        return null;
    }

    public static Node getImage(File file){
        ImageView imageView = new ImageView(String.format("file:/%s",file.getAbsolutePath()));
        imageView.setAccessibleText(ViewType.Image + String.format("=\"%s\"", file.getAbsolutePath()));
        return  imageView;
    }
}
