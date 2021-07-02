## SaveBlockStats
Spigot plugin to count how many times blocks were placed and broken by players in each chunk.<br>
This data is saved in the actual chunk data within the .mca region files

Meant to be viewed in [MCASelector](https://github.com/Querz/mcaselector) using custom overlays.<br>
Custom overlay paths:<br>
Blocks placed: `region/Level/ChunkBukkitValues/saveblockstats:blocksplaced`<br>
Blocks broken: `region/Level/ChunkBukkitValues/saveblockstats:blocksbroken`

Use the `/sbsreload` command to reload config file (containing worlds where the plugin should be disabled)