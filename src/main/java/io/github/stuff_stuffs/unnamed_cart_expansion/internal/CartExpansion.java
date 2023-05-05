package io.github.stuff_stuffs.unnamed_cart_expansion.internal;

import io.github.stuff_stuffs.unnamed_cart_expansion.internal.block.CartExBlocks;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartExpansion implements ModInitializer {
    public static final String MOD_ID = "unnamed_cart_expansion";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        CartExBlocks.init();
    }

    public static Identifier id(final String path) {
        return new Identifier(MOD_ID, path);
    }
}