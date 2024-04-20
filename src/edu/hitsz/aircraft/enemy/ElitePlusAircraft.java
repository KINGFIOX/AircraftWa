package edu.hitsz.aircraft.enemy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.propfactory.PropGenerator;
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
                3, direction, power, false);
    }

    @Override
    public List<BaseProp> award() {
        List<BaseProp> res = new LinkedList<>();
        BaseProp p = PropGenerator.generateProp(this.locationX, this.locationY);
        if (p != null) {
            res.add(p);
        }
        return res;
    }

}
