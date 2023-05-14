package io.github.stuff_stuffs.unnamed_cart_expansion.internal.block.entity;

import io.github.stuff_stuffs.train_lib.api.common.TrainLibApi;
import io.github.stuff_stuffs.train_lib.api.common.cart.RailProvider;
import io.github.stuff_stuffs.train_lib.api.common.cart.mine.MinecartRail;
import io.github.stuff_stuffs.train_lib.api.common.cart.mine.MinecartRailProvider;
import io.github.stuff_stuffs.train_lib.api.common.event.CartEvent;
import io.github.stuff_stuffs.train_lib.api.common.event.CartEventListener;
import io.github.stuff_stuffs.unnamed_cart_expansion.internal.block.SignalBlock;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

public class SignalBlockEntity extends BlockEntity implements CartEventListener {
    private final Set<RailEntry> positions;

    public SignalBlockEntity(final BlockEntityType<?> type, final BlockPos pos, final BlockState state) {
        super(type, pos, state);
        positions = new ObjectOpenHashSet<>();
    }

    public SignalBlockEntity(final BlockPos pos, final BlockState state) {
        this(CartExBlockEntities.SIGNAL_BLOCK_ENTITY_BLOCK_TYPE, pos, state);
    }

    private void update() {
        final Direction facing = getCachedState().get(SignalBlock.FACING);
        final BlockPos startPos = pos.offset(facing.rotateYCounterclockwise());
        final MinecartRailProvider provider = TrainLibApi.MINECART_RAIL_BLOCK_API.find(world, startPos, null);
        if (provider == null) {
            positions.clear();
            return;
        }
        final RailProvider.RailReflectionInfo<MinecartRail> info = provider.snapReflect(facing.getOpposite());
        if (info == null) {
            positions.clear();
            return;
        }
    }

    private void walk(final MinecartRail rail, final boolean forwards, final Consumer<RailEntry> consumer) {
        final Queue<DirectedRailEntry> entriesToScan = new ArrayDeque<>(32);
        final Set<RailEntry> visited = new ObjectOpenHashSet<>();
        entriesToScan.add(new DirectedRailEntry(rail, forwards, 0, 0));
        visited.add(new RailEntry(rail.railPosition(), rail.id()));
        while (!entriesToScan.isEmpty()) {
            final DirectedRailEntry entry = entriesToScan.remove();

        }
    }

    @Override
    public void listener(final CartEvent event) {
        if (world != null && !world.isClient) {
            System.out.println(event);
        }
    }

    private record DirectedRailEntry(MinecartRail rail, boolean forwards, double distance, int depth) {
    }

    private record RailEntry(BlockPos pos, int id) {
    }
}
