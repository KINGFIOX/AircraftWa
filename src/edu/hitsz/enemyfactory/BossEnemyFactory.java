package game.enemyfactory;

import game.aircraft.enemy.EnemyAircraft;
import game.aircraft.enemy.BossAircraft;

public class BossEnemyFactory implements IEnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        return new BossAircraft(locationX, locationY, speedX, speedY, hp, score);
    }
}