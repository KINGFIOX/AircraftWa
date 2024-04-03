package edu.hitsz.enemyfactory;

import edu.hitsz.aircraft.EnemyAircraft;

public interface IEnemyAircraftFactory {
    public EnemyAircraft createEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int score);
}

