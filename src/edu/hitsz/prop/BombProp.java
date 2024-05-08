package game.prop;

import game.aircraft.HeroAircraft;
import game.aircraft.bgm.WaveManager;


public class BombProp extends BaseProp {
    public BombProp(int locationX, int locationY, int speedX, int speedY, int score, int dura, int power) {
        super(locationX, locationY, speedX, speedY, score, dura, power);
    }

    @Override
    public void effect(HeroAircraft h) {
        // 音乐
        WaveManager.getInstance().playMusic("bomb_explosion");

        System.out.println("BombSupply active!");
    }


}
