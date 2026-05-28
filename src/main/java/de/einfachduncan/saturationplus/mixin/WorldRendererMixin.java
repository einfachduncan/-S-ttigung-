package de.einfachduncan.saturationplus.mixin;

import de.einfachduncan.saturationplus.effect.SaturationManager;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.render.WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void saturationplus$refreshWhenWorldRenders(CallbackInfo ci) {
        SaturationManager.syncShader(MinecraftClient.getInstance());
    }
}
