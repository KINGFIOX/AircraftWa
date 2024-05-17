package edu.hitsz.prop.bomb;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.enemy.EnemyAircraft;
import edu.hitsz.bgm.WaveManager;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.BaseProp;

import java.util.List;


public class BombProp extends BaseProp  {

    public BombProp(int locationX, int locationY, int speedX, int speedY, int score, int dura, int power) {
        super(locationX, locationY, speedX, speedY, score, dura, power);
    }

    @Override
    public void effect(HeroAircraft h) {
        // 音乐
        WaveManager.m_instance.playMusic("bomb_explosion");

        System.out.println("BombSupply active!");
    }


    public void emitNotify(List<BaseBullet> enemyBullets, List<EnemyAircraft> enemyAircrafts) {
        for (BaseBullet bullet : enemyBullets) {
            if (bullet instanceof EnemyBullet) {
                ((EnemyBullet) bullet).takeNotify();
            }
        }
        for (EnemyAircraft enemy : enemyAircrafts) {
            if (enemy instanceof EnemyAircraft) {
                enemy.takeNotify();
            }
        }
    }

}
