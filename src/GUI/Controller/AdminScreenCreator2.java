package GUI.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminScreenCreator2 implements Initializable {
    @FXML
    private GridPane grid;
    @FXML
    private ChoiceBox<String> choice1;
    @FXML
    private ChoiceBox<String> choice2;
    @FXML
    private ChoiceBox<Integer> choiceSkærm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> skærmMængde = FXCollections.observableArrayList();
        skærmMængde.addAll(1,2,3,4);

        ObservableList<String> arrayList = FXCollections.observableArrayList();
        arrayList.addAll("Bar Chart","Pie Chart","Area Chart","Bubble Chart","Website");

        choice1.setItems(arrayList);
        choice2.setItems(arrayList);

        choice1.getSelectionModel().selectFirst();
        choice2.getSelectionModel().selectFirst();

        choiceSkærm.setItems(skærmMængde);
        choiceSkærm.getSelectionModel().selectFirst();
    }
}
