package io.github.stuff_stuffs.unnamed_cart_expansion.internal.block;

import io.github.stuff_stuffs.unnamed_cart_expansion.internal.CartExpansion;
import io.github.stuff_stuffs.unnamed_cart_expansion.internal.block.entity.CartExBlockEntities;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class CartExBlocks {
    public static final SignalBlock SIGNAL_BLOCK = new SignalBlock(FabricBlockSettings.copyOf(Blocks.TRIPWIRE));

    public static void init() {
        Registry.register(Registries.BLOCK, CartExpansion.id("signal"), SIGNAL_BLOCK);
        CartExBlockEntities.init();
    }

    private CartExBlocks() {
    }
}
