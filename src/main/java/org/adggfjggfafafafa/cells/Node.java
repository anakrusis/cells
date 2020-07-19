package org.adggfjggfafafafa.cells;

import java.util.ArrayList;
import java.util.UUID;

public class Node {
    double x; double y;
    UUID uuid;
    ArrayList<UUID> neighborUUIDs;

    public Node(double x, double y ){
        this.x = x; this.y = y;
        this.neighborUUIDs = new ArrayList<>();
    }

    public void update() {

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
