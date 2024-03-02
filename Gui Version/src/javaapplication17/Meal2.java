/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication17;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author Dr.Hany
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
        try ( ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("meals.dat"))) {
            out.writeObject(meals);

            out.writeObject(meals);
            out.close();
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ArrayList<Meal2> readMealFromFile() {
        ArrayList<Meal2> meals = new ArrayList<>();

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("meals.dat"));
            meals = (ArrayList<Meal2>) in.readObject();
            in.close();
            System.out.println("Meals read from file successfully.");
        } catch (Exception e) {

            e.printStackTrace();
        }
        return meals;
    }

    @Override
    public String toString() {
        return "Meal2{" + "name=" + name + ", price=" + price + ", description=" + description + '}';
    }

}