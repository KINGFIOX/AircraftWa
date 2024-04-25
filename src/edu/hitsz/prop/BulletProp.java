package edu.hitsz.prop;


import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shootstrategy.ScatterShootStrategy;

public class BulletProp extends BaseProp {
    public BulletProp(int locationX, int locationY, int speedX, int speedY, int score, int dura, int power) {
        super(locationX, locationY, speedX, speedY, score, dura, power);
    }

    @Override
    public void effect(HeroAircraft h) {
        h.changeShootStrategy( new ScatterShootStrategy() );
        System.out.println("FireSupply active!");
    }


}
