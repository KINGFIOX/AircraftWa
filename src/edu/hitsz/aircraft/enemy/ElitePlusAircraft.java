package edu.hitsz.aircraft.enemy;

import edu.hitsz.config.CONFIG;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.observe.PropGenerator;
import edu.hitsz.shootstrategy.ScatterShootStrategy;

import java.util.LinkedList;
import java.util.List;

public class ElitePlusAircraft extends EnemyAircraft {
    public ElitePlusAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        super(locationX, locationY, speedX, speedY, hp, score);
        this.shootStrategy = new ScatterShootStrategy();
    }

    /**
     * @return
     * @brief 重写弹道
     */
    @Override
    public List<BaseBullet> shoot() {
        return shootStrategy.generateBullet(this.getLocationX(), this.getLocationY(), 0, this.getSpeedY(),
                CONFIG.Enemy.ELITE_PLUS_SHOOT_NUMBER, direction, power, false);
    }

    @Override
    public List<BaseProp> award(PropGenerator generator) {
        List<BaseProp> res = new LinkedList<>();
        BaseProp p = generator.generateProp(this.locationX, this.locationY);
        if (p != null) {
            res.add(p);
        }
        return res;
    }

    @Override
    public void takeNotify() {

        decreaseHp(this.maxHp / 3);
    }

}
