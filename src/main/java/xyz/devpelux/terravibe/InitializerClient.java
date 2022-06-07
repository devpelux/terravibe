package xyz.devpelux.terravibe;

import net.fabricmc.api.ClientModInitializer;
import xyz.devpelux.terravibe.block.BlockList;
import xyz.devpelux.terravibe.core.Util;
import xyz.devpelux.terravibe.item.ItemList;
import xyz.devpelux.terravibe.screenhandler.ScreenHandlerTypeList;

/** Client-side mod initializer. */
public class InitializerClient implements ClientModInitializer {
    /** {@inheritDoc} */
    @Override
    public void onInitializeClient() {
        ItemList.loadColorProviders();
        BlockList.loadColorProviders();
        BlockList.loadRenderLayerMaps();

        ScreenHandlerTypeList.loadScreens();

        Util.LOGGER.info("Loaded Terravibe client components.");
    }
}
