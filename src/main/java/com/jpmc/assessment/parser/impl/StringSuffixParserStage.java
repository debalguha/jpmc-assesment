package com.jpmc.assessment.parser.impl;

import com.jpmc.assessment.parser.ParseError;
import com.jpmc.assessment2.parser.ParseSuccess;
import com.jpmc.assessment2.parser.util.Either;

import java.util.function.Function;

public class StringSuffixParserStage extends AbstractParserStage {
    final String validSuffix;

    public StringSuffixParserStage(String validSuffix, Function<String, ParseError> parserErrorGenerator) {
        super(parserErrorGenerator);
        this.validSuffix = validSuffix;
    }

    @Override
    public Either<ParseError, ParseSuccess> parse(String input) {
        return input.equals(validSuffix) ? Either.right(new ParseSuccess("")) : Either.left(parserErrorGenerator.apply(input));
    }
}
