/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantttt;

/**
 *
 * @author Dr.Hany
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author mostafa_sasa
 */
public abstract class Person2 implements Serializable {

    private String firstName;
    private String lastName;
    private String username;
    private String password;

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" + "firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + ", password=" + password + '}';
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
            System.out.println("Wrong username and password");
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
                System.out.println("admin created successfully");
            } else if (type.equals("guest")) {
                Guest2 guest = new Guest2(firstName, lastName, username, password);
                persons.add(guest);
                System.out.println("Guest created successfully");
            } else if (type.equals("recepionist")) {
                Recepionist2 recepionist = new Recepionist2(firstName, lastName, username, password);
                persons.add(recepionist);
                System.out.println("supplier created successfully");
            }
            writeUserToFile(persons);
        } else {
            System.out.println("Username is taken");
        }
    }

    public static void writeUserToFile(ArrayList<Person2> Persons) {
        try {
            FileOutputStream o = new FileOutputStream("Person.txt");
            ObjectOutputStream out = new ObjectOutputStream(o);
            out.writeObject(Persons);
            out.close();
            o.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ArrayList<Person2> readUserFromFile() {
        ArrayList<Person2> Persons = new ArrayList<>();

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("Person.txt"));
            Persons = (ArrayList<Person2>) in.readObject();
            in.close();
        } catch (Exception e) {
            System.out.println(e);
            return Persons;
        }
        return Persons;
    }

    public abstract void mainMenu();
}
