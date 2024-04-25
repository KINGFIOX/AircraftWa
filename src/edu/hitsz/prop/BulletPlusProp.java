package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shootstrategy.CircleShootStrategy;
import edu.hitsz.shootstrategy.IShootStrategy;
import edu.hitsz.shootstrategy.ScatterShootStrategy;

/**
 * 环形弹道
 */
public class BulletPlusProp extends BaseProp {
    public BulletPlusProp(int locationX, int locationY, int speedX, int speedY, int score, int dura, int power) {
        super(locationX, locationY, speedX, speedY, score, dura, power);
    }

    @Override
    public void effect(HeroAircraft h) {
        h.changeShootStrategy( new CircleShootStrategy() );
        System.out.println("FireSupply active!");
    }

}
