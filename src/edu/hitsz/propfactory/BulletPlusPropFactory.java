package game.propfactory;

import game.prop.BaseProp;
import game.prop.bulletprop.BulletPlusProp;

public class BulletPlusPropFactory implements IPropFactory{
    public BaseProp createProp(int locationX, int locationY, int speedX, int speedY, int score, int dura, int power){
        return new BulletPlusProp(locationX, locationY, speedX, speedY, score, dura, power);
    }

}
