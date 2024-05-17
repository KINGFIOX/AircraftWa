package edu.hitsz.application;

import java.util.Random;

public class RANDOM {

    private static final Random random = new Random();

    /**
     * 左闭右闭的区间
     */
    public final static int getRandom(int min, int max) {
        int len = max - min + 1;
        return random.nextInt(len) + min;
    }

    // 0 ~ max-1
    public final static int getRandom(int max) {
        return random.nextInt(max);
    }

    public final static int getRandom(int[] range) {
        int len = range[1] - range[0] + 1;
        return random.nextInt(len) + range[0];
    }

}
