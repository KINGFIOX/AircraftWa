package edu.hitsz.aircraft;

import config.CONFIG;
import edu.hitsz.application.AircraftWar;
import edu.hitsz.application.ImageManager;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.shootstrategy.IShootStrategy;
import edu.hitsz.shootstrategy.StraightShootStrategy;

import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 *
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    /* ---------- ---------- 攻击方面 ----------- ---------- */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = CONFIG.Game.HERO_SHOOT_NUM; // FIXME CONFIG

    /**
     * 子弹伤害
     */
    private int power = CONFIG.Game.HERO_BULLET_POWER;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = -1;

    @Override
    /**
     * 通过射击产生子弹
     *
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        return shootStrategy.generateBullet(this.getLocationX(), this.getLocationY(),
                CONFIG.Game.HERO_BULLET_SPEED, CONFIG.Game.HERO_BULLET_SPEED,
                shootNum, direction, power, true);
    }

    /* ---------- ---------- 道具的作用 ----------- ---------- */

    public void addHp(int level) {
        int tmp = level + this.hp;
        if (tmp <= this.maxHp) {
            this.hp += level;
        }
    }

    public void changeShootStrategy(IShootStrategy shootStrategy) {
        System.out.println("change shoot strategy");
        this.shootStrategy = shootStrategy;
        return;
    }

    /**
     * @return
     * @brief 先获取这个来恢复
     */
    public IShootStrategy getShootStrategy() {
        return this.shootStrategy;
    }

    public void effect(BaseProp p) {
        p.effect(this);
    }

    /* ---------- ---------- 移动 ----------- ---------- */

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    /* ---------- ---------- 单例模式 ---------- ---------- */

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp        初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootStrategy = new StraightShootStrategy();
    }

    public static HeroAircraft getInstace() {
        // 第一次检查，避免不必要的同步
        if (m_instance == null) {
            // 同步块，对类对象加锁
            synchronized (HeroAircraft.class) {
                // 第二次检查，防止多线程问题
                if (m_instance == null) {
                    m_instance = new HeroAircraft(
                            AircraftWar.WINDOW_WIDTH / 2,
                            AircraftWar.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                            0, 0, CONFIG.Game.HERO_HP);
                    ;
                    System.out.println("m_instance created: " + m_instance);
                }
            }
        }
        return m_instance;
    }

    // 私有成员
    private static HeroAircraft m_instance;

}
