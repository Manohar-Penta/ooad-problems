package parking_lot;

import java.util.concurrent.atomic.AtomicBoolean;

import parking_lot.enums.VehicleType;

public class Spot {
    private final AtomicBoolean occupied = new AtomicBoolean(false);
    private final VehicleType type;

    public Spot(VehicleType type) {
        this.type = type;
    }

    /** Atomically claims this spot. Returns false if it was already taken. */
    public boolean tryOccupy() {
        return occupied.compareAndSet(false, true);
    }

    public void release() {
        occupied.set(false);
    }

    public VehicleType getSpotType() {
        return type;
    }

    public boolean isOccupied() {
        return occupied.get();
    }

    public boolean canFitVehicle(VehicleType type) {
        return this.type.ordinal() >= type.ordinal();
    }
}
