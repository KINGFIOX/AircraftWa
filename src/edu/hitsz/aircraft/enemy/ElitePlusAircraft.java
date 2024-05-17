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
//        List<BaseBullet> res = new LinkedList<>();
//        int x = this.getLocationX();
//        int y = this.getLocationY() + direction * 2;
//        int speedX = 0;
//        int speedY = this.getSpeedY() + direction * 5;
//        BaseBullet bullet;
//        for (int i = 0; i < shootNum; i++) {
//            // 子弹发射位置相对飞机位置向前偏移
//            // 多个子弹横向分散，但是感觉这也不分散啊
//            bullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, this.power);
//            // bullet = new HeroBullet(x , y, speedX, speedY, power);
//            res.add(bullet);
//        }
//        return res;
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
        // 直接让飞机死亡
        decreaseHp(this.maxHp / 3);
    }

}
