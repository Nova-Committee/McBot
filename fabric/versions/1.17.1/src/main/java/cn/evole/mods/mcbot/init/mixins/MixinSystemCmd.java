//#if MC >= 11600
package cn.evole.mods.mcbot.init.mixins;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.init.config.ModConfig;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.charset.StandardCharsets;

/**
 * Author cnlimiter
 * CreateTime 2023/5/19 0:54
 * Name MixinSystemCmd
 * Description
 */
@Mixin(value = Commands.class)
public abstract class MixinSystemCmd {
    @Unique
    private static void say_register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((Commands.literal("say").requires((CommandSourceStack) -> CommandSourceStack.hasPermission(2))).then(Commands.argument("message", MessageArgument.message()).executes((commandContext) -> {
            Component component = MessageArgument.getMessage(commandContext, "message");
            Component component2 = new TextComponent(String.format("[%s] %s", commandContext.getSource().getDisplayName().getString(), component.getString()));
            Entity entity = commandContext.getSource().getEntity();


            if (FabricLoader.getInstance().isModLoaded("mcbot")
                        && ModConfig.INSTANCE != null
                        && ModConfig.INSTANCE.getStatus().isSChatEnable()
                        && ModConfig.INSTANCE.getStatus().isSEnable()
                        && ModConfig.INSTANCE.getCmd().isMcPrefixOn()) {
                if (ModConfig.INSTANCE.getCommon().isGuildOn() && !ModConfig.INSTANCE.getCommon().getChannelIdList().isEmpty()) {
                    var msg = String.format("[" + ModConfig.INSTANCE.getCmd().getMcPrefix() + "] %s", new String(component.getString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
                    Const.sendGuildMsg(msg);
                } else {
                    var msg = String.format("[" + ModConfig.INSTANCE.getCmd().getMcPrefix() + "] %s", new String(component.getString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
                    Const.sendGroupMsg(msg);
                }
            }
            if (entity != null) {
                commandContext.getSource().getServer().getPlayerList().broadcastMessage(component2, ChatType.CHAT, entity.getUUID());
            } else {
                commandContext.getSource().getServer().getPlayerList().broadcastMessage(component2, ChatType.SYSTEM, Util.NIL_UUID);
            }

            return 1;
        })));
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/commands/SayCommand;register(Lcom/mojang/brigadier/CommandDispatcher;)V"))
    private void sayRedirect(CommandDispatcher<CommandSourceStack> dispatcher) {
        say_register(dispatcher);
    }
}
//#endif
