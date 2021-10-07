package cn.evolvefield.mods.botapi.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.message.SendMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.server.command.TextComponentHelper;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class Invoke {
    private static final DecimalFormat TIME_FORMATTER = new DecimalFormat("########0.000");


    public static void invokeCommand(String command) {
        String commandBody = command.substring(1);

        if("tps".equals(commandBody)) {
            MinecraftServer SERVER = FMLCommonHandler.instance().getMinecraftServerInstance().getServer();
            String outPut;
//            for (Integer dimId : DimensionManager.getIDs())
//            {
//                double worldTickTime = mean(SERVER.worldTickTimes.get(dimId)) * 1.0E-6D;
//                double worldTPS = Math.min(1000.0/worldTickTime, 20);
//                outPut = String.format("%s : TPS: %d", getDimensionPrefix(dimId),  TIME_FORMATTER.format(worldTPS));
//            }

            double overTickTime = mean(SERVER.worldTickTimes.get(0)) * 1.0E-6D;
            double overTPS = Math.min(1000.0 / overTickTime, 20);
            double netherTickTime = mean(SERVER.worldTickTimes.get(1)) * 1.0E-6D;
            double netherTPS = Math.min(1000.0 / netherTickTime, 20);
            double endTickTime = mean(SERVER.worldTickTimes.get(2)) * 1.0E-6D;
            double endTPS = Math.min(1000.0 / endTickTime, 20);

            outPut = String.format("主世界 TPS: %.2f", overTPS)
                    +"\n" + String.format("下界 TPS: %.2f", netherTPS)
                    +"\n" + String.format("末地 TPS: %.2f", endTPS);
            //BotApi.LOGGER.info(outPut);
            SendMessage.Group(BotApi.config.getCommon().getGroupId(), outPut);
        }

        else if("list".equals(commandBody)) {
            List<EntityPlayerMP> users = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
            String result = "在线玩家数量: " + users.size();

            if (users.size() > 0) {
                String userList = users.stream()
                        .map(EntityPlayer::getDisplayNameString)
                        .reduce("", (listString, user) ->
                                listString.length() == 0 ? user : listString + ", " + user
                        );
                result += "\n" + "玩家列表: " + userList;
            }

            //BotApi.LOGGER.info(result);
            SendMessage.Group(BotApi.config.getCommon().getGroupId(), result);
        }

    }

    private static long mean(long[] values) {
        long sum = Arrays.stream(values)
                .reduce(0L, (total, item) -> total + item);

        return sum / values.length;
    }

    private static String getDimensionPrefix(int dimId) {
        DimensionType providerType = DimensionManager.getProviderType(dimId);
        if (providerType == null) {
            return String.format("Dim %2d", dimId);
        } else {
            return String.format("Dim %2d (%s)", dimId, providerType.getName());
        }
    }

}
