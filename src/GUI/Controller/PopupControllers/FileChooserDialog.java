package GUI.Controller.PopupControllers;

import javafx.stage.FileChooser;

import java.util.List;

public class FileChooserDialog {
    public static FileChooser createFileChooser(FileChooser.ExtensionFilter filter) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(filter);
        return fileChooser;
    }

    public static FileChooser createFileChooser(List<FileChooser.ExtensionFilter> filters) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(filters);
        return fileChooser;
    }
}
