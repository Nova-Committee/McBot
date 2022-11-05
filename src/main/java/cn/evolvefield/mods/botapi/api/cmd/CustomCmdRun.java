package cn.evolvefield.mods.botapi.api.cmd;

import cn.evolvefield.mods.botapi.BotApi;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/2 13:51
 * Version: 1.0
 */
public class CustomCmdRun implements ICommandSender {
    public static CustomCmdRun CUSTOM = new CustomCmdRun();
    public List<String> outPut = new ArrayList<>();

    @Override
    public void sendMessage(ITextComponent component) {
        ICommandSender.super.sendMessage(component);
        this.outPut.add(component.getFormattedText());
    }


    @Override
    public String getName() {
        return "Bot";
    }

    @Override
    public boolean canUseCommand(int permLevel, String commandName) {
        return true;
    }

    @Override
    public World getEntityWorld() {
        return null;
    }

    @Nullable
    @Override
    public MinecraftServer getServer() {
        return BotApi.SERVER;
    }
}
