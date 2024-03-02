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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author mostafa_sasa
 */
public class Table2 implements Serializable {

    private int number;
    private String Category;
    private int numberOfChairs;
    private boolean isAvailable; // Add this line

    public Table2(int number, String Category, int numberOfChairs) {
        this.number = number;
        this.Category = Category;
        this.numberOfChairs = numberOfChairs;
        this.isAvailable = true;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public int getNumberOfChairs() {
        return numberOfChairs;
    }

    public void setNumberOfChairs(int numberOfChairs) {
        this.numberOfChairs = numberOfChairs;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Table{" + "number=" + number + ", Category=" + Category + ", numberOfChairs=" + numberOfChairs + '}';
    }

    public static void writeTableToFile(ArrayList<Table2> tables) {
        try {
            FileOutputStream o = new FileOutputStream("tables.dat");
            ObjectOutputStream out = new ObjectOutputStream(o);
            out.writeObject(tables);
            out.close();
            o.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ArrayList<Table2> readTableFromFile() {
        ArrayList<Table2> tables = new ArrayList<>();

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("tables.dat"));
            tables = (ArrayList<Table2>) in.readObject();
            in.close();
        } catch (Exception e) {
            return tables;
        }
        return tables;
    }

}
