package io.github.stuff_stuffs.unnamed_cart_expansion.api.train;

import io.github.stuff_stuffs.unnamed_cart_expansion.internal.CartExpansion;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;

public final class TrainApi {
    public static final BlockApiLookup<TrainRailProvider, Void> TRAIN_RAIL_PROVIDER_API = BlockApiLookup.get(CartExpansion.id("train_rail_provider"), TrainRailProvider.class, Void.class);

    private TrainApi() {
    }
}
