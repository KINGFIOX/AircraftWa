package edu.hitsz.observe;

import edu.hitsz.application.CONFIG;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.propfactory.*;

import java.util.Random;

public class PropGenerator {

    private static PropGenerator m_instance;

    private PropGenerator() {}

    public static PropGenerator getInstace() {
        // 第一次检查，避免不必要的同步
        if (m_instance == null) {
            // 同步块，对类对象加锁
            synchronized (HeroAircraft.class) {
                // 第二次检查，防止多线程问题
                if (m_instance == null) {
                    m_instance = new PropGenerator();
                    System.out.println("m_instance created: " + m_instance);
                }
            }
        }
        return m_instance;
    }


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
        int level;

        // 概率问题
        if ((Math.random() * 100) < 50) {
            return null;
        }

        // 根据敌机类型通过对应工厂创建实例
        switch (type) {
            case BOMB:
                level = 0;
                return bombFactory.createProp(locationX, locationY, speedX, speedY, CONFIG.Prop.PROP_SCORE, CONFIG.Prop.PROP_DURATION, level);
            case BLOOD:
                level = (int) (Math.random() * 100);
                return bloodFactory.createProp(locationX, locationY, speedX, speedY, CONFIG.Prop.PROP_SCORE, CONFIG.Prop.PROP_DURATION, level);
            // TODO 暂时不加入 BOSS
            case BULLET:
                level = 1;
                return bulletFactory.createProp(locationX, locationY, speedX, speedY, CONFIG.Prop.PROP_SCORE, CONFIG.Prop.PROP_DURATION, level);
            case BULLETPLUS:
                level = 1;
                return bulletPlusFactory.createProp(locationX, locationY, speedX, speedY, CONFIG.Prop.PROP_SCORE, CONFIG.Prop.PROP_DURATION, level);
            default:
                // 暂时默认生成
                return null;
        }
    }
}
