package cn.evolvefield.mods.botapi.api;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.util.json.JSONObject;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

import static cn.evolvefield.mods.botapi.network.WebSocket.WebSocketChannelSupervise.sendToAll;

public class SendMessage {

      public static void Private(long user_id, String message){
            JSONObject data = new JSONObject();
            JSONObject params = new JSONObject();
            data.put("action", "send_private_msg");

            params.put("user_id", user_id);
            params.put("message", message);
            data.put("params", params);
            if(BotApi.config.getCommon().isDebuggable()){
                  BotApi.LOGGER.info("向用户" + user_id + "发送消息" + message);
            }
            sendToAll(new TextWebSocketFrame(data.toString()));
      }


      public static void Group(long group_id, String message) {
            JSONObject data = new JSONObject();
            JSONObject params = new JSONObject();
            data.put("action", "send_group_msg");

            params.put("group_id", group_id);
            params.put("message", message);
            data.put("params", params);
            if(BotApi.config.getCommon().isDebuggable()){
                  BotApi.LOGGER.info("向群" + group_id + "发送消息" + message);
            }
            sendToAll(new TextWebSocketFrame(data.toString()));
      }

//      public static JSONObject getProfile(long group, long userId) {
//
//      }


      private static final JSONObject errorObject = new JSONObject("{\"retcode\": 1}");

      //获取群成员信息
//      public static JSONObject getProfile(long group, long userId) {
//
//            try {
//                  String resp = HttpRequest.post("http://" + BotApi.config.getCommon().getSendHOST() + ":" + BotApi.config.getCommon().getSendPORT() +
//                                  "/get_group_member_info?group_id=" + group + "&user_id=" + userId)
//                          .trustAllCerts()
//                          .trustAllHosts()
//                          .connectTimeout(60000)
//                          .readTimeout(60000)
//                          .body();
//                  return new JSONObject(resp);
//            } catch (JSONException e) {
//                  e.printStackTrace();
//                  return errorObject;
//            }
//      }

      //获取用户名信息
      public static String getUsernameFromInfo(JSONObject userInfo) {
            if (userInfo == null) {
                  return "";
            }

            if (userInfo.getNumber("retcode").intValue() != 0) {
                  return "";
            }

            String username = userInfo.getJSONObject("data").getString("card");
            if (username.equals("")) {
                  username = userInfo.getJSONObject("data").getString("nickname");
            }

            return username;
      }

      public static String setListMessage(List msg) {
            if (msg.size() <= 1) {
                  return (String)msg.get(0);
            } else {
                  String Message = null;

                  for (Object o : msg) {
                        String a = (String) o;
                        if (Message == null) {
                              Message = a;
                        } else {
                              Message = Message + "\n" + a;
                        }
                  }

                  return Message;
            }
      }
}
