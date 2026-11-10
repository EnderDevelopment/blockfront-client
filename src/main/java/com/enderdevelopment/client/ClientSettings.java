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
    private static final String[] COLORS = {"Purple", "Cyan", "Green", "Gold", "White"};
    private static final String[] LAYOUTS = {"Compact", "Stacked"};
    private static final ClientSettings INSTANCE = load();

    private boolean showConnection = true;
    private boolean showCoordinates = true;
    private int colorIndex;
    private int layoutIndex;

    private ClientSettings() { }
    public static ClientSettings get() { return INSTANCE; }
    public boolean showConnection() { return showConnection; }
    public boolean showCoordinates() { return showCoordinates; }
    public String colorName() { return COLORS[colorIndex]; }
    public String layoutName() { return LAYOUTS[layoutIndex]; }
    public int accentColor() {
        return switch (colorIndex) {
            case 1 -> 0xFF55FFFF;
            case 2 -> 0xFF55FF55;
            case 3 -> 0xFFFFAA00;
            case 4 -> 0xFFFFFFFF;
            default -> 0xFFAA55FF;
        };
    }
    public void toggleConnection() { showConnection = !showConnection; save(); }
    public void toggleCoordinates() { showCoordinates = !showCoordinates; save(); }
    public void cycleColor() { colorIndex = (colorIndex + 1) % COLORS.length; save(); }
    public void cycleLayout() { layoutIndex = (layoutIndex + 1) % LAYOUTS.length; save(); }

    private static ClientSettings load() {
        ClientSettings settings = new ClientSettings();
        if (!Files.isRegularFile(FILE)) return settings;
        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(FILE)) {
            properties.load(reader);
            settings.showConnection = Boolean.parseBoolean(properties.getProperty("showConnection", "true"));
            settings.showCoordinates = Boolean.parseBoolean(properties.getProperty("showCoordinates", "true"));
            settings.colorIndex = parseIndex(properties.getProperty("color"), COLORS.length);
            settings.layoutIndex = parseIndex(properties.getProperty("layout"), LAYOUTS.length);
        } catch (IOException exception) {
            System.err.println("Unable to read Ender Client settings: " + exception.getMessage());
        }
        return settings;
    }

    private static int parseIndex(String value, int count) {
        try { return Math.floorMod(Integer.parseInt(value), count); }
        catch (NumberFormatException exception) { return 0; }
    }

    private void save() {
        Properties properties = new Properties();
        properties.setProperty("showConnection", Boolean.toString(showConnection));
        properties.setProperty("showCoordinates", Boolean.toString(showCoordinates));
        properties.setProperty("color", Integer.toString(colorIndex));
        properties.setProperty("layout", Integer.toString(layoutIndex));
        try {
            Files.createDirectories(FILE.getParent());
            try (Writer writer = Files.newBufferedWriter(FILE)) { properties.store(writer, "Ender Client settings"); }
        } catch (IOException exception) {
            System.err.println("Unable to save Ender Client settings: " + exception.getMessage());
        }
    }
}
