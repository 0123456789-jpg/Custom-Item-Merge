package org.c191239.customitemmerge.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Box;
import org.c191239.customitemmerge.CustomItemMerge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Redirect(method = "tryMerge()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Box;expand(DDD)Lnet/minecraft/util/math/Box;"))
    private Box replaceArgs(Box box, double x, double y, double z){
        double xx = CustomItemMerge.configManager.mergeX;
        double yy = CustomItemMerge.configManager.mergeY;
        double zz = CustomItemMerge.configManager.mergeZ;
        return box.expand(xx, yy, zz);
    }
}
