package edu.hitsz.enemyfactory;

import java.util.Random;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.enemyfactory.*;
import edu.hitsz.aircraft.EnemyAircraft;

public class EnemyAircraftGenerator {

    private static final IEnemyAircraftFactory mobFactory = new MobEnemyFactory();
    private static final IEnemyAircraftFactory eliteFactory = new EliteEnemyFactory();
    private static final IEnemyAircraftFactory bossFactory = new BossEnemyFactory();
    private static final Random random = new Random();

    public static EnemyAircraft generateEnemy() {
        // 随机选择敌机类型
        EnemyType type = EnemyType.values()[random.nextInt(EnemyType.values().length)];

        // 敌机的初始位置和属性，这里仅为示例，实际可能需要更合理的生成逻辑
        int locationX = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())); // 假设游戏宽度为300
        int locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
        int speedX = 0;
        int speedY = random.nextInt(10) + 5; // 假设速度在5到14之间
        int hp = 100; // 基础生命值，实际中可能根据敌机类型不同而不同

        // 根据敌机类型通过对应工厂创建实例
        switch (type) {
            case MOB:
                return mobFactory.createEnemyAircraft(locationX, locationY, 0, 10, 30, 10);
//                return mobFactory.createEnemyAircraft(locationX, locationY, speedX, speedY, hp);
            case ELITE:
//                hp = 100; // 假设精英敌机有更高的生命值
                return eliteFactory.createEnemyAircraft(locationX, locationY, 2, 5, 100, 30);
            // TODO 暂时不加入 BOSS
//            case BOSS:
//                hp = 500; // 假设Boss敌机有最高的生命值
//                speedY = random.nextInt(5) + 3; // 假设Boss速度更慢
//                return bossFactory.createEnemyAircraft(locationX, locationY, speedX, speedY, hp);
            default:
                // 暂时默认生成
                return mobFactory.createEnemyAircraft(locationX, locationY, 0, 10, 30, 10);
//                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
