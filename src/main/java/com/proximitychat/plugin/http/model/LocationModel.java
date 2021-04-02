package com.proximitychat.plugin.http.model;

import com.proximitychat.plugin.location.SimpleLocation;

import java.util.UUID;

public class LocationModel {

    private final UUID ID;

    private final double X;

    private final double Z;

    public LocationModel(UUID uuid, SimpleLocation simpleLocation) {
        this.ID = uuid;
        this.X = simpleLocation.getX();
        this.Z = simpleLocation.getZ();
    }

    public UUID getID() {
        return ID;
    }

    public double getX() {
        return X;
    }

    public double getZ() {
        return Z;
    }

    @Override
    public String toString() {
        return '{' +
                "\"uuid\": \"" + ID + "\"" +
                ", \"x\": " + X +
                ", \"z\": " + Z +
                '}';
    }
}
