package edu.hitsz.shootstrategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterShootStrategy implements IShootStrategy {
    // TODO
    public List<BaseBullet> generateBullet(int getLocX, int getLocY, int getSpeedX, int getSpeedY, int getShootNum, int getDirect, int getPower, boolean isHero) {
        List<BaseBullet> res = new LinkedList<>();
        int direction = getDirect;
        int x = getLocX;
        int y = getLocY + direction * 2;
        int speedX = 0;
        int speedY = getSpeedY + direction * 5;
        int power = getPower;
        int shootNum = getShootNum;

        BaseBullet bullet;
        int i = 0;
        int offsetX;
        int scatterFactor = 3;
        for (i = 0; i < (shootNum >>> 1); i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散，但是感觉这也不分散啊
            // bullet = new HeroBullet(x , y, speedX, speedY, power);
            offsetX = (i * 2 - getShootNum + 1) * scatterFactor;
            speedX = -i;
            if (isHero) {
                bullet = new HeroBullet(x + offsetX, y, speedX, speedY, power);
            } else {
                bullet = new EnemyBullet(x + offsetX, y, speedX, speedY, power);
            }
            res.add(bullet);
        }
        if ((shootNum & 1) == 0) {  // 单数
            if (isHero) {
                bullet = new HeroBullet(x, y, 0, speedY, power);
            } else {
                bullet = new EnemyBullet(x, y, 0, speedY, power);
            }
            res.add(bullet);
            i++;
        } // 双数不用管
        for (; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散，但是感觉这也不分散啊
            // bullet = new HeroBullet(x , y, speedX, speedY, power);
            offsetX = (i * 2 - getShootNum + 1) * scatterFactor;
            speedX = i;
            if (isHero) {
                bullet = new HeroBullet(x + offsetX, y, speedX, speedY, power);
            } else {
                bullet = new EnemyBullet(x + offsetX, y, speedX, speedY, power);
            }
            res.add(bullet);
        }

        return res;
    }
}

