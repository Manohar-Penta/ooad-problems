package elevator_design;

import java.util.ArrayList;
import java.util.List;

import elevator_design.utils.enums.Direction;
import elevator_design.utils.enums.RequestType;

/*
Floor(int)
Elevator(class)
Request(class)
User
ElevatorSystem(class)
Direction (enum: UP, DOWN)
*/

public class ElevatorSystem {
    private Elevator[] elevators;

    public ElevatorSystem() {
        elevators = new Elevator[3];
    }

    /**
     * Flow:
     * Validate the floor no.
     * Pick the elevator that should handle
     * 
     * @param floor
     * @param direction
     * @return
     */
    public boolean requestElevator(int floor, RequestType type) {

        // Pick the elevator

        // add the request to the elevator
        Request request = new Request(floor, type);
        Elevator best = selectBestElevator(request);
        best.addRequest(request);

        return false;
    }

    public void step() {

    }

    private Elevator selectBestElevator(Request request) {
        return null;
    }
}