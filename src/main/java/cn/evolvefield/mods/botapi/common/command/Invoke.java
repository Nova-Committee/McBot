package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.SendMessage;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.DimensionManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Invoke {
    private static final DecimalFormat TIME_FORMATTER = new DecimalFormat("########0.000");


    public static void invokeCommand(String command) {
        String commandBody = command.substring(1);

        List<String> temp = new ArrayList<>();
        if("tps".equals(commandBody)) {
            MinecraftServer SERVER = FMLCommonHandler.instance().getMinecraftServerInstance();
            String outPut = "服务器TPS";
            for (Integer dimId : DimensionManager.getIDs())
            {
                double worldTickTime = mean(SERVER.worldTickTimes.get(dimId)) * 1.0E-6D;
                double worldTPS = Math.min( 1000.0 / worldTickTime, 20);
                temp.add(String.format("%s : TPS: %s ", String.format("Dim %d", dimId),  TIME_FORMATTER.format(worldTPS)));

            }
            BotApi.LOGGER.info(temp);
            String tpsOut = temp.stream().reduce("", (listString, tps) ->
                    listString.length() == 0 ? tps : listString + ", " + tps);
            outPut += "\n" + tpsOut;
            SendMessage.Group(BotApi.config.getCommon().getGroupId(), outPut);
            if(BotApi.config.getCommon().isDebuggable()){
                BotApi.LOGGER.info("处理命令tps:" + outPut);
            }




        }

        else if("list".equals(commandBody)) {
            List<EntityPlayerMP> users = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().playerEntities;
            String result = "在线玩家数量: " + users.size();

            if (users.size() > 0) {
                String userList = users.stream()
                        .map(EntityPlayer::getDisplayName)
                        .reduce("", (listString, user) ->
                                listString.length() == 0 ? user : listString + ", " + user
                        );
                result += "\n" + "玩家列表: " + userList;
            }

            if(BotApi.config.getCommon().isDebuggable()){
                BotApi.LOGGER.info("处理命令list:" + result);
            }
            SendMessage.Group(BotApi.config.getCommon().getGroupId(), result);
        }

    }

    private static long mean(long[] values) {
        long sum = Arrays.stream(values)
                .reduce(0L, Long::sum);

        return sum / values.length;
    }

//    private static String getDimensionPrefix(int dimId) {
//        DimensionType providerType = DimensionManager.getProviderType(dimId);
//        if (providerType == null) {
//            return String.format("Dim %2d", dimId);
//        } else {
//            return String.format("Dim %2d (%s)", dimId, providerType.getName());
//        }
//    }

}
