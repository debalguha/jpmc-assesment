package com.jpmc.assessment2.parser.impl;

import com.jpmc.assessment2.parser.PartExtractor;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPartExtractor implements PartExtractor {
    final Pattern regex;
    public RegexPartExtractor(String regex) {
        this.regex = Pattern.compile(regex);
    }

    public static RegexPartExtractor from(String regex) {
        return new RegexPartExtractor(regex);
    }
    @Override
    public Optional<String> extractPart(String input) {
        Matcher matcher = regex.matcher(input);
        if(matcher.find()) {
            return Optional.of(matcher.group(1));
        } else {
            return Optional.empty();
        }
    }
}
