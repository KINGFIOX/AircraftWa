package game.propfactory;


import game.prop.BaseProp;

public interface IPropFactory {
    public BaseProp createProp(int locationX, int locationY, int speedX, int speedY, int score, int dura, int level);
}
