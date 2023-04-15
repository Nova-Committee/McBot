package cn.evolvefield.mods.botapi.api.cmd;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/2 13:51
 * Version: 1.0
 */
public class BotCmdRun implements ICommandSender {
    public static BotCmdRun CUSTOM = new BotCmdRun("Bot");

    public static BotCmdRun OP = new BotCmdRun("OP");
    public List<String> outPut = new ArrayList<>();
    private final String name;

    public BotCmdRun(String name) {
        this.name = name;
    }

    @Override
    public String getCommandSenderName() {
        return name;
    }

    @Override
    public IChatComponent func_145748_c_() {
        return new ChatComponentText(this.getCommandSenderName());
    }

    @Override
    public void addChatMessage(IChatComponent component) {
        this.outPut.add(component.getUnformattedText());
    }

    @Override
    public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_) {
        return true;
    }

    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return new ChunkCoordinates(0, 0, 0);
    }

    @Override
    public World getEntityWorld() {
       return MinecraftServer.getServer().getEntityWorld();
    }
}
