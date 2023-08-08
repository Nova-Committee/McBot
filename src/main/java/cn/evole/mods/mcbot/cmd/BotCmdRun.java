package cn.evole.mods.mcbot.cmd;

import cn.evole.mods.mcbot.McBotKoishi;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Author cnlimiter
 * CreateTime 2023/7/28 16:49
 * Name BotCmdRun
 * Description
 */

public class BotCmdRun extends CommandSourceStack {
    public static BotCmdRun CUSTOM = new BotCmdRun(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, McBotKoishi.server.overworld(), 4, "Bot", Component.literal("Bot"), McBotKoishi.server, null);
    public static BotCmdRun OP = new BotCmdRun(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, McBotKoishi.server.overworld(), 4, "OP", Component.literal("OP"), McBotKoishi.server, null);


    public List<String> outPut = new ArrayList<>();

    public BotCmdRun(CommandSource commandSource, Vec3 vec3, Vec2 vec2, ServerLevel serverLevel, int i, String string, Component component, MinecraftServer minecraftServer, @Nullable Entity entity) {
        super(commandSource, vec3, vec2, serverLevel, i, string, component, minecraftServer, entity);
    }

    @Override
    public void sendSuccess(Supplier<Component> supplier, boolean bl) {
        super.sendSuccess(supplier, bl);
        this.outPut.add(supplier.get().getString());
    }
}
