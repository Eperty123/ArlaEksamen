package BLL;

import BLL.DataNodes.*;
import GUI.Controller.AdminControllers.PickerStageController;

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
        if (path.contains("\\Resources\\"))
            path = "src\\" + path.substring(path.indexOf("\\Resources\\"));
        file = new File(path);
        switch (viewType) {
            case HTTP, PDF -> iDataNode = new HTTPNode();
            case BarChart -> iDataNode = new BarChartNode();
            case PieChart -> iDataNode = new PieChartNode();
            case ScatterChart -> iDataNode = new ScatterChartNode();
            case LineChart -> iDataNode = new LineChartNode();
            case AreaChart -> iDataNode = new AreaChartNode();
            case BubbleChart -> iDataNode = new BubbleChartNode();
            case StackedBubbleChart -> iDataNode = new StackedBarChartNode();
            case StackedAreaChart -> iDataNode = new StackedAreaChartNode();
            case Image -> iDataNode = new ImageNode();
            default -> {
                throw new Exception("Option not implemented yet.");
            }
        }
        if (iDataNode != null)
            pickerStageController.setContent(iDataNode.getData(pickerStageController.getRoot(), file));
    }


}
