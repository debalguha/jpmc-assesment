package com.jpmc.assessment.parser;

public interface ParseError {
    enum Reason {
        INVALID_OVERALL_FORMAT, INVALID_PREFIX, INVALID_PORTFOLIO_CODE, INVALID_DATE_FORMAT, INVALID_SEQUENCE_DIGIT, INVALID_FILE_EXTENSION

    }
    String getErrorMessage(String fileName);
    Reason getReason();
}
