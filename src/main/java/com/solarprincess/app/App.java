package com.solarprincess.app;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Expecting number of trials as the argument");
            System.exit(1);
        }
        GameData gd = new GameData("model.xls");
        int trials = Integer.parseInt(args[0]);
        int totalResult = 0;
        int totalHits = 0;
        for (int i = 0; i < trials; i++ ) {
            int result = gd.Roll();
            totalResult += result;
            if (result > 0) totalHits += 1;
        }
        float hitFrequency = (float)totalHits / (float)trials;
        float returnToPlayer = (float)totalResult / (float)trials;
        System.out.println(String.format("Total Outcome %d", totalResult));
        System.out.println(String.format("Total Hit Frequency %f", hitFrequency));
        System.out.println(String.format("Return to Player %f", returnToPlayer));
    }
}
