package com.proximitychat.plugin.location;

import org.bukkit.Location;

public class SimpleLocation {

    private final double x;

    private final double z;

    public static SimpleLocation from(Location location) {
        return new SimpleLocation(location.getX(), location.getZ());
    }

    private SimpleLocation(double x, double z) {
        this.x = x;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "{" +
                "x: " + x +
                ", z: " + z +
                '}';
    }
}
