package io.github.stuff_stuffs.unnamed_cart_expansion.internal.block;

import io.github.stuff_stuffs.unnamed_cart_expansion.internal.block.entity.SignalBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class SignalBlock extends Block implements BlockEntityProvider {
    public static final Property<Direction> FACING = Properties.HORIZONTAL_FACING;

    public SignalBlock(final Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(final StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(final ItemPlacementContext ctx) {
        final Direction facing = ctx.getHorizontalPlayerFacing();
        return getDefaultState().with(FACING, facing.rotateYCounterclockwise());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(final BlockPos pos, final BlockState state) {
        return new SignalBlockEntity(pos, state);
    }
}
