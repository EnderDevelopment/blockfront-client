package com.enderdevelopment.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class EnderClient implements ClientModInitializer {
    private static final Text BUTTON_LABEL = Text.literal("Ender Client");
    private static KeyBinding openPanelKey;

    @Override
    public void onInitializeClient() {
        openPanelKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ender_client.open_panel", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.ender_client"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openPanelKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new ClientPanelScreen(null));
                }
            }
        });

        HudRenderCallback.EVENT.register((context, tickCounter) -> ClientHud.render(context));

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof TitleScreen) {
                int x = scaledWidth / 2 + 104;
                int y = scaledHeight / 4 + 48;
                screen.addDrawableChild(ButtonWidget.builder(BUTTON_LABEL, button ->
                        client.setScreen(new ClientPanelScreen(screen)))
                        .dimensions(x, y, 100, 20)
                        .build());
            }
        });
    }
}
