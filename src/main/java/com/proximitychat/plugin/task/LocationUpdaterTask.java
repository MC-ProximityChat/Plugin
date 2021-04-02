package com.proximitychat.plugin.task;

import com.proximitychat.plugin.ProximityChat;
import com.proximitychat.plugin.adapters.LocationModelAdapter;
import com.proximitychat.plugin.location.SimpleLocation;
import com.proximitychat.plugin.location.PreviousCurrentLocation;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicReference;

public class LocationUpdaterTask implements Runnable {

    private final AtomicReference<Player> playerRef;

    private final PreviousCurrentLocation location;

    public LocationUpdaterTask(Player playerRef) {
        this.playerRef = new AtomicReference<>(playerRef);
        this.location = new PreviousCurrentLocation(SimpleLocation.from(playerRef.getLocation()), SimpleLocation.from(playerRef.getLocation()));
    }

    @Override
    public void run() {
        Player player = playerRef.get();
        location.update(player);

        if (location.doesDistanceExceedThreshold()) {
            ProximityChat.getInstance().getRequestDispatcher().sendRequest(LocationModelAdapter.adapt(player.getUniqueId(), location.getCurrent()));
        }
    }
}
