package edu.hitsz.aircraft.enemy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.config.CONFIG;
import edu.hitsz.observe.PropGenerator;
import edu.hitsz.prop.BaseProp;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobAircraft extends EnemyAircraft {

    public MobAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        super(locationX, locationY, speedX, speedY, hp, score);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= CONFIG.Windows.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }

    @Override
    public List<BaseProp> award(PropGenerator generator) {
        return new LinkedList<>();
    }

    @Override
    public void takeNotify() {
        // boss 不受影响
        decreaseHp(this.maxHp);
    }


}
