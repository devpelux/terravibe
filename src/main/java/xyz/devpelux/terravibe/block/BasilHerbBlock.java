package xyz.devpelux.terravibe.block;

/**
 * Herb of the basil.
 */
public class BasilHerbBlock extends HerbBlock {
	/**
	 * Initializes a new instance.
	 */
	public BasilHerbBlock(Settings settings) {
		super(settings);
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
}
