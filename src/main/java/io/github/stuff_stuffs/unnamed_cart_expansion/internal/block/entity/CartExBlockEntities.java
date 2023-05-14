package io.github.stuff_stuffs.unnamed_cart_expansion.internal.block.entity;

import io.github.stuff_stuffs.unnamed_cart_expansion.internal.CartExpansion;
import io.github.stuff_stuffs.unnamed_cart_expansion.internal.block.CartExBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class CartExBlockEntities {
    public static final BlockEntityType<SignalBlockEntity> SIGNAL_BLOCK_ENTITY_BLOCK_TYPE = FabricBlockEntityTypeBuilder.create(SignalBlockEntity::new, CartExBlocks.SIGNAL_BLOCK).build();

    public static void init() {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, CartExpansion.id("signal"), SIGNAL_BLOCK_ENTITY_BLOCK_TYPE);
    }

    private CartExBlockEntities() {
    }
}
