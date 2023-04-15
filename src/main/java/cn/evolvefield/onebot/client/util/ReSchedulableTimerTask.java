package cn.evolvefield.onebot.client.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 11:37
 * Version: 1.0
 */
public abstract class ReSchedulableTimerTask extends TimerTask {

        public int i = 0;

        public void schedule(Timer timer, int checkInterval) {

            timer.scheduleAtFixedRate(this, 0, checkInterval);

        }

        public void re_schedule2(int newCheckInterval) {

            Date now = new Date();

            SimpleDateFormat dateFormat = new SimpleDateFormat(

                    "E MMMMM dd HH:mm:ss zzz yyyy");

            System.out.println("re_schedule2:" + dateFormat.format(now).toString());

            long nextExecutionTime = now.getTime() + newCheckInterval;

            setDeclaredField(this, "nextExecutionTime", nextExecutionTime);

            setDeclaredField(this, "period", newCheckInterval);

        }

        static boolean setDeclaredField(Object obj, String name, Object value) {

            try {
                Field field = TimerTask.class.getDeclaredField(name);

                field.setAccessible(true);

                field.set(obj, value);

                return true;

            } catch (Exception ex) {

                ex.printStackTrace();

                return false;

            }

        }

}
