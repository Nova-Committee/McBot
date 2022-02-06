package cn.evolvefield.mods.botapi.api;

import cn.evolvefield.mods.botapi.common.command.CommandTree;
import cn.evolvefield.mods.botapi.util.LangUtil;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/6 11:48
 * Version: 1.0
 */
public abstract class CommandBase implements ISubCommand{


    private String name;
    private List<String> subCommands = new ArrayList<>();
    protected int permissionLevel = 3;

    public CommandBase(String name, String... subCommands) {
        this.name = name;
        this.subCommands = Arrays.asList(subCommands);
    }

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public int getPermissionLevel() {
        return permissionLevel;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        List<String> results = new ArrayList<>();
        if (args.length == 1) {
            for (String subCommand : subCommands) {
                if (subCommand.startsWith(args[0])) {
                    results.add(subCommand);
                }
            }
        }
        return results;
    }

    @Override
    public boolean isVisible(ICommandSender sender) {
        return getPermissionLevel() <= 0 || isPlayerOp(sender);
    }

    @Override
    public int[] getSyntaxOptions(ICommandSender sender) {
        return new int[]{0};
    }



    public String getCombinedArgs(String[] args) {
        String text = "";
        for (String arg : args) {
            text += arg + " ";
        }
        return text.substring(0, text.length() - 1);
    }

    protected void sendChat(ICommandSender sender, String key, Object... args) {
        sendChat(sender, false, key, args);
    }

    protected void sendChat(ICommandSender sender, boolean plural, String key, Object... args) {
        sender.addChatMessage(new ChatComponentText(LangUtil.translate(plural, key, args)));
    }

    protected boolean isPlayerOp(ICommandSender sender) {
        return CommandTree.isOwnerOrOp(sender);
    }


}
