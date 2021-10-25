package Tests;

import LibaryFunctions.Quicksort;
import LibaryFunctions.Repository;
import User.User;

import java.util.List;

public class QuicksortTest {

    public static void main(String[] args) {

        List<User> users = Repository.getUsers();
        printArray(users);

        System.out.println("\n");

        ELO_Quicksort(users);
        //Username_Quicksort(users);

        printArray(users);


    }

    private static void ELO_Quicksort(List<User> users) {
        Quicksort.quicksort(users, true);
    }

    private static void Username_Quicksort(List<User> users) {
        Quicksort.quicksort(users, false);
    }

    private static void printArray(List<User> list) {
        for (User u :
                list) {
            System.out.println(u.getUserName() + " " + u.getStatistics().getELO());
        }
    }
}
