package cn.evolvefield.onebot.client.handler;

import cn.evolvefield.onebot.client.listener.EnableListener;
import cn.evolvefield.onebot.client.listener.Listener;
import cn.evolvefield.onebot.client.util.ListenerUtils;
import cn.evolvefield.onebot.sdk.event.Event;
import cn.evolvefield.onebot.sdk.util.json.GsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Project: onebot-client
 * Author: cnlimiter
 * Date: 2023/3/19 15:45
 * Description:
 */
@SuppressWarnings("unused")
public class EventBus implements Runnable {
    private static final Logger log = LogManager.getLogger(EventBus.class);
    //存储监听器对象
    protected List<Listener> listenerList = new ArrayList<>();
    //缓存类型与监听器的关系
    protected Map<Class<? extends Event>, List<Listener>> cache = new ConcurrentHashMap<>();
    //线程池 用于并发执行队列中的任务
    protected ExecutorService service;
    protected BlockingQueue<String> queue;
    private boolean close = false;
    private Listener<String> messageListener;

    public EventBus(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void addListener(Listener listener) {
        listenerList.add(listener);
    }

    public void start() {
        start(1);
    }

    public void start(Integer threadCount) {
        if (threadCount <= 0) {
            threadCount = 1;
        }
        service = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            service.execute(this);
        }
    }

    public void stop() {
        this.close = true;
        service.shutdownNow();
    }

    @Override
    public void run() {

        try {
            while (!close) {
                this.runTask();
            }
        } catch (Exception e) {
                log.error(e.getMessage());
        }


    }

    /**
     * 执行任务
     */
    protected void runTask() {
        String message = this.getTask();//获取消息
        if (message == null) {
            return;
        }
        Class<? extends Event> messageType = ListenerUtils.getMessageType(message);//获取消息对应的实体类型
        if (messageType == null) {
            return;
        }
        if (this.messageListener != null) {
            this.messageListener.onMessage(message);
        }
        log.debug(String.format("接收到上报消息内容：%s \n %s", messageType, messageType));
        Event bean = GsonUtil.strToJavaBean(message, messageType);//将消息反序列化为对象
        List<Listener> executeListener = (executeListener = cache.get(messageType)) == null ?
                getMethod(messageType) : executeListener;//检查缓存

        for (Listener listener : executeListener) {
            if (listener.valider(bean)) {
                listener.onMessage(bean);//调用监听方法
            }
        }
        if (executeListener != null) {
            cache.put(messageType, executeListener);
        }
    }

    /**
     * 从队列中获取任务
     *
     * @return
     */
    protected String getTask() {
        try {
            return this.queue.poll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 获取能处理消息类型的处理器
     *
     * @param messageType
     * @return
     */
    protected List<Listener> getMethod(Class messageType) {
        List<Listener> listeners = new ArrayList<>();
        for (Listener listener : listenerList) {
            try {
                try {
                    listener.getClass().getMethod("onMessage", messageType);//判断是否支持该类型
                } catch (NoSuchMethodException e) {
                    continue;//不支持则跳过
                }
                if (listener instanceof EnableListener) {
                    EnableListener enableListener = (EnableListener) listener;
                    if (!enableListener.enable()) {//检测是否开启该插件
                        continue;
                    }
                }
                listeners.add(listener);//开启后添加入当前类型的插件
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listeners;
    }

    public List<Listener> getListenerList() {
        return listenerList;
    }

    /**
     * 清除类型缓存
     */
    public void cleanCache() {
        cache.clear();
    }

    public Listener<String> getMessageListener() {
        return messageListener;
    }

    public void setMessageListener(Listener<String> messageListener) {
        this.messageListener = messageListener;
    }
}
