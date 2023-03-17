package com.jpmc.assessment.parser.impl;

import com.jpmc.assessment.parser.ParseError;
import com.jpmc.assessment2.parser.ParseSuccess;
import com.jpmc.assessment2.parser.util.Either;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DigitParserStage extends AbstractParserStage {

    final int numDigitsAllowed;
    final Pattern parsePattern;
    public DigitParserStage(int numDigitsAllowed, String regex, Function<String, ParseError> parserErrorGenerator) {
        super(parserErrorGenerator);
        this.numDigitsAllowed = numDigitsAllowed;
        this.parsePattern = Pattern.compile(regex);
    }

    @Override
    public Either<ParseError, ParseSuccess> parse(String input) {
        Matcher matcher = parsePattern.matcher(input);
        if(matcher.matches() && matcher.find(0)) {
            String group = matcher.group(1);
            if(group.length() == numDigitsAllowed) {
                return Either.right(new ParseSuccess(nextSequence(input, matcher.group(1).length())));
            } else {
                return Either.left(parserErrorGenerator.apply(group));
            }
        } else {
            return Either.left(parserErrorGenerator.apply("none"));
        }
    }
}
