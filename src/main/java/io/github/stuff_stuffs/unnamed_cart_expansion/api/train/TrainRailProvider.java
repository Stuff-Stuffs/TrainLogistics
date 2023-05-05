package io.github.stuff_stuffs.unnamed_cart_expansion.api.train;

import io.github.stuff_stuffs.train_lib.api.common.cart.RailProvider;
import net.minecraft.util.math.BlockPos;

public interface TrainRailProvider extends RailProvider<TrainRail> {
    BlockPos otherPart();
}
