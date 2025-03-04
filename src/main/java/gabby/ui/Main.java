package gabby.ui;

import java.io.IOException;

import gabby.Gabby;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Gabby using FXML.
 */
public class Main extends Application {
    private final Gabby gabby = new Gabby("data/tasks.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            fxmlLoader.<MainWindow>getController().setGabby(this.gabby); // inject the Gabby instance

            Scene scene = new Scene(ap);
            stage.setTitle(Gabby.NAME);
            stage.getIcons().add(new Image("images/Gabby-logo.png"));
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
