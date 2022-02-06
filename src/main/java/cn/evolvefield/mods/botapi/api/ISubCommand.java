package cn.evolvefield.mods.botapi.api;

import net.minecraft.command.ICommandSender;

import java.util.List;

public interface ISubCommand {
    int getPermissionLevel();

    String getCommandName();

    void handleCommand(ICommandSender sender, String[] args);

    List<String> addTabCompletionOptions(ICommandSender sender, String[] args);

    boolean isVisible(ICommandSender sender);

    int[] getSyntaxOptions(ICommandSender sender);
}
