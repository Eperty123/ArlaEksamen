package GUI.Controller.PopupControllers;

import BE.ScreenBit;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;

import java.util.List;

public class EmployeeScreenSelectController {
    @FXML
    private JFXComboBox<ScreenBit> comboBox;

    public void setData(List<ScreenBit> screenBits){
        comboBox.getItems().addAll(screenBits);
        comboBox.getSelectionModel().selectFirst();
    }

    public ScreenBit handleContinue(){
        return comboBox.getValue();
    }

    public ScreenBit handleCancel(){
        return null;
    }

}
