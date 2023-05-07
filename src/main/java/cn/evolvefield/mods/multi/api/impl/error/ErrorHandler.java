package cn.evolvefield.mods.multi.api.impl.error;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ErrorHandler {

	public static final String MOD_NAME = "FabricMultiVersionHelper";

	private static final String LOG_PREFIX = "[" + MOD_NAME + "] ";
	public static Logger LOGGER = LogManager.getLogger();

	public static boolean SHOW_REFLECTION_EXCEPTIONS = Boolean.parseBoolean(System.getProperty(MOD_NAME + ".showReflectionExceptions", "true"));

	public static void log(Level level, String message) {
		if (LOGGER.isEnabled(level)) {
			LOGGER.log(level, LOG_PREFIX + message);
		}
	}

	public static void logf(Level level, String messageFormat, Object... args) {
		if (LOGGER.isEnabled(level)) {
			LOGGER.log(level, LOG_PREFIX + String.format(messageFormat, args));
		}
	}

	public static void logException(Level level, Throwable e) {
		LOGGER.catching(level, e);
	}

	public static void handleReflectionException(@Nullable Exception e, String messageFormat, Object... args) {
		if (SHOW_REFLECTION_EXCEPTIONS) {
			logf(Level.ERROR, messageFormat, args);
			if (e != null) {
				logException(Level.ERROR, e);
			}
		}
	}

}
