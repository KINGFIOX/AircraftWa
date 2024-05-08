package game.enemyfactory;

import game.aircraft.enemy.EliteAircraft;
import game.aircraft.enemy.EnemyAircraft;

public class EliteEnemyFactory implements IEnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        return new EliteAircraft(locationX, locationY, speedX, speedY, hp, score);
    }
}