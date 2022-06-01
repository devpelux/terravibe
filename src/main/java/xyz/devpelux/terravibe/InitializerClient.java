package xyz.devpelux.terravibe;

import net.fabricmc.api.ClientModInitializer;
import xyz.devpelux.terravibe.block.BlockList;
import xyz.devpelux.terravibe.core.Util;

/** Client-side mod initializer. */
public class InitializerClient implements ClientModInitializer {
    /** {@inheritDoc} */
    @Override
    public void onInitializeClient() {
        BlockList.loadRenderLayerMaps();

        Util.LOGGER.info("Loaded Terravibe client components.");
    }
}
