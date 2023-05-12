package net.fabricmc.fabric.api.command.v1;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.commands.CommandSourceStack;

public interface CommandRegistrationCallback {
    Event<CommandRegistrationCallback> EVENT = EventFactory.createArrayBacked(CommandRegistrationCallback.class, (callbacks) -> (dispatcher, dedicated) -> {
        for (CommandRegistrationCallback callback : callbacks) {
            callback.register(dispatcher, dedicated);
        }
    });

    /**
     * Called when the server is registering commands.
     *
     * @param dispatcher the command dispatcher to register commands to.
     * @param dedicated whether the server this command is being registered on is a dedicated server.
     */
    void register(CommandDispatcher<CommandSourceStack> dispatcher, boolean dedicated);
}
