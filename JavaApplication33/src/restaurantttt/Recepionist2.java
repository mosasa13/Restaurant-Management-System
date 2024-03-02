/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantttt;

/**
 *
 * @author Dr.Hany
 */
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author mostafa_sasa
 */
public class Recepionist2 extends Person2 implements Serializable {

    private List<Reservation2> reservations;
    private String loggedInUsername;

    public Recepionist2(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        this.reservations = new ArrayList<>();
        this.loggedInUsername = null;
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    @Override
    public void mainMenu() {
        loggedInUsername = getUsername();
        Scanner scanner = new Scanner(System.in);
        loadReservationsFromFile();
        System.out.println("\n=== Restaurant Management System ===");
        System.out.println("1. Create reservation");
        System.out.println("2. cancel reservation");
        System.out.println("3. view reservation");
        System.out.println("4. view all reservations");
        System.out.println("Enter your choice: ");
        int choicee = scanner.nextInt();
        switch (choicee) {
            case 1:

                createReservation();
                break;
            case 2:
                cancelReservation();
                break;
            case 3:
                countReservationsForReceptionists();

                break;
            case 4:
                countReservationsPerGuest();
                break;

            default:
                System.out.println("Invalid choic.");
        }

    }

    public String getReceptionistUsername() {
        return loggedInUsername;
    }

    public void createReservation() {
        loadReservationsFromFile();
        Scanner scanner = new Scanner(System.in);

        // Validate guest credentials
        Guest2 newGuest = getValidGuest(scanner);
        if (newGuest == null) {
            System.out.println("Invalid guest details. Reservation failed.");
            return;
        }

        // Gather available tables and meals data
        ArrayList<Table2> tables = Table2.readTableFromFile();
        if (tables.isEmpty()) {
            System.out.println("No tables found");
        } else {
            for (Table2 table : tables) {
                System.out.println("Table " + table.getNumber());
            }
        }

        // Ask for table selection
        System.out.println("Enter the table number for reservation:");
        int tableNumber = scanner.nextInt();

        // Check if the selected table exists
        Table2 selectedTable = null;
        for (Table2 table : tables) {
            if (table.getNumber() == tableNumber) {
                selectedTable = table;
                break;
            }
        }

        if (selectedTable != null) {
            ArrayList<Meal2> meals = Meal2.readMealFromFile();
            if (meals.isEmpty()) {
                System.out.println("No meals in the menu.");
            } else {
                System.out.println("Available Meals:");
                for (Meal2 meal : meals) {
                    System.out.println("- " + meal.getName() + " - $" + meal.getPrice());
                }

                // Ask for meal selection and quantity until the user enters "exit"
                Map<Meal2, Integer> selectedMealsWithQuantity = new HashMap<>();
                while (true) {
                    System.out.println("Enter the name of the meal for reservation (type 'exit' to finish):");
                    String mealName = scanner.next();

                    if (mealName.equalsIgnoreCase("exit")) {
                        break;
                    }

                    // Find the selected meal and get its price
                    Meal2 selectedMeal = meals.stream()
                            .filter(meal -> meal.getName().equalsIgnoreCase(mealName))
                            .findFirst()
                            .orElse(null);

                    if (selectedMeal != null) {
                        // Ask for the quantity of the selected meal
                        System.out.println("Enter the number of " + selectedMeal.getName() + " for reservation:");
                        int quantity = scanner.nextInt();

                        // Add the selected meal with its quantity to the map
                        selectedMealsWithQuantity.put(selectedMeal, quantity);
                    } else {
                        System.out.println("Invalid meal name.");
                    }
                }

                // Calculate total price
                double totalPrice = selectedMealsWithQuantity.entrySet().stream()
                        .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                        .sum();

                // Save the reservation with timestamp
                Date timestamp = new Date();  // Capture current time

                // Save the reservation
                String receptionistUsername = getReceptionistUsername();
                Reservation2 reservation = new Reservation2(newGuest, selectedTable, selectedMealsWithQuantity, totalPrice, timestamp, receptionistUsername);
                reservations.add(reservation);
                saveReservationsToFile();

                // Display reservation details
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("\nReservation Details:");
                System.out.println("Guest: " + newGuest.getFullName());
                System.out.println("Table: " + selectedTable.getNumber());
                System.out.println("Meals:");

                for (Map.Entry<Meal2, Integer> entry : selectedMealsWithQuantity.entrySet()) {
                    System.out.println("- " + entry.getKey().getName() + " - Quantity: " + entry.getValue());
                }

                System.out.println("Total Price: " + totalPrice);
                System.out.println("Reservation Time: " + dateFormat.format(timestamp));
                System.out.println("Receptionist Username: " + receptionistUsername);

                countReservationsPerGuest();
                countReservationsForReceptionists();
            }
        } else {
            System.out.println("Invalid table number. Reservation failed.");
        }
    }

    private boolean hasExistingReservation(Guest2 guest) {
        for (Reservation2 reservation : reservations) {
            Guest2 existingGuest = reservation.getGuest();
            if (existingGuest != null && existingGuest.getFirstName().equals(guest.getLastName())) {

                return true;
            }
        }
        return false;
    }

    // Add this method to load reservations from file
    private void loadReservationsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("reservations.dat"))) {
            reservations = (List<Reservation2>) ois.readObject();
            System.out.println("Reservations loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading reservations: " + e.getMessage());
        }
    }

    private Guest2 getValidGuest(Scanner scanner) {
        // Retrieve guest information from the file
        ArrayList<Person2> persons = readUserFromFile();

        // Get user input for guest details
        System.out.println("Enter guest details:");
        System.out.println("First Name:");
        String guestFirstName = scanner.next();
        System.out.println("Last Name:");
        String guestLastName = scanner.next();

        // Validate guest details against information in the file
        for (Person2 person : persons) {
            if (person instanceof Guest2) {
                Guest2 guest = (Guest2) person;
                if (guest.getFirstName().equals(guestFirstName) && guest.getLastName().equals(guestLastName)) {
                    return guest; // Valid guest found
                }
            }
        }
        return null; // Invalid guest
    }

    private void saveReservationsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("reservations.dat"))) {
            oos.writeObject(reservations);
            System.out.println("Reservation saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving reservations: " + e.getMessage());
        }
    }

    public void cancelReservation() {
        Scanner scanner = new Scanner(System.in);

        // Get guest details for canceling reservation
        System.out.println("Enter the first name of the guest:");
        String guestFirstName = scanner.next().toLowerCase();  // Convert to lowercase
        System.out.println("Enter the last name of the guest:");
        String guestLastName = scanner.next().toLowerCase();   // Convert to lowercase

        Iterator<Reservation2> iterator = reservations.iterator();
        boolean reservationFound = false;

        while (iterator.hasNext()) {
            Reservation2 reservation = iterator.next();
            Guest2 guest = reservation.getGuest();

            if (guest != null && guest.getFirstName().toLowerCase().equals(guestFirstName) && guest.getLastName().toLowerCase().equals(guestLastName)) {
                reservationFound = true;

                // Display reservation details
                displayReservationDetails(reservation);

                // Ask for the reason of cancellation
                System.out.println("Enter the reason for cancellation:");
                String cancellationReason = scanner.next();

                // Optionally, you can do something with the cancellation reason
                // Remove the reservation from the list
                iterator.remove();
                saveReservationsToFile();

                System.out.println("Reservation canceled successfully.");
                break; // Exit the loop once the reservation is found and canceled
            }
        }

        if (!reservationFound) {
            System.out.println("Reservation not found for the specified guest.");
        }
    }

    private void displayReservationDetails(Reservation2 reservation) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("\nReservation Details:");
        System.out.println("Guest: " + reservation.getGuest().getFullName());
        System.out.println("Table: " + reservation.getTable().getNumber() + " - Reserved");
        System.out.println("Meals:");

        for (Map.Entry<Meal2, Integer> entry : reservation.getMealReservations().entrySet()) {
            System.out.println("- " + entry.getKey().getName() + " - Quantity: " + entry.getValue());
        }

        System.out.println("Total Price: " + reservation.getTotalPrice());
        System.out.println("Reservation Time: " + dateFormat.format(reservation.getTimestamp()));
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
        writeReservationsAndCountsToBinaryFile(reservationsByReceptionist);

    }

    private void writeReservationsAndCountsToBinaryFile(Map<String, List<Reservation2>> reservationsByReceptionist) {
        String filePath = "reservations_and_counts.bin";

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
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

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // Save the counts and details for each guest
            Map<String, Object> reservationsInfoByGuest = new HashMap<>();
            reservationsInfoByGuest.put("countByGuest", reservationsCountByGuest);
            reservationsInfoByGuest.put("detailsByGuest", reservationsDetailsByGuest);

            oos.writeObject(reservationsInfoByGuest);

        } catch (IOException e) {

        }
    }

}
