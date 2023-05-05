package io.github.stuff_stuffs.unnamed_cart_expansion.internal.block;

import io.github.stuff_stuffs.train_lib.internal.common.TrainLib;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class CartExBlocks {
    public static final StraightTrainRailBlock STRAIGHT_TRAIN_RAIL = new StraightTrainRailBlock(FabricBlockSettings.copyOf(Blocks.RAIL));

    public static void init() {
        Registry.register(Registries.BLOCK, TrainLib.id("train_rail"), STRAIGHT_TRAIN_RAIL);
    }

    private CartExBlocks() {
    }
}
