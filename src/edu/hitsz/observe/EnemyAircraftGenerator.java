package edu.hitsz.observe;

import edu.hitsz.application.RANDOM;
import edu.hitsz.aircraft.enemy.EnemyAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.config.CONFIG;
import edu.hitsz.enemyfactory.*;


public class EnemyAircraftGenerator implements ISubscriber {

    // 可能随着时间改变
    private int mob_hp;
    private int elite_hp;
    private int elite_plus_hp;

    private final int maxScore_mob;
    private final int maxScore_elite;
    private final int maxScore_elite_plus;

    public EnemyAircraftGenerator(
            int mob_hp,
            int maxScore_mob,
            int elite_hp,
            int maxScore_elite,
            int elite_plus_hp,
            int maxScore_elite_plus
    ) {
        this.mob_hp = mob_hp;
        this.elite_hp = elite_hp;
        this.elite_plus_hp = elite_plus_hp;
        this.maxScore_mob = maxScore_mob;
        this.maxScore_elite = maxScore_elite;
        this.maxScore_elite_plus = maxScore_elite_plus;
    }


    @Override
    public void takeNotify() {
        // TODO
        mob_hp += CONFIG.Enemy.MOB_HP_UPGRADE;
        elite_hp += CONFIG.Enemy.ELITE_HP_UPGRADE;
        elite_plus_hp += CONFIG.Enemy.ELITE_PLUS_HP_UPGRADE;
        System.out.println("mob hp += 10");
    }

    private static final IEnemyAircraftFactory mobFactory = new MobEnemyFactory();
    private static final IEnemyAircraftFactory eliteFactory = new EliteEnemyFactory();
    private static final IEnemyAircraftFactory elitePlusFactory = new ElitePlusEnemyFactory();

    public EnemyAircraft generateEnemy() {
        // 随机选择敌机类型
        EEnemyType type = EEnemyType.values()[RANDOM.getRandom(EEnemyType.values().length)];

        // 敌机的初始位置和属性，这里仅为示例，实际可能需要更合理的生成逻辑
        int locationX = (int) (Math.random() * (CONFIG.Windows.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())); // 假设游戏宽度为300
        int locationY = (int) (Math.random() * CONFIG.Windows.WINDOW_HEIGHT * 0.05);
        int speedX = RANDOM.getRandom(CONFIG.Game.speedX);  // 假设速度在 [-2, 2] 之间
        int speedY = RANDOM.getRandom(CONFIG.Game.speedY); // 假设速度在5到14之间

        // 根据敌机类型通过对应工厂创建实例
        return switch (type) {
            case MOB ->
                    mobFactory.createEnemyAircraft(locationX, locationY, 0, speedY, mob_hp, RANDOM.getRandom(maxScore_mob));
            //                return mobFactory.createEnemyAircraft(locationX, locationY, speedX, speedY, hp);
            case ELITE ->
                //                hp = 100; // 假设精英敌机有更高的生命值
                    eliteFactory.createEnemyAircraft(locationX, locationY, speedX, speedY, elite_hp, RANDOM.getRandom(maxScore_elite));
            case ELITEPLUS ->
                    elitePlusFactory.createEnemyAircraft(locationX, locationY, speedX, speedY, elite_plus_hp, RANDOM.getRandom(maxScore_elite_plus));
            default ->
                // 暂时默认生成
                    mobFactory.createEnemyAircraft(locationX, locationY, 0, speedY, mob_hp, RANDOM.getRandom(maxScore_elite_plus));
            //                throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}
