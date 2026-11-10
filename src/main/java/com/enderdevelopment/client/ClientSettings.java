package com.enderdevelopment.client;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class ClientSettings {
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("ender-client.properties");
    private static final String[] LAYOUTS = {"Compact", "Stacked"};
    private static final ClientSettings INSTANCE = load();

    private boolean showConnection = true;
    private boolean showCoordinates = true;
    private int red = 170;
    private int green = 85;
    private int blue = 255;
    private int backgroundOpacity = 96;
    private int layoutIndex;

    private ClientSettings() { }
    public static ClientSettings get() { return INSTANCE; }
    public boolean showConnection() { return showConnection; }
    public boolean showCoordinates() { return showCoordinates; }
    public String layoutName() { return LAYOUTS[layoutIndex]; }
    public int red() { return red; }
    public int green() { return green; }
    public int blue() { return blue; }
    public int backgroundOpacity() { return backgroundOpacity; }
    public int accentColor() { return 0xFF000000 | red << 16 | green << 8 | blue; }
    public int backgroundColor() { return backgroundOpacity << 24; }
    public String rgbLabel() { return String.format("RGB: %d, %d, %d", red, green, blue); }
    public String opacityLabel() { return "Background opacity: " + backgroundOpacity + "/255"; }

    public void toggleConnection() { showConnection = !showConnection; save(); }
    public void toggleCoordinates() { showCoordinates = !showCoordinates; save(); }
    public void cycleLayout() { layoutIndex = (layoutIndex + 1) % LAYOUTS.length; save(); }
    public void adjustRed(int amount) { red = clamp(red + amount); save(); }
    public void adjustGreen(int amount) { green = clamp(green + amount); save(); }
    public void adjustBlue(int amount) { blue = clamp(blue + amount); save(); }
    public void adjustBackgroundOpacity(int amount) { backgroundOpacity = clamp(backgroundOpacity + amount); save(); }

    private static int clamp(int value) { return Math.max(0, Math.min(255, value)); }

    private static ClientSettings load() {
        ClientSettings settings = new ClientSettings();
        if (!Files.isRegularFile(FILE)) return settings;
        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(FILE)) {
            properties.load(reader);
            settings.showConnection = Boolean.parseBoolean(properties.getProperty("showConnection", "true"));
            settings.showCoordinates = Boolean.parseBoolean(properties.getProperty("showCoordinates", "true"));
            settings.red = parseValue(properties.getProperty("red"), 170);
            settings.green = parseValue(properties.getProperty("green"), 85);
            settings.blue = parseValue(properties.getProperty("blue"), 255);
            settings.backgroundOpacity = parseValue(properties.getProperty("backgroundOpacity"), 96);
            settings.layoutIndex = parseIndex(properties.getProperty("layout"), LAYOUTS.length);
        } catch (IOException exception) {
            System.err.println("Unable to read Ender Client settings: " + exception.getMessage());
        }
        return settings;
    }

    private static int parseValue(String value, int fallback) {
        try { return clamp(Integer.parseInt(value)); }
        catch (NumberFormatException exception) { return fallback; }
    }

    private static int parseIndex(String value, int count) {
        try { return Math.floorMod(Integer.parseInt(value), count); }
        catch (NumberFormatException exception) { return 0; }
    }

    private void save() {
        Properties properties = new Properties();
        properties.setProperty("showConnection", Boolean.toString(showConnection));
        properties.setProperty("showCoordinates", Boolean.toString(showCoordinates));
        properties.setProperty("red", Integer.toString(red));
        properties.setProperty("green", Integer.toString(green));
        properties.setProperty("blue", Integer.toString(blue));
        properties.setProperty("backgroundOpacity", Integer.toString(backgroundOpacity));
        properties.setProperty("layout", Integer.toString(layoutIndex));
        try {
            Files.createDirectories(FILE.getParent());
            try (Writer writer = Files.newBufferedWriter(FILE)) { properties.store(writer, "Ender Client settings"); }
        } catch (IOException exception) {
            System.err.println("Unable to save Ender Client settings: " + exception.getMessage());
        }
    }
}
