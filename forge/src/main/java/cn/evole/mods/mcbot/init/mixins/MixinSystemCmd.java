package cn.evole.mods.mcbot.init.mixins;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.config.ModConfig;
import cn.evole.mods.mcbot.util.MCVerUtil;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.SayCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.charset.StandardCharsets;

/**
 * Author cnlimiter
 * CreateTime 2023/5/19 0:54
 * Name MixinSystemCmd
 * Description
 */

@Mixin(value = SayCommand.class)
public abstract class MixinSystemCmd {
    @Inject(method = "method_13563", at = @At(value = "RETURN"))
    private static void mcbot$say(CommandContext commandContext, CallbackInfoReturnable<Integer> cir) throws CommandSyntaxException {
        if (Const.isLoad("mcbot")
                && ModConfig.INSTANCE != null
                && ModConfig.INSTANCE.getStatus().isSChatEnable()
                && ModConfig.INSTANCE.getStatus().isSEnable()
                && ModConfig.INSTANCE.getCmd().isMcPrefixOn()) {
            Component component = MessageArgument.getMessage(commandContext, "message");
            val msg = String.format("[" + ModConfig.INSTANCE.getCmd().getMcPrefix() + "] %s", new String(component.getString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
            Const.sendAllGroupMsg(msg);

        }
    }
}
