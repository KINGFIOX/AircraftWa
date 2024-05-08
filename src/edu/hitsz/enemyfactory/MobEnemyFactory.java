package game.enemyfactory;
import game.aircraft.enemy.EnemyAircraft;
import game.aircraft.enemy.MobAircraft;

public class MobEnemyFactory implements IEnemyAircraftFactory {
    @Override
    public EnemyAircraft createEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        return new MobAircraft(locationX, locationY, speedX, speedY, hp, score);
    }
}
