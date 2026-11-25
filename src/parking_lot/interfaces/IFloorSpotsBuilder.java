package parking_lot.interfaces;

import parking_lot.Floor;
import parking_lot.enums.VehicleType;

public interface IFloorSpotsBuilder {
    IFloorSpotsBuilder addVehicle(VehicleType type, int count);

    Floor build();
}
