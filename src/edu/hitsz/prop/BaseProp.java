package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.AircraftWar;
import edu.hitsz.basic.AbstractFlyingObject;

public abstract class BaseProp  extends AbstractFlyingObject {

    public abstract void effect(HeroAircraft h);

    protected int score;

    protected int dura;

    protected int level;

    public int getLevel() {
        return this.level;
    }

    /**
     *
     * @param locationX
     * @param locationY
     * @param speedX
     * @param speedY
     * @param score
     * @param dura 持续时间
     * @param power 效果的数值，比方说: 加多少血量;
     */
    public BaseProp(int locationX, int locationY, int speedX, int speedY, int score, int dura, int power) {
        super(locationX, locationY, speedX, speedY);
        this.score = score;
        this.dura = dura;
        this.level = power;
    }


    @Override
    public void forward() {
        super.forward();

        // 判定 y 轴出界
        if (speedY > 0 && locationY >= AircraftWar.WINDOW_HEIGHT ) {
            // 向下飞行出界
            vanish();
        }else if (locationY <= 0){
            // 向上飞行出界
            vanish();
        }
    }



    public int getScore() {
        return score;
    }

}
