package edu.hitsz.bullet;

import edu.hitsz.observe.ISubscriber;
import edu.hitsz.prop.bomb.IBombScriber;

/**
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements IBombScriber {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void takeNotify() {
        // 子弹直接消失
        vanish();
    }
}
