package com.jpmc.assessment.parser.impl;

import com.jpmc.assessment.parser.ParseError;
import com.jpmc.assessment2.parser.ParseSuccess;
import com.jpmc.assessment2.parser.util.Either;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.function.Function;

public class DateFormatParserStage extends AbstractParserStage {
    final DateTimeFormatter dateTimeFormatter;
    final String dateFormat;

    public DateFormatParserStage(String format, Function<String, ParseError> parserErrorGenerator) {
        super(parserErrorGenerator);
        this.dateFormat = format;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(format, Locale.getDefault());
    }

    @Override
    public Either<ParseError, ParseSuccess> parse(String input) {
        return canParseAsDate(input.substring(0, dateFormat.length())) ? Either.right(new ParseSuccess(nextSequence(input, dateFormat.length()))) : Either.left(parserErrorGenerator.apply(""));
    }

    private boolean canParseAsDate(String input) {
        try {
            dateTimeFormatter.parse(input);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
