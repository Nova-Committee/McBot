package cn.evole.mods.mcbot.cmds;

import cn.evole.mods.mcbot.McBot;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import java.util.function.Supplier;
//#if MC < 11900
import net.minecraft.network.chat.TextComponent;
//#endif


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/2 13:51
 * Version: 1.0
 */
public class BotCmdRun extends CommandSourceStack {

    //#if MC < 11900
    public static BotCmdRun CUSTOM = new BotCmdRun(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, McBot.SERVER.overworld(), 0, "Bot", new TextComponent("Bot"), McBot.SERVER, null);
    public static BotCmdRun OP = new BotCmdRun(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, McBot.SERVER.overworld(), 4, "OP", new TextComponent("OP"), McBot.SERVER, null);
    //#else
    //$$ public static BotCmdRun CUSTOM = new BotCmdRun(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, McBot.SERVER.overworld(), 0, "Bot", Component.literal("Bot"), McBot.SERVER, null);
    //$$ public static BotCmdRun OP = new BotCmdRun(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, McBot.SERVER.overworld(), 4, "OP", Component.literal("OP"), McBot.SERVER, null);
    //#endif


    public List<String> outPut = new ArrayList<>();

    public BotCmdRun(CommandSource commandSource, Vec3 vec3, Vec2 vec2, ServerLevel serverLevel, int i, String string, Component component, MinecraftServer minecraftServer, @Nullable Entity entity) {
        super(commandSource, vec3, vec2, serverLevel, i, string, component, minecraftServer, entity);
    }

    //#if MC >= 12000
    //$$ @Override
    //$$ public void sendSuccess(Supplier<Component> supplier, boolean bl) {
    //$$     super.sendSuccess(supplier, bl);
    //$$     this.outPut.add(supplier.get().getString());
    //$$ }
    //#else
    @Override
    public void sendSuccess(@NotNull Component component, boolean p_81356_) {
        super.sendSuccess(component, p_81356_);
        this.outPut.add(component.getString());
    }
    //#endif

}
