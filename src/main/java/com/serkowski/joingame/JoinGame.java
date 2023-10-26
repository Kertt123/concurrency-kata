package com.serkowski.joingame;


import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.concurrent.TimeUnit.SECONDS;

public class JoinGame {

    private static final int TEAM_ROOM_SIZE = 8;
    private static CountDownLatch countDownLatch;


    public static void main(String[] args) throws IOException, InterruptedException {
        countDownLatch = new CountDownLatch(TEAM_ROOM_SIZE);
        try (ScheduledExecutorService executorService = Executors.newScheduledThreadPool(TEAM_ROOM_SIZE)) {
            for (int i = 0; i < TEAM_ROOM_SIZE; i++) {
                executorService.schedule(() -> joinTheGame(countDownLatch), ThreadLocalRandom.current().nextInt(10), SECONDS);
            }
        }
        countDownLatch.await();
        System.out.println("Team ready to start!");

    }

    private static Object joinTheGame(CountDownLatch countDownLatch) {
        System.out.println("Player Joined");
        countDownLatch.countDown();
        return "Joined!";
    }
}