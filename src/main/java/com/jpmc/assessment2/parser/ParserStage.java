package com.jpmc.assessment2.parser;

import com.jpmc.assessment2.parser.util.Either;
import com.jpmc.assessment2.parser.error.ParseError;

public interface ParserStage {
    static final String UNEXPECTED_ERROR_TEMPLATE = "Unexpected error while parsing!!";
    Either<ParseError, ParseSuccess> parse(String input);
    default String nextSequence(String input, int length) {
        return input.substring(length);
    }
}
