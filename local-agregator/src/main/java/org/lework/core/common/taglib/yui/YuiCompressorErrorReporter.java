package org.lework.core.common.taglib.yui;


import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YuiCompressorErrorReporter implements ErrorReporter {
    private static Logger logger = LoggerFactory.getLogger(YuiCompressorErrorReporter.class);

    public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
        if (line < 0) {
            logger.warn(message);
        } else {
            logger.warn(line + ':' + lineOffset + ':' + message);
        }
    }

    public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
        if (line < 0) {
            logger.error(message);
        } else {
            logger.error(line + ':' + lineOffset + ':' + message);
        }

    }

    public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset) {
        error(message, sourceName, line, lineSource, lineOffset);
        return new EvaluatorException(message);
    }
}
