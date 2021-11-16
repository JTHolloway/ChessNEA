package LibaryFunctions;

import User.User;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public final class Quicksort {

    /**
     * Calls the main quicksort method
     *
     * @param unsortedList The List to be sorted via the quicksort algorithm
     * @param sortByELO    a boolean value which determines weather the list of users are to be sorted in username
     *                     alphabetical order or ELO rank order.
     *                     True = ELO Sort.
     *                     False = Username Sort.
     */
    public static void quicksort(List<User> unsortedList, final boolean sortByELO) {
        quicksort(unsortedList, 0, unsortedList.size() - 1, sortByELO);
    }

    /**
     * Recursive method which checks whether a partition of the total list has greater than 1 elements to sort. It does this
     * for each partition, if a partition does not contain more than 1 element to be sorted then that partition is sorted, Otherwise
     * that partition is partitioned further and sorted until there are no longer any partitions left to be sorted.
     *
     * @param unsortedList The List to be sorted via the quicksort algorithm
     * @param lowerBound   The lower index of a partition of the list
     * @param upperBound   The upper index of a partition of the list
     * @param sortByELO    a boolean value which determines weather the list of users are to be sorted in username
     *                     alphabetical order or ELO rank order.
     *                     True = ELO Sort.
     *                     False = Username Sort.
     */
    private static void quicksort(List<User> unsortedList, final int lowerBound, final int upperBound, final boolean sortByELO) {

        if (lowerBound < upperBound + 1) {     //If there are greater than 1 more elements left to sort THEN;
            int partition = partition(unsortedList, lowerBound, upperBound, sortByELO);    //Returns pivot index

            //Quicksort each partition either side of pivot recursively
            quicksort(unsortedList, lowerBound, partition - 1, sortByELO);
            quicksort(unsortedList, partition + 1, upperBound, sortByELO);
        }
    }

    /**
     * Takes a list with bounds to denote a partition of said list and chooses a pivot by calling the choosePivotPoint() method,
     * each value is compared with the value of the pivot, if the value is higher numerically or lower alphabetically the value is
     * swapped so that all of these values are before the pivot and all other values are after the pivot.
     *
     * @param unsortedList The List to be sorted via the quicksort algorithm
     * @param lowerBound   The lower index of a partition of the list
     * @param upperBound   The upper index of a partition of the list
     * @param sortByELO    a boolean value which determines weather the list of users are to be sorted in username
     *                     alphabetical order or ELO rank order.
     *                     True = ELO Sort.
     *                     False = Username Sort.
     * @return an integer value denoting the index of the pivot so that partitions can be taken either side of the pivot.
     */
    private static int partition(List<User> unsortedList, final int lowerBound, final int upperBound, final boolean sortByELO) {
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

    /**
     * Swaps two elements at index_A and index_B in the unsortedList
     *
     * @param unsortedList The List to be sorted via the quicksort algorithm
     * @param index_A      The index in the list of the item to be swapped with the item at index_B
     * @param index_B      The index in the list of the item to be swapped with the item at index_A
     */
    private static void swap(List<User> unsortedList, final int index_A, final int index_B) {
        User userReference = unsortedList.get(index_A);
        unsortedList.set(index_A, unsortedList.get(index_B));
        unsortedList.set(index_B, userReference);
    }

    /**
     * Selects a random index as the pivot point of a partition between two bounds
     *
     * @param lowerBound The lower index of a partition of the list
     * @param upperBound The upper index of a partition of the list
     * @return an integer value denoting the index of the pivot so that partitions can be taken either side of the pivot.
     */
    private static int choosePivotPoint(final int lowerBound, final int upperBound) {
        Random random = new Random();
        return random.nextInt((upperBound - lowerBound) + 1) + lowerBound;
    }

    /**
     * Compares numeric ELO values to be sorted in descending order
     *
     * @param unsortedList The List to be sorted via the quicksort algorithm
     * @param lowerBound   The lower index of a partition of the list
     * @param loopIterator The index of the item currently being compared in the loop of the partition method
     * @return true if value at index is greater than pivot, so that larger values appear earlier in the list (Descending order)
     */
    private static boolean numericComparison(List<User> unsortedList, final int lowerBound, final int loopIterator) {
        return unsortedList.get(loopIterator).getStatistics().getELO()
                >= unsortedList.get(lowerBound).getStatistics().getELO();
    }

    /**
     * Compares AlphaNumeric Username values to be sorted in ascending order
     *
     * @param unsortedList The List to be sorted via the quicksort algorithm
     * @param lowerBound   The lower index of a partition of the list
     * @param loopIterator The index of the item currently being compared in the loop of the partition method
     * @return true if value at index is less (alphabetically) than pivot, so that smaller alphabetic values appear
     * earlier in the list (alphabetic ascending order)
     */
    private static boolean alphaNumericComparison(List<User> unsortedList, final int lowerBound, final int loopIterator) {
        String usernameAtIterator = unsortedList.get(loopIterator).getUserName().toLowerCase(Locale.ROOT);
        String usernameAtLowerBound = unsortedList.get(lowerBound).getUserName().toLowerCase(Locale.ROOT);

        int compare = usernameAtIterator.compareTo(usernameAtLowerBound);
        return compare < 0;
    }
}
