package com.maklpvp5.autoshader;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

public class SildursLookClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.world == null || client.options.hudHidden) return;

            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            long timeOfDay = client.world.getTimeOfDay() % 24000;
            boolean isSunsetOrSunrise = (timeOfDay >= 12000 && timeOfDay <= 13800) || (timeOfDay >= 22200 || timeOfDay <= 1000);
            boolean isNight = timeOfDay > 13800 && timeOfDay < 22200;

            int tintColor;
            if (isSunsetOrSunrise) {
                // Теплый золотисто-оранжевый закат/рассвет как в Sildur's Basic
                tintColor = 0x1CFF9E3D;
            } else if (isNight) {
                // Глубокий сине-фиолетовый оттенок ночи
                tintColor = 0x1A182B5C;
            } else {
                // Яркий солнечный теплый фильтр днем
                tintColor = 0x12FFE49E;
            }

            drawContext.fill(0, 0, width, height, tintColor);
        });
    }
}
