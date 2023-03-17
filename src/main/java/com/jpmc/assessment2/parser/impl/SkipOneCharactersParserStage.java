package com.jpmc.assessment2.parser.impl;

import com.jpmc.assessment2.parser.ParseSuccess;
import com.jpmc.assessment2.parser.util.Either;
import com.jpmc.assessment2.parser.ParserStage;
import com.jpmc.assessment2.parser.error.ParseError;

public class SkipOneCharactersParserStage implements ParserStage {

    public SkipOneCharactersParserStage() {
    }

    @Override
    public Either<ParseError, ParseSuccess> parse(String input) {
        return Either.right(new ParseSuccess(input.substring(1)));
    }

}
