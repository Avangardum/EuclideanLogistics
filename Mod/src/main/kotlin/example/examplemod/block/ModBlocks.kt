package example.examplemod.block

import example.examplemod.EuclideanLogisticsMod
import net.neoforged.neoforge.registries.DeferredRegister

// THIS LINE IS REQUIRED FOR USING PROPERTY DELEGATES
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object ModBlocks {
    val REGISTRY = DeferredRegister.createBlocks(EuclideanLogisticsMod.ID)
}
