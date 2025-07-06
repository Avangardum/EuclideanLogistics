/*
 * Copyright (C) 2025 Euclidean Logistics contributors. Licensed under GNU AGPL v3. See License.md.
 */

import net.avangardum.euclideanlogistics.block.ModBlocks
import net.neoforged.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(EuclideanLogisticsModCopy.ID)
object EuclideanLogisticsModCopy {
    const val ID = "euclideanlogistics";

    val LOGGER: Logger = LogManager.getLogger(ID)

    fun doNothing(): Unit {
        val five: Int = 5
        if (five == 5) {

        }
        return Unit;
    }

    init {
        ModBlocks.REGISTRY.register(MOD_BUS)
    }
}
