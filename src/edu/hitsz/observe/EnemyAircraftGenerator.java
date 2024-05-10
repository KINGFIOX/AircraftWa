package edu.hitsz.observe;

import edu.hitsz.application.CONFIG;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.enemy.EnemyAircraft;
import edu.hitsz.application.AircraftWar;
import edu.hitsz.application.ImageManager;
import edu.hitsz.enemyfactory.*;

import java.util.Random;

public class EnemyAircraftGenerator implements ISubscriber {

    private EnemyAircraftGenerator() {
    }

    private static EnemyAircraftGenerator m_instance;

    public static EnemyAircraftGenerator getInstace() {
        // 第一次检查，避免不必要的同步
        if (m_instance == null) {
            // 同步块，对类对象加锁
            synchronized (HeroAircraft.class) {
                // 第二次检查，防止多线程问题
                if (m_instance == null) {
                    m_instance = new EnemyAircraftGenerator();
                    System.out.println("m_instance created: " + m_instance);
                }
            }
        }
        return m_instance;
    }


    private static int mob_hp = CONFIG.Enemy.MOB_HP;

    @Override
    public void takeNotify() {
        mob_hp += 10;
        System.out.println("mob hp += 10");
    }

    private static final IEnemyAircraftFactory mobFactory = new MobEnemyFactory();
    private static final IEnemyAircraftFactory eliteFactory = new EliteEnemyFactory();
    private static final IEnemyAircraftFactory elitePlusFactory = new ElitePlusEnemyFactory();
    private static final Random random = new Random();

    public EnemyAircraft generateEnemy() {
        // 随机选择敌机类型
        EEnemyType type = EEnemyType.values()[random.nextInt(EEnemyType.values().length)];

        // 敌机的初始位置和属性，这里仅为示例，实际可能需要更合理的生成逻辑
        int locationX = (int) (Math.random() * (AircraftWar.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())); // 假设游戏宽度为300
        int locationY = (int) (Math.random() * AircraftWar.WINDOW_HEIGHT * 0.05);
        int speedX = random.nextInt(4) - 2;  // 假设速度在 (0, 4) - 2 之间
        int speedY = random.nextInt(10) + 5; // 假设速度在5到14之间
        int hp = 100; // 基础生命值，实际中可能根据敌机类型不同而不同

        // 根据敌机类型通过对应工厂创建实例
        switch (type) {
            case MOB:
                return mobFactory.createEnemyAircraft(locationX, locationY, 0, 10, mob_hp, 10);
            //                return mobFactory.createEnemyAircraft(locationX, locationY, speedX, speedY, hp);
            case ELITE:
                //                hp = 100; // 假设精英敌机有更高的生命值
                return eliteFactory.createEnemyAircraft(locationX, locationY, speedX, 5, CONFIG.Enemy.ELITE_HP, 30);
            case ELITEPLUS:
                return elitePlusFactory.createEnemyAircraft(locationX, locationY, speedX, 5, CONFIG.Enemy.ELITE_PLUS_HP, 30);
            // TODO 暂时不加入 BOSS
            //            case BOSS:
            //                return bossFactory.createEnemyAircraft(locationX, locationY, speedX >> 2, speedY >> 2, 500, 100);
            default:
                // 暂时默认生成
                return mobFactory.createEnemyAircraft(locationX, locationY, 0, 10, mob_hp, 10);
            //                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
