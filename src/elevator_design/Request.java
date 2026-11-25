package elevator_design;

import elevator_design.utils.enums.Direction;
import elevator_design.utils.enums.RequestType;

public class Request {
    private int floor;
    private RequestType type;

    public Request(int floor, RequestType type) {
        this.floor = floor;
        this.type = type;
    }

    public int getFloor() {
        return floor;
    }

    public RequestType getType() {
        return type;
    }
}
