package com.jpmc.assessment2.parser.error;

public interface ParseError {
    enum Reason {
        INVALID_OVERALL_FORMAT, INVALID_PREFIX, INVALID_PORTFOLIO_CODE, INVALID_DATE_FORMAT, INVALID_SEQUENCE_DIGIT, INVALID_FILE_EXTENSION, UNEXPECTED_ERROR

    }
    String getErrorMessage(String fileName);
    Reason getReason();
}
