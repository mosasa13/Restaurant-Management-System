package javaapplication17;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

public class Table2 extends Application implements Serializable {

    private int number;
    private String category;
    private int numberOfChairs;
   
        private boolean highlighted;

    public Table2(int number, String category, int numberOfChairs) {
        this.number = number;
        this.category = category;
        this.numberOfChairs = numberOfChairs;
          this.highlighted = false;
    }
public boolean isHighlighted() {
            return highlighted;
        }

        public void setHighlighted(boolean highlighted) {
            this.highlighted = highlighted;
        }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNumberOfChairs() {
        return numberOfChairs;
    }

    public void setNumberOfChairs(int numberOfChairs) {
        this.numberOfChairs = numberOfChairs;
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

     @Override
    public String toString() {
        return "Table " + getNumber(); // Assuming getNumber() is a method that returns the table number
    }

    @Override
    public void start(Stage primaryStage) {
        // Application start method
    }

    public static void main(String[] args) {
        launch(args);
    }
}