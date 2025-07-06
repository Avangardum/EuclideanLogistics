/*
 * Copyright (C) 2025 Euclidean Logistics contributors. Licensed under GNU AGPL v3. See License.md.
 */

package net.avangardum.euclideanlogistics.mixins;

import net.avangardum.euclideanlogistics.EuclideanLogisticsDomain;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Block.class)
public abstract class MixinBlock extends BlockBehaviour {
    public MixinBlock(Properties properties) {
        super(properties);
    }

    @Unique
    private static final String DROP_RESOURCES_DESCRIPTOR =
        "Lnet/minecraft/world/level/block/Block;dropResources(Lnet/minecraft/world/level/block/state/BlockState;" +
        "Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;" +
        "Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;" +
        "Lnet/minecraft/world/item/ItemStack;)V";

    @Redirect(method = "playerDestroy", at = @At(value = "INVOKE", target = DROP_RESOURCES_DESCRIPTOR))
    private void dropResourcesOnPlayerDestroyProxy(
        @NotNull BlockState blockState,
        @NotNull Level level,
        @NotNull BlockPos blockPos,
        @Nullable BlockEntity blockEntity,
        @NotNull Entity player,
        @NotNull ItemStack tool
    ) {
        EuclideanLogisticsDomain.INSTANCE.onPlayerDestroyedBlock(
            blockState,
            level,
            blockPos,
            blockEntity,
            (Player) player
        );
    }
}
