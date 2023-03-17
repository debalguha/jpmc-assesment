package com.jpmc.assessment2.parser.impl;

import com.jpmc.assessment2.parser.ParseSuccess;
import com.jpmc.assessment2.parser.util.Either;
import com.jpmc.assessment2.parser.ParserStage;
import com.jpmc.assessment2.parser.error.ParseError;
import com.jpmc.assessment2.parser.error.impl.ParseErrorImpl;

import java.util.regex.Pattern;

public class OverallFormatParserStage implements ParserStage {
    final static String ERROR_TEMPLATE = "File '%s' failed validation.File format should be 'Test_<portfoliocode>_<ddmmyyyy>_<2digit-sequencenumber>.csv'";
    final Pattern overallPattern;

    public OverallFormatParserStage(String regex) {
        this.overallPattern = Pattern.compile(regex);
    }

    @Override
    public Either<ParseError, ParseSuccess> parse(String input) {
        return overallPattern.matcher(input).matches() ? Either.right(new ParseSuccess(input))
                : Either.left(new ParseErrorImpl(ERROR_TEMPLATE, ParseError.Reason.INVALID_OVERALL_FORMAT, ""));
    }
}
