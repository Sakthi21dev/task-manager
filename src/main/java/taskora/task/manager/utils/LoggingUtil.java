package taskora.task.manager.utils;

import org.apache.logging.log4j.Logger;

import taskora.task.manager.constants.LogLevel;

public class LoggingUtil {

  public static void log(Logger logger, LogLevel level, String message, Object... params) {
    String logMessage = String.format(message, params);
    switch (level) {
    case INFO:
      logger.info(logMessage);
      break;
    case DEBUG:
      logger.debug(logMessage);
      break;
    case WARN:
      logger.warn(logMessage);
      break;
    case ERROR:
      logger.error(logMessage);
      break;
    }
  }

  public static void logException(Logger logger, Throwable throwable) {
    logger.error("An exception occurred: " + throwable.getMessage(), throwable);
  }

}
