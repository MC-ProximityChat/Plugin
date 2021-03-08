package com.proximitychat.plugin.location;

import org.bukkit.entity.Player;

import static com.proximitychat.plugin.ProximityChat.DISTANCE_THRESHOLD;

public class PreviousCurrentLocation {

    private SimpleLocation previous;

    private SimpleLocation current;

    public PreviousCurrentLocation(SimpleLocation previous, SimpleLocation currentLocation) {
        this.previous = previous;
        this.current = currentLocation;
    }

    public void update(Player player) {
        this.previous = current;
        this.current = SimpleLocation.from(player.getLocation());
    }

    public boolean doesDistanceExceedThreshold() {
        return Math.abs(previous.getX() - current.getX()) > DISTANCE_THRESHOLD || Math.abs(previous.getZ() - current.getZ()) > DISTANCE_THRESHOLD;
    }

    public SimpleLocation getPrevious() {
        return previous;
    }

    public void setPrevious(SimpleLocation previous) {
        this.previous = previous;
    }

    public SimpleLocation getCurrent() {
        return current;
    }

    public void setCurrent(SimpleLocation current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "{" +
                "previous: " + previous +
                ", current: " + current +
                '}';
    }
}
