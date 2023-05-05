package io.github.stuff_stuffs.unnamed_cart_expansion.api.train.basic;

import io.github.stuff_stuffs.train_lib.api.common.cart.train.TrainRail;
import io.github.stuff_stuffs.train_lib.api.common.util.CubicCurve;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class BezierTrainCartRail implements TrainRail {
    private final int id;
    private final CubicCurve.Cached cached;
    private final Position position;
    private final Position entrancePosition;
    private final Position exitPosition;
    private final Direction entranceDirection;
    private final Direction exitDirection;

    public BezierTrainCartRail(final int id, final Position position, final Position entrancePosition, final Position exitPosition, final Direction direction, final Direction exitDirection) {
        this.id = id;
        this.position = position;
        this.entrancePosition = entrancePosition;
        this.exitPosition = exitPosition;
        entranceDirection = direction;
        this.exitDirection = exitDirection;
        final Direction entranceOpposite = entranceDirection.getOpposite();
        final Direction exitOpposite = exitDirection.getOpposite();
        final Vec3d start = entrancePosition.first().offset(entranceOpposite).toCenterPos().add(entrancePosition.second().offset(entranceOpposite).toCenterPos()).multiply(0.5);
        final Vec3d end = exitPosition.first().offset(exitOpposite).toCenterPos().add(exitPosition.second().offset(exitOpposite).toCenterPos()).multiply(0.5);
        final double distance = start.distanceTo(end);
        final CubicCurve curve = new CubicCurve(start, start.multiply(0.8).add(end.multiply(0.2)), start.multiply(0.2).add(end.multiply(0.8)), end);
        cached = new CubicCurve.Cached(curve, MathHelper.floor(distance * 3));
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public Vec3d position(final double progress) {
        return cached.eval(progress);
    }

    @Override
    public Vec3d tangent(final double progress) {
        return cached.tangent(progress);
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
        return cached.averageSlope();
    }

    @Override
    public double length() {
        return cached.length();
    }

    @Override
    public Position entrancePosition() {
        return entrancePosition;
    }

    @Override
    public Position exitPosition() {
        return exitPosition;
    }

    @Override
    public Position railPosition() {
        return position;
    }
}
