package javaapplication17;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import java.util.Map;
import java.util.Date;
import javafx.scene.layout.HBox;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
// Import necessary packages
import javafx.scene.control.CheckBox;
import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

// ... (existing import statements)
import java.io.*;

import javafx.application.Platform;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.ComboBox;

import javafx.scene.control.*;

import java.util.List;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class Recepionist2 extends Person2 {

    private List<Reservation2> reservations;
    private String loggedInUsername;

    public Recepionist2(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        this.reservations = new ArrayList<>();
        this.loggedInUsername = null;
    }

    @Override
    public void mainMenu() {
        loggedInUsername = getUsername();
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Restaurant Management System - Receptionist Menu");

        // Create a GridPane for layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        // Load the image
        Image backgroundImage = new Image("file:C:\\Users\\mosta\\Desktop\\BACKBACK.jpg");

// Set the background image to the grid
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        grid.setBackground(new Background(background));
        String buttonStyle = "-fx-border-color: #B8860B; -fx-border-width: 2; -fx-background-color: transparent; -fx-text-fill: #B8860B;";

        Label welcomeLabel = new Label("Welcome, " + getUsername());
        welcomeLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #B8860B;");
        grid.add(welcomeLabel, 0, 0, 2, 1);
        // Add components to the grid
        Button createReservationButton = new Button("Create Reservation");
        createReservationButton.setStyle(buttonStyle);

        Button cancelReservationButton = new Button("Cancel Reservation");
        cancelReservationButton.setStyle(buttonStyle);
        grid.add(createReservationButton, 0, 2);
        grid.add(cancelReservationButton, 1, 2);

        // Set event handlers for the buttons (you need to implement the actions)
        createReservationButton.setOnAction(event -> createReservation());
        cancelReservationButton.setOnAction(event -> cancelReservation());

        GridPane.setFillWidth(grid, true);
        GridPane.setFillHeight(grid, true);
        // Create a scene and set it on the stage
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    public String getReceptionistUsername() {
        return loggedInUsername;
    }

    // You need to implement the GUI actions for the following methods
    private void createReservation() {
        loadReservationsFromFile();

        // Validate guest credentials
        Guest2 newGuest = getValidGuest();
        if (newGuest == null) {
            showAlert("Invalid guest details. Reservation failed.");
            return;
        }

        // Gather available tables and meals data
        ArrayList<Table2> tables = Table2.readTableFromFile();

        if (tables.isEmpty()) {
            showAlert("No tables found");
            return;
        }

        // Create JavaFX controls for table selection
        ComboBox<Table2> tableComboBox = new ComboBox<>(FXCollections.observableArrayList(tables));
        tableComboBox.setPromptText("Select a table");

        // Create JavaFX controls for meal selection using checkboxes
        ArrayList<Meal2> availableMeals = Meal2.readMealFromFile();
        VBox mealCheckBoxes = new VBox(10);

        for (Meal2 meal : availableMeals) {
            // Create JavaFX controls for quantity input
            Spinner<Integer> quantitySpinner = new Spinner<>(1, 10, 1);
            Label mealLabel = new Label(meal.getName() + " - Price: $" + meal.getPrice());

            HBox mealBox = new HBox(10, new CheckBox(), mealLabel, quantitySpinner);
            // Add an additional spacing node
            Region spacer = new Region();
            VBox.setVgrow(spacer, Priority.ALWAYS);
            mealCheckBoxes.getChildren().addAll(mealBox, spacer);
        }

        // Create a Button to submit the reservation
        Button submitButton = new Button("Submit Reservation");

        // Create a GridPane for layout
        GridPane reservationGrid = new GridPane();
        reservationGrid.setHgap(10);
        reservationGrid.setVgap(10);
        reservationGrid.setPadding(new Insets(20, 20, 20, 20));

        reservationGrid.add(new Label("Table:"), 0, 0);
        reservationGrid.add(tableComboBox, 1, 0);
        reservationGrid.add(new Label("Meals:"), 0, 1);
        reservationGrid.add(mealCheckBoxes, 1, 1);
        reservationGrid.add(submitButton, 1, 3);

        // Create a new stage for the reservation form
        Stage reservationStage = new Stage();
        reservationStage.setTitle("Create Reservation");

        // Set event handler for the submit button
        submitButton.setOnAction(event -> {
            Table2 selectedTable = tableComboBox.getValue();

            // Collect selected meals and quantities from checkboxes
            Map<Meal2, Integer> selectedMealsWithQuantity = new HashMap<>();
            for (Node node : mealCheckBoxes.getChildren()) {
                if (node instanceof HBox) {
                    HBox mealBox = (HBox) node;
                    CheckBox checkBox = (CheckBox) mealBox.getChildren().get(0);
                    Label mealLabel = (Label) mealBox.getChildren().get(1);
                    Spinner<Integer> quantitySpinner = (Spinner<Integer>) mealBox.getChildren().get(2);

                    if (checkBox.isSelected()) {
                        // Extract quantity from the spinner
                        int quantity = quantitySpinner.getValue();

                        // Extract meal name from the label
                        String mealName = mealLabel.getText().split(" - Price: ")[0];

                        // Find the selected meal
                        Meal2 selectedMeal = availableMeals.stream()
                                .filter(meal -> meal.getName().equals(mealName))
                                .findFirst()
                                .orElse(null);

                        if (selectedMeal != null) {
                            selectedMealsWithQuantity.put(selectedMeal, quantity);
                        }
                    }
                }
            }

            if (selectedTable != null && !selectedMealsWithQuantity.isEmpty()) {
                // Calculate total price
                double totalPrice = selectedMealsWithQuantity.entrySet().stream()
                        .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                        .sum();

                // Save the reservation with timestamp
                Date timestamp = new Date();

                // Save the reservation
                String receptionistUsername = getReceptionistUsername();
                Reservation2 reservation = new Reservation2(newGuest, selectedTable, selectedMealsWithQuantity, totalPrice, timestamp, receptionistUsername);
                reservations.add(reservation);
                saveReservationsToFile();

                // Display reservation details
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                showAlert("Reservation Details:\n"
                        + "Guest: " + newGuest.getFullName() + "\n"
                        + "Table: " + selectedTable.getNumber() + " - Reserved\n"
                        + "Meals: " + selectedMealsWithQuantity.entrySet().stream()
                                .map(entry -> entry.getKey().getName() + " - Quantity: " + entry.getValue())
                                .collect(Collectors.joining("\n")) + "\n"
                        + "Total Price: " + totalPrice + "\n"
                        + "Reservation Time: " + dateFormat.format(timestamp) + "\n"
                        + "Receptionist Username: " + receptionistUsername);
                countReservationsPerGuest();
                countReservationsForReceptionists();
                // Close the reservation form stage
                reservationStage.close();
            }
        });

        // Create a scene and set it on the stage
        Scene reservationScene = new Scene(reservationGrid, 400, 300);
        reservationStage.setScene(reservationScene);

        // Show the reservation form stage
        reservationStage.show();
    }
// Helper method to convert selected meals to a map with quantities

    private Map<Meal2, Integer> getSelectedMeals(List<Meal2> selectedMeals, int quantity) {
        return selectedMeals.stream().collect(Collectors.toMap(meal -> meal, meal -> quantity));
    }

// Method to show an alert message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void cancelReservation() {
        loadReservationsFromFile();

        // Create JavaFX controls for reservation cancellation
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        Button cancelReservationButton = new Button("Cancel Reservation");

        // Create a GridPane for layout
        GridPane cancellationGrid = new GridPane();
        cancellationGrid.setHgap(10);
        cancellationGrid.setVgap(10);
        cancellationGrid.setPadding(new Insets(20, 20, 20, 20));

        cancellationGrid.add(new Label("Enter first name:"), 0, 0);
        cancellationGrid.add(firstNameField, 1, 0);
        cancellationGrid.add(new Label("Enter last name:"), 0, 1);
        cancellationGrid.add(lastNameField, 1, 1);
        cancellationGrid.add(cancelReservationButton, 1, 2);

        // Create a new stage for the cancellation form
        Stage cancellationStage = new Stage();
        cancellationStage.setTitle("Cancel Reservation");

        // Set event handler for the cancelReservationButton
        cancelReservationButton.setOnAction(event -> {
            // Get guest details for canceling reservation
            String guestFirstName = firstNameField.getText().toLowerCase();
            String guestLastName = lastNameField.getText().toLowerCase();

            Iterator<Reservation2> iterator = reservations.iterator();
            boolean reservationFound = false;

            while (iterator.hasNext()) {
                Reservation2 reservation = iterator.next();
                Guest2 guest = reservation.getGuest();

                if (guest != null && guest.getFirstName().toLowerCase().equals(guestFirstName)
                        && guest.getLastName().toLowerCase().equals(guestLastName)) {
                    reservationFound = true;

                    // Display reservation details
                    displayReservationDetails(reservation);

                    // Ask for the reason of cancellation using a ChoiceDialog
                    List<String> cancellationReasons = Arrays.asList(
                            "Change of plans",
                            "Unexpected circumstances",
                            "Not satisfied with the service",
                            "Other"
                    );

                    ChoiceDialog<String> dialog = new ChoiceDialog<>(cancellationReasons.get(0), cancellationReasons);
                    dialog.setTitle("Cancellation Reason");
                    dialog.setHeaderText(null);
                    dialog.setContentText("Choose the reason for cancellation:");

                    Optional<String> result = dialog.showAndWait();

                    // Optionally, you can handle the chosen reason (including "Other")
                    result.ifPresent(reason -> {
                        String cancellationReason;
                        if (reason.equals("Other")) {
                            TextInputDialog customReasonDialog = new TextInputDialog();
                            customReasonDialog.setTitle("Custom Cancellation Reason");
                            customReasonDialog.setHeaderText(null);
                            customReasonDialog.setContentText("Enter the custom reason for cancellation:");

                            // Show the dialog and capture the result
                            Optional<String> customReasonResult = customReasonDialog.showAndWait();

                            // Check if the user entered a custom reason
                            if (customReasonResult.isPresent()) {
                                cancellationReason = customReasonResult.get();
                                // Handle the custom reason as needed
                                showAlert("Custom Cancellation Reason: " + cancellationReason);
                            } else {
                                // Handle the case where the user canceled the custom reason input
                                showAlert("Custom Cancellation Reason not provided");
                            }
                        } else {
                            // Use the predefined reason
                            cancellationReason = reason;
                            // Optionally, you can perform additional logic with the predefined reason here
                            showAlert("Predefined Cancellation Reason: " + cancellationReason);
                        }

                        // Optionally, you can perform additional logic with the cancellation reason here
                    });

                    // Remove the reservation from the list
                    iterator.remove();
                    saveReservationsToFile();

                    showAlert("Reservation canceled successfully.");
                    break; // Exit the loop once the reservation is found and canceled
                }
            }

            if (!reservationFound) {
                showAlert("Reservation not found for the specified guest.");
            }

            // Close the cancellation form stage
            cancellationStage.close();
        });

        // Create a scene and set it on the stage
        Scene cancellationScene = new Scene(cancellationGrid, 400, 200);
        cancellationStage.setScene(cancellationScene);

        // Show the cancellation form stage
        cancellationStage.show();
    }

    private void displayReservationDetails(Reservation2 reservation) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Create a new stage for displaying reservation details
        Stage detailsStage = new Stage();
        detailsStage.setTitle("Reservation Details");

        // Create a VBox for layout
        VBox detailsVBox = new VBox(10);
        detailsVBox.setPadding(new Insets(20, 20, 20, 20));

        // Add reservation details to VBox
        detailsVBox.getChildren().addAll(
                new Label("Guest: " + reservation.getGuest().getFullName()),
                new Label("Table: " + reservation.getTable().getNumber() + " - Reserved"),
                new Label("Meals:"),
                new Label(formatMealDetails(reservation.getMealReservations())),
                new Label("Total Price: " + reservation.getTotalPrice()),
                new Label("Reservation Time: " + dateFormat.format(reservation.getTimestamp()))
        );

        // Create a Scene and set it on the stage
        Scene detailsScene = new Scene(detailsVBox, 400, 300);
        detailsStage.setScene(detailsScene);

        // Show the details stage
        detailsStage.show();
    }

// Helper method to format meal details for display
    private String formatMealDetails(Map<Meal2, Integer> mealReservations) {
        StringBuilder mealDetails = new StringBuilder();
        for (Map.Entry<Meal2, Integer> entry : mealReservations.entrySet()) {
            mealDetails.append("- ").append(entry.getKey().getName())
                    .append(" - Quantity: ").append(entry.getValue()).append("\n");
        }
        return mealDetails.toString();
    }

    private boolean hasExistingReservation(Guest2 guest) {
        for (Reservation2 reservation : reservations) {
            Guest2 existingGuest = reservation.getGuest();
            if (existingGuest != null && existingGuest.getFirstName().equals(guest.getFirstName())
                    && existingGuest.getLastName().equals(guest.getLastName())) {
                return true;
            }
        }
        return false;
    }

    private void loadReservationsFromFile() {
        try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream("reservations.dat"))) {
            reservations = (List<Reservation2>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {

        }
    }

    private Guest2 getValidGuest() {
        ArrayList<Person2> persons = readUserFromFile();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Guest Details");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter guest details:");

        // Create JavaFX controls for guest details
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();

        GridPane grid = new GridPane();
        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the first name field by default.
        Platform.runLater(() -> firstNameField.requestFocus());

        // Convert the result to a valid guest
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            return new Guest2(firstNameField.getText(), lastNameField.getText(), "", "");
        }

        return null;
    }

    private void saveReservationsToFile() {
        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("reservations.dat"))) {
            oos.writeObject(reservations);
            //  showAlert("Reservation saved successfully.");
        } catch (IOException e) {
            showAlert("Error saving reservations: " + e.getMessage());
        }
    }

    public void countReservationsForReceptionists() {

        Map<String, List<Reservation2>> reservationsByReceptionist = new HashMap<>();

        for (Reservation2 reservation : reservations) {
            String receptionistUsername = reservation.getReceptionistUsername();

            if (receptionistUsername != null) {
                // Update reservations count map
                reservationsByReceptionist.computeIfAbsent(receptionistUsername, k -> new ArrayList<>()).add(reservation);
            }
        }

        // Display all reservations for each receptionist
        for (Map.Entry<String, List<Reservation2>> entry : reservationsByReceptionist.entrySet()) {
            String receptionistUsername = entry.getKey();
            List<Reservation2> reservationsForReceptionist = entry.getValue();

            // Display details for each reservation
        }

        // Write the reservations count to a binary file
        writeReservationsCountsToBinaryFile(reservationsByReceptionist);
    }

    private void writeReservationsCountsToBinaryFile(Map<String, List<Reservation2>> reservationsByReceptionist) {
        String filePath = "reservations_counts_by_receptionist.bin";

        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // Save the counts and details for each receptionist
            oos.writeObject(reservationsByReceptionist);

        } catch (IOException e) {

        }
    }

    public void countReservationsPerGuest() {
        Map<String, Integer> reservationsCountByGuest = new HashMap<>();
        Map<String, List<Reservation2>> reservationsDetailsByGuest = new HashMap<>();

        for (Reservation2 reservation : reservations) {
            Guest2 guest = reservation.getGuest();

            if (guest != null) {
                // Update reservations count map
                reservationsCountByGuest.merge(guest.getFullName(), 1, Integer::sum);

                // Update reservations details map
                reservationsDetailsByGuest.computeIfAbsent(guest.getFullName(), k -> new ArrayList<>()).add(reservation);
            }
        }

        for (Map.Entry<String, Integer> entry : reservationsCountByGuest.entrySet()) {
            String guestFullName = entry.getKey();
            int reservationsCount = entry.getValue();

            // Display details for each reservation
            List<Reservation2> reservationsForGuest = reservationsDetailsByGuest.get(guestFullName);
            if (reservationsForGuest != null) {
                for (Reservation2 reservation : reservationsForGuest) {

                }
            }

        }
        writeReservationsInfoByGuestToBinaryFile(reservationsCountByGuest, reservationsDetailsByGuest);
    }

    private void writeReservationsInfoByGuestToBinaryFile(Map<String, Integer> reservationsCountByGuest,
            Map<String, List<Reservation2>> reservationsDetailsByGuest) {
        String filePath = "reservations_info_by_guest.bin";

        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // Save the counts and details for each guest
            Map<String, Object> reservationsInfoByGuest = new HashMap<>();
            reservationsInfoByGuest.put("countByGuest", reservationsCountByGuest);
            reservationsInfoByGuest.put("detailsByGuest", reservationsDetailsByGuest);

            oos.writeObject(reservationsInfoByGuest);

        } catch (IOException e) {

        }
    }
}