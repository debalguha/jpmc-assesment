package com.jpmc.assessment2.parser;

import com.jpmc.assessment.parser.ParserCombinator;

public class ParserCombinatorFactory {
    public static ParserCombinator buildOneForFileNameValidation() {
/*        Test_<portfoliocode>_<ddmmyyyy>_<2digit-sequencenumber>.csv
        Where:
        Test – hardcoded string prefix
        <portfoliocode> - can only be A,B,C
        <ddmmyyyy>– is valuation date format dd e.g 07, mm e.g 12, yyyy e.g 1987.
        <2digit-sequencenumber> - is 2 digit sequence number*/

/*        return ParserCombinator.builder()
                .nextStage(new OverallFormatParserStage(Pattern.compile("^.*_.*_.*_.*\\..*$"), ParseError.InvalidOverallDataFormat))
                .nextStage(new StringPrefixParserStage("Test", ParseError.INVALID_PREFIX))
                .nextStage(new SkipCharacterParserStage(1))
                .nextStage(new RegularExpressionParserStage(ParseError.INVALID_PORTFOLIO_CODE, "^([A|B|C])(.*)"))
                .nextStage(new SkipCharacterParserStage(1))
                .nextStage(new DateFormatParserStage("ddMMyyyy", ParseError.INVALID_DATE_FORMAT))
                .nextStage(new SkipCharacterParserStage(1))
                .nextStage(new RegularExpressionParserStage(ParseError.INVALID_SEQUENCE_DIGIT, "([0-9][0-9])\\.(.*)"))
                .nextStage(new SkipCharacterParserStage(1))
                .nextStage(new StringSuffixParserStage("csv", ParseError.INVALID_FILE_EXTENSION))
                .build();*/
        return null;
    }
}
