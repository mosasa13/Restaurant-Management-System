/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package restaurantttt;

/**
 *
 * @author Dr.Hany
 */
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author mostafa_sasa
 */
public class Restaurantttt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // to view the users  -> for development onlyyyyyy (remove before decussion)
        ArrayList<Person2> persons = Person2.readUserFromFile();
        if (persons.isEmpty()) {
            System.out.println("no users found");
        } else {
            for (Person2 p : persons) {
                System.out.println(p);
            }

        }
        ArrayList<Table2> tables = Table2.readTableFromFile();
        if (tables.isEmpty()) {
            System.out.println("no tables found");
        } else {
            for (Table2 t : tables) {
                System.out.println(t);
            }

        }

        Scanner in = new Scanner(System.in);
        System.out.println("1 login ");
        System.out.println("2 sign up");
        int x = in.nextInt();
        if (x == 1) {
            System.out.println("Enter username :");
            String userName = in.next();
            System.out.println("Enter password :");
            String password = in.next();
            Person2.login(userName, password);
        } else if (x == 2) {
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
    }

}
