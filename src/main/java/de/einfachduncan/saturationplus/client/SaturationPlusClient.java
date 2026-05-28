package de.einfachduncan.saturationplus.client;

import de.einfachduncan.saturationplus.config.ConfigManager;
import de.einfachduncan.saturationplus.effect.SaturationManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public final class SaturationPlusClient implements ClientModInitializer {
    private static final String CATEGORY = "key.category.saturationplus";

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

    @Override
    public void onInitializeClient() {
        ConfigManager.load();
        SaturationManager.applyConfig();

        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
    }

    private void onClientTick(MinecraftClient client) {
        while (TOGGLE_KEY.wasPressed()) {
            SaturationManager.toggle(client);
            ConfigManager.setEffectEnabled(SaturationManager.isEnabled());
            ConfigManager.save();
            sendActionBar(client, Text.translatable(
                    SaturationManager.isEnabled() ? "message.saturationplus.enabled" : "message.saturationplus.disabled"
            ));
        }

        while (DECREASE_KEY.wasPressed()) {
            adjustSaturation(client, -0.1f);
        }

        while (INCREASE_KEY.wasPressed()) {
            adjustSaturation(client, 0.1f);
        }
    }

    private void adjustSaturation(MinecraftClient client, float delta) {
        float newValue = MathHelper.clamp(ConfigManager.getSaturationIntensity() + delta, 0.0f, 2.0f);
        ConfigManager.setSaturationIntensity(newValue);
        ConfigManager.save();
        SaturationManager.applyConfig();
        sendActionBar(client, Text.translatable("message.saturationplus.intensity", Math.round(newValue * 100.0f)));
    }

    private void sendActionBar(MinecraftClient client, Text message) {
        if (client.player != null) {
            client.player.sendMessage(message, true);
        }
    }
}
