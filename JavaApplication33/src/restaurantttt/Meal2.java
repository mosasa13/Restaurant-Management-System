/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantttt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.*;

/**
 *
 * @author mostafa_sasa
 */
public class Meal2 implements Serializable {

    private String name;
    private double price;
    private String description;

    public Meal2(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static void writeMealToFile(ArrayList<Meal2> meals) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("meals.dat"))) {
            oos.writeObject(meals);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Meal2> readMealFromFile() {
        ArrayList<Meal2> meals = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("meals.dat"))) {
            meals = (ArrayList<Meal2>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found (may be created for the first time)
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return meals;
    }

    @Override
    public String toString() {
        return "Meal2{" + "name=" + name + ", price=" + price + ", description=" + description + '}';
    }

}
