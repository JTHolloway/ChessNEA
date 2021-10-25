package LibaryFunctions;

import User.User;

import java.util.List;
import java.util.Random;

public class Quicksort {

    public static void quicksort(List<User> unsortedList, boolean sortByELO) {
        quicksort(unsortedList, 0, unsortedList.size() - 1, sortByELO);
    }

    private static void quicksort(List<User> unsortedList, int lowerBound, int upperBound) {
        quicksort(unsortedList, 0, unsortedList.size() - 1, true);
    }

    private static void quicksort(List<User> unsortedList, int lowerBound, int upperBound, boolean sortByELO) {

        if (lowerBound < upperBound + 1) {     //If there are greater than 1 more elements left to sort THEN;
            int partition = partition(unsortedList, lowerBound, upperBound, sortByELO);    //Returns pivot index

            //Quicksort each partition either side of pivot recursively
            quicksort(unsortedList, lowerBound, partition - 1, sortByELO);
            quicksort(unsortedList, partition + 1, upperBound, sortByELO);
        }
    }

    private static int partition(List<User> unsortedList, int lowerBound, int upperBound, final boolean sortByELO) {
        swap(unsortedList, lowerBound, choosePivotPoint(lowerBound, upperBound));
        int border = lowerBound + 1;
        for (int i = border; i <= upperBound; i++) {
            if (sortByELO) {
                if (numericComparison(unsortedList, lowerBound, i)) {
                    swap(unsortedList, i, border++);
                }
            } else if (alphaNumericComparison(unsortedList, lowerBound, i)) {
                swap(unsortedList, i, border++);
            }
        }
        swap(unsortedList, lowerBound, border - 1);
        return border - 1;
    }

    private static void swap(List<User> unsortedList, final int index_A, final int index_B) {
        User userReference = unsortedList.get(index_A);
        unsortedList.set(index_A, unsortedList.get(index_B));
        unsortedList.set(index_B, userReference);
    }

    private static int choosePivotPoint(final int lowerBound, final int upperBound) {
        Random random = new Random();
        return random.nextInt((upperBound - lowerBound) + 1) + lowerBound;
    }

    private static boolean numericComparison(List<User> unsortedList, int lowerBound, int loopIterator) {
        return unsortedList.get(loopIterator).getStatistics().getELO()
                < unsortedList.get(lowerBound).getStatistics().getELO();
    }

    private static boolean alphaNumericComparison(List<User> unsortedList, int lowerBound, int loopIterator) {
        String usernameAtIterator = unsortedList.get(loopIterator).getUserName();
        String usernameAtLowerBound = unsortedList.get(lowerBound).getUserName();

        int compare = usernameAtIterator.compareTo(usernameAtLowerBound);
        return compare < 0;
    }
}
