package net.avangardum.euclideanlogistics.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static net.avangardum.euclideanlogistics.helpers.MixinHelpers.*;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow @Nullable public LocalPlayer player;
    @Shadow @Nullable public MultiPlayerGameMode gameMode;
    @Shadow protected int missTime;

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    private void cancelSetInventoryScreen(@Nullable Screen guiScreen, @NotNull CallbackInfo ci) {
        if (guiScreen instanceof InventoryScreen) ci.cancel();
    }

    @Unique
    private static final String START_DESTROY_BLOCK_DESCRIPTOR =
        "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;" +
        "startDestroyBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z";

    @Redirect(method = "startAttack", at = @At(value = "INVOKE", target = START_DESTROY_BLOCK_DESCRIPTOR))
    private boolean startDestroyBlockWrapper(MultiPlayerGameMode gameMode, BlockPos blockPos, Direction face) {
        if (isDiggingDisabledBecauseHoldingBlock()) {
            onAttackMiss();
            return false;
        }
        return gameMode.startDestroyBlock(blockPos, face);
    }

    @Unique
    private boolean isDiggingDisabledBecauseHoldingBlock() {
        boolean isHoldingBlock = asNotNull(player).getMainHandItem().getItem() instanceof BlockItem;
        return isHoldingBlock && !asNotNull(gameMode).hasInfiniteItems();
    }

    @Unique
    private void onAttackMiss() {
        // copied from startAttack case MISS

        if (asNotNull(this.gameMode).hasMissTime()) {
            this.missTime = 10;
        }

        asNotNull(this.player).resetAttackStrengthTicker();
        CommonHooks.onEmptyLeftClick(this.player);
    }

    @Unique
    private static final String CONTINUE_DESTROY_BLOCK_DESCRIPTOR =
        "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;" +
        "continueDestroyBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z";

    @Redirect(method = "continueAttack", at = @At(value = "INVOKE", target = CONTINUE_DESTROY_BLOCK_DESCRIPTOR))
    private boolean continueDestroyBlockWrapper(MultiPlayerGameMode gameMode, BlockPos blockPos, Direction face) {
        if (isDiggingDisabledBecauseHoldingBlock()) return false;
        return gameMode.continueDestroyBlock(blockPos, face);
    }
}
