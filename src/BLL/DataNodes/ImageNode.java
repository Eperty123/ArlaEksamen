package BLL.DataNodes;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;

public class ImageNode implements IDataNode {
    /**
     * Adds a Image to the view
     *
     * @param pane The PickerStage's BorderPane
     * @param file the file of the image
     * @return A Node with the given Image
     */
    @Override
    public Node getData(BorderPane pane, File file) {
        ImageView imageView = new ImageView(new Image("file:/" + file.getAbsolutePath(), true));
        imageView.setPreserveRatio(true);

        //Makes it follow the panes width
        pane.widthProperty().addListener((observableValue, bounds, t1) -> {
            imageView.setFitWidth(t1.doubleValue());
        });

        //Makes it follow the panes height
        pane.heightProperty().addListener((observableValue, bounds, t1) -> {
            imageView.setFitHeight(t1.doubleValue());
        });

        //sets initial height and width
        imageView.setFitWidth(pane.getWidth());
        imageView.setFitHeight(pane.getHeight());

        //imageView.setAccessibleText(ViewType.Image + String.format("=\"%s%s\"","file:/", file.getAbsolutePath()));
        imageView.setAccessibleText(ViewType.Image.name() + String.format("=\"%s\"", file.getAbsolutePath()));
        return imageView;
    }

    @Override
    public Node getData(BorderPane pane, String uri) {
        return this.getData(pane, new File(uri));
    }
}
