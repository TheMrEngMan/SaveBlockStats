package com.MrEngMan.SaveBlockStats;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class Listeners implements Listener {

    public Listeners(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        // Ignore if block was not actually placed
        if(event.isCancelled()) return;

        // Ignore if block was placed in a world that is disabled within the config
        Chunk chunk = event.getBlock().getChunk();
        World world = chunk.getWorld();
        if(Main.plugin.isWorldDisabled(world)) return;

        // Get number of blocks placed in this chunk so far
        long blocksPlacedCount = 0;
        PersistentDataContainer dataContainer = chunk.getPersistentDataContainer();
        if(dataContainer.has(Main.plugin.blocksPlacedKey, PersistentDataType.LONG)) {
            blocksPlacedCount = dataContainer.get(Main.plugin.blocksPlacedKey, PersistentDataType.LONG);
        }

        // Increment and save value back to chunk
        blocksPlacedCount++;
        dataContainer.set(Main.plugin.blocksPlacedKey, PersistentDataType.LONG, blocksPlacedCount);

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        // Ignore if block was not actually broken
        if(event.isCancelled()) return;

        // Ignore if block was broken in a world that is disabled within the config
        Chunk chunk = event.getBlock().getChunk();
        World world = chunk.getWorld();
        if(Main.plugin.isWorldDisabled(world)) return;

        // Get number of blocks broken in this chunk so far
        long blocksBrokenCount = 0;
        PersistentDataContainer dataContainer = chunk.getPersistentDataContainer();
        if(dataContainer.has(Main.plugin.blocksBrokenKey, PersistentDataType.LONG)) {
            blocksBrokenCount = dataContainer.get(Main.plugin.blocksBrokenKey, PersistentDataType.LONG);
        }

        // Increment and save value back to chunk
        blocksBrokenCount++;
        dataContainer.set(Main.plugin.blocksBrokenKey, PersistentDataType.LONG, blocksBrokenCount);

    }

}
