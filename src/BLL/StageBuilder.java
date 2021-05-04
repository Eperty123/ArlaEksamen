package BLL;
import GUI.Controller.AdminControllers.PickerStageController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.regex.Pattern;

public class StageBuilder {
    private PickerStageController rootController;
    private FXMLLoader loader = new FXMLLoader(getClass().getResource("PickerStage.fxml"));
    private Node node;

    public StageBuilder() {
        try {
            node = loader.load();
            rootController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PickerStageController getRootController() {
        return rootController;
    }

    public Node makeStage(String builderString) throws IOException {
        return makeStage(rootController, builderString);
    }

    private Node makeStage(PickerStageController pickerStageController, String builderString) {
        if (builderString.startsWith("|"))
            builderString = builderString.substring(1);
        if (!builderString.isEmpty()) {
            String pickerPattern = "^[HV]\\d\\.\\d\\d";
            if (builderString.length() >= 5 && Pattern.matches(pickerPattern, builderString.substring(0, 5))) {
                pickerStageController.split(getOrientation(builderString));
                builderString = builderString.substring(1);
                double dividerPoint = Double.parseDouble(builderString.substring(0, 3));
                pickerStageController.getSplitPane().setDividerPosition(0, dividerPoint);
                builderString = builderString.substring(4);
                builderString = builderString.substring(1, builderString.length() - 1);
                if (!builderString.isEmpty()) {
                    for (PickerStageController pickerStageController1 : pickerStageController.getControllers()) {
                        if (builderString.contains("|")) {
                            String[] builderStrings = {builderString.substring(0, findEndBracket(builderString, '{', '}')+1), builderString.substring(findEndBracket(builderString, '{', '}') + 2)};
                            int index = pickerStageController.getControllers().indexOf(pickerStageController1);
                            if (builderStrings[index].startsWith("|"))
                                builderStrings[index] = builderStrings[index].substring(1);
                            if (builderStrings[index].length() > 5 && Pattern.matches(pickerPattern, builderStrings[index].substring(0, 5))) {
                                makeStage(pickerStageController1, builderStrings[index]);
                            }else if(!builderStrings[index].isEmpty()){
                                pickerStageController1.setContent(new ImageView("com/company/imgs/KO.jpg"));
                            }
                        }
                    }
                }
            }
        }
        return node;
    }

    private Orientation getOrientation(String builderString) {
        Orientation orientation = builderString.charAt(0) == 'V' ? Orientation.VERTICAL : Orientation.HORIZONTAL;
        return orientation;
    }

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

