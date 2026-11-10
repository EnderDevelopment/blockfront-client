package com.enderdevelopment.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public final class ClientHud {
    private ClientHud() { }

    public static void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientSettings settings = ClientSettings.get();
        if (client.player == null || client.options.hudHidden) return;

        List<String> lines = new ArrayList<>();
        if (settings.showConnection()) lines.add(client.getNetworkHandler() == null ? "Singleplayer" : "Connected");
        if (settings.showCoordinates()) {
            lines.add(String.format("XYZ %.1f / %.1f / %.1f", client.player.getX(), client.player.getY(), client.player.getZ()));
        }
        if (lines.isEmpty()) return;

        int color = settings.accentColor();
        int startX = 8;
        int startY = 8;
        if (settings.layoutName().equals("Compact")) {
            String line = String.join("  |  ", lines);
            context.drawTextWithShadow(client.textRenderer, Text.literal(line), startX, startY, color);
        } else {
            for (int i = 0; i < lines.size(); i++) {
                context.drawTextWithShadow(client.textRenderer, Text.literal(lines.get(i)), startX, startY + i * 12, color);
            }
        }
    }
}
