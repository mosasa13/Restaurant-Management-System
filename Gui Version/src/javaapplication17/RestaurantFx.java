package javaapplication17;

import javafx.geometry.HPos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.geometry.Pos;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Optional;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;

public class RestaurantFx extends Application {

    @Override

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Restaurant Management System");

        // Create a GridPane for layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(40, 40, 40, 40));
        // Set the background image
        String backgroundImagePath = "file:///C:\\Users\\mosta\\Desktop\\WhatsApp Image 2024-01-14 at 17.52.03_c090b0c2.jpg";
        Image backgroundImage = new Image(backgroundImagePath);
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        grid.setBackground(new Background(background));

        Hyperlink offersLink = new Hyperlink("Offers");
        Hyperlink informationLink = new Hyperlink("Information");

        // Handle the actions when hyperlinks are clicked
        offersLink.setOnAction(event -> showOffersWindow());
        informationLink.setOnAction(event -> showInformationWindow());

        // Add hyperlinks to an HBox at the top-right corner
        HBox hyperlinksHBox = new HBox(10, offersLink, informationLink);
        hyperlinksHBox.setAlignment(Pos.TOP_RIGHT);
        hyperlinksHBox.setStyle("-fx-font-size: 11; -fx-font-weight: bold; -fx-text-fill: #B8860B;");

        // Add the Craving Something? label above the buttons
        Label cravingLabel = new Label("Craving Something?");
        cravingLabel.setStyle("-fx-font-size: 25; -fx-font-weight: bold; -fx-text-fill: #B8860B;"); // Dark yellow color

        // Add labels and text fields for username and password in a GridPane
        GridPane textFieldsGrid = new GridPane();
        textFieldsGrid.setHgap(10);
        textFieldsGrid.setVgap(10);

        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        usernameLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: black;"); // Dark yellow color
        passwordLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: black;"); // Dark yellow color
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        usernameField.setPromptText("Enter username here");
        passwordField.setPromptText("Enter password here");
        usernameField.setPrefWidth(200); // Set the preferred width
        passwordField.setPrefWidth(200);

        textFieldsGrid.add(usernameLabel, 0, 0);
        textFieldsGrid.add(usernameField, 1, 0);
        textFieldsGrid.add(passwordLabel, 0, 1);
        textFieldsGrid.add(passwordField, 1, 1);

        // Add the login and sign-up buttons in an HBox
        Button loginButton = new Button("Login");
        Hyperlink signUpButton = new Hyperlink("Sign Up");

        loginButton.setOnAction(event -> {
            // Get the entered values from the text fields
            String enteredUsername = usernameField.getText();
            String enteredPassword = passwordField.getText();

            // Validate the entered username and password
            Person2.login(enteredUsername, enteredPassword);
        });
        signUpButton.setStyle("-fx-font-size: 11; -fx-font-weight: bold; -fx-text-fill: #B8860B;");

        signUpButton.setOnAction(event -> showSignUpDialog());

        // Set the button types (OK and Cancel)
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        signUpButton.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white;");
        HBox buttonsHBox = new HBox(10, loginButton, signUpButton);
        buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
        // Add all components to a VBox
        VBox root = new VBox(20, cravingLabel, textFieldsGrid, buttonsHBox);

        // Add the VBox to the grid
        grid.add(root, 10, 1);
        GridPane.setHalignment(root, HPos.CENTER);
        grid.add(hyperlinksHBox, 10, 0);

        // Create a scene and set it on the stage
        Scene scene = new Scene(grid, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        // Show the stage
        primaryStage.show();
    }

// ... (your existing code)
    private void showSignUpDialog() {

        // Create a dialog with custom buttons
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Sign Up");
        dialog.setHeaderText(null);

        // Set the button types (OK and Cancel)
        ButtonType signUpButtonType = new ButtonType("Sign Up", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(signUpButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        // Create a ComboBox for selecting the user type
        ObservableList<String> userTypeOptions = FXCollections.observableArrayList(
                "guest",
                "recepionist",
                "admin"
        );
        ComboBox<String> typeComboBox = new ComboBox<>(userTypeOptions);
        typeComboBox.setValue("Select");
        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Username:"), 0, 2);
        grid.add(usernameField, 1, 2);
        grid.add(new Label("Password:"), 0, 3);
        grid.add(passwordField, 1, 3);
        grid.add(new Label("Type:"), 0, 4);
        grid.add(typeComboBox, 1, 4);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the first name field by default
        Platform.runLater(firstNameField::requestFocus);

        // Convert the result to a Person2 object when the sign-up button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == signUpButtonType) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                String type = typeComboBox.getValue();

                // Perform sign-up logic
                Person2.signUp(firstName, lastName, username, password, type);
            }
            return null;
        });

        // Show the dialog and handle the result
        Optional<ButtonType> result = dialog.showAndWait();
    }

    private void showOffersWindow() {
        // Create a new stage for the "Offers" window
        Stage offersStage = new Stage();
        offersStage.setTitle("Offers");

        // Create a TextFlow for displaying content
        TextFlow textFlow = new TextFlow();
        Text offersText = new Text("Explore our latest offers and promotions! "
                + "We have 15% off all over our Menu "
                + "Use this promocode 45MK36");
        offersText.setStyle("-fx-font-weight: bold;");
        textFlow.getChildren().add(offersText);

        // Create a scene and set it on the stage
        Scene offersScene = new Scene(textFlow, 300, 100);
        offersStage.setScene(offersScene);

        // Set modality to APPLICATION_MODAL to block input to other windows
        offersStage.initModality(Modality.APPLICATION_MODAL);

        // Show the stage
        offersStage.show();
    }

    private void showInformationWindow() {
        // Create a new stage for the "Information" window
        Stage informationStage = new Stage();
        informationStage.setTitle("Information");

        // Create a TextFlow for displaying content
        TextFlow textFlow = new TextFlow();
        Text locationText = new Text("Location: We are at NYC, street 56 building 28, Phone No.: 011002133521");
        locationText.setStyle("-fx-font-weight: bold;");
        textFlow.getChildren().add(locationText);

        // Create a scene and set it on the stage
        Scene informationScene = new Scene(textFlow, 400, 100);
        informationStage.setScene(informationScene);

        // Set modality to APPLICATION_MODAL to block input to other windows
        informationStage.initModality(Modality.APPLICATION_MODAL);

        // Show the stage
        informationStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}