package gabby.ui;

import gabby.Gabby;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Gabby gabby;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Chad.gif"));
    private Image gabbyImage = new Image(this.getClass().getResourceAsStream("/images/Skibidi.gif"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Gabby instance */
    public void setGabby(Gabby gabby) {
        this.gabby = gabby;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Gabby's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = this.userInput.getText();
        String response = this.gabby.getResponse(input);
        this.dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getGabbyDialog(response, gabbyImage)
        );
        userInput.clear();
    }
}
