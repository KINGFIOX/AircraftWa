package edu.hitsz.enemyfactory;

import edu.hitsz.enemyfactory.IEnemyAircraftFactory;
import edu.hitsz.aircraft.EliteAircraft;
import edu.hitsz.aircraft.EnemyAircraft;

public class EliteEnemyFactory implements IEnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        return new EliteAircraft(locationX, locationY, speedX, speedY, hp, score);
    }
}