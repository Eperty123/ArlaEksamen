package GUI.Controller.AdminControllers;

import BLL.DataGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class DataManagementController implements Initializable {
    public AnchorPane root;
    @FXML
    private TextField textField;
    @FXML
    private ComboBox<ViewType> comboBox;
    @FXML
    private Button pickFile;
    @FXML
    private BorderPane pane;
    private AtomicReference<File> file = new AtomicReference<>();
    private Stage stage;
    private PickerStageController pickerStageController;
    private Node previousNode;
    private DataGenerator dataGenerator = new DataGenerator();

    public void setPickerStageController(PickerStageController pickerStageController) {
        this.pickerStageController = pickerStageController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        previousNode = pickerStageController.getContent();

        pickFile.setOnAction((v) -> {
            FileChooser fileChooser = new FileChooser();
            file.set(fileChooser.showOpenDialog(stage));
            if (file.get() != null) {
                textField.setText(file.get().getAbsolutePath());
                tryToMakeContent();
            }
        });
        comboBox.setOnAction((v) -> {
            tryToMakeContent();
        });

        textField.textProperty().addListener((observable -> tryToMakeContent()));

    }

    private void tryToMakeContent() {
        try {
            if (comboBox.getSelectionModel().getSelectedItem() != null && file.get() != null) {
                switch (comboBox.getSelectionModel().getSelectedItem()) {
                    case BarChart -> {
                        pickerStageController.setContent(ViewMaker.getBarChart(file.get()));
                    }
                    case PieChart -> {
                        pickerStageController.setContent(ViewMaker.getPieChart(file.get()));
                    }
                    case HTTP -> {
                        pickerStageController.setContent(ViewMaker.getHTTP(file.get()));
                    }
                    case Image -> {
                        pickerStageController.setContent(ViewMaker.getImage(file.get()));
                    }
                    default -> System.out.println("Option has not been implemented");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (ViewType viewType : ViewType.values())
            comboBox.getItems().add(viewType);
    }

    public void confirm(ActionEvent event) {
        stage.close();
    }

    public void cancel(ActionEvent event) {
        pickerStageController.setContent(previousNode, null);
        stage.close();
    }
}
