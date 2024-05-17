package edu.hitsz.observe;

import edu.hitsz.aircraft.enemy.BossAircraft;
import edu.hitsz.aircraft.enemy.EnemyAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.RANDOM;
import edu.hitsz.bgm.WaveManager;
import edu.hitsz.config.CONFIG;
import edu.hitsz.enemyfactory.BossEnemyFactory;
import edu.hitsz.enemyfactory.IEnemyAircraftFactory;

import java.util.List;

public class BossNotifier implements ISubscriber {

    private int score = 0;

    private int lastBossScore = 0;

    public BossNotifier(int hp) {
        this.boss_hp = hp;
    }

    private final static IEnemyAircraftFactory bossFactory = new BossEnemyFactory();

    private int boss_hp;


    /**
     * @param updataScore
     * @brief 更新分数
     */
    public void update(int updataScore, List<EnemyAircraft> enemyAircrafts) {
        this.score = updataScore;
        if (score - lastBossScore >= CONFIG.Enemy.BOSS_EVERY_SCORE) {
            // 保证战场上只有一个 boss
            lastBossScore = score;
            // 保证战场上只有一个 boss
            boolean existed = enemyAircrafts.stream().anyMatch(x -> (x instanceof BossAircraft));
            if (!existed) {
                int locationX = (int) (Math.random()
                        * (CONFIG.Windows.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())); // 假设游戏宽度为300
                int locationY = (int) (Math.random() * CONFIG.Windows.WINDOW_HEIGHT * 0.05);
                int speedX = RANDOM.getRandom(CONFIG.Game.speedX);
                int speedY = RANDOM.getRandom(CONFIG.Game.speedY); // 假设速度在5到14之间
                enemyAircrafts
                        .add(bossFactory.createEnemyAircraft(locationX, locationY, speedX >> 2, speedY >> 2, 500, 100));
                // 播放音频
                WaveManager.m_instance.playMusic("bgm_boss");
                lastBossScore = score;
            }
        }
    }

    @Override
    public void takeNotify() {
        this.boss_hp += CONFIG.Enemy.BOSS_HP_UPGRADE;
    }
}


