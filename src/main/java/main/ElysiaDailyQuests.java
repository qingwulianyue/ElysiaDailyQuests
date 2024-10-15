package main;

import command.CommandManager;
import command.CommandTabComplete;
import file.ConfigManager;
import file.PlayerDataManager;
import file.QuestsManager;
import listener.ElysiaDailyQuestsListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class ElysiaDailyQuests extends JavaPlugin {
    private static ConfigManager configManager;
    private static QuestsManager questsManager;
    private static PlayerDataManager playerDataManager;
    private static ElysiaDailyQuests instance;
    public static ConfigManager getConfigManager() {
        return configManager;
    }
    public static QuestsManager getQuestsManager() {
        return questsManager;
    }
    public static PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
    public static ElysiaDailyQuests getInstance() {
        return instance;
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();
        instance = this;
        createFile();
        configManager = new ConfigManager(this);
        questsManager = new QuestsManager(this);
        playerDataManager = new PlayerDataManager(this);
        configManager.loadConfig();
        questsManager.loadQuestsData();
        playerDataManager.loadPlayerData();
        Bukkit.getPluginCommand("ElysiaDailyQuests").setExecutor(new CommandManager());
        Bukkit.getPluginCommand("ElysiaDailyQuests").setTabCompleter(new CommandTabComplete());
        Bukkit.getPluginManager().registerEvents(new ElysiaDailyQuestsListener(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        super.onDisable();
        try {
            playerDataManager.savePlayerData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void createFile() {
        saveDefaultConfig();
        createFormulaFile();
    }
    private void createFormulaFile() {
        Path questsFolderPath = getDataFolder().toPath().resolve("Quests");
        Path playerDataFolderPath = getDataFolder().toPath().resolve("PlayerData");
        createDirectoryIfNotExists(questsFolderPath);
        createDirectoryIfNotExists(playerDataFolderPath);
        Path questsFilePath = questsFolderPath.resolve("default.yml");
        if (!Files.exists(questsFilePath)) {
            try (InputStream resourceStream = getResourceAsStream()) {
                Files.copy(resourceStream, questsFilePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to create sample file.", e);
            }
        }
    }
    private void createDirectoryIfNotExists(Path directoryPath) {
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to create directory.", e);
            }
        }
    }
    private InputStream getResourceAsStream() {
        InputStream resourceStream = getResource("Quests/default.yml");
        if (resourceStream == null) {
            throw new RuntimeException("Resource 'Quests/default.yml' not found in classpath.");
        }
        return resourceStream;
    }

}

