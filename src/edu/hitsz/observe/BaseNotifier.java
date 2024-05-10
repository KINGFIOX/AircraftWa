package edu.hitsz.observe;

import java.util.HashSet;
import java.util.Set;

/**
 * 通知器父类，也可以叫做 主题父类
 */
public abstract class BaseNotifier {

    public BaseNotifier() {

    }

    /**
     * 不出现重复订阅者
     */
    protected Set<ISubscriber> m_subers = new HashSet<>();

    /**
     * @brief 发起通知
     */
    protected void emitNotify() {
    }

    /**
     * @brief 注册
     */
    public void addSubscriber(ISubscriber sub) {
        m_subers.add(sub);
    }

    /**
     * @brief 注销
     */
    public void removeSubscriber(ISubscriber sub) {
        m_subers.remove(sub);
    }

}
