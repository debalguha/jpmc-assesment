package com.jpmc.assessment2.parser.impl;

import com.jpmc.assessment2.parser.ParseSuccess;
import com.jpmc.assessment2.parser.util.Either;
import com.jpmc.assessment2.parser.ParserStage;
import com.jpmc.assessment2.parser.PartExtractor;
import com.jpmc.assessment2.parser.error.ParseError;
import com.jpmc.assessment2.parser.error.impl.ParseErrorImpl;

public class DigitParserStage implements ParserStage {
    static final String ERROR_TEMPLATE = "File '%s' failed validation. Double digit sequence code expected but found %s .";
    final int numDigitsAllowed;
    final PartExtractor partExtractor;

    public DigitParserStage(int numDigitsAllowed, PartExtractor partExtractor) {
        this.numDigitsAllowed = numDigitsAllowed;
        this.partExtractor = partExtractor;
    }


    @Override
    public Either<ParseError, ParseSuccess> parse(String input) {
        return partExtractor.extractPart(input)
                .map(s -> match(s, input))
                .orElseGet(() -> Either.left(new ParseErrorImpl(ERROR_TEMPLATE, ParseError.Reason.INVALID_SEQUENCE_DIGIT, "none")));
    }

    private Either<ParseError, ParseSuccess> match(String extracted, String input) {
        if(numDigitsAllowed == extracted.length()) {
            return Either.right(new ParseSuccess(nextSequence(input, numDigitsAllowed)));
        } else {
            return Either.left(new ParseErrorImpl(ERROR_TEMPLATE, ParseError.Reason.INVALID_SEQUENCE_DIGIT, extracted));
        }
    }
}
