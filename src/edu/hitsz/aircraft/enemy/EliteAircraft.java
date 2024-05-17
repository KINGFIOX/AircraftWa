package edu.hitsz.aircraft.enemy;

import edu.hitsz.config.CONFIG;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.observe.PropGenerator;
import edu.hitsz.shootstrategy.StraightShootStrategy;

import java.util.LinkedList;
import java.util.List;

public class EliteAircraft extends EnemyAircraft {
    public EliteAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        super(locationX, locationY, speedX, speedY, hp, score);
        this.shootStrategy = new StraightShootStrategy();
    }

    @Override
    public List<BaseBullet> shoot() {
        return shootStrategy.generateBullet(this.getLocationX(), this.getLocationY(), 0, this.getSpeedY(),
                CONFIG.Enemy.ELITE_SHOOT_NUMBER, direction, power, false);
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
        // 直接让飞机死亡
        decreaseHp(this.maxHp);
    }
}
