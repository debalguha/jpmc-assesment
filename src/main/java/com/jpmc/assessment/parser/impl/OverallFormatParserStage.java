package com.jpmc.assessment.parser.impl;

import com.jpmc.assessment.parser.ParseError;
import com.jpmc.assessment2.parser.ParseSuccess;
import com.jpmc.assessment2.parser.util.Either;

import java.util.function.Function;
import java.util.regex.Pattern;

public class OverallFormatParserStage extends AbstractParserStage {

    final Pattern regexPattern;

    public OverallFormatParserStage(Pattern regexPattern, Function<String, ParseError> parserErrorGenerator) {
        super(parserErrorGenerator);
        this.regexPattern = regexPattern;
    }

    @Override
    public Either<ParseError, ParseSuccess> parse(String input) {
        return regexPattern.matcher(input).matches() ? Either.right(new ParseSuccess(input)) : Either.left(parserErrorGenerator.apply(""));
    }
}
