package parking_lot;

import parking_lot.enums.VehicleType;

public class Vehicle {
    String id;
    VehicleType type;

    public Vehicle(VehicleType type, String id) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public VehicleType getType() {
        return type;
    }
}
