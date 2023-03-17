package com.jpmc.assessment.parser;

import com.jpmc.assessment2.parser.util.Either;
import com.jpmc.assessment2.parser.util.NotNull;

public interface ParserStage {
    Either<ParseError, ParseSuccess> parse(@NotNull String input);
    default String nextSequence(String input, int length) {
        return input.substring(length);
    }
}
