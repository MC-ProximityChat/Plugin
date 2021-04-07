package com.proximitychat.plugin.adapters;

import com.proximitychat.plugin.requests.model.LocationModel;
import com.proximitychat.plugin.location.SimpleLocation;

import java.util.UUID;

public class LocationModelAdapter {

    public static LocationModel adapt(UUID uuid, SimpleLocation location) {
        return new LocationModel(uuid, location);

    }
}
