package edu.hitsz.enemyfactory;

import edu.hitsz.aircraft.enemy.EliteAircraft;
import edu.hitsz.aircraft.enemy.EnemyAircraft;

public class EliteEnemyFactory implements IEnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        return new EliteAircraft(locationX, locationY, speedX, speedY, hp, score);
    }
}