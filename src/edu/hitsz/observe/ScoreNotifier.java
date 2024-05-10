package edu.hitsz.observe;

import edu.hitsz.application.CONFIG;
import edu.hitsz.aircraft.HeroAircraft;

public class ScoreNotifier extends BaseNotifier {

    private int score = 0;

    private int lastScore = 0;

    // 单例
    static private ScoreNotifier m_instance;

    private ScoreNotifier() {
        super();
    }

    public static ScoreNotifier getInstace() {
        // 第一次检查，避免不必要的同步
        if (m_instance == null) {
            // 同步块，对类对象加锁
            synchronized (HeroAircraft.class) {
                // 第二次检查，防止多线程问题
                if (m_instance == null) {
                    m_instance = new ScoreNotifier();
                    System.out.println("m_instance created: " + m_instance);
                }
            }
        }
        return m_instance;
    }


    /**
     * @param updataScore
     * @brief 更新分数
     */
    public void update(int updataScore) {
        this.score = updataScore;
        if (score - lastScore >= CONFIG.Game.BOSS_EVERY_SCORE) {
            // 保证战场上只有一个 boss
            lastScore = score;
            emitNotify();
        }
    }

    @Override
    protected void emitNotify() {
        for (ISubscriber sub : m_subers) {
            sub.takeNotify();
        }
    }

}
