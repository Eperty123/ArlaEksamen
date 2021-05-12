package BLL;

import BLL.DataGenerator;
import BLL.DataNodes.*;
import GUI.Controller.AdminControllers.PickerStageController;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import java.io.File;

public class ViewMaker {

    /**
     * Switches between the different ViewTypes to call the right IDataNode
     *
     * @param pickerStageController the controller
     * @param viewType              The ViewType you want
     * @param file                  the file with our markdown
     */
    public static void callProperMethod(PickerStageController pickerStageController, ViewType viewType, File file) throws Exception {
        IDataNode iDataNode;
        String path = file.getAbsolutePath();
        if(path.contains("\\Resources\\"))
            path= "src\\" + path.substring(path.indexOf("\\Resources\\"));
        file = new File(path);
        switch (viewType) {
            case HTTP, PDF -> {
                iDataNode = new HTTPNode();
            }
            case BarChart -> {
                iDataNode = new BarChartNode();
            }
            case PieChart -> {
                iDataNode = new PieChartNode();
            }
            case Image -> {
                iDataNode = new ImageNode();
            }
            default -> {
                throw new Exception("Option not implemented yet");
            }
        }
        if(iDataNode!=null)
        pickerStageController.setContent(iDataNode.getData(pickerStageController.getRoot(), file));
    }


}
