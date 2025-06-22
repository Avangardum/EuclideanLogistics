package net.avangardum.euclideanlogistics.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.neoforged.neoforge.client.gui.GuiLayerManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class MixinGui {
    private final int BOTTOM_GUI_Y_OFFSET = 22;

    @Inject(method = "renderItemHotbar", at = @At("HEAD"), cancellable = true)
    public void onRenderItemHotbar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        ci.cancel();
    }

    // draft:

    @Shadow public int leftHeight;
    @Shadow public int rightHeight;

    @Final @Shadow private GuiLayerManager layerManager;
    @Final @Shadow private Minecraft minecraft;

    @Overwrite
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        RenderSystem.enableDepthTest();
        this.leftHeight = 39 - BOTTOM_GUI_Y_OFFSET;
        this.rightHeight = 39 - BOTTOM_GUI_Y_OFFSET;
        this.layerManager.render(guiGraphics, deltaTracker);
        RenderSystem.disableDepthTest();
    }

    @Overwrite
    private void renderExperienceLevel(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        int i = this.minecraft.player.experienceLevel;
        if (true) {
            //this.minecraft.getProfiler().push("expLevel");
            String s = "" + i;
            int j = (guiGraphics.guiWidth() - this.getFont().width(s)) / 2;
            int k = guiGraphics.guiHeight() - 31 - 4 + BOTTOM_GUI_Y_OFFSET;
            guiGraphics.drawString(this.getFont(), s, j + 1, k, 0, false);
            guiGraphics.drawString(this.getFont(), s, j - 1, k, 0, false);
            guiGraphics.drawString(this.getFont(), s, j, k + 1, 0, false);
            guiGraphics.drawString(this.getFont(), s, j, k - 1, 0, false);
            guiGraphics.drawString(this.getFont(), s, j, k, 8453920, false);
            //this.minecraft.getProfiler().pop();
        }
    }

    @Shadow public Font getFont() { return null; }

    @Shadow @Final private static ResourceLocation EXPERIENCE_BAR_BACKGROUND_SPRITE;
    @Shadow @Final private static ResourceLocation EXPERIENCE_BAR_PROGRESS_SPRITE;

    @Shadow @Final private static ResourceLocation JUMP_BAR_BACKGROUND_SPRITE;
    @Shadow @Final private static ResourceLocation JUMP_BAR_COOLDOWN_SPRITE;
    @Shadow @Final private static ResourceLocation JUMP_BAR_PROGRESS_SPRITE;

    @Overwrite
    private void renderExperienceBar(GuiGraphics guiGraphics, int x) {
        this.minecraft.getProfiler().push("expBar");
        int i = this.minecraft.player.getXpNeededForNextLevel();
        if (i > 0) {
            int k = (int)(this.minecraft.player.experienceProgress * 183.0F);
            int l = guiGraphics.guiHeight() - 32 + 3 + BOTTOM_GUI_Y_OFFSET;
            RenderSystem.enableBlend();
            guiGraphics.blitSprite(EXPERIENCE_BAR_BACKGROUND_SPRITE, x, l, 182, 5);
            if (k > 0) {
                guiGraphics.blitSprite(EXPERIENCE_BAR_PROGRESS_SPRITE, 182, 5, 0, 0, x, l, k, 5);
            }

            RenderSystem.disableBlend();
        }

        this.minecraft.getProfiler().pop();
    }

    private void renderJumpMeter(PlayerRideableJumping rideable, GuiGraphics guiGraphics, int x) {
        this.minecraft.getProfiler().push("jumpBar");
        float f = this.minecraft.player.getJumpRidingScale();
        int j = (int)(f * 183.0F);
        int k = guiGraphics.guiHeight() - 32 + 3 + BOTTOM_GUI_Y_OFFSET;
        RenderSystem.enableBlend();
        guiGraphics.blitSprite(JUMP_BAR_BACKGROUND_SPRITE, x, k, 182, 5);
        if (rideable.getJumpCooldown() > 0) {
            guiGraphics.blitSprite(JUMP_BAR_COOLDOWN_SPRITE, x, k, 182, 5);
        } else if (j > 0) {
            guiGraphics.blitSprite(JUMP_BAR_PROGRESS_SPRITE, 182, 5, 0, 0, x, k, j, 5);
        }

        RenderSystem.disableBlend();
        this.minecraft.getProfiler().pop();
    }
}
