package cn.evolvefield.mods.botapi.init.mixins.cmd;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
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

/**
 * Project: Bot-Connect-fabric-1.18
 * Author: cnlimiter
 * Date: 2023/1/23 18:52
 * Description:
 */
@Mixin(value = Commands.class)
public class SystemCmdMixin {
    private static void say_register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((Commands.literal("say").requires((commandSourceStack) -> commandSourceStack.hasPermission(2))).then(Commands.argument("message", MessageArgument.message()).executes((commandContext) -> {
            MessageArgument.resolveChatMessage(commandContext, "message", (playerChatMessage) -> {
                CommandSourceStack commandSourceStack = commandContext.getSource();
                PlayerList playerList = commandSourceStack.getServer().getPlayerList();
                /////////////////////////
                if (FabricLoader.getInstance().isModLoaded("botapi")
                        && ConfigHandler.cached() != null
                        && ConfigHandler.cached().getStatus().isS_CHAT_ENABLE()
                        && ConfigHandler.cached().getStatus().isSEND_ENABLED()
                        && ConfigHandler.cached().getCmd().isMcSystemPrefixEnable()) {
                    if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                        for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                            BotApi.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(),
                                    id,
                                    String.format("[" + ConfigHandler.cached().getCmd().getMcSystemPrefix() + "] %s", playerChatMessage.decoratedContent().getString()));
                    } else {
                        for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                            BotApi.bot.sendGroupMsg(id,
                                    String.format("[" + ConfigHandler.cached().getCmd().getMcSystemPrefix() + "] %s", playerChatMessage.decoratedContent().getString()),
                                    true);
                    }
                }
                /////////////////////////
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
