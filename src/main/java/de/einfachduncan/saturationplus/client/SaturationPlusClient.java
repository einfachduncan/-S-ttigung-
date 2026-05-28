package de.einfachduncan.saturationplus.client;

import de.einfachduncan.saturationplus.config.ConfigManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class SaturationPlusClient {
    private static final String CATEGORY = "key.category.saturationplus";
    private static boolean initialized;
    private static boolean enabled = true;

    private static final KeyBinding TOGGLE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.saturationplus.toggle",
            GLFW.GLFW_KEY_V,
            CATEGORY
    ));

    private static final KeyBinding DECREASE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.saturationplus.decrease",
            GLFW.GLFW_KEY_LEFT_BRACKET,
            CATEGORY
    ));

    private static final KeyBinding INCREASE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.saturationplus.increase",
            GLFW.GLFW_KEY_RIGHT_BRACKET,
            CATEGORY
    ));

    private SaturationPlusClient() {
    }

    public static void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;

        ConfigManager.load();
        enabled = ConfigManager.isEffectEnabled();

        ClientTickEvents.END_CLIENT_TICK.register(SaturationPlusClient::onClientTick);
    }

    private static void onClientTick(MinecraftClient client) {
        while (TOGGLE_KEY.wasPressed()) {
            enabled = !enabled;
            ConfigManager.setEffectEnabled(enabled);
            ConfigManager.save();
            sendActionBar(client, Text.translatable(
                    enabled ? "message.saturationplus.enabled" : "message.saturationplus.disabled"
            ));
        }

        while (DECREASE_KEY.wasPressed()) {
            adjustSaturation(client, -0.1f);
        }

        while (INCREASE_KEY.wasPressed()) {
            adjustSaturation(client, 0.1f);
        }
    }

    private static void adjustSaturation(MinecraftClient client, float delta) {
        float newValue = Math.clamp(ConfigManager.getSaturationIntensity() + delta, 0.0f, 2.0f);
        ConfigManager.setSaturationIntensity(newValue);
        ConfigManager.save();
        sendActionBar(client, Text.translatable("message.saturationplus.intensity", Math.round(newValue * 100.0f)));
    }

    private static void sendActionBar(MinecraftClient client, Text message) {
        if (client.player != null) {
            client.player.sendMessage(message, true);
        }
    }
}
