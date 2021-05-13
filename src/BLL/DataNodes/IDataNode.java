package BLL.DataNodes;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.File;

public interface IDataNode {
    Node getData(BorderPane pane, File file);
}
