package cn.evole.mods.mcbot.command;


import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.config.ModConfig;
import cn.evole.mods.mcbot.core.event.IBotEvent;
import cn.evole.onebot.client.OneBotClient;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import java.util.regex.Pattern;

//#if MC <11900
import net.minecraft.network.chat.TextComponent;
//#else
//$$ import net.minecraft.network.chat.Component;
//#endif

public class ConnectCommand {

    public static int cqhttpExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val parameter = context.getArgument("parameter", String.class);


        val pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        val matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            ModConfig.INSTANCE.getBotConfig().setUrl(parameter);
            //#if MC >= 12000
            //$$ context.getSource().sendSuccess(()->Component.literal("▌ " +ChatFormatting.LIGHT_PURPLE + "尝试链接框架"), true);
            //#elseif MC < 11900
            context.getSource().sendSuccess(new TextComponent("▌ " + ChatFormatting.LIGHT_PURPLE + "尝试链接框架"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("▌ " +ChatFormatting.LIGHT_PURPLE + "尝试链接框架"), true);
            //#endif
            cqhttpDoConnect();
            return 1;

        } else {
            //#if MC >= 12000
            //$$ context.getSource().sendSuccess(()->Component.literal("▌ " +ChatFormatting.RED + "参数错误❌"), true);
            //#elseif MC < 11900
            context.getSource().sendSuccess(new TextComponent("▌ " +ChatFormatting.RED + "参数错误❌"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("▌ " +ChatFormatting.RED + "参数错误❌"), true);
            //#endif
            return 0;
        }
    }



    public static int cqhttpCommonExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        //#if MC >= 12000
        //$$ context.getSource().sendSuccess(()->Component.literal("▌ " +ChatFormatting.LIGHT_PURPLE + "尝试链接框架"), true);
        //#elseif MC < 11900
        context.getSource().sendSuccess(new TextComponent("▌ " +ChatFormatting.LIGHT_PURPLE + "尝试链接框架"), true);
        //#else
        //$$ context.getSource().sendSuccess(Component.literal("▌ " +ChatFormatting.LIGHT_PURPLE + "尝试链接框架"), true);
        //#endif
        cqhttpDoConnect();
        return 1;

    }

    public static void cqhttpDoConnect() {
        McBot.onebot = OneBotClient.create(ModConfig.INSTANCE.getBotConfig().build()).open().registerEvents(new IBotEvent());
        ModConfig.INSTANCE.getStatus().setREnable(true);
        ModConfig.INSTANCE.getCommon().setEnable(true);
        ModConfig.INSTANCE.save();
        McBot.connected = true;
    }
}
