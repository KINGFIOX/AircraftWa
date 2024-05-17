package edu.hitsz.observe;

import edu.hitsz.config.CONFIG;
import edu.hitsz.aircraft.enemy.EnemyAircraft;
import edu.hitsz.application.AircraftWar;
import edu.hitsz.application.ImageManager;
import edu.hitsz.enemyfactory.*;

import java.util.Random;

public class EnemyAircraftGenerator implements ISubscriber {

    private int mob_hp;
    private int elite_hp;
    private int elite_plus_hp;

    public EnemyAircraftGenerator(int mob_hp, int elite_hp, int elite_plus_hp) {
        this.mob_hp = mob_hp;
        this.elite_hp = elite_hp;
        this.elite_plus_hp = elite_plus_hp;
    }


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
                return eliteFactory.createEnemyAircraft(locationX, locationY, speedX, 5, elite_hp, 30);
            case ELITEPLUS:
                return elitePlusFactory.createEnemyAircraft(locationX, locationY, speedX, 5, elite_plus_hp, 30);
            default:
                // 暂时默认生成
                return mobFactory.createEnemyAircraft(locationX, locationY, 0, 10, mob_hp, 10);
            //                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
