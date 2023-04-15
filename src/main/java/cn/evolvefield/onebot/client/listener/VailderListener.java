package cn.evolvefield.onebot.client.listener;

/**
 * 提供是否预先校验
 * @param <T>
 */
public interface VailderListener<T> {

    /**
     * 验证方法 在onMessage前调用，返回false时不调用onMessage
     *
     * @param t 消息实体
     * @return 返回true时调用onMessage 否则跳过
     */
    default Boolean valider(T t) {
        return true;
    }
}
