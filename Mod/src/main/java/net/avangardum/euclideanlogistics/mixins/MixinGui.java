package net.avangardum.euclideanlogistics.mixins;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class MixinGui {
    @Unique private static final int BOTTOM_GUI_Y_OFFSET = 22;

    @Inject(method = "renderItemHotbar", at = @At("HEAD"), cancellable = true)
    public void onRenderItemHotbar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        ci.cancel();
    }

    @Unique private static final String LAYER_MANAGER_RENDER_DESCRIPTOR =
            "Lnet/neoforged/neoforge/client/gui/GuiLayerManager;render" +
            "(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V";

    @Shadow public int leftHeight;
    @Shadow public int rightHeight;

    @Inject(method = "render", at = @At(value = "INVOKE", target = LAYER_MANAGER_RENDER_DESCRIPTOR))
    private void modifyHeights(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        leftHeight -= BOTTOM_GUI_Y_OFFSET;
        rightHeight -= BOTTOM_GUI_Y_OFFSET;
    }

    @Unique private static final String GET_FONT_DESCRIPTOR =
            "Lnet/minecraft/client/gui/Gui;getFont()Lnet/minecraft/client/gui/Font;";

    @ModifyVariable(
            method = "renderExperienceLevel",
            at = @At(value = "INVOKE", target = GET_FONT_DESCRIPTOR, ordinal = 1),
            ordinal = 2
    )
    private int modifyExperienceLevelY(int y) {
        return y + BOTTOM_GUI_Y_OFFSET;
    }

    @Unique private static final String ENABLE_BLEND_DESCRIPTOR =
            "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V";

    @ModifyVariable(
            method = "renderExperienceBar",
            at = @At(value = "INVOKE", target = ENABLE_BLEND_DESCRIPTOR),
            ordinal = 4
    )
    private int modifyExperienceBarY(int y) {
        return y + BOTTOM_GUI_Y_OFFSET;
    }

    @ModifyVariable(
            method = "renderJumpMeter",
            at = @At(value = "INVOKE", target = ENABLE_BLEND_DESCRIPTOR),
            ordinal = 3
    )
    private int modifyJumpBarY(int y) {
        return y + BOTTOM_GUI_Y_OFFSET;
    }
}
