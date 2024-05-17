package edu.hitsz.observe;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.propfactory.*;

import java.util.Random;

public class PropGenerator {

    private int maxScore;
    private int dura_bullet;
    private int dura_bullet_plus;
    private int probability_blood;
    private int probability_bomb;
    private int probability_bullet;
    private int probability_bullet_plus;
    private int maxBlood;
    private int maxBomb;

    /**
     * maxScore
     * duration
     * probability 比方说 90 就是 90%
     * maxBomb 比方说 25 就是 elite 最多 - 25 % 的血量
     */
    public PropGenerator(
            int maxScore,
            int maxBlood,
            int probability_blood,
            int maxBomb,
            int probability_bomb,
            int dura_bullet,
            int probability_bullet,
            int dura_bullet_plus,
            int probability_bullet_plus
    ) {
        this.maxScore = maxScore;
        this.maxBlood = maxBlood;
        this.maxBomb = maxBomb;
        this.probability_blood = probability_blood;
        this.probability_bomb = probability_bomb;
        this.dura_bullet = dura_bullet;
        this.probability_bullet = probability_bullet;
        this.dura_bullet_plus = dura_bullet_plus;
        this.probability_bullet_plus = probability_bullet_plus;
    }

    // 这些工厂确实可以是 static
    private static final IPropFactory bloodFactory = new BloodPropFactory();
    private static final IPropFactory bombFactory = new BombPropFactory();
    private static final IPropFactory bulletFactory = new BulletPropFactory();
    private static final IPropFactory bulletPlusFactory = new BulletPlusPropFactory();

    // 用来获取整数的
    private static final Random random = new Random();

    public BaseProp generateProp(int locationX, int locationY) {
        // 随机选择敌机类型
        EPropType type = EPropType.values()[random.nextInt(EPropType.values().length)];

        // 敌机的初始位置和属性，这里仅为示例，实际可能需要更合理的生成逻辑
        int speedX = random.nextInt(10) + 5;
        int speedY = random.nextInt(10) + 5; // 假设速度在5到14之间

        // 概率问题
        if (random.nextInt(100) < 50) {
            return null;
        }

        int score = random.nextInt(maxScore);
        int blood = random.nextInt(maxBlood);
        int bomb = random.nextInt(maxBomb);

        if (random.nextInt(100) < probability_bomb) {
            return bombFactory.createProp(locationX, locationY, speedX, speedY, score, -1, bomb);
        }

        if (random.nextInt(100) < probability_bullet) {
            return bulletFactory.createProp(locationX, locationY, speedX, speedY, score, dura_bullet, -1);
        }

        if (random.nextInt(100) < probability_bullet_plus) {
            return bulletPlusFactory.createProp(locationX, locationY, speedX, speedY, score, dura_bullet_plus, -1);
        }

        if (random.nextInt(100) < probability_blood) {
            return bloodFactory.createProp(locationX, locationY, speedX, speedY, score, -1, blood);
        }

        // 实在倒霉
        return null;
    }
}
