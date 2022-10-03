package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;

/**
 * Herb of the basil.
 */
public class BasilHerbBlock extends HerbBlock {
	/**
	 * Settings of the block.
	 */
	public static final Settings SETTINGS;

	/**
	 * Initializes a new {@link BasilHerbBlock}.
	 */
	public BasilHerbBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Initializes a new {@link BasilHerbBlock} with default settings.
	 */
	public static BasilHerbBlock of() {
		return new BasilHerbBlock(SETTINGS);
	}

	/**
	 * Gets the time to grow.
	 */
	@Override
	public int getGrowingTime() {
		return 20;
	}

	/**
	 * Gets the required light to grow.
	 */
	@Override
	public int getMinLightToGrow() {
		return 9;
	}

	static {
		SETTINGS = FabricBlockSettings.copyOf(Blocks.GRASS);
	}
}
