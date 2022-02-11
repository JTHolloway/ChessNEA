package Tests;

import Game.GameOutcome;
import LibaryFunctions.Utility;

public class ELOCalculationTest {

    public static void main(String[] args) {

        System.out.println(Utility.CalculateNew_ELO(2000, 1800, GameOutcome.WIN));
        System.out.println(Utility.CalculateNew_ELO(2000, 1800, GameOutcome.DRAW));
        System.out.println(Utility.CalculateNew_ELO(2000, 1800, GameOutcome.LOSS));

    }
}
