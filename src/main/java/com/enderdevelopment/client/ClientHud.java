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

        int x = 8;
        int y = 8;
        int lineHeight = 12;
        int width = 0;
        if (settings.layoutName().equals("Compact")) {
            width = client.textRenderer.getWidth(String.join("  |  ", lines));
        } else {
            for (String line : lines) width = Math.max(width, client.textRenderer.getWidth(line));
        }
        int height = settings.layoutName().equals("Compact") ? lineHeight : lines.size() * lineHeight;
        context.fill(x - 4, y - 3, x + width + 4, y + height + 2, settings.backgroundColor());
        if (settings.layoutName().equals("Compact")) {
            context.drawTextWithShadow(client.textRenderer, Text.literal(String.join("  |  ", lines)), x, y, settings.accentColor());
        } else {
            for (int i = 0; i < lines.size(); i++) {
                context.drawTextWithShadow(client.textRenderer, Text.literal(lines.get(i)), x, y + i * lineHeight, settings.accentColor());
            }
        }
    }
}
