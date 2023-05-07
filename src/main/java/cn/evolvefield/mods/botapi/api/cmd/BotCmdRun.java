package cn.evolvefield.mods.botapi.api.cmd;

import cn.evolvefield.mods.botapi.BotApi;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/2 13:51
 * Version: 1.0
 */
public class BotCmdRun extends CommandSourceStack {
    public static BotCmdRun CUSTOM = new BotCmdRun(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, BotApi.SERVER.overworld(), 4,
            "Bot", Component.literal("Bot"), BotApi.SERVER, null);

    public static BotCmdRun OP = new BotCmdRun(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, BotApi.SERVER.overworld(), 4,
            "OP", Component.literal("OP"), BotApi.SERVER, null);
    public List<String> outPut = new ArrayList<>();

    public BotCmdRun(CommandSource p_81302_, Vec3 p_81303_, Vec2 p_81304_, ServerLevel p_81305_, int p_81306_, String p_81307_, Component p_81308_, MinecraftServer p_81309_, @Nullable Entity p_81310_) {
        super(p_81302_, p_81303_, p_81304_, p_81305_, p_81306_, p_81307_, p_81308_, p_81309_, p_81310_);
    }

    @Override
    public void sendSuccess(@NotNull Component component, boolean p_81356_) {
        super.sendSuccess(component, p_81356_);
        this.outPut.add(component.getString());

    }


}
