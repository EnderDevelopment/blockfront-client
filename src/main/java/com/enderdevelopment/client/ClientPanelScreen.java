package com.enderdevelopment.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class ClientPanelScreen extends Screen {
    private final Screen parent;

    public ClientPanelScreen(Screen parent) {
        super(Text.literal("Ender Client"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int top = this.height / 2 - 70;
        this.addDrawableChild(ButtonWidget.builder(connectionLabel(), button -> {
            ClientSettings.get().toggleConnection(); button.setMessage(connectionLabel());
        }).dimensions(centerX - 100, top, 200, 20).build());
        this.addDrawableChild(ButtonWidget.builder(coordinatesLabel(), button -> {
            ClientSettings.get().toggleCoordinates(); button.setMessage(coordinatesLabel());
        }).dimensions(centerX - 100, top + 23, 200, 20).build());
        this.addDrawableChild(ButtonWidget.builder(layoutLabel(), button -> {
            ClientSettings.get().cycleLayout(); button.setMessage(layoutLabel());
        }).dimensions(centerX - 100, top + 46, 200, 20).build());
        addChannelButton(centerX - 100, top + 72, "R-", 0, -16);
        addChannelButton(centerX - 50, top + 72, "R+", 0, 16);
        addChannelButton(centerX, top + 72, "G-", 1, -16);
        addChannelButton(centerX + 50, top + 72, "G+", 1, 16);
        addChannelButton(centerX - 100, top + 96, "B-", 2, -16);
        addChannelButton(centerX - 50, top + 96, "B+", 2, 16);
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Opacity -"), button -> {
            ClientSettings.get().adjustBackgroundOpacity(-16);
        }).dimensions(centerX, top + 96, 98, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Opacity +"), button -> {
            ClientSettings.get().adjustBackgroundOpacity(16);
        }).dimensions(centerX + 102, top + 96, 98, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), button -> this.close())
                .dimensions(centerX - 50, top + 132, 100, 20).build());
    }

    private void addChannelButton(int x, int y, String label, int channel, int change) {
        this.addDrawableChild(ButtonWidget.builder(Text.literal(label), button -> {
            ClientSettings settings = ClientSettings.get();
            if (channel == 0) settings.adjustRed(change);
            else if (channel == 1) settings.adjustGreen(change);
            else settings.adjustBlue(change);
        }).dimensions(x, y, 48, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        int centerX = this.width / 2;
        int top = this.height / 2 - 70;
        ClientSettings settings = ClientSettings.get();
        context.fill(centerX - 104, top - 25, centerX + 204, top + 158, settings.backgroundColor());
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, centerX, top - 20, settings.accentColor());
        context.drawTextWithShadow(this.textRenderer, Text.literal(settings.rgbLabel()), centerX + 3, top + 75, settings.accentColor());
        context.drawTextWithShadow(this.textRenderer, Text.literal(settings.opacityLabel()), centerX - 96, top + 120, settings.accentColor());
        super.render(context, mouseX, mouseY, delta);
    }

    private static Text connectionLabel() {
        return Text.literal("Show connection: " + (ClientSettings.get().showConnection() ? "On" : "Off"));
    }
    private static Text coordinatesLabel() {
        return Text.literal("Show coordinates: " + (ClientSettings.get().showCoordinates() ? "On" : "Off"));
    }
    private static Text layoutLabel() { return Text.literal("HUD layout: " + ClientSettings.get().layoutName()); }

    @Override
    public void close() {
        if (this.client != null) this.client.setScreen(this.parent);
    }
}
