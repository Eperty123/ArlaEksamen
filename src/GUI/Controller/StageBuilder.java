package GUI.Controller;

import GUI.Controller.AdminControllers.ViewMaker;
import GUI.Controller.AdminControllers.ViewType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import GUI.Controller.AdminControllers.PickerStageController;

public class StageBuilder {
    private PickerStageController rootController;
    private Node node;

    public StageBuilder() {

    }

    public PickerStageController getRootController() {
        return rootController;
    }

    /**
     * Makes a node of the given builderString
     *
     * @param builderString the string that defines the stage
     * @return A node that contains the result of the builderString
     * @throws IOException if the FXML file is somehow invalid or the stage is already loaded.
     */
    public Node makeStage(String builderString) throws Exception {
        builderString = builderString.replaceAll("\n", "").replaceAll("\r", "");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/AdminViews/PickerStage.fxml"));
        node = loader.load();
        rootController = loader.getController();
        return makeStage(rootController, builderString);
    }

    /**
     * This method checks that the given string follows the proper pattern, and then it splits the stages up if that is
     * what the builder string determines it should do. This works recursively such that the outer stage tells the inner
     * stage how the inner stage should be made, and then the inner stage tells the inner inner stage how it should be made
     * and so on.
     *
     * @param pickerStageController The controller of the current stage
     * @param builderString         The builderString that defines what needs to be made
     * @return the Parent node of all stages
     */
    private Node makeStage(PickerStageController pickerStageController, String builderString) {
        //Ensures the builderString follows the pattern below which is the first letter of the orientation, and a double less than one with two decimal points
        pickerStageController.setParentPickerStageController(rootController);
        String pickerPattern = "^[HV][01]\\.\\d\\d";
        if (builderString.length() >= 5 && Pattern.matches(pickerPattern, builderString.substring(0, 5))) {
            //At this stage we pull the orientation and Devider position from the builderString, split the stage and cut these parts off the builderString
            pickerStageController.split(getOrientation(builderString));
            builderString = builderString.substring(1);
            double dividerPoint = Double.parseDouble(builderString.substring(0, 3));
            pickerStageController.getSplitPane().setDividerPosition(0, dividerPoint);
            builderString = builderString.substring(4);
            builderString = builderString.substring(1, builderString.length() - 1);
            //if the builderString is now empty we are done
            if (!builderString.isEmpty()) {
                splitToSeparateControllers(pickerStageController, builderString, pickerPattern);
            }
        } else {
            makeView(pickerStageController, builderString);
        }
        return node;
    }

    private void splitToSeparateControllers(PickerStageController pickerStageController, String builderString, String pickerPattern) {
        //otherwise we send the leftover builderString to the newly made PickerStageControllers from the PickerStageController.split method
        String[] builderStrings = {builderString.substring(0, findEndBracket(builderString, '{', '}') + 1), builderString.substring(findEndBracket(builderString, '{', '}') + 1)};
        if (!builderString.contains("{") && builderString.split("\\|").length == 2) {
            builderStrings = builderString.split("\\|");
        }
        for (PickerStageController pickerStageController1 : pickerStageController.getControllers()) {
            //Checks that the string is split by "|"
            //This splits the sting up properly given the left and right bracket. it uses findEndBracket
            // method to find the point that the current builderString ends and then splits it
            //up between the two PickerStageController
            int index = pickerStageController.getControllers().indexOf(pickerStageController1);
            //Making sure that if teh string starts with a splitter if just cuts it off, again
            if (builderStrings.length >= 2 && builderStrings[index].startsWith("|") && index == 0) {
                pickerStageController1.flipSplitPane();
                builderStrings[index] = builderStrings[index].substring(1);
            }
            makeStage(pickerStageController1, builderStrings[index]);
        }
    }

    /**
     * Separates the currentString to a path, and a viewType, and calls the ViewMaker which will change the content of the PickerStageController
     */
    private void makeView(PickerStageController pickerStageController, String currentString) {
        String path = currentString.split("=\"")[1].substring(0, currentString.split("=\"")[1].indexOf("\""));
        File file = new File(path);
        String viewType = currentString.split("=\"")[0];
        ViewMaker.callProperMethod(pickerStageController, viewType, file);
    }

    /**
     * Turns the first letter of an orientation into an orientation
     *
     * @param builderString the builderString
     * @return the orientation
     */
    private Orientation getOrientation(String builderString) {
        Orientation orientation = builderString.charAt(0) == 'V' ? Orientation.VERTICAL : Orientation.HORIZONTAL;
        return orientation;
    }

    /**
     * It finds the appropriate endBracket for the first start bracket
     *
     * @param string the string of which you want to find the end bracket
     * @param start  the start bracket
     * @param end    the end bracket
     * @return the index of the end bracket or -1 if none was found.
     */
    private int findEndBracket(String string, char start, char end) {
        int count = 0;
        boolean hasFoundStartBracket = false;
        for (int i = 0; i < string.length(); i++) {
            if (string.toCharArray()[i] == start) {
                count++;
                hasFoundStartBracket = true;
            } else if (string.toCharArray()[i] == end)
                count--;
            if (count == 0 && hasFoundStartBracket)
                return i;
        }
        return -1;
    }
}

