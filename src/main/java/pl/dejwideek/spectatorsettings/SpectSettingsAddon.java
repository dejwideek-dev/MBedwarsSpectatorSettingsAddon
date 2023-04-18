package pl.dejwideek.spectatorsettings;

import co.aikar.commands.PaperCommandManager;
import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.BedwarsAddon;
import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.game.spectator.Spectator;
import de.marcely.bedwars.api.game.spectator.SpectatorItem;
import de.marcely.bedwars.api.game.spectator.SpectatorItemHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import pl.dejwideek.spectatorsettings.commands.ReloadCmd;
import pl.dejwideek.spectatorsettings.events.InvClickEvent;

@SuppressWarnings("ALL")
public class SpectSettingsAddon extends BedwarsAddon {

    private static SpectSettingsPlugin plugin;

    public SpectSettingsAddon(SpectSettingsPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    public void registerCommands() {
        PaperCommandManager manager =
                new PaperCommandManager(plugin);

        manager.registerCommand(new ReloadCmd(plugin));
    }

    public void registerEvents() {
        PluginManager manager = Bukkit.getPluginManager();

        manager.registerEvents(new InvClickEvent(plugin), plugin);
    }

    public void registerItem() {
        BedwarsAPI.onReady(() -> {
            BedwarsAPI.getGameAPI().registerSpectatorItemHandler(
                    new SpectatorItemHandler("spectator-settings", plugin) {
                        @Override
                        public void handleUse(Spectator spectator,
                                              SpectatorItem spectatorItem) {
                            new GUI(plugin).gui(spectator.getPlayer());
                        }

                        @Override
                        public boolean isVisible(Spectator spectator,
                                                 SpectatorItem spectatorItem) {
                            Player p = spectator.getPlayer();
                            Arena a = BedwarsAPI.getGameAPI()
                                    .getArenaBySpectator(p);
                            Arena ap = BedwarsAPI.getGameAPI()
                                    .getArenaByPlayer(p);

                            if(a != ap) return true;
                            else return false;
                        }
                    });
        });
    }
}
