package com.jpmc.assessment.parser.impl;

import com.jpmc.assessment.parser.ParseError;
import com.jpmc.assessment2.parser.ParseSuccess;
import com.jpmc.assessment.parser.ParserStage;
import com.jpmc.assessment2.parser.util.Either;

public class SkipCharacterParserStage implements ParserStage {
    final int numCharactersToSkip;
    public SkipCharacterParserStage(int numCharactersToSkip) {
        this.numCharactersToSkip = numCharactersToSkip;
    }

    @Override
    public Either<ParseError, ParseSuccess> parse(String input) {
        return Either.right(new ParseSuccess(nextSequence(input, numCharactersToSkip)));
    }
}
