package cn.evolvefield.mods.botapi.command;



import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.config.ModConfig;
import cn.evolvefield.mods.botapi.message.SendMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class Invoke {


    public static void invokeCommand(String command) {
        String commandBody = command.substring(1);

        if("tps".equals(commandBody)) {
            double overTickTime = mean(BotApi.SERVER.getTickTime(World.OVERWORLD)) * 1.0E-6D;
            double overTPS = Math.min(1000.0 / overTickTime, 20);
            double netherTickTime = mean(BotApi.SERVER.getTickTime(World.NETHER)) * 1.0E-6D;
            double netherTPS = Math.min(1000.0 / netherTickTime, 20);
            double endTickTime = mean(BotApi.SERVER.getTickTime(World.END)) * 1.0E-6D;
            double endTPS = Math.min(1000.0 / endTickTime, 20);

            String outPut = String.format("主世界 TPS: %.2f", overTPS)
                    +"\n" + String.format("下界 TPS: %.2f", netherTPS)
                    +"\n" + String.format("末地 TPS: %.2f", endTPS);
            //BotApi.LOGGER.info(outPut);
            SendMessage.Group(ModConfig.GROUP_ID.get(), outPut);
        }

        else if("list".equals(commandBody)) {
            List<ServerPlayerEntity> users = BotApi.SERVER.getPlayerList().getPlayers();

            String result = "在线玩家数量: " + users.size();

            if (users.size() > 0) {
                ITextComponent userList = users.stream()
                        .map(PlayerEntity::getDisplayName)
                        .reduce(new StringTextComponent(""), (listString, user) ->
                                listString.getString().length() == 0 ? user : new StringTextComponent(listString.getString() + ", " + user.getString())
                        );
                result += "\n" + "玩家列表: " + userList.getString();
            }
            //BotApi.LOGGER.info(result);
            SendMessage.Group(ModConfig.GROUP_ID.get(), result);
        }

    }

    private static long mean(long[] values) {
        long sum = Arrays.stream(values)
                .reduce(0L, (total, item) -> total + item);

        return sum / values.length;
    }

}
