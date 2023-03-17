package com.jpmc.assessment2.parser.impl;

import com.jpmc.assessment2.parser.ParseSuccess;
import com.jpmc.assessment2.parser.util.Either;
import com.jpmc.assessment2.parser.ParserStage;
import com.jpmc.assessment2.parser.PartExtractor;
import com.jpmc.assessment2.parser.error.ParseError;
import com.jpmc.assessment2.parser.error.impl.ParseErrorImpl;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateFormatParserStage implements ParserStage {

    final static String ERROR_TEMPLATE = "File '%s' failed validation. Valuation Date is not a valid date format 'ddmmyyyy'.";

    final DateTimeFormatter dateTimeFormatter;
    final String dateFormat;
    final PartExtractor partExtractor;

    public DateFormatParserStage(String dateFormat, PartExtractor partExtractor) {
        this.partExtractor = partExtractor;
        this.dateFormat = dateFormat;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat, Locale.getDefault());
    }


    @Override
    public Either<ParseError, ParseSuccess> parse(String input) {
        return partExtractor.extractPart(input)
                .map(s -> canParseAsDate(s, input))
                .orElseGet(() -> Either.left(new ParseErrorImpl(ERROR_TEMPLATE, ParseError.Reason.INVALID_DATE_FORMAT, "none")));

    }

    private Either<ParseError, ParseSuccess> canParseAsDate(String extracted, String input) {
        try {
            dateTimeFormatter.parse(extracted);
            return Either.right(new ParseSuccess(nextSequence(input, dateFormat.length())));
        } catch (DateTimeParseException e) {
            return Either.left(new ParseErrorImpl(ERROR_TEMPLATE, ParseError.Reason.INVALID_DATE_FORMAT, ""));
        }

    }
}
