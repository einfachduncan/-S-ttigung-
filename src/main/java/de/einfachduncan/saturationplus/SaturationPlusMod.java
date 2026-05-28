package de.einfachduncan.saturationplus;

import de.einfachduncan.saturationplus.client.SaturationPlusClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SaturationPlusMod implements ModInitializer {
    public static final String MOD_ID = "saturationplus";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            SaturationPlusClient.initialize();
        }
        LOGGER.info("SaturationPlus initialized.");
    }
}
