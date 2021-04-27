package GUI.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminScreenCreator implements Initializable {
    @FXML
    private GridPane grid;
    @FXML
    private ChoiceBox<String> choice1;
    @FXML
    private ChoiceBox<String> choice2;
    @FXML
    private ChoiceBox<String> choice3;
    @FXML
    private ChoiceBox<String> choice4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> arrayList = FXCollections.observableArrayList();
        arrayList.add("Bar Chart");
        arrayList.add("Pie Chart");
        arrayList.add("Area Chart");
        arrayList.add("Bubble Chart");
        arrayList.add("Website");

        choice1.setItems(arrayList);
        choice2.setItems(arrayList);
        choice3.setItems(arrayList);
        choice4.setItems(arrayList);

        choice1.getSelectionModel().selectFirst();
        choice2.getSelectionModel().selectFirst();
        choice3.getSelectionModel().selectFirst();
        choice4.getSelectionModel().selectFirst();

    }
}
