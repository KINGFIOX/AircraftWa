package edu.hitsz.observe;

import edu.hitsz.config.CONFIG;

import java.util.HashSet;
import java.util.Set;

public class ScoreNotifier {

    /**
     * 不出现重复订阅者
     */
    private final Set<ISubscriber> m_subers = new HashSet<>();


    private int score = 0;

    private int lastScore = 0;

    public ScoreNotifier() {
        super();
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
//            emitNotify();
//            this.emitNotify();
            for (ISubscriber sub : m_subers) {
                sub.takeNotify();
            }
        }
    }

    public void addSubscriber(ISubscriber sub) {
        m_subers.add(sub);
    }

    public void removeSubscriber(ISubscriber sub) {
        m_subers.remove(sub);
    }

}
