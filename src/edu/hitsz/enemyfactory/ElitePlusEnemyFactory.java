package game.enemyfactory;

import game.aircraft.enemy.ElitePlusAircraft;

public class ElitePlusEnemyFactory implements IEnemyAircraftFactory {
    @Override
    public ElitePlusAircraft createEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score) {
        return new ElitePlusAircraft(locationX, locationY, speedX, speedY, hp, score);
    }
}