package cn.evolvefield.mods.botapi.core.bot;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.core.service.WebSocketService;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/6 19:41
 * Version: 1.0
 */
public class BotHandler {
    public static void init() {
        BotApi.LOGGER.info("▌ §a开始对接机器人框架 §6┈━═☆");
        if (BotApi.config.getCommon().getFrame().equalsIgnoreCase("cqhttp")) {
            BotData.setWs(BotApi.config.getCommon().getWsCommon());
            BotData.setBotFrame("cqhttp");

            if (BotApi.config.getCommon().isAutoOpen()) {
                WebSocketService.main(BotData.getWs());
            }
            BotApi.LOGGER.info("§7[§a§l*§7] §a启用框架: §e" + BotData.getBotFrame());
        } else if (BotApi.config.getCommon().getFrame().equalsIgnoreCase("mirai")) {
            BotData.setWs(BotApi.config.getMirai().getWsMirai());
            BotData.setQQId(BotApi.config.getCommon().getBotId());
            BotData.setVerifyKey(BotApi.config.getMirai().getVerifyKey());
            BotData.setBotFrame("mirai");

            if (BotApi.config.getCommon().isAutoOpen()) {
                WebSocketService.main(BotData.getWs() + "/all?verifyKey=" + BotData.getVerifyKey() + "&qq=" + BotData.getQQId());
            }
            BotApi.LOGGER.info("§7[§a§l*§7] §a启用框架: §e" + BotData.getBotFrame());
        } else {
            BotApi.LOGGER.warn("§7[§a§l*§7] §c未找到对应框架.");
        }
    }
}
