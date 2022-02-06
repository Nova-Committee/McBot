package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.api.ISubCommand;
import com.mojang.authlib.GameProfile;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.*;

public class CommandTree extends CommandBase {

    public static Map<String, ISubCommand> commands = new LinkedHashMap<String, ISubCommand>();
    public static CommandTree instance = new CommandTree();
    static  {

        register(new ConnectCommand());
        register(new DisconnectCommand());
        register(new ReceiveCommand());
        register(new SendCommand());
        register(new StatusCommand());
        register(new GroupIDCommand());
        register(new DebugCommand());
    }



    public static void register(ISubCommand command) {
        commands.put(command.getCommandName(), command);
    }

    public static boolean commandExists(String name) {
        return commands.containsKey(name);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandName() {
        return "mcbot";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName() ;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            String subCommand = args[0];
            List result = new ArrayList();
            for (ISubCommand command : commands.values()) {
                if (command.isVisible(sender) && command.getCommandName().startsWith(subCommand))
                    result.add(command.getCommandName());
            }
            return result;
        } else if (commands.containsKey(args[0]) && commands.get(args[0]).isVisible(sender)) {
            return commands.get(args[0]).addTabCompletionOptions(sender, Arrays.copyOfRange(args, 1, args.length));
        }
        return null;
    }


    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 1) {
            args = new String[]{"help"};
        }
        ISubCommand command = commands.get(args[0]);
        if (command != null) {
            if (command.isVisible(sender) && (sender.canCommandSenderUseCommand(command.getPermissionLevel(), getCommandName() + " " + command.getCommandName())
                    || (sender instanceof EntityPlayerMP && command.getPermissionLevel() <= 0))) {
                command.handleCommand(sender, Arrays.copyOfRange(args, 1, args.length));
                return;
            }
            throw new CommandException("botapi.commands.no_permision");
        }
        throw new CommandNotFoundException("botapi.commands.notFound");
    }

    public static boolean isOwnerOrOp(ICommandSender sender) {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            GameProfile username = player.getGameProfile();
            return isCommandsAllowedOrOwner(username);
        } else
            return true;
    }


    public static boolean isCommandsAllowedOrOwner(GameProfile username) {
        return MinecraftServer.getServer().getConfigurationManager().func_152596_g(username) || MinecraftServer.getServer().isSinglePlayer() && MinecraftServer.getServer().getServerOwner().equals(username);
    }

    @Override
    public int compareTo(Object obj) {
        {
            if (obj instanceof ICommand) {
                return this.compareTo((ICommand) obj);
            } else {
                return 0;
            }
        }
    }

    public static ISubCommand getCommand(String commandName) {
        return commands.get(commandName);
    }
}
