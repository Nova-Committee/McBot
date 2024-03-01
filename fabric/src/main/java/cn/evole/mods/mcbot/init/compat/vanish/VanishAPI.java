package cn.evole.mods.mcbot.init.compat.vanish;

import com.mojang.authlib.GameProfile;
import me.drex.vanish.util.VanishManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.UUID;

public class VanishAPI {
    public static boolean isVanished(ServerPlayer player){
        if(!VanishCompat.VANISH) return false;
        // 获取玩家的 GameProfile
        GameProfile gameProfile = player.getGameProfile();
        // 从 GameProfile 中获取 UUID
        UUID uuid = gameProfile.getId();

        return VanishManager.isVanished(player.getServer(), uuid);
    }

    /**
     * 替代原版指令执行。此处执行命令将保留Vanish兼容。
     * 如：非OP的目标选择器等。
     * @param commands MinecraftServer.getCommands()
     * @param sender BotCmdRun.CUSTOM / BotCmdRun.OP
     * @param cmd 指令
     */
    public static void performPrefixedCommand(Commands commands, CommandSourceStack sender, String cmd) {
        String targetCmd = cmd.length() > Short.MAX_VALUE ? cmd.substring(0, Short.MAX_VALUE) : cmd;  // 截断字符串以防管理不当人（？

        if (targetCmd.indexOf('@') == -1)
            //#if MC < 11900
            commands.performCommand(sender, cmd);
            //#else
            //$$ commands.performPrefixedCommand(sender, cmd);
            //#endif

        HashMap<String, String> cache = new HashMap<>();

        for (String pattern : "@e @a @p @r @s".split(" ")) {
            while (targetCmd.contains(pattern)) {
                if (cache.containsKey(pattern)) {
                    targetCmd = targetCmd.replace(pattern, cache.get(pattern));
                } else {
                    switch (pattern) {
                        case "@e": { cache.put(pattern, TargetSelector.getEntityList(sender.getLevel())); break; }
                        case "@a": { cache.put(pattern, TargetSelector.getPlayerList(sender.getLevel())); break; }
                        case "@p": { cache.put(pattern, TargetSelector.getNearlyPlayer(sender.getLevel())); break; }
                        case "@r": { cache.put(pattern, TargetSelector.getRandomPlayer(sender.getLevel())); break; }
                        case "@s": { cache.put(pattern, TargetSelector.getSelfPlayer()); break; }
                    }
                }
            }
        }
        //#if MC < 11900
        commands.performCommand(sender, targetCmd);
        //#else
        //$$ commands.performPrefixedCommand(sender, targetCmd);
        //#endif
    }
}
