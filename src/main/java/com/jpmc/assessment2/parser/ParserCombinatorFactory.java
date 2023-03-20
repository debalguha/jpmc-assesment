package com.jpmc.assessment2.parser;

import com.jpmc.assessment2.parser.impl.*;

import java.util.function.Function;

public class ParserCombinatorFactory {
    public static ParserCombinator buildOneForFileNameValidation() {
/*        Test_<portfoliocode>_<ddmmyyyy>_<2digit-sequencenumber>.csv
        Where:
        Test – hardcoded string prefix
        <portfoliocode> - can only be A,B,C
        <ddmmyyyy>– is valuation date format dd e.g 07, mm e.g 12, yyyy e.g 1987.
        <2digit-sequencenumber> - is 2 digit sequence number*/

        final Function<String, PartExtractor> fun = RegexPartExtractor::new;
        return ParserCombinator.builder()
                .nextStage(new OverallFormatParserStage("^(.*)+_(.*)+_(.*)+_(.*)+\\.(.*)+$"))
                //.nextStage(new OverallFormatParserStage("(^[\\S\\s]+)_([\\S\\s])_([\\S\\s]+)_([\\S\\s]+)\\.([\\S\\s]+)$"))
                .nextStage(new PrefixParserStage("Test", fun.apply("^([a-zA-Z]+)_(.*)")))
                .nextStage(new SkipOneCharactersParserStage())
                .nextStage(new PortfolioCodeParserStage(fun.apply("([A-Z])_.*"), 'A', 'B', 'C'))
                .nextStage(new SkipOneCharactersParserStage())
                .nextStage(new DateFormatParserStage("ddMMyyyy", fun.apply("([a-zA-Z0-9]+)_.*")))
                .nextStage(new SkipOneCharactersParserStage())
                .nextStage(new DigitParserStage(2, fun.apply("^([0-9]+)\\..*")))
                .nextStage(new SkipOneCharactersParserStage())
                .nextStage(new FileExtensionParserStage("csv"))
                .build();
    }
}
