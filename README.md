# Minecraft Client Mod: Ender Client

Ender Client is a lightweight client-side Minecraft mod for Fabric 1.21.1 that adds a configurable HUD, connection and coordinate displays, color and opacity controls, and an in-game settings panel accessible from the title screen or a configurable keyboard shortcut.

## Install

1. Install Minecraft Java Edition 1.21.1.
2. Install Fabric Loader 0.16.0 or newer.
3. Install Fabric API for Minecraft 1.21.1.
4. Install Java 21.
5. Build the project from its directory with:

   `./gradlew build`

6. Copy the generated `build/libs/ender-client-1.0.0.jar` file into the Minecraft `mods` folder.
7. Launch Minecraft using the Fabric installation.

## Use

1. Start Minecraft with Fabric and Ender Client installed.
2. Open the client settings from the Ender Client button on the title screen.
3. In a world, press Right Shift to open the settings panel.
4. Change the following settings:
   - Toggle the connection status HUD line.
   - Toggle the player coordinates HUD line.
   - Switch between Compact and Stacked HUD layouts.
   - Adjust the red, green, and blue accent channels.
   - Adjust the HUD and settings-panel background opacity.
5. Change the Right Shift keybind in Minecraft's Controls menu under the Ender Client category.
6. Settings are saved automatically to `config/ender-client.properties`.

---

More Minecraft mod projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/1a11663d-adee-4e5f-8ceb-850c629c12c4?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=1a11663d-adee-4e5f-8ceb-850c629c12c4
