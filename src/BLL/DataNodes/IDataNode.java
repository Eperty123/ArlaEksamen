package BLL.DataNodes;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.FileNotFoundException;

public interface IDataNode {
    Node getData(BorderPane pane, File file) throws FileNotFoundException;
    Node getData(BorderPane pane, String uri) throws FileNotFoundException;
}
