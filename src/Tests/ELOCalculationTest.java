package Tests;

import LibaryFunctions.Utility;

public class ELOCalculationTest {

        public static void main(String[] args) {

                System.out.println(Utility.CalculateNew_ELO(2000, 1800, 1));
                System.out.println(Utility.CalculateNew_ELO(2000, 1800, 0.5));
                System.out.println(Utility.CalculateNew_ELO(2000, 1800, 0));

        }
}
