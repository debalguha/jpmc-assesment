package com.jpmc.assessment.parser.impl;

import com.jpmc.assessment.parser.ParseError;
import com.jpmc.assessment.parser.ParserStage;

import java.util.function.Function;

public abstract class AbstractParserStage implements ParserStage {
    final Function<String, ParseError> parserErrorGenerator;

    protected AbstractParserStage(Function<String, ParseError> parserErrorGenerator) {
        this.parserErrorGenerator = parserErrorGenerator;
    }
}
