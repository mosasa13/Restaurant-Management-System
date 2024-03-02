/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantttt;

/**
 *
 * @author Dr.Hany
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author mostafa_sasa
 */
public class Guest2 extends Person2 implements Serializable {

    List<Reservation2> reservations;

    public Guest2(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
    }

    public String getFullName() {
        return super.getFullName(); // Call the getFullName method from the superclass
    }

    @Override
    public void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Restaurant Management System ===");
        System.out.println("1. View resevations");
        System.out.println("2. Rate Your Booking");
        System.out.println("Enter your choice: ");
        int choicee = scanner.nextInt();
        switch (choicee) {
            case 1:
                viewReservationHistory();
                break;
            case 2:
                System.out.println("enter rating");
                int rate;
                rate = scanner.nextInt();
                RateBooking(rate);
                break;
            default:
                System.out.println("Invalid choic.");
        }
    }

    public void viewReservationHistory() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("reservations.dat"))) {
            reservations = (List<Reservation2>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading reservations: " + e.getMessage());
        }
        for (Reservation2 r : reservations) {
            if (r.getGuest().getUsername().equals(this.getUsername())) {
                System.out.println(r);
            }
        }
    }

    public void addReservation(Reservation2 reservation) {
        reservations.add(reservation);
    }

    public void RateBooking(int rate) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("reservations.dat"))) {
            reservations = (List<Reservation2>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading reservations: " + e.getMessage());
        }

        for (Reservation2 r : reservations) {
            if (r.getGuest().getUsername().equals(this.getUsername())) {
                System.out.println("Rated Succefully . Thanks For Choosing Us!! Hope To See You Soon :))");
                reservations.remove(r);
                r.setRate(rate);
                reservations.add(r);
            }
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("reservations.dat"))) {
            oos.writeObject(reservations);
            System.out.println("Reservation saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving reservations: " + e.getMessage());
        }
    }
}
