package net.avangardum.euclideanlogistics.extensions

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

object ItemExtensions {
    public fun Item.asStack(count: Int = 1): ItemStack {
        return ItemStack(this, count)
    }
}
