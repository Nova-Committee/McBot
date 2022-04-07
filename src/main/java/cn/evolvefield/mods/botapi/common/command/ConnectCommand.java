package cn.evolvefield.mods.botapi.common.command;


import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.server.command.CommandTreeBase;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ConnectCommand extends CommandTreeBase {


    public ConnectCommand() {
        this.addSubcommand(new MiraiCmd());
        this.addSubcommand(new CqhttpCmd());
    }


    @Override
    public String getName() {
        return "connect";
    }


    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/mcbot connect <mirai|cqhttp> <host>:<port>";
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, "mirai", "cqhttp");
        }

        return Collections.emptyList();
    }


}
