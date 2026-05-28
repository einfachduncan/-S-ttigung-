package de.einfachduncan.saturationplus.effect;

public final class SaturationShader {
    private SaturationShader() {
    }

    public static float[] processColor(
            float red,
            float green,
            float blue,
            float saturation,
            float contrast,
            boolean bloomEnabled,
            float bloomIntensity
    ) {
        return ColorProcessor.process(red, green, blue, saturation, contrast, bloomEnabled, bloomIntensity);
    }

    public static String fragmentSource() {
        return """
                #version 150
                uniform sampler2D DiffuseSampler;
                uniform float Saturation;
                uniform float Contrast;
                uniform float BloomEnabled;
                uniform float BloomIntensity;
                in vec2 texCoord;
                out vec4 fragColor;

                vec3 applySaturation(vec3 color, float saturation) {
                    float luma = dot(color, vec3(0.2126, 0.7152, 0.0722));
                    return mix(vec3(luma), color, saturation);
                }

                vec3 applyContrast(vec3 color, float contrast) {
                    return clamp((color - 0.5) * contrast + 0.5, 0.0, 1.0);
                }

                void main() {
                    vec4 base = texture(DiffuseSampler, texCoord);
                    vec3 color = applySaturation(base.rgb, Saturation);
                    color = applyContrast(color, Contrast);

                    if (BloomEnabled > 0.5) {
                        vec3 highlight = max(color - vec3(0.75), vec3(0.0));
                        color += highlight * BloomIntensity;
                    }

                    fragColor = vec4(clamp(color, 0.0, 1.0), base.a);
                }
                """;
    }
}
