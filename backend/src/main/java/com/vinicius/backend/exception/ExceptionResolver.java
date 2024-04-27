package com.vinicius.backend.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ExceptionResolver {
    public static String getRootException(Throwable e) {
        return String.format("%s in class: %s Line: %s", ExceptionUtils.getRootCauseMessage(e), e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getLineNumber());
    }
}
