package GUI.Controller.AdminControllers;

import BLL.DataGenerator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class DataManagementController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private FontAwesomeIconView leftBtn;
    @FXML
    private FontAwesomeIconView rightBtn;
    @FXML
    private BorderPane bar;
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
    private ViewType selectedItem;

    public BorderPane getBar() {
        return bar;
    }

    public FontAwesomeIconView getLeftBtn() {
        return leftBtn;
    }

    public FontAwesomeIconView getRightBtn() {
        return rightBtn;
    }

    /**
     * Sets the current pickerStageController
     *
     * @param pickerStageController
     */
    public void setPickerStageController(PickerStageController pickerStageController) {
        this.pickerStageController = pickerStageController;
    }

    /**
     * Sets the window in the PickerStageController to the given Stage
     *
     * @param stage the owner of this Window
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        previousNode = pickerStageController.getContent();
        FileChooser fileChooser = new FileChooser();
        
        // We need a selection from the combo box first.
        comboBox.setOnAction((v) -> {
            fileChooser.getExtensionFilters().clear();
            var pdfExtension = new FileChooser.ExtensionFilter("Pdf file", "*.pdf");
            var htmlExtension = new FileChooser.ExtensionFilter("Html page", "*.html");
            var jpgExtension = new FileChooser.ExtensionFilter("Jpg file", "*.jpg");
            var pngExtension = new FileChooser.ExtensionFilter("Png file", "*.png");

            var csvExtension = new FileChooser.ExtensionFilter("Csv file", "*.csv");
            var excelExtension = new FileChooser.ExtensionFilter("Excel file", "*.xlsx");

            // Now let's add some extension based on the selected item.
            var viewTypeSelected = ViewType.valueOf(comboBox.getSelectionModel().getSelectedItem().toString());
            switch (viewTypeSelected) {
                case HTTP, PDF -> {
                    fileChooser.getExtensionFilters().addAll(htmlExtension, pdfExtension);
                }
                case Image -> {
                    fileChooser.getExtensionFilters().addAll(jpgExtension, pngExtension);
                }
                case BarChart, PieChart -> {
                    fileChooser.getExtensionFilters().addAll(csvExtension, excelExtension);
                }
            }
            //tryToMakeContent();
            selectedItem = viewTypeSelected;
        });

        pickFile.setOnAction((v) -> {
            if (selectedItem != null) {
                file.set(fileChooser.showOpenDialog(stage));
                if (file.get() != null) {
                    textField.setText(file.get().getAbsolutePath());
                    tryToMakeContent();
                }
            } else System.out.println("Please select a file first.");
        });

        textField.textProperty().addListener((observable -> tryToMakeContent()));

    }

    /**
     * Tries to use the selected Item to make the given file
     */
    private void tryToMakeContent() {
        if (comboBox.getSelectionModel().getSelectedItem() != null && file.get() != null) {
            ViewMaker.callProperMethod(pickerStageController, selectedItem, file.get());
        }
    }

    /**
     * Adds all the ViewTypes to the comboBox
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (ViewType viewType : ViewType.values())
            comboBox.getItems().add(viewType);
    }

    /**
     * Closes the stage
     *
     * @param event
     */
    public void confirm(ActionEvent event) {
        stage.close();
    }

    /**
     * Changes the pickerStageController Back to its original state and closes the stage.
     *
     * @param event
     */
    public void cancel(ActionEvent event) {
        pickerStageController.setContent(previousNode, null);
        stage.close();
    }
}
