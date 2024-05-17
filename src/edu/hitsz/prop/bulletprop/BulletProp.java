package edu.hitsz.prop.bulletprop;


import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bgm.WaveManager;
import edu.hitsz.shootstrategy.IShootStrategy;
import edu.hitsz.shootstrategy.ScatterShootStrategy;
import edu.hitsz.shootstrategy.StraightShootStrategy;

public class BulletProp extends BaseBulletProp {
    public BulletProp(int locationX, int locationY, int speedX, int speedY, int score, int dura, int power) {
        super(locationX, locationY, speedX, speedY, score, dura, power);
    }

    @Override
    public void effect(HeroAircraft h) {
        // 音乐
        WaveManager.getM_instance().playMusic("get_supply");

        IShootStrategy origin = new StraightShootStrategy();
        r = () -> {
            h.changeShootStrategy(new ScatterShootStrategy());
            // 休眠 5s
            try {
                System.out.println("环射，持续 5s");
                Thread.sleep(dura * 1000);
                h.changeShootStrategy(origin);
            } catch (InterruptedException e) {
                // 打印中断信息
                System.out.println("线程被中断：" + e);
                // 我们这个中断是一次性的，因此不用重新设置中断的标志位
//                // 重新设置中断状态
//                Thread.currentThread().interrupt();
            }
        };
        // 清除现有的效果
        if (BaseBulletProp.running != null && BaseBulletProp.running.isAlive()) {
            BaseBulletProp.running.interrupt();
        }

        BaseBulletProp.running = new Thread(r);
        running.start();
        System.out.println("FireSupply active!");
    }


}
