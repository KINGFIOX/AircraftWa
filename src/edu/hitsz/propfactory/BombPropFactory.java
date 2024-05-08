package game.propfactory;

import game.prop.BaseProp;
import game.prop.BombProp;

public class BombPropFactory implements IPropFactory {
    public BaseProp createProp(int locationX, int locationY, int speedX, int speedY, int score, int dura, int power){
        return new BombProp(locationX, locationY, speedX, speedY, score, dura, power);
    }

}
