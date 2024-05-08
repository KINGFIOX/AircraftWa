package game.aircraft.enemy;

import game.bullet.BaseBullet;
import game.prop.BaseProp;
import game.propfactory.PropGenerator;
import game.shootstrategy.StraightShootStrategy;

import java.util.LinkedList;
import java.util.List;

public class EliteAircraft extends EnemyAircraft {
    public EliteAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        super(locationX, locationY, speedX, speedY, hp, score);
        this.shootStrategy = new StraightShootStrategy();
    }

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
                1, direction, power, false);
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
