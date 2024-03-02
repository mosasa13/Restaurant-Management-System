package javaapplication17;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Guest2 extends Person2 implements Serializable {

    List<Reservation2> reservations;

    public Guest2(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        this.reservations = new ArrayList<>();
    }

    public String getFullName() {
        return super.getFullName(); // Call the getFullName method from the superclass
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Guest2 guest = (Guest2) obj;
        return Objects.equals(getUsername(), guest.getUsername())
                && Objects.equals(getPassword(), guest.getPassword())
                && Objects.equals(getFirstName(), guest.getFirstName())
                && Objects.equals(getLastName(), guest.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

    @Override
public void mainMenu() {
    Stage primaryStage = new Stage();
    primaryStage.setTitle("Restaurant Management System - Guest Menu");

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 20, 20, 20));

    String backgroundImagePath = "file:///C:\\Users\\mosta\\Desktop\\BACKBACK.jpg";
    Image backgroundImage = new Image(backgroundImagePath);
    BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
    );
    grid.setBackground(new Background(background));

    Label welcomeLabel = new Label("Welcome, " + getUsername());
    welcomeLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #B8860B;");
    grid.add(welcomeLabel, 0, 0, 2, 1);

    Button viewReservationsButton = new Button("View Reservations");
    Button RateBookingButton = new Button("Rate Booking");

    String buttonStyle = "-fx-border-color: #B8860B; -fx-border-width: 2; -fx-background-color: transparent; -fx-text-fill: #B8860B;";
    viewReservationsButton.setStyle(buttonStyle);
    RateBookingButton.setStyle(buttonStyle);

    HBox buttonsHBox = new HBox(10, viewReservationsButton, RateBookingButton); // Arrange buttons horizontally
    buttonsHBox.setAlignment(Pos.CENTER); // Set alignment to center for HBox
    grid.add(buttonsHBox, 0, 1, 2, 1); // Adjusted the row and column index

    viewReservationsButton.setOnAction(event -> viewReservationHistory());
    RateBookingButton.setOnAction(event -> RateBooking());

    Scene scene = new Scene(grid, 400, 300);
    primaryStage.setScene(scene);

    primaryStage.show();
}

    private void viewReservationHistory() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Restaurant Management System - Guest Menu");

        List<Reservation2> reservations = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("reservations.dat"))) {
            reservations = (List<Reservation2>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading reservations: " + e.getMessage());
        }
        VBox vbox = new VBox();

        for (Reservation2 r : reservations) {
            if (r.getGuest().getLastName().equals(this.getLastName())) {
                Label l = new Label(r.toString());
                vbox.getChildren().add(l);
            }
        }

        // Increase the width and height of the Scene
        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public void RateBooking() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Rate Booking");

        ToggleGroup rateToggleGroup = new ToggleGroup();
        RadioButton rb1 = createStyledRadioButton("1 (Lowest)");
        RadioButton rb2 = createStyledRadioButton("2");
        RadioButton rb3 = createStyledRadioButton("3");
        RadioButton rb4 = createStyledRadioButton("4");
        RadioButton rb5 = createStyledRadioButton("5 (Highest)");

        rb5.setSelected(true);

        rb1.setToggleGroup(rateToggleGroup);
        rb2.setToggleGroup(rateToggleGroup);
        rb3.setToggleGroup(rateToggleGroup);
        rb4.setToggleGroup(rateToggleGroup);
        rb5.setToggleGroup(rateToggleGroup);

        ComboBox<String> ratingIdeasComboBox = new ComboBox<>();
        ratingIdeasComboBox.getItems().addAll("Good Food", "Good Service", "Friendly Staff", "Other");
        ratingIdeasComboBox.setValue("Select Rating Idea");

        TextArea feedbackTextArea = new TextArea();
        feedbackTextArea.setPromptText("Provide additional feedback...");
        feedbackTextArea.setMinHeight(80);  // Adjusted the height

        Button doneButton = new Button("Done");
        doneButton.setStyle("-fx-background-color: #8B4513; -fx-text-fill: white;");
        doneButton.setOnAction((t) -> {
            int selectedRate = Integer.parseInt(((RadioButton) rateToggleGroup.getSelectedToggle()).getText().substring(0, 1));
            String selectedRatingIdea = ratingIdeasComboBox.getValue();
            String additionalFeedback = feedbackTextArea.getText();

            List<Reservation2> reservations;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("reservations.dat"))) {
                reservations = (List<Reservation2>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading reservations: " + e.getMessage());
                return;
            }

            for (Reservation2 r : reservations) {
                if (r.getGuest().getLastName().equals(getLastName())) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Rated Successfully. Thank you for choosing us! Hope to see you soon :)");
                    alert.showAndWait();

                    reservations.remove(r);
                    r.setRate(selectedRate);
                    r.setRatingIdea(selectedRatingIdea);
                    r.setAdditionalFeedback(additionalFeedback);
                    reservations.add(r);

                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("reservations.dat"))) {
                        oos.writeObject(reservations);
                        System.out.println("Reservation saved successfully.");
                        return;
                    } catch (IOException e) {
                        System.out.println("Error saving reservations: " + e.getMessage());
                        return;
                    }
                }
            }
        });

        HBox ratingButtons = new HBox(10, rb1, rb2, rb3, rb4, rb5);
        ratingButtons.setAlignment(javafx.geometry.Pos.CENTER);

        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(ratingButtons, ratingIdeasComboBox, feedbackTextArea, doneButton);

        Scene scene = new Scene(vbox, 600, 300);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private RadioButton createStyledRadioButton(String text) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 10; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color: #8B4513;");
        return radioButton;
    }
}