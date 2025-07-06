/*
 * Copyright (C) 2025 Euclidean Logistics contributors. Licensed under GNU AGPL v3. See License.md.
 */

package net.avangardum.euclideanlogistics

import net.avangardum.euclideanlogistics.extensions.ItemExtensions.asStack
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

object EuclideanLogisticsDomain {
    public fun onPlayerDestroyedBlock(
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        blockEntity: BlockEntity?,
        player: Player
    ) {
        // TODO Drop currently held items.
        player.inventory.items[0] = blockState.block.asItem().asStack()
    }
}
