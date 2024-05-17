package edu.hitsz.aircraft.enemy;

import edu.hitsz.config.CONFIG;
import edu.hitsz.application.AircraftWar;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.observe.PropGenerator;
import edu.hitsz.shootstrategy.CircleShootStrategy;

import java.util.LinkedList;
import java.util.List;

public class BossAircraft extends EnemyAircraft {

    public BossAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        super(locationX, locationY, speedX, speedY, hp, score);
        this.shootStrategy = new CircleShootStrategy();
    }

    @Override
    public List<BaseProp> award(PropGenerator generator) {

        // FIXME 奖励的时候，关闭 boss 的音乐，不知道这样行不行，感觉不符合逻辑

        List<BaseProp> res = new LinkedList<>();
        BaseProp p;
        for (int i = 0; i < CONFIG.Enemy.BOSS_DROP_NUMBER; i++) {
            p = generator.generateProp(this.locationX, this.locationY);
            if (p != null) {
                res.add(p);
            }
        }
        return res;
    }

    public void forward() {
        locationX += speedX;
        locationY += speedY;

        if (locationX <= 0 || locationX >= AircraftWar.WINDOW_WIDTH) {
            // 横向超出边界后反向
            speedX = -speedX;
        }
        if (locationY <= 0 || locationY >= AircraftWar.WINDOW_HEIGHT / 4) {
            // 横向超出边界后反向
            speedY = -speedY;
        }
    }


    /**
     * @return
     * @brief boss 重写 boss 弹道
     */
    @Override
    public List<BaseBullet> shoot() {
        int bulletSpeed = 4;
        return shootStrategy.generateBullet(this.getLocationX(), this.getLocationY(), bulletSpeed, bulletSpeed,
                CONFIG.Enemy.BOSS_SHOOT_NUMBER, direction, power, false);
    }

    @Override
    public void takeNotify() {
        // boss 不受影响
    }

}
