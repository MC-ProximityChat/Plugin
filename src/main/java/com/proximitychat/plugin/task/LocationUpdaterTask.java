package com.proximitychat.plugin.task;

import com.proximitychat.plugin.location.SimpleLocation;
import com.proximitychat.plugin.location.PreviousCurrentLocation;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicReference;

public class LocationUpdaterTask implements Runnable {

    private final AtomicReference<Player> player;

    private final PreviousCurrentLocation location;

    public LocationUpdaterTask(Player player) {
        this.player = new AtomicReference<>(player);
        this.location = new PreviousCurrentLocation(SimpleLocation.from(player.getLocation()), SimpleLocation.from(player.getLocation()));
    }

    @Override
    public void run() {
        location.update(player.get());

        if (location.doesDistanceExceedThreshold()) {
            player.get().sendMessage(location.toString());
        }
    }
}
