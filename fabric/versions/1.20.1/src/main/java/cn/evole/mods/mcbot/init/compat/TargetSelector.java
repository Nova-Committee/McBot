//#if MC == 12001
package cn.evole.mods.mcbot.init.compat;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class TargetSelector {
    private static String removeVanishedPlayers(String string) {
        String str = string;
        for (ServerPlayer player : VanishCompat.getVanishedPlayers()) {
            int index = str.indexOf(player.getName().getString());
            if (index != -1) {
                // 移除名称
                str = str.substring(0, index) + str.substring(index + player.getName().getString().length() + 2);
            }
        }
        return str;
    }

    public static String getEntityList(ServerLevel level) {
        final String[] back = new String[1];
        back[0] = "";
        level.getAllEntities().forEach(entity -> back[0] += String.format("%s%s", entity.getName().getString(), ", "));
        back[0] = back[0].contains(", ") ? back[0].substring(0, back[0].length() - 2) : back[0];
        return removeVanishedPlayers(back[0]);
    }

    public static String getPlayerList(ServerLevel level) {
        final String[] back = new String[1];
        back[0] = "";
        level.players().forEach(player -> back[0] += String.format("%s%s", player.getName().getString(), ", "));
        back[0] = back[0].contains(", ") ? back[0].substring(0, back[0].length() - 2) : back[0];
        return removeVanishedPlayers(back[0]);
    }

    public static String getNearlyPlayer(ServerLevel level) {
        BlockPos spawnPos = level.getSharedSpawnPos();
        ServerPlayer player = (ServerPlayer) level.getNearestPlayer(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), -1, false);
        if (player == null) {
            return "";
        }
        return VanishCompat.getVanishedPlayers().contains(player) ? getNearlyPlayer(level) : player.getName().getString();
    }

    public static String getRandomPlayer(ServerLevel level) {
        ServerPlayer player = level.getRandomPlayer();
        if (player == null) {
            return "";
        }
        return VanishCompat.getVanishedPlayers().contains(player) ? getRandomPlayer(level) : player.getName().getString();
    }

    public static String getSelfPlayer() {
        return "";
    }
}
//#endif
