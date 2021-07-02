package com.MrEngMan.SaveBlockStats;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;

public class Main extends JavaPlugin implements Listener {

    public static Main plugin;

    NamespacedKey blocksPlacedKey;
    NamespacedKey blocksBrokenKey;

    ArrayList<String> disabledWorldsList;

    // When plugin is first enabled
    @SuppressWarnings("static-access")
    @Override
    public void onEnable() {
        this.plugin = this;

        // Generate the config if need be
        reloadTheConfig();

        // Register stuff
        new Listeners(this);
        ReloadCommandHandler reloadCommandHandler = new ReloadCommandHandler();
        this.getCommand("sbsreload").setExecutor(reloadCommandHandler);
        Bukkit.getPluginManager().registerEvents(this, this);

        blocksPlacedKey = new NamespacedKey(plugin, "blocksPlaced");
        blocksBrokenKey = new NamespacedKey(plugin, "blocksBroken");

    }

    public void reloadTheConfig() {

        // Generate the config file if it was deleted
        if (!(new File(this.getDataFolder(), "config.yml").exists())) {
            this.saveDefaultConfig();
        }

        // Load new config values
        reloadConfig();

        disabledWorldsList = (ArrayList<String>) getConfig().getStringList("DisabledWorlds");

    }

    public boolean isWorldDisabled(World world) {
        return disabledWorldsList.contains(world.getName());
    }

    public class ReloadCommandHandler implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

            // Player issued command
            if (sender instanceof Player) {
                Player player = (Player) sender;

                // Make sure they have permission
                if (player.hasPermission("sbs.reload")) {
                    plugin.reloadTheConfig();
                    player.sendMessage(Utils.SendChatMessage(plugin.getConfig().getString("ReloadedMessage")));
                } else {
                    player.sendMessage(Utils.SendChatMessage(plugin.getConfig().getString("NoPermissionMessage")));
                }

            }

            // Console issued command
            else if (sender instanceof ConsoleCommandSender) {
                plugin.reloadTheConfig();
                ConsoleCommandSender console = getServer().getConsoleSender();
                console.sendMessage(Utils.SendChatMessage(plugin.getConfig().getString("ReloadedMessage")));
            }

            return true;
        }

    }

}