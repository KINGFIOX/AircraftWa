package game.prop.bulletprop;

import game.prop.BaseProp;

public abstract class BaseBulletProp extends BaseProp {

    protected Runnable r = null;

    protected static Thread running;

    /**
     * @param locationX
     * @param locationY
     * @param speedX
     * @param speedY
     * @param score
     * @param dura      持续时间
     * @param power     效果的数值，比方说: 加多少血量;
     */
    public BaseBulletProp(int locationX, int locationY, int speedX, int speedY, int score, int dura, int power) {
        super(locationX, locationY, speedX, speedY, score, dura, power);
    }
}
