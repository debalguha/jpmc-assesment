package com.jpmc.assessment.parser.impl;

import com.jpmc.assessment.parser.ParseError;
import com.jpmc.assessment2.parser.ParseSuccess;
import com.jpmc.assessment2.parser.util.Either;

import java.util.List;
import java.util.function.Function;

public class PortfolioCodeParserStage extends AbstractParserStage {
    final List<Character> validPortfolioCodes;
    public PortfolioCodeParserStage(Function<String, ParseError> parserErrorGenerator, Character ... validPortfolioCodes) {
        super(parserErrorGenerator);
        this.validPortfolioCodes = List.of(validPortfolioCodes);
    }

    @Override
    public Either<ParseError, ParseSuccess> parse(String input) {
        final char portfolioCode = input.charAt(0);
        return validPortfolioCodes.contains(portfolioCode) ? Either.right(new ParseSuccess(nextSequence(input, 1))) : Either.left(parserErrorGenerator.apply(String.valueOf(portfolioCode)));
    }
}
