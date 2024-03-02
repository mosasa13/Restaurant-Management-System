/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantttt;

/**
 *
 * @author Dr.Hany
 */
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mostafa_sasa
 */
public class Reservation2 implements Serializable {

    private static final long serialVersionUID = 1L;
    private Guest2 guest;
    private Table2 table;
    private List<Meal2> meals;
    private double totalPrice;
    private Date timestamp;
    private Map<Meal2, Integer> mealReservations;
    private int rate;
    private String receptionistUsername;

    public Reservation2(Guest2 guest, Table2 table, Map<Meal2, Integer> mealReservations, double totalPrice, Date timestamp, String receptionistUsername) {
        this.guest = guest;
        this.table = table;
        this.meals = meals;
        this.totalPrice = totalPrice;
        this.mealReservations = mealReservations;
        this.timestamp = new Date();
        this.receptionistUsername = receptionistUsername;
    }

    public Guest2 getGuest() {
        return guest;
    }

    public Table2 getTable() {
        return table;
    }

    public List<Meal2> getMeals() {
        return meals;
    }

    public Map<Meal2, Integer> getMealReservations() {
        return mealReservations;
    }

    public ArrayList<Meal2> getMealList() {
        ArrayList<Meal2> mealList = new ArrayList<>();

        // Iterate through the mealReservations Map
        for (Map.Entry<Meal2, Integer> entry : mealReservations.entrySet()) {
            Meal2 meal = entry.getKey();
            int quantity = entry.getValue();

            // Add each meal based on its quantity to the mealList
            for (int i = 0; i < quantity; i++) {
                mealList.add(meal);
            }
        }

        return mealList;
    }

    public void setMealReservations(Map<Meal2, Integer> mealReservations) {
        this.mealReservations = mealReservations;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getReceptionistUsername() {
        return receptionistUsername;
    }

    @Override
    public String toString() {
//        StringBuilder mealNames = new StringBuilder();
//        for (Meal2 meal : meals) {
//            mealNames.append(meal.getName()).append(", ");
//        }
//        // Remove the trailing comma and space
//        if (mealNames.length() > 2) {
//            mealNames.setLength(mealNames.length() - 2);
//        }

        return "Reservation Details:\n"
                + "Guest: " + guest.getFullName() + "\n"
                + "Table: " + table.getNumber() + " - Reserved\n"
                + "Meals: " + mealReservations + "\n"
                + "Total Price: " + totalPrice + "\n"
                + "Reservation Time: " + timestamp
                + "\nRate: " + rate;
    }

}
