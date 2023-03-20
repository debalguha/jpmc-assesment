package com.jpmc.assessment2.parser.error.impl;


import com.jpmc.assessment2.parser.error.ParseError;

public class ParseErrorImpl implements ParseError {

    final String errorMessageTemplate;
    final Reason reason;
    final String invalidText;

    public ParseErrorImpl(String errorMessageTemplate, Reason reason, String invalidText) {
        this.errorMessageTemplate = errorMessageTemplate;
        this.reason = reason;
        this.invalidText = invalidText;
    }

    @Override
    public String getErrorMessage(String fileName) {
        return String.format(errorMessageTemplate, fileName, invalidText);
    }

    @Override
    public Reason getReason() {
        return reason;
    }
}
