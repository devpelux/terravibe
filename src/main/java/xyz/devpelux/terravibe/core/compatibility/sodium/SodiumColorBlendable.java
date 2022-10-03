package xyz.devpelux.terravibe.core.compatibility.sodium;

/**
 * Defines an object that supports color blending in sodium.
 */
public interface SodiumColorBlendable {
	/**
	 * Returns a value indicating if to enable color blending for this block.
	 */
	default boolean enableSodiumColorBlending() {
		return true;
	}
}
