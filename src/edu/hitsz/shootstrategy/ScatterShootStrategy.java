package edu.hitsz.shootstrategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterShootStrategy implements IShootStrategy {
    // TODO
    @Override
    public List<BaseBullet> generateBullet(int getLocX, int getLocY, int getSpeedX, int getSpeedY, int getShootNum, int getDirect, int getPower, boolean isHero) {
        List<BaseBullet> res = new LinkedList<>();
        int direction = getDirect; // 正数或负数表示方向
        int x = getLocX;
        int y = getLocY + direction * 2; // 在y方向上小幅偏移
        int power = getPower;
        int scatterFactor = 10; // 控制散射角度的因子，数值越大散射角度越宽

        // 生成散射子弹
        for (int i = 0; i < getShootNum; i++) {
            // 计算子弹的水平偏移
            int offsetX = scatterFactor * (i - getShootNum / 2);
            // 计算子弹的水平速度，使得子弹向两边散开
            int speedX = (i - getShootNum / 2) * 2;

            // 创建子弹，根据isHero标志判断是英雄的子弹还是敌人的子弹
            BaseBullet bullet;
            if (isHero) {
                bullet = new HeroBullet(x + offsetX, y, speedX, 2 * getSpeedY * direction, power);
            } else {
                bullet = new EnemyBullet(x + offsetX, y, speedX, 2 * getSpeedY * direction, power);
            }
            res.add(bullet);
        }
        return res;
    }
}

