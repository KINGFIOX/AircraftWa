package edu.hitsz.aircraft;

import edu.hitsz.prop.BaseProp;

public class BossAircraft extends EnemyAircraft {
    public BossAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        super(locationX, locationY, speedX, speedY, hp, score);
    }

    @Override
    public BaseProp award() {
        return null;
    }

}
