package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;


public class BombProp extends BaseProp {
    public BombProp(int locationX, int locationY, int speedX, int speedY, int score, int dura, int power) {
        super(locationX, locationY, speedX, speedY, score, dura, power);
    }

    @Override
    public void effect(HeroAircraft h) {
        System.out.println("BombSupply active!");
    }


}
