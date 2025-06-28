package net.avangardum.euclideanlogistics

import net.avangardum.euclideanlogistics.block.ModBlocks
import net.neoforged.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(EuclideanLogisticsMod.ID)
object EuclideanLogisticsMod {
    const val ID = "euclideanlogistics"

    val LOGGER: Logger = LogManager.getLogger(ID)

    init {
        ModBlocks.REGISTRY.register(MOD_BUS)
    }
}
