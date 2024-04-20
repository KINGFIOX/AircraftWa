package edu.hitsz.enemyfactory;

import edu.hitsz.aircraft.enemy.EnemyAircraft;
import edu.hitsz.aircraft.enemy.BossAircraft;

public class BossEnemyFactory implements IEnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        return new BossAircraft(locationX, locationY, speedX, speedY, hp, score);
    }
}