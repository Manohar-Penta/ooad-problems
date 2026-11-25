package parking_lot;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class Ticket {
    private final String id;
    private final Vehicle vehicle;
    private final Spot assignedSpot;
    private final Instant entryTime;
    private Instant exitTime;

    public Ticket(Vehicle vehicle, Spot assignedSpot, Instant entryTime) {
        this.id = UUID.randomUUID().toString();
        this.vehicle = vehicle;
        this.assignedSpot = assignedSpot;
        this.entryTime = entryTime;
    }

    public String getTicketId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Spot getSpot() {
        return assignedSpot;
    }

    public Instant getEntryTime() {
        return entryTime;
    }

    public Instant getExitTime() {
        return exitTime;
    }

    public void setExitTime(Instant exitTime) {
        this.exitTime = exitTime;
    }

    public Duration getParkedDuration() {
        return Duration.between(entryTime, exitTime);
    }
}
