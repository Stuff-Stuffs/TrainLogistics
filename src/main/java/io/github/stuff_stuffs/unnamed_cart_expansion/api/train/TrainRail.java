package io.github.stuff_stuffs.unnamed_cart_expansion.api.train;

import io.github.stuff_stuffs.train_lib.api.common.cart.Rail;
import net.minecraft.util.math.BlockPos;

public interface TrainRail extends Rail<TrainRail> {
    Position entrancePosition();

    Position exitPosition();

    Position railPosition();

    record Position(BlockPos first, BlockPos second) {
        public Position(final BlockPos first, final BlockPos second) {
            final boolean less = first.compareTo(second) < 0;
            this.first = less ? first.toImmutable() : second.toImmutable();
            this.second = less ? second.toImmutable() : first.toImmutable();
            if (first.getManhattanDistance(second) != 1) {
                throw new IllegalArgumentException();
            }
        }
    }
}
