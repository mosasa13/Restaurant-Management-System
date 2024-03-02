package javaapplication17;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import java.io.Serializable;
import java.time.LocalDate;
import javafx.util.StringConverter;
import java.util.Optional;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.stream.Collectors;
import javafx.scene.Node;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class Admin2 extends Person2 implements Serializable {

    public Admin2(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
    }

    @Override
    public void mainMenu() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Restaurant Management System - Admin Menu");

        // Create a GridPane for layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        // Add a Label to display welcome message with admin's username
        Label welcomeLabel = new Label("Welcome, " + getUsername());
        welcomeLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #B8860B;");
        grid.add(welcomeLabel, 0, 0, 2, 1);

        // Add components to the grid
        String buttonStyle = "-fx-border-color: #B8860B; -fx-border-width: 2; -fx-background-color: transparent; -fx-text-fill: #B8860B;";

        // Add components to the VBox
        Button addTableButton = new Button("Add Table");
        addTableButton.setStyle(buttonStyle);
        Button editTableButton = new Button("Edit Table");
        editTableButton.setStyle(buttonStyle);
        Button removeTableButton = new Button("Remove Table");
        removeTableButton.setStyle(buttonStyle);
        Button searchTableButton = new Button("Search Table");
        searchTableButton.setStyle(buttonStyle);
        Button viewTableReportsButton = new Button("View Table Reports");
        viewTableReportsButton.setStyle(buttonStyle);
        Button addMealButton = new Button("Add Meals");
        addMealButton.setStyle(buttonStyle);
        Button editMealButton = new Button("Edit Meals ");
        editMealButton.setStyle(buttonStyle);
        Button removeMealButton = new Button("Remove Meals");
        removeMealButton.setStyle(buttonStyle);
        Button displayMenuButton = new Button("Display Menu");
        displayMenuButton.setStyle(buttonStyle);
        Button addUserButton = new Button("Add User");
        addUserButton.setStyle(buttonStyle);
        Button editUserButton = new Button("Edit User");
        editUserButton.setStyle(buttonStyle);
        Button removeUserButton = new Button("Remove User");
        removeUserButton.setStyle(buttonStyle);
        Button displayUsersButton = new Button("Display Users");
        displayUsersButton.setStyle(buttonStyle);
        Button viewMenuReportsButton = new Button("View Meal Reports");
        viewMenuReportsButton.setStyle(buttonStyle);
        Button exitButton = new Button("Exit");
        exitButton.setStyle(buttonStyle);

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

        grid.add(addTableButton, 1, 1);
        grid.add(editTableButton, 2, 1);
        grid.add(removeTableButton, 3, 1);
        grid.add(searchTableButton, 4, 1);
        grid.add(viewTableReportsButton, 5, 1);

        grid.add(addMealButton, 1, 2);
        grid.add(editMealButton, 2, 2);
        grid.add(removeMealButton, 3, 2);
        grid.add(displayMenuButton, 4, 2);
        grid.add(viewMenuReportsButton, 5, 2);

        grid.add(addUserButton, 1, 3);
        grid.add(editUserButton, 2, 3);
        grid.add(removeUserButton, 3, 3);
        grid.add(displayUsersButton, 4, 3);
        grid.add(exitButton, 3, 4);

        addTableButton.setOnAction(event -> addTable());
        editTableButton.setOnAction(event -> EditTable());
        removeTableButton.setOnAction(event -> RemoveTable());
        searchTableButton.setOnAction(event -> DisplayandsearchTable());
        addMealButton.setOnAction(event -> addMeal());
        removeMealButton.setOnAction(event -> removeMeal());
        editMealButton.setOnAction(event -> editMeal());
        displayMenuButton.setOnAction(event -> displayMenu());
        addUserButton.setOnAction(event -> addUser());
        editUserButton.setOnAction(event -> EditUser());
        removeUserButton.setOnAction(event -> removeUser());
        displayUsersButton.setOnAction(event -> displayUsers());
        viewMenuReportsButton.setOnAction(event -> ViewMenuReport());
        exitButton.setOnAction(event -> System.exit(0));

        Scene scene = new Scene(grid, 600, 250);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void addTable() {
        // Create a dialog for adding a table
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Table");
        dialog.setHeaderText(null);

        // Set the button types (OK and Cancel)
        ButtonType addTableButtonType = new ButtonType("Add Table", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addTableButtonType, ButtonType.CANCEL);

        // Create the input fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField numberField = new TextField();
        RadioButton categoryField1 = new RadioButton("Vip");
        RadioButton Categoryfield2 = new RadioButton("Premium");
        RadioButton Categoryfield3 = new RadioButton("Normal");
        TextField chairsField = new TextField();

        grid.add(new Label("Table Number:"), 0, 0);
        grid.add(numberField, 1, 0);
        grid.add(new Label("Table Category:"), 0, 1);

        HBox hx = new HBox(10, categoryField1, Categoryfield2, Categoryfield3);
        grid.add(hx, 1, 1);
        ToggleGroup tg = new ToggleGroup();
        categoryField1.setToggleGroup(tg);
        Categoryfield2.setToggleGroup(tg);
        Categoryfield3.setToggleGroup(tg);
        RadioButton rb;
        rb = (RadioButton) tg.getSelectedToggle();

        grid.add(new Label("Number of Chairs:"), 0, 2);
        grid.add(chairsField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the table number field by default
        Platform.runLater(numberField::requestFocus);

        // Disable the default behavior of closing the dialog on pressing the OK button
        Button addButton = (Button) dialog.getDialogPane().lookupButton(addTableButtonType);
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (numberField.getText().isEmpty() || (!categoryField1.isSelected() && !Categoryfield2.isSelected() && !Categoryfield3.isSelected()) || chairsField.getText().isEmpty()) {
                // Display an alert for missing fields
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
                event.consume(); // Consume the event to prevent closing the dialog
            }
        });

        // Convert the result to a Table2 object when the add table button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addTableButtonType) {
                try {
                    int number = Integer.parseInt(numberField.getText());
                    String category = "";
                    if (categoryField1.isSelected()) {
                        category = "Vip";
                    } else if (Categoryfield2.isSelected()) {
                        category = "Premium";
                    } else if (Categoryfield3.isSelected()) {
                        category = "Normal";
                    }
                    int numberOfChairs = Integer.parseInt(chairsField.getText());

                    // Perform add table logic
                    Table2 table = new Table2(number, category, numberOfChairs);
                    ArrayList<Table2> tables = Table2.readTableFromFile();
                    tables.add(table);
                    Table2.writeTableToFile(tables);
                } catch (NumberFormatException e) {
                    // Handle invalid input
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid input. Please enter valid numbers.");
                    alert.showAndWait();
                }
            }
            return null;
        });

        // Show the dialog and handle the result
        Optional<ButtonType> result = dialog.showAndWait();
    }

    private void EditTable() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Edit Table");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the table number to edit:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            int tableNumberToEdit = Integer.parseInt(result.get());
            ArrayList<Table2> tables = Table2.readTableFromFile();

            boolean found = false;

            for (Table2 table : tables) {
                if (table.getNumber() == tableNumberToEdit) {
                    // Show a dialog with radio buttons for the new category
                    Dialog<String> categoryDialog = new Dialog<>();
                    categoryDialog.setTitle("Edit Table");
                    categoryDialog.setHeaderText("Enter the new category for table " + tableNumberToEdit + ":");

                    // Set the button types (OK and Cancel)
                    ButtonType editButtonType = new ButtonType("Edit", ButtonData.OK_DONE);
                    categoryDialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

                    // Create the input fields (radio buttons for categories)
                    ToggleGroup categoryToggleGroup = new ToggleGroup();
                    RadioButton vipRadioButton = new RadioButton("Vip");
                    vipRadioButton.setToggleGroup(categoryToggleGroup);
                    RadioButton premiumRadioButton = new RadioButton("Premium");
                    premiumRadioButton.setToggleGroup(categoryToggleGroup);
                    RadioButton normalRadioButton = new RadioButton("Normal");
                    normalRadioButton.setToggleGroup(categoryToggleGroup);

                    // Add radio buttons to the dialog
                    categoryDialog.getDialogPane().setContent(new VBox(10, vipRadioButton, premiumRadioButton, normalRadioButton));

                    // Request focus on the vipRadioButton by default
                    Platform.runLater(vipRadioButton::requestFocus);

                    // Convert the result to the selected category when the Edit button is clicked
                    categoryDialog.setResultConverter(dialogButton -> {
                        if (dialogButton == editButtonType) {
                            RadioButton selectedRadioButton = (RadioButton) categoryToggleGroup.getSelectedToggle();
                            if (selectedRadioButton != null) {
                                return selectedRadioButton.getText();
                            }
                        }
                        return null;
                    });

                    Optional<String> newCategoryResult = categoryDialog.showAndWait();
                    if (newCategoryResult.isPresent()) {
                        String newCategory = newCategoryResult.get();

                        // Update the category of the matching table
                        table.setCategory(newCategory);

                        // Update the file after modification
                        Table2.writeTableToFile(tables);

                        // Show a confirmation alert
                        Alert confirmationAlert = new Alert(AlertType.INFORMATION);
                        confirmationAlert.setTitle("Edit Table");
                        confirmationAlert.setHeaderText(null);
                        confirmationAlert.setContentText("Table " + tableNumberToEdit + " category has been updated to '" + newCategory + "'.");
                        confirmationAlert.showAndWait();

                        found = true;
                    }
                    break;
                }
            }

            if (!found) {
                // Show an error alert if the table is not found
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Edit Table");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Table " + tableNumberToEdit + " not found.");
                errorAlert.showAndWait();
            }
        }
    }

    private void RemoveTable() {
        // Create a dialog to get the table number to remove
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Remove Table");
        dialog.setHeaderText(null);
        dialog.setContentText("Choose the table to remove:");

        // Create a ComboBox to display the available table numbers
        ComboBox<Integer> tableComboBox = new ComboBox<>();
        tableComboBox.getItems().addAll(getTableNumbers()); // Implement getTableNumbers() to return a list of table numbers
        tableComboBox.setPromptText("List Of Tables"); // Set the prompt text

        dialog.getDialogPane().setContent(new VBox(10, tableComboBox));

        // Set the button types (OK and Cancel)
        ButtonType removeButtonType = new ButtonType("Remove", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(removeButtonType, ButtonType.CANCEL);

        // Enable/Disable Remove button depending on whether a table is selected
        Node removeButton = dialog.getDialogPane().lookupButton(removeButtonType);
        removeButton.setDisable(true);

        // Listen for changes in the ComboBox selection
        tableComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            removeButton.setDisable(newValue == null || "List Of Tables".equals(newValue.toString()));
        });

        // Convert the result to the selected table number when the Remove button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == removeButtonType) {
                return tableComboBox.getValue();
            }
            return null;
        });

        // Show the dialog and wait for the user's input
        dialog.showAndWait().ifPresent(tableNumberToRemove -> {
            ArrayList<Table2> tables = Table2.readTableFromFile();
            boolean found = false;

            for (int i = 0; i < tables.size(); i++) {
                Table2 table = tables.get(i);
                if (table.getNumber() == tableNumberToRemove) {
                    tables.remove(i);
                    found = true;
                    break;
                }
            }

            if (found) {
                Table2.writeTableToFile(tables);

                // Show a confirmation alert
                Alert confirmationAlert = new Alert(AlertType.INFORMATION);
                confirmationAlert.setTitle("Remove Table");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Table " + tableNumberToRemove + " removed successfully.");
                confirmationAlert.showAndWait();
            } else {
                // Show an alert if the table is not found
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Table Not Found");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Table " + tableNumberToRemove + " not found.");
                errorAlert.showAndWait();
            }
        });
    }

    private List<Integer> getTableNumbers() {
        ArrayList<Table2> tables = Table2.readTableFromFile();
        return tables.stream().map(Table2::getNumber).collect(Collectors.toList());
    }

    private void DisplayandsearchTable() {
        ArrayList<Table2> tables = Table2.readTableFromFile();

        // Create a TableView
        TableView<Table2> tableView = new TableView<>();

        // Create TableColumn instances for each property you want to display
        TableColumn<Table2, Integer> numberColumn = new TableColumn<>("Table Number");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<Table2, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Table2, Integer> chairsColumn = new TableColumn<>("Number of Chairs");
        chairsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfChairs"));

        // Add the columns to the TableView
        tableView.getColumns().addAll(numberColumn, categoryColumn, chairsColumn);

        // Populate the TableView with the data
        ObservableList<Table2> tableData = FXCollections.observableArrayList(tables);
        tableView.setItems(tableData);

        // Create a TextField for search
        TextField searchField = new TextField();
        searchField.setPromptText("Search by Table Number");

        // Set the TableView to single selection mode
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Set up the VBox to contain TableView and searchField
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().addAll(searchField, tableView);

        // Set up the dialog with the VBox
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Search Table");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setContent(vbox);

        // Set the button types (OK and Cancel)
        ButtonType searchTableButtonType = new ButtonType("Search Table", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(searchTableButtonType, ButtonType.CANCEL);

        // Show the dialog and wait for the user's input
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == searchTableButtonType) {
            // Get the entered table number from the searchField
            try {
                int selectedTableNumber = Integer.parseInt(searchField.getText());

                // Perform the search based on the entered table number
                boolean found = false;

                for (Table2 table : tables) {
                    if (table.getNumber() == selectedTableNumber) {
                        // Highlight the row in the TableView
                        int index = tables.indexOf(table);
                        tableView.getSelectionModel().clearAndSelect(index);

                        // Display table information using an alert
                        Alert informationAlert = new Alert(AlertType.INFORMATION);
                        informationAlert.setTitle("Table Information");
                        informationAlert.setHeaderText(null);
                        informationAlert.setContentText(
                                "Table Number: " + table.getNumber() + "\n"
                                + "Category: " + table.getCategory() + "\n"
                                + "Number of Chairs: " + table.getNumberOfChairs()
                        );
                        informationAlert.showAndWait();

                        found = true;
                        break;
                    }
                }

                if (!found) {
                    // Show an alert if the table is not found
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Table Not Found");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Table " + selectedTableNumber + " not found.");
                    errorAlert.showAndWait();
                }
            } catch (NumberFormatException e) {
                // Show an alert if the entered value is not a valid number
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Invalid Input");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Please enter a valid table number.");
                errorAlert.showAndWait();
            }
        }
    }

    private void addMeal() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Meal");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the number of meals you want to add:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(numberOfMealsStr -> {
            try {
                int numberOfMeals = Integer.parseInt(numberOfMealsStr);
                ArrayList<Meal2> meals = Meal2.readMealFromFile();  // Read existing meals from the file

                for (int i = 0; i < numberOfMeals; i++) {
                    Dialog<String[]> mealDialog = new Dialog<>();
                    mealDialog.setTitle("Add Meal");
                    mealDialog.setHeaderText(null);

                    // Set the button types (OK and Cancel)
                    ButtonType addMealButtonType = new ButtonType("Add Meal", ButtonData.OK_DONE);
                    mealDialog.getDialogPane().getButtonTypes().addAll(addMealButtonType, ButtonType.CANCEL);

                    // Create the input fields
                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.setPadding(new Insets(20, 150, 10, 10));

                    TextField nameField = new TextField();
                    TextField priceField = new TextField();
                    TextField descriptionField = new TextField();

                    grid.add(new Label("Meal Name:"), 0, 0);
                    grid.add(nameField, 1, 0);
                    grid.add(new Label("Meal Price:"), 0, 1);
                    grid.add(priceField, 1, 1);
                    grid.add(new Label("Meal Description:"), 0, 2);
                    grid.add(descriptionField, 1, 2);

                    mealDialog.getDialogPane().setContent(grid);

                    // Request focus on the name field by default
                    Platform.runLater(nameField::requestFocus);

                    // Disable the default behavior of closing the dialog on pressing the OK button
                    Button addMealButton = (Button) mealDialog.getDialogPane().lookupButton(addMealButtonType);
                    addMealButton.addEventFilter(ActionEvent.ACTION, event -> {
                        if (nameField.getText().isEmpty() || priceField.getText().isEmpty() || descriptionField.getText().isEmpty()) {
                            // Display an alert for missing fields
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText(null);
                            alert.setContentText("Please fill in all fields.");
                            alert.showAndWait();
                            event.consume(); // Consume the event to prevent closing the dialog
                        }
                    });

                    // Convert the result to a Meal2 object when the add meal button is clicked
                    mealDialog.setResultConverter(dialogButton -> {
                        if (dialogButton == addMealButtonType) {
                            try {
                                String name = nameField.getText();
                                double price = Double.parseDouble(priceField.getText());
                                String description = descriptionField.getText();

                                // Perform add meal logic
                                Meal2 meal = new Meal2(name, price, description);
                                meals.add(meal);
                            } catch (NumberFormatException e) {
                                // Handle invalid input
                                showAlert("Invalid input. Please enter valid numbers.", Alert.AlertType.ERROR);
                            }
                        }
                        return null;
                    });

                    // Show the dialog and handle the result
                    mealDialog.showAndWait();
                }

                Meal2.writeMealToFile(meals); // Write all meals to the file
                showAlert(numberOfMeals + " meals added successfully", Alert.AlertType.INFORMATION);

            } catch (NumberFormatException e) {
                // Handle invalid input
                showAlert("Invalid input. Please enter a valid number.", Alert.AlertType.ERROR);
            }
        });
    }

    private void displayMenu() {
        ArrayList<Meal2> meals = Meal2.readMealFromFile();

        if (meals.isEmpty()) {
            showAlert("No meals found.", Alert.AlertType.INFORMATION);
        } else {
            // Create a TableView to display the menu
            TableView<Meal2> tableView = new TableView<>();

            // Create columns for Meal properties
            TableColumn<Meal2, String> nameColumn = new TableColumn<>("Meal Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Meal2, Double> priceColumn = new TableColumn<>("Meal Price");
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            TableColumn<Meal2, String> descriptionColumn = new TableColumn<>("Meal Description");
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

            // Add columns to the TableView
            tableView.getColumns().addAll(nameColumn, priceColumn, descriptionColumn);

            // Populate the TableView with data
            ObservableList<Meal2> observableMeals = FXCollections.observableArrayList(meals);
            tableView.setItems(observableMeals);

            // Create a dialog to display the menu
            Alert menuAlert = new Alert(Alert.AlertType.INFORMATION);
            menuAlert.setTitle("Menu");
            menuAlert.setHeaderText(null);
            menuAlert.getDialogPane().setContent(tableView);
            menuAlert.showAndWait();
        }
    }

    private void removeMeal() {
        // Create a TextInputDialog to get the name of the meal to remove
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Remove Meal");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the name of the meal to remove:");

        // Show the dialog and wait for the user's input
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(nameToRemove -> {
            ArrayList<Meal2> meals = Meal2.readMealFromFile();
            boolean found = false;

            for (int i = 0; i < meals.size(); i++) {
                Meal2 meal = meals.get(i);
                if (meal.getName().equals(nameToRemove)) {
                    meals.remove(i);
                    found = true;
                    break;
                }
            }

            if (found) {
                Meal2.writeMealToFile(meals);
                showAlert("Meal '" + nameToRemove + "' removed successfully.", AlertType.INFORMATION);
            } else {
                showAlert("Meal '" + nameToRemove + "' not found.", AlertType.ERROR);
            }
        });
    }

    private void editMeal() {
        ArrayList<Meal2> meals = Meal2.readMealFromFile();

        ChoiceDialog<Meal2> dialog = new ChoiceDialog<>(meals.get(0), meals);
        dialog.setTitle("Edit Meal");
        dialog.setHeaderText(null);
        dialog.setContentText("Choose the meal to edit:");

        Optional<Meal2> result = dialog.showAndWait();

        result.ifPresent(selectedMeal -> {
            // Create a dialog for editing options
            Dialog<String> editDialog = new Dialog<>();
            editDialog.setTitle("Edit Meal");
            editDialog.setHeaderText("Selected Meal: " + selectedMeal.getName());

            // Set the button types (OK and Cancel)
            ButtonType confirmButtonType = new ButtonType("Confirm", ButtonData.OK_DONE);
            editDialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

            // Create the input fields
            TextField nameField = new TextField(selectedMeal.getName());
            TextField priceField = new TextField(String.valueOf(selectedMeal.getPrice()));

            editDialog.getDialogPane().setContent(new VBox(10, new Label("New Name:"), nameField, new Label("New Price:"), priceField));

            // Convert the result to the selected category when the Edit button is clicked
            editDialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButtonType) {
                    try {
                        selectedMeal.setName(nameField.getText());
                        selectedMeal.setPrice(Double.parseDouble(priceField.getText()));
                        return "Edit";
                    } catch (NumberFormatException e) {
                        showAlert("Invalid input. Please enter valid numbers.", AlertType.ERROR);
                    }
                }
                return null;
            });

            // Show the dialog and handle the result
            Optional<String> editResult = editDialog.showAndWait();

            editResult.ifPresent(action -> {
                // Update the file after modification
                Meal2.writeMealToFile(meals);

                showAlert("Meal edited successfully.", AlertType.INFORMATION);

            });
        });
    }

    private void addUser() {
        // Create a dialog for adding a user
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add User");
        dialog.setHeaderText(null);

        // Set the button types (OK and Cancel)
        ButtonType addUserButtonType = new ButtonType("Add User", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addUserButtonType, ButtonType.CANCEL);

        // Create the input fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField userNameField = new TextField();
        PasswordField passwordField = new PasswordField();
        ComboBox<String> userTypeComboBox = new ComboBox<>();
        userTypeComboBox.setItems(FXCollections.observableArrayList("admin", "guest", "receptionist"));

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Username:"), 0, 2);
        grid.add(userNameField, 1, 2);
        grid.add(new Label("Password:"), 0, 3);
        grid.add(passwordField, 1, 3);
        grid.add(new Label("User Type:"), 0, 4);
        grid.add(userTypeComboBox, 1, 4);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the first name field by default
        Platform.runLater(firstNameField::requestFocus);

        // Convert the result to a User object when the Add User button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addUserButtonType) {
                try {
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String userName = userNameField.getText();
                    String password = passwordField.getText();
                    String userType = userTypeComboBox.getValue();

                    // Perform add user logic
                    Person2.signUp(firstName, lastName, userName, password, userType);
                } catch (Exception e) {
                    // Handle any exceptions that might occur during user addition
                    e.printStackTrace();
                }
            }
            return null;
        });

        // Show the dialog and handle the result
        Optional<ButtonType> result = dialog.showAndWait();
    }

    private void EditUser() {
        // Create a dialog for editing a user
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Edit User");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the username of the user to edit:");

        // Show the dialog and wait for the user's input
        dialog.showAndWait().ifPresent(usernameToEdit -> {
            ArrayList<Person2> persons = Person2.readUserFromFile();
            final boolean[] found = {false};

            for (Person2 person : persons) {
                if (person.getUsername().equals(usernameToEdit)) {
                    // User found, show a dialog to edit user information
                    Dialog<ButtonType> editUserDialog = new Dialog<>();
                    editUserDialog.setTitle("Edit User");
                    editUserDialog.setHeaderText(null);

                    // Set the button types (OK and Cancel)
                    ButtonType editUserButtonType = new ButtonType("Edit User", ButtonBar.ButtonData.OK_DONE);
                    editUserDialog.getDialogPane().getButtonTypes().addAll(editUserButtonType, ButtonType.CANCEL);

                    // Create the input fields
                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.setPadding(new Insets(20, 150, 10, 10));

                    TextField newPasswordField = new PasswordField();
                    newPasswordField.setPromptText("New Password");

                    grid.add(new Label("New Password:"), 0, 0);
                    grid.add(newPasswordField, 1, 0);

                    editUserDialog.getDialogPane().setContent(grid);

                    // Request focus on the new password field by default
                    Platform.runLater(newPasswordField::requestFocus);

                    // Convert the result to a User object when the Edit User button is clicked
                    editUserDialog.setResultConverter(editUserButton -> {
                        if (editUserButton == editUserButtonType) {
                            // Perform edit user logic
                            String newPassword = newPasswordField.getText();
                            person.setPassword(newPassword);

                            // Update the file after modification
                            Person2.writeUserToFile(persons);

                            // Show a confirmation alert
                            showAlert("Password for user " + usernameToEdit + " has been updated.", Alert.AlertType.INFORMATION);

                            found[0] = true;
                        }
                        return null;
                    });

                    // Show the dialog and handle the result
                    editUserDialog.showAndWait();
                    break;
                }
            }

            if (!found[0]) {
                // Show an error alert if the user is not found
                showAlert("User " + usernameToEdit + " not found.", Alert.AlertType.ERROR);
            }
        });
    }

    private void removeUser() {

        // Create a dialog for removing a user
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Remove User");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the username of the user to remove:");

        // Show the dialog and wait for the user's input
        dialog.showAndWait().ifPresent(usernameToRemove -> {
            ArrayList<Person2> persons = Person2.readUserFromFile();
            boolean found = false;

            for (int i = 0; i < persons.size(); i++) {
                Person2 person = persons.get(i);
                if (person.getUsername().equals(usernameToRemove)) {
                    // User found, show a dialog to verify the password
                    TextInputDialog passwordDialog = new TextInputDialog();
                    passwordDialog.setTitle("Remove User");
                    passwordDialog.setHeaderText(null);
                    passwordDialog.setContentText("Enter the password for user " + usernameToRemove + ":");

                    Optional<String> passwordResult = passwordDialog.showAndWait();
                    if (passwordResult.isPresent() && person.getPassword().equals(passwordResult.get())) {
                        persons.remove(i);
                        found = true;

                        // Update the file after modification
                        Person2.writeUserToFile(persons);

                        // Show a confirmation alert
                        Alert confirmationAlert = new Alert(AlertType.INFORMATION);
                        confirmationAlert.setTitle("Remove User");
                        confirmationAlert.setHeaderText(null);
                        confirmationAlert.setContentText("User " + usernameToRemove + " removed successfully.");
                        confirmationAlert.showAndWait();

                        break;
                    } else {
                        // Show an error alert if the password is incorrect
                        Alert errorAlert = new Alert(AlertType.ERROR);
                        errorAlert.setTitle("Incorrect Password");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Incorrect password for user " + usernameToRemove + ".");
                        errorAlert.showAndWait();
                        return; // Stop further processing if the password is incorrect
                    }
                }
            }

            if (!found) {
                // Show an error alert if the user is not found
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("User Not Found");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("User " + usernameToRemove + " not found.");
                errorAlert.showAndWait();
            }
        });
    }

    private void displayUsers() {
        ArrayList<Person2> persons = Person2.readUserFromFile();

        if (persons.isEmpty()) {
            showAlert("No users found.", AlertType.INFORMATION);
        } else {
            // Create a TextArea to display users
            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setWrapText(true);

            for (Person2 p : persons) {
                textArea.appendText(p.toString() + "\n");
            }

            // Create a dialog to display the users
            Alert usersAlert = new Alert(AlertType.INFORMATION);
            usersAlert.setTitle("Users List");
            usersAlert.setHeaderText(null);
            usersAlert.getDialogPane().setContent(textArea);
            usersAlert.showAndWait();
        }
    }

    public void ViewMenuReport() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Restaurant Management System");

        // Create buttons
        Button viewMostOrderedMealButton = new Button("View Most Ordered Meal");
        Button viewMealsForTableButton = new Button("View Meals for Table");
        Button numberOfOrdersBetweenTimeButton = new Button("Number of Orders Between Time");

        // Set actions for buttons
        viewMostOrderedMealButton.setOnAction(e -> viewMostOrderedMeal());
        viewMealsForTableButton.setOnAction(e -> viewMealsForTable());
        numberOfOrdersBetweenTimeButton.setOnAction(e -> numberOfOrdersBetweenTime());

        // Create layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(
                viewMostOrderedMealButton,
                viewMealsForTableButton,
                numberOfOrdersBetweenTimeButton
        );

        // Set up scene
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private void viewMostOrderedMeal() {

        Stage primaryStage = new Stage();

        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();

        Label meal = new Label("");

        Button viewButton = new Button("View Most Ordered Meals");
        viewButton.setOnAction(e -> {
            Date startDate = java.sql.Date.valueOf(startDatePicker.getValue());
            Date endDate = java.sql.Date.valueOf(endDatePicker.getValue());
            meal.setText(viewMostOrderedMealsOverTime(startDate, endDate).getName());
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(
                new Label("Start Date:"),
                startDatePicker,
                new Label("End Date:"),
                endDatePicker,
                viewButton,
                meal
        );

        Scene scene = new Scene(vbox, 400, 300);

        primaryStage.setTitle("Most Ordered Meals Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void viewMealsForTable() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Restaurant Management System");

        VBox vbox = new VBox();
        TextField tablenumberField = new TextField();
        Button tablenumberButton = new Button("Search");

        tablenumberButton.setOnAction((t) -> {
            try {
                int tableNumber = Integer.parseInt(tablenumberField.getText());
                ArrayList<Meal2> meals = viewOrderedMealsForTable(tableNumber);

                // Create a TableView to represent meals data
                TableView<Meal2> tableView = new TableView<>();

                // Assuming Meal2 has properties like name, price, description
                TableColumn<Meal2, String> nameColumn = new TableColumn<>("Name");
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Meal2, Double> priceColumn = new TableColumn<>("Price");
                priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

                TableColumn<Meal2, String> descriptionColumn = new TableColumn<>("Description");
                descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

                // Add columns to the TableView
                tableView.getColumns().addAll(nameColumn, priceColumn, descriptionColumn);

                // Add data to the TableView
                tableView.getItems().addAll(meals);

                // Add TableView to the VBox
                vbox.getChildren().add(tableView);
            } catch (NumberFormatException e) {
                // Handle the case when the input is not a valid number
                System.out.println("Please enter a valid table number.");
            }
        });

        vbox.getChildren().addAll(tablenumberField, tablenumberButton);

        primaryStage.setScene(new Scene(vbox, 400, 400));
        primaryStage.show();
    }

    private void numberOfOrdersBetweenTime() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Restaurant Management System");

        VBox vbox = new VBox();

        // Replace TextField with DatePicker
        DatePicker datePicker = new DatePicker();
        DatePicker datePicker2 = new DatePicker();

        Button tablenumberButton = new Button("Search");

        tablenumberButton.setOnAction((t) -> {
            try {
                Date startDate = java.sql.Date.valueOf(datePicker.getValue());
                Date endDate = java.sql.Date.valueOf(datePicker2.getValue());

                ArrayList<Meal2> meals = viewNumberOfOrdersOverTime(startDate, endDate);

                // Create a TableView to represent meals data
                TableView<Meal2> tableView = new TableView<>();

                // Assuming Meal2 has properties like name, price, description
                TableColumn<Meal2, String> nameColumn = new TableColumn<>("Name");
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Meal2, Double> priceColumn = new TableColumn<>("Price");
                priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

                TableColumn<Meal2, String> descriptionColumn = new TableColumn<>("Description");
                descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

                // Add columns to the TableView
                tableView.getColumns().addAll(nameColumn, priceColumn, descriptionColumn);

                // Add data to the TableView
                tableView.getItems().addAll(meals);

                // Add TableView to the VBox
                vbox.getChildren().add(tableView);
            } catch (Exception e) {
                // Handle any exceptions
                System.out.println("Error: " + e.getMessage());
            }
        });

        vbox.getChildren().addAll(datePicker, datePicker2, tablenumberButton);

        primaryStage.setScene(new Scene(vbox, 400, 400));
        primaryStage.show();
    }

    private ArrayList<Meal2> viewOrderedMealsForTable(int TableNumber) {

        // Load reservations from file
        ArrayList<Reservation2> reservations = loadReservationsFromFile();
        ArrayList<Meal2> meals = new ArrayList<>();

        for (Reservation2 r : reservations) {
            if (r.getTable().getNumber() == TableNumber) {
                meals.addAll(r.getMealList());
            }
        }

        return meals;

    }

    private ArrayList<Meal2> viewNumberOfOrdersOverTime(Date start, Date end) {
        ArrayList<Reservation2> reservations = loadReservationsFromFile();
        ArrayList<Meal2> meals = new ArrayList<>();

        for (Reservation2 r : reservations) {
            Date reservationDate = r.getTimestamp();

            if (reservationDate.after(start) && reservationDate.before(end)) {
                meals.addAll(r.getMealList());
            }
        }

        return meals;
    }

    private Meal2 viewMostOrderedMealsOverTime(Date start, Date end) {
        ArrayList<Meal2> meals = viewNumberOfOrdersOverTime(start, end);
        Map<Meal2, Integer> mealCountMap = new HashMap<>();

        for (Meal2 meal : meals) {
            mealCountMap.put(meal, mealCountMap.getOrDefault(meal, 0) + 1);
        }

        Meal2 mostOrderedMeal = null;
        int maxCount = 0;

        for (Map.Entry<Meal2, Integer> entry : mealCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostOrderedMeal = entry.getKey();
            }
        }

        return mostOrderedMeal;

    }

    private ArrayList<Reservation2> loadReservationsFromFile() {
        try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream("reservations.dat"))) {
            System.out.println("Reservations loaded successfully.");
            return (ArrayList<Reservation2>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading reservations: " + e.getMessage());
        }
        return new ArrayList<Reservation2>();
    }
}