/*
 * Copyright (C) 2025 Euclidean Logistics contributors. Licensed under GNU AGPL v3. See License.md.
 */

package net.avangardum.euclideanlogistics.mixins;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Inventory.class)
public abstract class MixinInventory implements Container {
    @Shadow @Final public NonNullList<ItemStack> items;
    @Shadow public int selected;

    @Overwrite
    public int getFreeSlot() {
        return this.items.getFirst().isEmpty() ? 0 : -1;
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack stack) {
        return 1;
    }

    /** Handle mouse scroll. */
    @Overwrite
    public void swapPaint(double direction) {
        selected = 0;
    }
}
