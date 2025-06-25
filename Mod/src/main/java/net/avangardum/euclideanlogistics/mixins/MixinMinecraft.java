package net.avangardum.euclideanlogistics.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    private void cancelSetInventoryScreen(@Nullable Screen guiScreen, @NotNull CallbackInfo ci) {
        if (guiScreen instanceof InventoryScreen) ci.cancel();
    }
}
