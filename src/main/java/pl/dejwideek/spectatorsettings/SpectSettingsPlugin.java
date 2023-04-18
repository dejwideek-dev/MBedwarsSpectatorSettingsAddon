package pl.dejwideek.spectatorsettings;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import dev.dejvokep.boostedyaml.spigot.SpigotSerializer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("ALL")
public class SpectSettingsPlugin extends JavaPlugin {

    public YamlDocument config;

    public void onEnable() {
        if(!mbwCheck()) return;
        if(!registerAddon()) return;

        loadConfig();
        new SpectSettingsAddon(this).registerCommands();
        new SpectSettingsAddon(this).registerEvents();
        new SpectSettingsAddon(this).registerItem();
    }

    private void loadConfig() {
        try {
            config = YamlDocument.create(
                    new File(new SpectSettingsAddon(this).getDataFolder(), "config.yml"),
                    getResource("config.yml"),
                    GeneralSettings.builder().setSerializer(
                            SpigotSerializer.getInstance()).build(),
                    LoaderSettings.DEFAULT,
                    DumperSettings.DEFAULT,
                    UpdaterSettings.DEFAULT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean mbwCheck() {
        if(Bukkit.getPluginManager().getPlugin("MBedwars") != null) {
            final int supportedAPIVersion = 16;

            try {
                Class apiClass = Class.forName("de.marcely.bedwars.api.BedwarsAPI");
                int apiVersion = (int) apiClass.getMethod("getAPIVersion").invoke(null);

                if (apiVersion < supportedAPIVersion)
                    throw new IllegalStateException();
            } catch (Exception e) {
                this.getLogger().warning("Your MBedwars version is not supported. Supported version: 5.1.1 or higher!");
                Bukkit.getPluginManager().disablePlugin(this);
                return false;
            }
        }
        else return false;
        return true;
    }

    private boolean registerAddon() {
        SpectSettingsAddon addon = new SpectSettingsAddon(this);

        if(!addon.register()) {
            this.getLogger().warning("It seems like this addon has already been loaded. Please delete duplicates and try again.");
            Bukkit.getPluginManager().disablePlugin(this);
            return false;
        }
        return true;
    }
}
