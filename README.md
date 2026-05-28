# SaturationPlus

SaturationPlus ist ein Minecraft-Fabric-Mod für **Minecraft 1.21.1**, der die Farbsättigung per Post-Processing anhebt und die Welt lebendiger wirken lässt.

## Features

- Sättigungs-Effekt über Post-Processing Shader
- Intensität von **0% bis 200%** (0.0 bis 2.0)
- Toggle mit **V**
- Intensität ändern mit **[** und **]**
- Optional vorbereitete Parameter für Bloom und Kontrast
- Konfiguration in `config/saturationplus.properties`

## Tasten

- `V` – Effekt an/aus
- `[` – Sättigung verringern
- `]` – Sättigung erhöhen

## Konfiguration

Datei: `config/saturationplus.properties`

- `effect_enabled=true|false`
- `saturation_intensity=0.0..2.0`
- `bloom_enabled=true|false`
- `bloom_intensity=0.0..1.0`
- `contrast_boost=0.5..2.0`

## Build

```bash
./gradlew build
```
