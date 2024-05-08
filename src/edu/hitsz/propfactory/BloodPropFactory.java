package edu.hitsz.propfactory;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BloodProp;

public class BloodPropFactory implements IPropFactory {
    @Override
    public BaseProp createProp(int locationX, int locationY, int speedX, int speedY, int score, int dura, int power){
        return new BloodProp(locationX, locationY, speedX, speedY, score, dura, power);
    }

}
