package stackover_flow.entities;

import java.util.Objects;
import java.util.UUID;

public class User {
    private String name;
    private final String id;
    private int reputation;

    public User(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.reputation = 0;
    }

    public int getReputation() {
        return this.reputation;
    }

    public int updateReputation(int value) {
        reputation += value;
        return reputation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        // 1. If they point to the same memory address, they are identical
        if (this == obj)
            return true;

        // 2. If the other object is null or a different class, they aren't equal
        if (obj == null || getClass() != obj.getClass())
            return false;

        // 3. Cast the other object so we can read its fields
        User other = (User) obj;

        // 4. Compare the actual values (id )
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        // Must use the exact same fields as equals()
        return Objects.hash(id);
    }
}
