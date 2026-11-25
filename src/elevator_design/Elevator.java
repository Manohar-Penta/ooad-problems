package elevator_design;

import java.util.Set;
import java.util.TreeSet;

import elevator_design.utils.enums.Direction;
import elevator_design.utils.enums.RequestType;

public class Elevator {
    private int currFloor;
    private Direction direction;
    private Set<RequestType> requests;

    public Elevator() {
        currFloor = 0;
        direction = Direction.IDLE;
        requests = new TreeSet<>();
    }

    public void addRequest(Request request) {

    }

    public void step() {

    }

    public int getCurrentFloor() {
        return currFloor;
    }

    public Direction getDirection() {
        return direction;
    }
}
