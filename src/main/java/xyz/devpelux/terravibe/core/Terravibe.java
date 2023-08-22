package xyz.devpelux.terravibe.core;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains some information about the mod.
 */
public final class Terravibe {
	/**
	 * This is the identifier of the mod.
	 */
	public static final String ID = "terravibe";

	/**
	 * This is a logger to write text to the console and the log file identified by the mod.
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	/**
	 * Creates an identifier with the mod namespace.
	 */
	public static Identifier identified(String path) {
		return new Identifier(ID, path);
	}
}
