package edu.hitsz.shootstrategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public interface IShootStrategy {
    public List<BaseBullet> generateBullet(int getLocX, int getLocY, int getSpeedX, int getSpeedY, int getShootNum, int getDirect,int getPower, boolean isHero);
}
