package de.einfachduncan.saturationplus.effect;

import net.minecraft.util.math.MathHelper;

public final class ColorProcessor {
    private ColorProcessor() {
    }

    public static float[] process(
            float red,
            float green,
            float blue,
            float saturation,
            float contrast,
            boolean bloomEnabled,
            float bloomIntensity
    ) {
        float luma = red * 0.2126f + green * 0.7152f + blue * 0.0722f;

        float processedRed = applySaturation(red, luma, saturation);
        float processedGreen = applySaturation(green, luma, saturation);
        float processedBlue = applySaturation(blue, luma, saturation);

        processedRed = applyContrast(processedRed, contrast);
        processedGreen = applyContrast(processedGreen, contrast);
        processedBlue = applyContrast(processedBlue, contrast);

        if (bloomEnabled) {
            processedRed = applyBloom(processedRed, bloomIntensity);
            processedGreen = applyBloom(processedGreen, bloomIntensity);
            processedBlue = applyBloom(processedBlue, bloomIntensity);
        }

        return new float[]{
                MathHelper.clamp(processedRed, 0.0f, 1.0f),
                MathHelper.clamp(processedGreen, 0.0f, 1.0f),
                MathHelper.clamp(processedBlue, 0.0f, 1.0f)
        };
    }

    private static float applySaturation(float color, float luma, float saturation) {
        return luma + (color - luma) * saturation;
    }

    private static float applyContrast(float color, float contrast) {
        return (color - 0.5f) * contrast + 0.5f;
    }

    private static float applyBloom(float color, float bloomIntensity) {
        float highlight = Math.max(0.0f, color - 0.75f);
        return color + (highlight * bloomIntensity);
    }
}
