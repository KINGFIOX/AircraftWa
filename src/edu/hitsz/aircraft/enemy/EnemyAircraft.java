package edu.hitsz.aircraft.enemy;

import config.CONFIG;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.AircraftWar;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;

import java.util.LinkedList;
import java.util.List;

/**
 * 抽象类
 */
public abstract class EnemyAircraft extends AbstractAircraft {

    /* ---------- ---------- 子弹 ---------- ---------- */

    /**
     * 子弹一次发射数量
     */
    protected int shootNum = CONFIG.Enemy.ELITE_SHOOT_NUMBER; // FIXME CONFIG

    /**
     * 子弹伤害
     */
    protected int power = CONFIG.Enemy.ENEMY_BULLET_POWER; // FIXME CONFIG

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    protected int direction = 1;

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }

    /* ---------- ---------- 分数 ---------- ---------- */

    // 敌人的分数（ 悬赏人头 ）
    private int score;

    public int getScore() {
        return this.score;
    }

    /* ---------- ---------- 构造函数 ---------- ---------- */

    public EnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int _score) {
        super(locationX, locationY, speedX, speedY, hp);
        this.score = _score;
    }

    /* ---------- ---------- 飞行一格 ---------- ---------- */

    /**
     * enemy
     */
    @Override
    public void forward() {
        super.forward(); // 调用 AbstractFlyingObject 的 forward
        // 判定 y 轴向下飞行出界
        if (locationY >= AircraftWar.WINDOW_HEIGHT) {
            vanish();
        }
    }

    /* ---------- ---------- 掉落物品 ---------- ---------- */

    /**
     * 听说你喜欢奖励
     */
    abstract public List<BaseProp> award();

}
