package com.fungwen.com.test;

public class VolatileTest {
    private static volatile int race = 0;

    private static void increase(){
        race ++;
    }

    private static final int THREAD_NUM = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREAD_NUM];
        for (int i = 0; i <THREAD_NUM; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++)
                        increase();
                }
            });
            threads[i].start();
        }

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(race);
    }
}
