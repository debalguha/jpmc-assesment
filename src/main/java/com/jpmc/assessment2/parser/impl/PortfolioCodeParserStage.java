package com.jpmc.assessment2.parser.impl;

import com.jpmc.assessment2.parser.ParseSuccess;
import com.jpmc.assessment2.parser.util.Either;
import com.jpmc.assessment2.parser.ParserStage;
import com.jpmc.assessment2.parser.PartExtractor;
import com.jpmc.assessment2.parser.error.ParseError;
import com.jpmc.assessment2.parser.error.impl.ParseErrorImpl;

import java.util.Arrays;
import java.util.List;

public class PortfolioCodeParserStage implements ParserStage {
    final static String ERROR_TEMPLATE = "File '%s' failed validation. PortfolioCode should be A/B/C found %s.";
    final List<Character> validPortfolioCodes;
    final PartExtractor partExtractor;

    public PortfolioCodeParserStage(PartExtractor partExtractor, Character... validPortfolioCodes) {
        this.validPortfolioCodes = Arrays.asList(validPortfolioCodes);
        this.partExtractor = partExtractor;
    }

    @Override
    public Either<ParseError, ParseSuccess> parse(String input) {
        return partExtractor.extractPart(input)
                .map(s -> match(s, input))
                .orElseGet(() -> Either.left(new ParseErrorImpl(ERROR_TEMPLATE, ParseError.Reason.INVALID_PORTFOLIO_CODE, "none")));
    }

    private Either<ParseError, ParseSuccess> match(String extracted, String input) {
        if (extracted.length() == 1 && validPortfolioCodes.contains(extracted.charAt(0))) {
            return Either.right(new ParseSuccess(nextSequence(input, extracted.length())));
        } else {
            return Either.left(new ParseErrorImpl(ERROR_TEMPLATE, ParseError.Reason.INVALID_PORTFOLIO_CODE, extracted));
        }
    }
}
