package de.einfachduncan.saturationplus.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class ConfigManager {
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("saturationplus.properties");

    private static boolean effectEnabled = true;
    private static float saturationIntensity = 1.3f;

    private ConfigManager() {
    }

    public static void load() {
        if (Files.notExists(CONFIG_FILE)) {
            save();
            return;
        }

        Properties properties = new Properties();
        try (InputStream in = Files.newInputStream(CONFIG_FILE)) {
            properties.load(in);
            effectEnabled = Boolean.parseBoolean(properties.getProperty("effect_enabled", Boolean.toString(effectEnabled)));
            saturationIntensity = clamp(parseFloat(properties.getProperty("saturation_intensity"), saturationIntensity), 0.0f, 2.0f);
        } catch (IOException ignored) {
            save();
        }
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_FILE.getParent());
            Properties properties = new Properties();
            properties.setProperty("effect_enabled", Boolean.toString(effectEnabled));
            properties.setProperty("saturation_intensity", Float.toString(saturationIntensity));
            try (OutputStream out = Files.newOutputStream(CONFIG_FILE)) {
                properties.store(out, "SaturationPlus configuration");
            }
        } catch (IOException ignored) {
        }
    }

    private static float parseFloat(String input, float fallback) {
        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }

    private static float clamp(float value, float min, float max) {
        return Math.clamp(value, min, max);
    }

    public static boolean isEffectEnabled() {
        return effectEnabled;
    }

    public static void setEffectEnabled(boolean effectEnabled) {
        ConfigManager.effectEnabled = effectEnabled;
    }

    public static float getSaturationIntensity() {
        return saturationIntensity;
    }

    public static void setSaturationIntensity(float saturationIntensity) {
        ConfigManager.saturationIntensity = clamp(saturationIntensity, 0.0f, 2.0f);
    }
}
