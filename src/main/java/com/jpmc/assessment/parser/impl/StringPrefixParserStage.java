package com.jpmc.assessment.parser.impl;

import com.jpmc.assessment.parser.ParseError;
import com.jpmc.assessment2.parser.ParseSuccess;
import com.jpmc.assessment2.parser.util.Either;

import java.util.function.Function;

public class StringPrefixParserStage extends AbstractParserStage {
    final String validPrefix;

    public StringPrefixParserStage(String validPrefix, Function<String, ParseError> parserErrorGenerator) {
        super(parserErrorGenerator);
        this.validPrefix = validPrefix;
    }

    @Override
    public Either<ParseError, ParseSuccess> parse(String input) {
        return input.startsWith(validPrefix)
                ? Either.right(new ParseSuccess(nextSequence(input, validPrefix.length())))
                : Either.left(parserErrorGenerator.apply(input.substring(0, validPrefix.length())));
    }
}
