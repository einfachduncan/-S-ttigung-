package de.einfachduncan.saturationplus;

import de.einfachduncan.saturationplus.client.SaturationPlusClient;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SaturationPlusMod implements ModInitializer {
    public static final String MOD_ID = "saturationplus";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        SaturationPlusClient.initialize();
        LOGGER.info("SaturationPlus initialized.");
    }
}
