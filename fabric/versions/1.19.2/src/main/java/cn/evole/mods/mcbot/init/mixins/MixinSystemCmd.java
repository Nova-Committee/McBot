//#if MC >= 11900
package cn.evole.mods.mcbot.init.mixins;

import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.init.handler.ConfigHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.ChatType;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
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
    private static void say_register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((Commands.literal("say").requires((CommandSourceStack) -> CommandSourceStack.hasPermission(2))).then(Commands.argument("message", MessageArgument.message()).executes((commandContext) -> {
            MessageArgument.ChatMessage chatMessage = MessageArgument.getChatMessage(commandContext, "message");
            CommandSourceStack commandSourceStack = commandContext.getSource();
            PlayerList playerList = commandSourceStack.getServer().getPlayerList();
            chatMessage.resolve(commandSourceStack, (playerChatMessage) -> {

                if (FabricLoader.getInstance().isModLoaded("mcbot")
                        && ConfigHandler.cached() != null
                        && ConfigHandler.cached().getStatus().isS_CHAT_ENABLE()
                        && ConfigHandler.cached().getStatus().isSEND_ENABLED()
                        && ConfigHandler.cached().getCmd().isMcSystemPrefixOn()) {
                    if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                        for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                            McBot.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(),
                                    id,
                                    String.format("[" + ConfigHandler.cached().getCmd().getMcSystemPrefix() + "] %s", new String(playerChatMessage.serverContent().getString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8)));
                    } else {
                        for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                            McBot.bot.sendGroupMsg(id,
                                    String.format("[" + ConfigHandler.cached().getCmd().getMcSystemPrefix() + "] %s", new String(playerChatMessage.serverContent().getString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8)),
                                    true);
                    }
                }
                playerList.broadcastChatMessage(playerChatMessage, commandSourceStack, ChatType.bind(ChatType.SAY_COMMAND, commandSourceStack));
            });


            return 1;
        })));
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/commands/SayCommand;register(Lcom/mojang/brigadier/CommandDispatcher;)V"))
    private void sayRedirect(CommandDispatcher<CommandSourceStack> dispatcher) {
        say_register(dispatcher);
    }
}
//#endif
