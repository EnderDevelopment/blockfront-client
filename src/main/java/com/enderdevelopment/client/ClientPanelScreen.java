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
        int top = this.height / 2 - 28;
        this.addDrawableChild(ButtonWidget.builder(connectionLabel(), button -> {
            ClientSettings.get().toggleConnection(); button.setMessage(connectionLabel());
        }).dimensions(centerX - 100, top, 200, 20).build());
        this.addDrawableChild(ButtonWidget.builder(coordinatesLabel(), button -> {
            ClientSettings.get().toggleCoordinates(); button.setMessage(coordinatesLabel());
        }).dimensions(centerX - 100, top + 24, 200, 20).build());
        this.addDrawableChild(ButtonWidget.builder(colorLabel(), button -> {
            ClientSettings.get().cycleColor(); button.setMessage(colorLabel());
        }).dimensions(centerX - 100, top + 48, 200, 20).build());
        this.addDrawableChild(ButtonWidget.builder(layoutLabel(), button -> {
            ClientSettings.get().cycleLayout(); button.setMessage(layoutLabel());
        }).dimensions(centerX - 100, top + 72, 200, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), button -> this.close())
                .dimensions(centerX - 50, top + 104, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2,
                this.height / 2 - 64, ClientSettings.get().accentColor());
        super.render(context, mouseX, mouseY, delta);
    }

    private static Text connectionLabel() {
        return Text.literal("Show connection: " + (ClientSettings.get().showConnection() ? "On" : "Off"));
    }
    private static Text coordinatesLabel() {
        return Text.literal("Show coordinates: " + (ClientSettings.get().showCoordinates() ? "On" : "Off"));
    }
    private static Text colorLabel() { return Text.literal("Accent color: " + ClientSettings.get().colorName()); }
    private static Text layoutLabel() { return Text.literal("HUD layout: " + ClientSettings.get().layoutName()); }

    @Override
    public void close() {
        if (this.client != null) this.client.setScreen(this.parent);
    }
}
