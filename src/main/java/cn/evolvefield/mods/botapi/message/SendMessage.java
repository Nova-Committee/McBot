package cn.evolvefield.mods.botapi.message;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.network.HttpRequest;
import cn.evolvefield.mods.botapi.util.json.JSONException;
import cn.evolvefield.mods.botapi.util.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class SendMessage {
      public void Private(long user_id, String message) {
            try {
                  message = URLEncoder.encode(message, "utf-8");
                  URL url = new URL("http://" + BotApi.config.getCommon().getSendHOST() + ":" + BotApi.config.getCommon().getSendPORT() +
                          "/send_private_msg?user_id=" + user_id + "&message=" + message);
                  if(BotApi.config.getCommon().isDebuggable()){
                        BotApi.LOGGER.info("向用户" + user_id + "发送消息" + message);
                  }
                  url.openStream();
            } catch (IOException e) {
                  e.printStackTrace();
            }

      }

      public void Private(long user_id, long group_id, String message) {
            try {
                  message = URLEncoder.encode(message, "utf-8");
                  URL url = new URL("http://" + BotApi.config.getCommon().getSendHOST() + ":" + BotApi.config.getCommon().getSendPORT() +
                          "/send_private_msg?user_id=" + user_id + "&group_id=" + group_id + "&message=" + message);
                  if(BotApi.config.getCommon().isDebuggable()){
                        BotApi.LOGGER.info("向群" + group_id + "内的" + user_id +"发送消息" + message);
                  }
                  url.openStream();
            } catch (IOException e) {
                  e.printStackTrace();
            }

      }
      public static void Group(long group_id, String message) {
            try {
                  message = URLEncoder.encode(message, "utf-8");
                  URL url = new URL("http://" + BotApi.config.getCommon().getSendHOST() + ":" +BotApi.config.getCommon().getSendPORT() +
                          "/send_group_msg?group_id=" + group_id + "&message=" + message);
                  if(BotApi.config.getCommon().isDebuggable()){
                        BotApi.LOGGER.info("向群" + group_id + "发送消息" + message);
                  }
                  url.openStream();
            } catch (IOException e) {
                  e.printStackTrace();
            }

      }

      private static final JSONObject errorObject = new JSONObject("{\"retcode\": 1}");

      //获取群成员信息
      public static JSONObject getProfile(long group, long userId) {

            try {
                  String resp = HttpRequest.post("http://" + BotApi.config.getCommon().getSendHOST() + ":" + BotApi.config.getCommon().getSendPORT() +
                                  "/get_group_member_info?group_id=" + group + "&user_id=" + userId)
                          .trustAllCerts()
                          .trustAllHosts()
                          .connectTimeout(60000)
                          .readTimeout(60000)
                          .body();
                  return new JSONObject(resp);
            } catch (JSONException e) {
                  e.printStackTrace();
                  return errorObject;
            }
      }

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
