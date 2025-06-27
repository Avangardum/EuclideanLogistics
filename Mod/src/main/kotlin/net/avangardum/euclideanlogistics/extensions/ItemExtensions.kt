package net.avangardum.euclideanlogistics.extensions

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block

object ItemExtensions {
    public fun Item.asBlock(): Block {
        return Block.byItem(this)
    }

    public fun Item.asStack(count: Int = 1): ItemStack {
        return ItemStack(this, count)
    }
}