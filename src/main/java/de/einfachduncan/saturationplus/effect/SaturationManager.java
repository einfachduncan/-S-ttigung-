package de.einfachduncan.saturationplus.effect;

import de.einfachduncan.saturationplus.SaturationPlusMod;
import de.einfachduncan.saturationplus.config.ConfigManager;
import de.einfachduncan.saturationplus.mixin.GameRendererAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public final class SaturationManager {
    private static final Identifier POST_SHADER = Identifier.of(SaturationPlusMod.MOD_ID, "shaders/post/saturationplus.json");

    private static boolean enabled;
    private static boolean shaderLoaded;
    private static boolean dirty = true;

    private SaturationManager() {
    }

    public static void applyConfig() {
        enabled = ConfigManager.isEffectEnabled();
        dirty = true;
    }

    public static void toggle(MinecraftClient client) {
        enabled = !enabled;
        dirty = true;
        syncShader(client);
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void syncShader(MinecraftClient client) {
        if (!dirty || client == null || client.gameRenderer == null) {
            return;
        }
        dirty = false;

        if (shaderLoaded) {
            client.gameRenderer.disablePostProcessor();
            shaderLoaded = false;
        }

        if (!enabled) {
            return;
        }

        try {
            ((GameRendererAccessor) client.gameRenderer).invokeLoadPostProcessor(POST_SHADER);
            shaderLoaded = true;
        } catch (Exception ex) {
            SaturationPlusMod.LOGGER.warn("Could not load saturation post shader", ex);
            enabled = false;
            ConfigManager.setEffectEnabled(false);
            ConfigManager.save();
        }
    }

    public static float[] processPreviewColor(float red, float green, float blue) {
        return SaturationShader.processColor(
                red,
                green,
                blue,
                ConfigManager.getSaturationIntensity(),
                ConfigManager.getContrastBoost(),
                ConfigManager.isBloomEnabled(),
                ConfigManager.getBloomIntensity()
        );
    }
}
