package edu.hitsz.application;

import java.util.Random;

public class RANDOM {

    private static final Random random = new Random();

    /**
     * 左闭右闭的区间
     */
    public static int getRandom(int min, int max) {
        int len = max - min + 1;
        return random.nextInt(len) + min;
    }

    public static int getRandom(int max) {
        return random.nextInt(max);
    }

}
