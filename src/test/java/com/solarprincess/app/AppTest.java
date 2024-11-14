package com.solarprincess.app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.math3.stat.inference.TTest;

import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void mathTest() throws IOException {
        int runs = 9999999;

        GameData game = new GameData("model.xls");
        BalanceData balance = new BalanceData("model.xls");

        int[] returns = new int[runs];
        int[] hits = new int[runs];
        
        for (int i = 0; i < runs; i++) {
            GameData.ReelResult result = game.Roll();
            returns[i] = result.payout;
            hits[i] = result.payout > 0 ? 1 : 0;
        }

        TTest tTest = new TTest();

        double hitPValue = tTest.tTest(balance.hitPercent,Arrays.stream(hits).asDoubleStream().toArray());
        assertTrue(hitPValue > 0.05);
        double returnPValue = tTest.tTest(balance.returnPercent, Arrays.stream(returns).asDoubleStream().toArray());
        assertTrue(returnPValue > 0.05);
    }
}
