package org.c191239.customitemmerge;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.io.IOException;

public class MyReloadCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("reload").requires(source -> source.hasPermissionLevel(2)).then(CommandManager.literal("item-merge").executes(context -> {
            context.getSource().sendFeedback(Text.literal("Reloading merge distance..."), true);
            try {
                CustomItemMerge.configManager.loadConfigElseDefault();
            } catch (IOException e) {
                CustomItemMerge.errorLog("Suppressed exception", e);
            }
            return 1;
        })));
    }
}