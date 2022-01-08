import cn.evolvefield.mods.botapi.util.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/7 13:53
 * Version: 1.0
 */
public class Test {

    public static void main(String args[]){
        JSONObject data = new JSONObject();
        JSONObject params = new JSONObject();
        data.put("action", "send_group_msg");
        params.put("group_id", "337631140");
        params.put("message", "hello world");
        data.put("params", params);
        Logger LOGGER = LogManager.getLogger();
        LOGGER.info(data.toString());
    }


}
