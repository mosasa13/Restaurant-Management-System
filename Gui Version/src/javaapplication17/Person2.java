package javaapplication17;

import javafx.scene.control.Alert;
import java.io.*;
import java.util.ArrayList;

public abstract class Person2 implements Serializable {

    private String firstName;
    private String lastName;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName
                + "\nUsername: " + username
                + "\nType: " + getClass().getSimpleName(); // Assuming you want to display the type
    }

    public Person2(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void login(String username, String password) {
        boolean found = false;
        ArrayList<Person2> persons = readUserFromFile();
        Person2 foundUser = null;
        for (Person2 u : persons) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                found = true;
                foundUser = u;
                break;
            }
        }
        if (!found) {
            showAlert("Wrong username and password", Alert.AlertType.ERROR);
        } else {
            foundUser.mainMenu();
        }
    }

    public static void signUp(String firstName, String lastName, String username, String password, String type) {
        boolean found = false;
        ArrayList<Person2> persons = readUserFromFile();

        for (Person2 p : persons) {
            if (p.getUsername().equals(username)) {
                found = true;
                break;
            }
        }

        if (!found) {
            if (type.equals("admin")) {
                Admin2 admin = new Admin2(firstName, lastName, username, password);
                persons.add(admin);
                showAlert("Admin created successfully", Alert.AlertType.INFORMATION);
            } else if (type.equals("guest")) {
                Guest2 guest = new Guest2(firstName, lastName, username, password);
                persons.add(guest);
                showAlert("Guest created successfully", Alert.AlertType.INFORMATION);
            } else if (type.equals("recepionist")) {
                Recepionist2 receptionist = new Recepionist2(firstName, lastName, username, password);
                persons.add(receptionist);
                showAlert("Receptionist created successfully", Alert.AlertType.INFORMATION);
            }
            writeUserToFile(persons);
        } else {
            showAlert("Username is taken", Alert.AlertType.ERROR);
        }
    }

    public static void writeUserToFile(ArrayList<Person2> persons) {
        try {
            FileOutputStream o = new FileOutputStream("personn.txt");
            ObjectOutputStream out = new ObjectOutputStream(o);
            out.writeObject(persons);
            out.close();
            o.close();
        } catch (Exception e) {
            showAlert(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static ArrayList<Person2> readUserFromFile() {
        ArrayList<Person2> persons = new ArrayList<>();

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("personn.txt"));
            persons = (ArrayList<Person2>) in.readObject();
            in.close();
        } catch (Exception e) {
            showAlert(e.getMessage(), Alert.AlertType.ERROR);
        }
        return persons;
    }

    public abstract void mainMenu();

    // Example method using JavaFX Alert for GUI representation
    protected static void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}