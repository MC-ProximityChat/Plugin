package com.proximitychat.plugin.http.model;

import com.proximitychat.plugin.location.SimpleLocation;

import java.util.UUID;

public class LocationModel {

    private final UUID uuid;

    private final SimpleLocation simpleLocation;

    public LocationModel(UUID uuid, SimpleLocation simpleLocation) {
        this.uuid = uuid;
        this.simpleLocation = simpleLocation;
    }

    public UUID getUuid() {
        return uuid;
    }

    public SimpleLocation getSimpleLocation() {
        return simpleLocation;
    }

    @Override
    public String toString() {
        return "{" +
                "uuid: " + uuid +
                ", x: " + simpleLocation.getX() +
                ", z: " + simpleLocation.getZ() +
                '}';
    }
}
