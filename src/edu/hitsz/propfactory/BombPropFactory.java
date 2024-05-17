package edu.hitsz.propfactory;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.bomb.BombProp;

public class BombPropFactory implements IPropFactory {
    public BaseProp createProp(int locationX, int locationY, int speedX, int speedY, int score, int dura, int power){
        return new BombProp(locationX, locationY, speedX, speedY, score, dura, power);
    }

}
