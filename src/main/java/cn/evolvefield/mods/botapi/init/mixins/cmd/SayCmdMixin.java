package cn.evolvefield.mods.botapi.init.mixins.cmd;

import cn.evolvefield.mods.botapi.BotApi;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.ChatType;
import net.minecraft.server.commands.SayCommand;
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
@Mixin(value = SayCommand.class)
public class SayCmdMixin {


    @Redirect(method = "register", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;then(Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;"))
    private static ArgumentBuilder cmd_registry(LiteralArgumentBuilder instance, ArgumentBuilder argumentBuilder) {
        return Commands.argument("message", MessageArgument.message()).executes((commandContext) -> {
            MessageArgument.resolveChatMessage(commandContext, "message", (playerChatMessage) -> {
                CommandSourceStack commandSourceStack = commandContext.getSource();
                PlayerList playerList = commandSourceStack.getServer().getPlayerList();
                /////////////////////////
                if (FabricLoader.getInstance().isModLoaded("botapi")
                        && BotApi.config != null
                        && BotApi.config.getStatus().isS_CHAT_ENABLE()
                        && BotApi.config.getStatus().isSEND_ENABLED()
                        && BotApi.config.getCmd().isMcSystemPrefixEnable()) {
                    if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                        for (String id : BotApi.config.getCommon().getChannelIdList())
                            BotApi.bot.sendGuildMsg(BotApi.config.getCommon().getGuildId(),
                                    id,
                                    String.format("[SERVER] %s", playerChatMessage.decoratedContent().getString()));
                    } else {
                        for (long id : BotApi.config.getCommon().getGroupIdList())
                            BotApi.bot.sendGroupMsg(id,
                                    String.format("[SERVER] %s", playerChatMessage.decoratedContent().getString()),
                                    true);
                    }
                }
                /////////////////////////

                playerList.broadcastChatMessage(playerChatMessage, commandSourceStack, ChatType.bind(ChatType.SAY_COMMAND, commandSourceStack));
            });
            return 1;
        });
    }
}
