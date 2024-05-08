package game.propfactory;

import game.prop.BaseProp;
import game.prop.bulletprop.BulletProp;

public class BulletPropFactory implements IPropFactory{
    public BaseProp createProp(int locationX, int locationY, int speedX, int speedY, int score, int dura, int power){
        return new BulletProp(locationX, locationY, speedX, speedY, score, dura, power);
    }



}
