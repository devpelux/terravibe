package xyz.devpelux.terravibe.screenhandler;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.block.ShredderBlock;

/** List of all the screen handler types. */
public class ScreenHandlerTypeList {
    /** Shredder screen handler type: Screen handler type for the shredder block entity UI. */
    public static final ScreenHandlerType<ShredderScreenHandler> SHREDDER;

    /** Loads all the screen handler types. */
    public static void load() {}

    /** Loads all the screens for the screen handler types. */
    @Environment(EnvType.CLIENT)
    public static void loadScreens() {
        HandledScreens.register(SHREDDER, ShredderScreen::new);
    }

    /** Registers the specified screen handler type with the specified id. */
    private static <T extends ScreenHandler> ScreenHandlerType<T> register(Identifier id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registry.SCREEN_HANDLER, id, new ScreenHandlerType<>(factory));
    }

    static {
        SHREDDER = register(ShredderBlock.ID, ShredderScreenHandler::new);
    }
}
