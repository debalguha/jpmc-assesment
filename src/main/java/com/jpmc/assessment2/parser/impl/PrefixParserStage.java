package com.jpmc.assessment2.parser.impl;

import com.jpmc.assessment2.parser.ParseSuccess;
import com.jpmc.assessment2.parser.util.Either;
import com.jpmc.assessment2.parser.ParserStage;
import com.jpmc.assessment2.parser.PartExtractor;
import com.jpmc.assessment2.parser.error.ParseError;
import com.jpmc.assessment2.parser.error.impl.ParseErrorImpl;

public class PrefixParserStage implements ParserStage {

    static final String ERROR_TEMPLATE = "File '%s' failed validation. Prefix for the file should be 'Test' found '%s'.";
    final String validPrefix;

    final PartExtractor partExtractor;

    public PrefixParserStage(String validPrefix, PartExtractor partExtractor){
        this.validPrefix = validPrefix;
        this.partExtractor = partExtractor;
    }

    @Override
    public Either<ParseError, ParseSuccess> parse(String input) {
        return partExtractor.extractPart(input)
                .map(s -> match(s, input))
                .orElseGet(() -> Either.left(new ParseErrorImpl(ERROR_TEMPLATE, ParseError.Reason.INVALID_PREFIX, "none")));
    }

    private Either<ParseError, ParseSuccess> match(String extracted, String input) {
        if(validPrefix.equals(extracted)) {
            return Either.right(new ParseSuccess(nextSequence(input, validPrefix.length())));
        } else {
            return Either.left(new ParseErrorImpl(ERROR_TEMPLATE, ParseError.Reason.INVALID_PREFIX, extracted));
        }
    }
}
