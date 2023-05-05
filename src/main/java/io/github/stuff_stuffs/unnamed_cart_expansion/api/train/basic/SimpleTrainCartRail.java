package io.github.stuff_stuffs.unnamed_cart_expansion.api.train.basic;

import io.github.stuff_stuffs.unnamed_cart_expansion.api.train.TrainRail;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class SimpleTrainCartRail implements TrainRail {
    private final int id;
    private final Vec3d start;
    private final Vec3d deltaNorm;
    private final double slope;
    private final double length;
    private final Position railPosition;
    private final Position entrance;
    private final Position exit;
    private final @Nullable Direction entranceDirection;
    private final @Nullable Direction exitDirection;

    public SimpleTrainCartRail(final Vec3d start, final Vec3d end, final int id, final Position railPosition, final Position entrance, final Position exit, final @Nullable Direction entranceDirection, final @Nullable Direction exitDirection) {
        this.start = start;
        this.id = id;
        this.railPosition = railPosition;
        this.entrance = entrance;
        this.entranceDirection = entranceDirection;
        this.exitDirection = exitDirection;
        final Vec3d delta = end.subtract(start);
        deltaNorm = delta.normalize();
        slope = Math.asin(deltaNorm.y);
        length = delta.length();
        this.exit = exit;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public Vec3d position(final double progress) {
        return start.add(deltaNorm.multiply(progress));
    }

    @Override
    public Position entrancePosition() {
        return entrance;
    }

    @Override
    public Position exitPosition() {
        return exit;
    }

    @Override
    public Position railPosition() {
        return railPosition;
    }

    @Override
    public Vec3d tangent(final double progress) {
        return deltaNorm;
    }

    @Override
    public @Nullable Direction entranceDirection() {
        return entranceDirection;
    }

    @Override
    public @Nullable Direction exitDirection() {
        return exitDirection;
    }

    @Override
    public double slopeAngle() {
        return slope;
    }

    @Override
    public double length() {
        return length;
    }
}
