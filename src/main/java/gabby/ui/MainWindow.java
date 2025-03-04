package gabby.ui;

import gabby.Gabby;
import javafx.application.Platform;
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
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/Woody.jpeg"));
    private final Image gabbyImage = new Image(this.getClass().getResourceAsStream("/images/Gabby.png"));
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    private Gabby gabby;

    /**
     * Initializes the main window.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Greet the user
        this.dialogContainer.getChildren().add(
                DialogBox.getGabbyDialog(
                        "Fancy seeing you here! What can I do for you?", this.gabbyImage, false, "")
        );
    }

    /**
     * Injects the Gabby instance
     */
    public void setGabby(Gabby gabby) {
        this.gabby = gabby;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Gabby's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = this.userInput.getText().strip();
        if (input.isBlank()) {
            return;
        }

        String response = this.gabby.getResponse(input);
        String commandType = this.gabby.getCommandType();
        this.dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, this.userImage),
                DialogBox.getGabbyDialog(response, this.gabbyImage, this.gabby.hasEncounteredError(), commandType)
        );
        this.userInput.clear();

        if (commandType.equals("ByeCommand")) {
            Platform.exit();
        }
    }
}
