/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantttt;

/**
 *
 * @author Dr.Hany
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.List;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author mostafa_sasa
 */
public class Admin2 extends Person2 implements Serializable {

    public Admin2(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
    }

    @Override
    public void mainMenu() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Restaurant Management System ===");
        System.out.println("1. Add Table");
        System.out.println("2. Edit Table");
        System.out.println("3. Remove Table");
        System.out.println("4. Search Table");
        System.out.println("5. View Table Reports");
        System.out.println("6. Create menu");
        System.out.println("7.Edit menu");
        System.out.println("8.Remove Menu");
        System.out.println("9.Add user");
        System.out.println("10.Edit User");
        System.out.println("11.Remove User");
        System.out.println("12.Display User");
        System.out.println("13.display meals");
        System.out.println("14.View User Reports");
        System.out.println("15.View Menu Reports");
        System.out.println("16.View Reservation Reports");
        System.out.println("16.Exit");

        System.out.println("Enter your choice: ");
        int choicee = scanner.nextInt();

        switch (choicee) {
            case 1:

                addTable();
                break;
            case 2:
                EditTable();
                break;
            case 3:
                RemoveTable();
                break;
            case 4:
                SearchTable();
                break;
            case 5:
                break;
            case 6:
                AddMeal();
                break;
            case 7:
                editMeal();
                break;
            case 8:
                removeMeal();
                break;
            case 9:
                addUser();
                break;
            case 10:
                EditUser();
                break;
            case 11:
                displayUsers();
                System.out.println("Enter the username of the user you want to delete :");
                String username = scanner.next();
                System.out.println("Enter the password");
                String password = scanner.next();
                removeUser(username, password);
                break;
            case 12:
                displayUsers();
                break;
            case 13:
                displaymeals();
                break;

            case 14:
                viewUserReports();

                // Additional actions if needed
                System.exit(0);
            case 15:
                viewMenuReports();
                break;
            case 16:
                viewReservationReports();
                break;
            case 17:

                System.exit(0); // Terminate the program
                break;
            default:
                System.out.println("Invalid choic.");
        }

    }

    public void addUser() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter first name :");
        String firstName = in.next();
        System.out.println("Enter last name  :");
        String lastName = in.next();
        System.out.println("Enter username :");
        String userName = in.next();
        System.out.println("Enter password :");
        String password = in.next();
        System.out.println("Enter type :");
        String type = in.next();
        Person2.signUp(firstName, lastName, userName, password, type);
    }

    public void EditUser() {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for the username whose password they want to edit
        System.out.println("Enter the username of the user whose password you want to edit: ");
        String usernameToEdit = scanner.next();

        ArrayList<Person2> persons = Person2.readUserFromFile();
        boolean found = false;

        for (Person2 person : persons) {
            if (person.getUsername().equals(usernameToEdit)) {
                // User found, prompt for a new password
                System.out.println("Enter the new password for user " + usernameToEdit + ": ");
                String newPassword = scanner.next();

                // Update the password of the matching user
                person.setPassword(newPassword);

                // Update the file after modification
                Person2.writeUserToFile(persons);

                System.out.println("Password for user " + usernameToEdit + " has been updated.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("User " + usernameToEdit + " not found.");
        }
    }

    public void removeUser(String username, String password) {

        ArrayList<Person2> persons = Person2.readUserFromFile();
        boolean found = false;

        for (int i = 0; i < persons.size(); i++) {
            Person2 person = persons.get(i);
            if (person.getUsername().equals(username)) {
                // User found, verify the password
                if (person.getPassword().equals(password)) {
                    persons.remove(i);
                    found = true;
                    break;
                } else {
                    System.out.println("Incorrect password for user " + username + ".");
                    return; // Stop further processing if the password is incorrect
                }
            }
        }

        if (found) {
            // Update the file after modification
            Person2.writeUserToFile(persons);
            System.out.println("User " + username + " removed successfully.");
        } else {
            System.out.println("User " + username + " not found.");
        }
    }

    public void displayUsers() {
        ArrayList<Person2> persons = Person2.readUserFromFile();
        if (persons.isEmpty()) {
            System.out.println("no users found");
        } else {
            for (Person2 p : persons) {
                System.out.println(p);
            }

        }

    }

    public void addTable() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter table number :");
        int number = in.nextInt();
        System.out.println("Enter table Category  :");
        String category = in.next();
        System.out.println("Enter number Of Chairs :");
        int numberOfChairs = in.nextInt();
        Table2 table = new Table2(number, category, numberOfChairs);
        ArrayList<Table2> tables = Table2.readTableFromFile();
        tables.add(table);
        Table2.writeTableToFile(tables);
    }

    public void EditTable() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the table number to edit: ");
        int tableNumberToEdit = scanner.nextInt();

        ArrayList<Table2> tables = Table2.readTableFromFile();

        boolean found = false;

        for (Table2 table : tables) {
            if (table.getNumber() == tableNumberToEdit) {
                System.out.println("Enter the new category for table " + tableNumberToEdit + ": ");
                String newCategory = scanner.next();

                // Update the category of the matching table
                table.setCategory(newCategory);

                // Update the file after modification
                Table2.writeTableToFile(tables);

                System.out.println("Table " + tableNumberToEdit + " category has been updated to '" + newCategory + "'.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Table " + tableNumberToEdit + " not found.");
        }
    }

    public void RemoveTable() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the table number to remove: ");
        int tableNumberToRemove = scanner.nextInt();

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
            System.out.println("Table " + tableNumberToRemove + " removed successfully.");
        } else {
            System.out.println("Table " + tableNumberToRemove + " not found.");
        }
    }

    public void SearchTable() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the table number to search: ");
        int tableNumberToSearch = scanner.nextInt();

        ArrayList<Table2> tables = Table2.readTableFromFile();
        boolean found = false;

        for (Table2 table : tables) {
            if (table.getNumber() == tableNumberToSearch) {
                System.out.println("Table Information:");
                System.out.println("Table Number: " + table.getNumber());
                System.out.println("Category: " + table.getCategory());
                System.out.println("Number of Chairs: " + table.getNumberOfChairs());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Table " + tableNumberToSearch + " not found.");
        }
    }

    public void AddMeal() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of meals you want to add:");
        int numberOfMeals = scanner.nextInt();

        for (int i = 0; i < numberOfMeals; i++) {
            System.out.println("Enter meal name:");
            String name = scanner.next();
            System.out.println("Enter meal price:");
            double price = scanner.nextDouble();
            System.out.println("Enter meal description:");
            String description = scanner.next();

            Meal2 meal = new Meal2(name, price, description);
            ArrayList<Meal2> meals = Meal2.readMealFromFile();
            meals.add(meal);
            Meal2.writeMealToFile(meals);
        }

        displaymeals();
        System.out.println(numberOfMeals + " meals added successfully");

    }

    public void displaymeals() {
        System.out.println("\n=== Menu ===");

        ArrayList<Meal2> meals = Meal2.readMealFromFile();

        if (meals.isEmpty()) {
            System.out.println("No meals in the menu.");
        } else {
            for (int i = 0; i < meals.size(); i++) {
                Meal2 meal = meals.get(i);
                System.out.println((i + 1) + ". " + meal.getName() + " - $" + meal.getPrice());
            }
        }

    }

    private Table2 findTableByNumber(int tableNumber) {
        ArrayList<Table2> tables = Table2.readTableFromFile();
        for (Table2 table : tables) {
            if (table.getNumber() == tableNumber) {
                return table;
            }
        }
        return null;

    }

    public void removeMeal() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the meal to remove:");
        String nameToRemove = scanner.next();

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
            System.out.println("Meal '" + nameToRemove + "' removed successfully.");
        } else {
            System.out.println("Meal '" + nameToRemove + "' not found.");
        }

    }

    public void editMeal() {
        Scanner scanner = new Scanner(System.in);

        // Display meals to choose from
        displaymeals();

        System.out.println("Enter the number of the meal you want to edit:");
        int mealNumber = scanner.nextInt();

        ArrayList<Meal2> meals = Meal2.readMealFromFile();

        if (mealNumber >= 1 && mealNumber <= meals.size()) {
            Meal2 selectedMeal = meals.get(mealNumber - 1);

            System.out.println("Selected Meal: " + selectedMeal.getName());

            // Prompt for editing options
            System.out.println("1. Edit Name");
            System.out.println("2. Edit Price");
            System.out.println("3. Cancel");

            System.out.println("Enter your choice:");
            int editChoice = scanner.nextInt();

            switch (editChoice) {
                case 1:
                    System.out.println("Enter the new name:");
                    String newName = scanner.next();
                    selectedMeal.setName(newName);
                    break;
                case 2:
                    System.out.println("Enter the new price:");
                    double newPrice = scanner.nextDouble();
                    selectedMeal.setPrice(newPrice);
                    break;
                case 3:
                    System.out.println("Editing canceled.");
                    return;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            // Update the file after modification
            Meal2.writeMealToFile(meals);

            System.out.println("Meal edited successfully.");
            displaymeals(); // Display the updated menu
        } else {
            System.out.println("Invalid meal number.");
        }
    }

    public void viewTableReports() {

    }

    private ArrayList<Reservation2> loadReservationsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("reservations.dat"))) {
            System.out.println("Reservations loaded successfully.");
            return (ArrayList<Reservation2>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading reservations: " + e.getMessage());
        }
        return new ArrayList<Reservation2>();
    }

    public void viewMenuReports() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Menu Reports ===");
        System.out.println("1. Ordered Meals for a Specific Table");
        System.out.println("2. Number of Orders Over a Specified Period of Time");
        System.out.println("3. Most Ordered Meals Over a Specified Period of Time");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                // Call the method to view ordered meals for a specific table
                System.out.println("Enter table number");
                int x = scanner.nextInt();
                System.out.println(viewOrderedMealsForTable(x));
                break;
            case 2:
                // Enter the start and end dates for the specified period of time
                Date startDate = enterDate("Enter the start date (yyyy-MM-dd HH:mm:ss): ");
                Date endDate = enterDate("Enter the end date (yyyy-MM-dd HH:mm:ss): ");
                // Call the method to view the number of orders over a specified period of time
                System.out.println(viewNumberOfOrdersOverTime(startDate, endDate));
                break;
            case 3:
                // Call the method to view the most ordered meals over a specified period of time
                Date startDate2 = enterDate("Enter the start date (yyyy-MM-dd HH:mm:ss): ");
                Date endDate2 = enterDate("Enter the end date (yyyy-MM-dd HH:mm:ss): ");
                // Call the method to view the number of orders over a specified period of time
                System.out.println(viewMostOrderedMealsOverTime(startDate2, endDate2));
                break;
            default:
                System.out.println("Invalid choice.");
        }
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

    private Date enterDate(String prompt) {
        while (true) {
            System.out.println(prompt);
            Scanner scanner = new Scanner(System.in);
            String dateString = scanner.next();

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return dateFormat.parse(dateString);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format 'yyyy-MM-dd'.");
            }
        }
    }

    public void viewUserReports() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== User Reports ===");
        System.out.println("1. View the number of reservations with each receptionist");
        System.out.println("2. View the Recepionist with maximum number of reservations");
        System.out.println("3. View the Recepionist with maximum revenue");
        System.out.println("4. View number of reservations per each guest and their details");
        System.out.println("5. View the guest with maximum revenue");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                readReservationsDetailsAndCountsFromFile();
                break;
            case 2:
                viewReceptionistWithMaxReservations();
                break;
            case 3:
                viewReceptionistWithMaxRevenue();
                break;
            case 4:
                readReservationsInfoByGuestFromFile();
                break;
            case 5:
                viewGuestWithMaxRevenue();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    public void readReservationsDetailsAndCountsFromFile() {
        String filePath = "reservations_and_counts.bin";

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Map<String, List<Reservation2>> reservationsDetailsAndCounts = (Map<String, List<Reservation2>>) ois.readObject();

            if (reservationsDetailsAndCounts.isEmpty()) {
                System.out.println("No reservations details and counts found.");
            } else {
                System.out.println("Reservations Details and Counts:");

                for (Map.Entry<String, List<Reservation2>> entry : reservationsDetailsAndCounts.entrySet()) {
                    String receptionistUsername = entry.getKey();
                    List<Reservation2> reservationsForReceptionist = entry.getValue();

                    System.out.println("Receptionist: " + receptionistUsername + " - Reservations Count: " + reservationsForReceptionist.size());

                    // Display details for each reservation
                    for (Reservation2 reservation : reservationsForReceptionist) {
                        // Display only relevant details; you can customize as needed
                        System.out.println("- Guest: " + reservation.getGuest().getFullName());
                        System.out.println("- Table: " + reservation.getTable().getNumber() + " - Reserved");
                        System.out.print("- Meals:  ");
                        for (Map.Entry<Meal2, Integer> entry1 : reservation.getMealReservations().entrySet()) {
                            System.out.println(entry1.getKey().getName() + " - Quantity: " + entry1.getValue());
                        }
                        System.out.println("- Total Price: " + reservation.getTotalPrice());
                        System.out.println("- Reservation Time: " + reservation.getTimestamp());
                        System.out.println(); // Separate entries for different reservations
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading reservations details and counts from file: " + e.getMessage());
        }
    }

    public void viewReceptionistWithMaxReservations() {
        Map<String, List<Reservation2>> reservationsDetailsAndCounts = readReservationsDetailsAndCountsFromFilee();

        if (reservationsDetailsAndCounts.isEmpty()) {
            System.out.println("No reservations details and counts found.");
            return;
        }

        String receptionistWithMaxReservations = null;
        int maxReservations = 0;

        for (Map.Entry<String, List<Reservation2>> entry : reservationsDetailsAndCounts.entrySet()) {
            String receptionistUsername = entry.getKey();
            List<Reservation2> reservationsForReceptionist = entry.getValue();
            int reservationsCount = reservationsForReceptionist.size();

            if (reservationsCount > maxReservations) {
                maxReservations = reservationsCount;
                receptionistWithMaxReservations = receptionistUsername;
            }
        }

        if (receptionistWithMaxReservations != null) {
            System.out.println("Receptionist with Maximum Reservations:");
            System.out.println("Receptionist: " + receptionistWithMaxReservations + " - Reservations Count: " + maxReservations);
        } else {
            System.out.println("No receptionist found with reservations.");
        }
    }

    public Map<String, List<Reservation2>> readReservationsDetailsAndCountsFromFilee() {
        String filePath = "reservations_and_counts.bin";

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Map<String, List<Reservation2>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading reservations details and counts from file: " + e.getMessage());
            return Collections.emptyMap();
        }
    }

    public void viewReceptionistWithMaxRevenue() {
        Map<String, List<Reservation2>> reservationsDetailsAndCounts = readReservationsDetailsAndCountsFromFilee();

        if (reservationsDetailsAndCounts.isEmpty()) {
            System.out.println("No reservations details and counts found.");
            return;
        }

        String receptionistWithMaxRevenue = null;
        double maxRevenue = 0.0;
        int maxReservations = 0;

        for (Map.Entry<String, List<Reservation2>> entry : reservationsDetailsAndCounts.entrySet()) {
            String receptionistUsername = entry.getKey();
            List<Reservation2> reservationsForReceptionist = entry.getValue();

            // Calculate total revenue for the current receptionist
            double totalRevenue = 0.0;
            for (Reservation2 reservation : reservationsForReceptionist) {
                totalRevenue += reservation.getTotalPrice();
            }

            // Check if the current receptionist has maximum revenue
            if (totalRevenue > maxRevenue) {
                maxRevenue = totalRevenue;
                receptionistWithMaxRevenue = receptionistUsername;
                maxReservations = reservationsForReceptionist.size();
            }
        }

        if (receptionistWithMaxRevenue != null) {
            System.out.println("Receptionist with Maximum Revenue:");
            System.out.println("Receptionist: " + receptionistWithMaxRevenue + " - Reservations Count: " + maxReservations
                    + " - Total Revenue: $" + maxRevenue);
        } else {
            System.out.println("No receptionist found with revenue.");
        }
    }

    public void readReservationsInfoByGuestFromFile() {
        String filePath = "reservations_info_by_guest.bin";

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            // Read reservations information by guest from the file
            Map<String, Object> reservationsInfoByGuest = (Map<String, Object>) ois.readObject();

            // Extract counts and details for each guest
            Map<String, Integer> reservationsCountByGuest = (Map<String, Integer>) reservationsInfoByGuest.get("countByGuest");
            Map<String, List<Reservation2>> reservationsDetailsByGuest = (Map<String, List<Reservation2>>) reservationsInfoByGuest.get("detailsByGuest");

            // Display reservations information by guest
            displayReservationsInfoByGuest(reservationsCountByGuest, reservationsDetailsByGuest);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading reservations info by guest from " + filePath + ": " + e.getMessage());
        }
    }

    private void displayReservationsInfoByGuest(Map<String, Integer> reservationsCountByGuest,
            Map<String, List<Reservation2>> reservationsDetailsByGuest) {
        System.out.println("Reservations Count and Details for Each Guest:");

        for (Map.Entry<String, Integer> entry : reservationsCountByGuest.entrySet()) {
            String guestFullName = entry.getKey();
            int reservationsCount = entry.getValue();

            System.out.println("Guest: " + guestFullName + " - Reservations Count: " + reservationsCount);

            // Display details for each reservation
            List<Reservation2> reservationsForGuest = reservationsDetailsByGuest.get(guestFullName);
            if (reservationsForGuest != null) {
                for (Reservation2 reservation : reservationsForGuest) {
                    // Display only relevant details; you can customize as needed
                    System.out.println("- Guest: " + reservation.getGuest().getFullName());
                    System.out.println("- Table: " + reservation.getTable().getNumber() + " - Reserved");
                    System.out.print("- Meals:  ");
                    for (Map.Entry<Meal2, Integer> entry1 : reservation.getMealReservations().entrySet()) {
                        System.out.println(entry1.getKey().getName() + " - Quantity: " + entry1.getValue());
                    }
                    System.out.println("- Total Price: " + reservation.getTotalPrice());
                    System.out.println("- Reservation Time: " + reservation.getTimestamp());
                    System.out.println(); // Separate entries for different reservations
                }
            }

            System.out.println(); // Separate entries for different guests
        }

    }

    public void viewGuestWithMaxRevenue() {
        Map<String, Object> reservationsInfoByGuest = readReservationsInfoByGuestFromFilee();

        if (reservationsInfoByGuest.isEmpty()) {
            System.out.println("No reservations information by guest found.");
            return;
        }

        String guestWithMaxRevenue = null;
        double maxRevenue = 0.0;

        Map<String, Integer> reservationsCountByGuest = (Map<String, Integer>) reservationsInfoByGuest.get("countByGuest");
        Map<String, List<Reservation2>> reservationsDetailsByGuest = (Map<String, List<Reservation2>>) reservationsInfoByGuest.get("detailsByGuest");

        for (Map.Entry<String, Integer> entry : reservationsCountByGuest.entrySet()) {
            String guestFullName = entry.getKey();
            int reservationsCount = entry.getValue();

            List<Reservation2> reservationsForGuest = reservationsDetailsByGuest.get(guestFullName);

            // Calculate total revenue for the current guest
            double totalRevenue = 0.0;
            if (reservationsForGuest != null) {
                for (Reservation2 reservation : reservationsForGuest) {
                    totalRevenue += reservation.getTotalPrice();
                }
            }

            // Check if the current guest has maximum revenue
            if (totalRevenue > maxRevenue) {
                maxRevenue = totalRevenue;
                guestWithMaxRevenue = guestFullName;
            }
        }

        if (guestWithMaxRevenue != null) {
            System.out.println("Guest with Maximum Revenue:");
            System.out.println("Guest: " + guestWithMaxRevenue + " - Total Revenue: $" + maxRevenue);
        } else {
            System.out.println("No guest found with revenue.");
        }
    }

    private Map<String, Object> readReservationsInfoByGuestFromFilee() {
        String filePath = "reservations_info_by_guest.bin";

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            // Read reservations information by guest from the file
            return (Map<String, Object>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading reservations info by guest from " + filePath + ": " + e.getMessage());
            return new HashMap<>(); // Return an empty map in case of an error
        }
    }

    public void viewAllReservations() {
        // Load reservations from file
        ArrayList<Reservation2> reservations = loadAllReservationsFromFile();

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("=== All Reservation Details ===");
            for (Reservation2 reservation : reservations) {
                displayReservationDetails(reservation);
                System.out.println("-------------------------");
            }
        }
    }
    // Helper method to load all reservations from file
    private ArrayList<Reservation2> loadAllReservationsFromFile() {
        ArrayList<Reservation2> allReservations = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("reservations.dat"))) {
            List<Serializable> serializableList = (List<Serializable>) ois.readObject();
            for (Serializable obj : serializableList) {
                if (obj instanceof Reservation2) {
                    allReservations.add((Reservation2) obj);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading reservations: " + e.getMessage());
        }

        return allReservations;
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
    System.out.println("Receptionist Username: " + reservation.getReceptionistUsername());
}
    
    public void calculateAverageThroughSpecificTimeRange() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter start date and time (yyyy-MM-dd HH:mm:ss): ");
        String startDateTimeStr = scanner.nextLine();

        System.out.println("Enter end date and time (yyyy-MM-dd HH:mm:ss): ");
        String endDateTimeStr = scanner.nextLine();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date startDateTime = dateFormat.parse(startDateTimeStr);
            Date endDateTime = dateFormat.parse(endDateTimeStr);

            // Load reservations from file
            ArrayList<Reservation2> reservations = loadAllReservationsFromFile();

            if (reservations.isEmpty()) {
                System.out.println("No reservations found.");
            } else {
                System.out.println("=== Reservations Within the Specified Time Range ===");
                int count = 0;
                double total = 0;

                for (Reservation2 reservation : reservations) {
                    Date reservationTime = reservation.getTimestamp();

                    if (reservationTime.after(startDateTime) && reservationTime.before(endDateTime)) {
                        displayReservationDetails(reservation);
                        System.out.println("-------------------------");

                        count++;
                        total += reservation.getTotalPrice();
                    }
                }

                if (count > 0) {
                    double average = total / count;
                    System.out.println("Average Total Price: " + average);
                } else {
                    System.out.println("No reservations found within the specified time range.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
    }
    
    
   public void viewReservationReports() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Reservation Reports Menu ===");
        System.out.println("1. View All Reservation Details");
        System.out.println("2. Calculate Average Through Specific Time Range");
        System.out.println("Enter your choice: ");

        int choice = scanner.nextInt();
         
        switch (choice) {
            case 1:
                viewAllReservations();
                break;
                
            case 2:
              calculateAverageThroughSpecificTimeRange();              
               break;
            default:
                System.out.println("Invalid choice.");
        }
    }

}