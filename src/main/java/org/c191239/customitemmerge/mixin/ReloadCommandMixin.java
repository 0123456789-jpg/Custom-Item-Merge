package org.c191239.customitemmerge.mixin;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ReloadCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.SaveProperties;
import org.c191239.customitemmerge.CustomItemMerge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(ReloadCommand.class)
public class ReloadCommandMixin {
    @ModifyArg(method = "register", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;executes(Lcom/mojang/brigadier/Command;)Lcom/mojang/brigadier/builder/ArgumentBuilder;", remap = false))
    private static Command<?> configReloadInjector(Command<?> command){
        return context -> {
            ServerCommandSource serverCommandSource = (ServerCommandSource) context.getSource();
            MinecraftServer minecraftServer = serverCommandSource.getServer();
            ResourcePackManager resourcePackManager = minecraftServer.getDataPackManager();
            SaveProperties saveProperties = minecraftServer.getSaveProperties();
            Collection<String> enabledPacks = resourcePackManager.getEnabledNames();
            resourcePackManager.scanPacks();
            ArrayList<String> newPacks = Lists.newArrayList(enabledPacks);
            List<String> disabledPacks = saveProperties.getDataPackSettings().getDisabled();
            for (String string : resourcePackManager.getNames()){
                if (disabledPacks.contains(string) || newPacks.contains(string)) continue;
                newPacks.add(string);
            }
            serverCommandSource.sendFeedback(new TranslatableText("commands.reload.success"), true);
            ReloadCommand.tryReloadDataPacks(newPacks, serverCommandSource);
            try {
                CustomItemMerge.configManager.loadConfigElseDefault();
            } catch (IOException e) {
                CustomItemMerge.errorLog("Suppressed exception", e);
            }
            return 0;
        };
    }

}
