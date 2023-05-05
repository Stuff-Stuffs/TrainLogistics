package io.github.stuff_stuffs.unnamed_cart_expansion.api.train;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import io.github.stuff_stuffs.train_lib.api.common.cart.RailProvider;
import io.github.stuff_stuffs.train_lib.impl.common.AbstractCartImpl;
import io.github.stuff_stuffs.train_lib.impl.common.CartPathfinder;
import io.github.stuff_stuffs.train_lib.internal.common.TrainLib;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TrainCartImpl extends AbstractCartImpl<TrainRail, TrainRail.Position> {
    public static final CartPathfinder<TrainRail, TrainRail.Position> PATHFINDER = new CartPathfinder<>(TrainLib.MINECART_RECURSION_LIMIT * 4) {
        @Override
        protected TrainRail.Position extract(final TrainRail rail) {
            return rail.railPosition();
        }

        @Override
        protected @Nullable Pair<TrainRail.Position, RailProvider<TrainRail>> next(final TrainRail rail, final boolean forwards, final World world) {
            final TrainRail.Position pos = forwards ? rail.exitPosition() : rail.entrancePosition();
            final RailProvider<TrainRail> provider = TrainCartImpl.tryGetProvider(pos, world);
            if (provider == null) {
                return null;
            }
            return Pair.of(pos, provider);
        }
    };

    public TrainCartImpl(final World world, final Tracker tracker, final OffRailHandler offRailHandler, final Entity holder) {
        super(world, tracker, offRailHandler, holder, PATHFINDER);
    }

    @Override
    protected TrainRail.Position positionFromRail(final TrainRail rail) {
        return rail.railPosition();
    }

    @Override
    protected Either<MoveInfo<TrainRail.Position>, Pair<TrainRail.Position, RailProvider.NextRailInfo<TrainRail>>> next(final TrainRail.Position pos, final Direction exitDirection, final double time) {
        final TrainRail.Position exitPos;
        if (forwards()) {
            exitPos = currentRail().exitPosition();
        } else {
            exitPos = currentRail().entrancePosition();
        }
        final RailProvider<TrainRail> provider = tryGetProvider(exitPos);
        if (provider == null) {
            return Either.left(new MoveInfo<>(time, null));
        }
        final RailProvider.NextRailInfo<TrainRail> next = provider.next(this, currentRail(), exitDirection);
        if (next == null) {
            return Either.left(new MoveInfo<>(time, null));
        }
        return Either.right(Pair.of(exitPos, next));
    }

    @Override
    protected TrainRail.Position findOrDefault(final Vec3d position, final World world) {
        final BlockPos pos = BlockPos.ofFloored(position);
        final TrainRailProvider provider = TrainApi.TRAIN_RAIL_PROVIDER_API.find(world, pos, null);
        if (provider != null) {
            return new TrainRail.Position(pos, provider.otherPart());
        } else {
            return new TrainRail.Position(pos, pos.north());
        }
    }

    @Override
    protected @Nullable RailProvider<TrainRail> tryGetProvider(final TrainRail.Position pos) {
        return tryGetProvider(pos, world);
    }

    public static RailProvider<TrainRail> tryGetProvider(final TrainRail.Position pos, final World world) {
        boolean first = true;
        boolean down = false;
        TrainRailProvider provider = TrainApi.TRAIN_RAIL_PROVIDER_API.find(world, pos.first(), null);
        if (provider == null) {
            first = false;
            provider = TrainApi.TRAIN_RAIL_PROVIDER_API.find(world, pos.second(), null);
            if (provider == null) {
                first = true;
                down = true;
                provider = TrainApi.TRAIN_RAIL_PROVIDER_API.find(world, pos.first().down(), null);
                if (provider == null) {
                    first = false;
                    provider = TrainApi.TRAIN_RAIL_PROVIDER_API.find(world, pos.second().down(), null);
                    if (provider == null) {
                        return null;
                    }
                }
            }
        }
        if (first) {
            if (down) {
                if (provider.otherPart().equals(pos.second().down())) {
                    return provider;
                }
            } else {
                if (provider.otherPart().equals(pos.second())) {
                    return provider;
                }
            }
        } else {
            if (down) {
                if (provider.otherPart().equals(pos.first().down())) {
                    return provider;
                }
            } else {
                if (provider.otherPart().equals(pos.first())) {
                    return provider;
                }
            }
        }
        return null;
    }
}
