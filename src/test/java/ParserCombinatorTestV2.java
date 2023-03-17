import com.jpmc.assessment2.parser.ParseSuccess;
import com.jpmc.assessment2.parser.ParserCombinator;
import com.jpmc.assessment2.parser.PartExtractor;
import com.jpmc.assessment2.parser.error.ParseError;
import com.jpmc.assessment2.parser.impl.*;
import com.jpmc.assessment2.parser.util.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserCombinatorTestV2 {

    ParserCombinator parserCombinator;

    @BeforeEach
    public void setup() {
        final String partExtractorRegEx = "^([a-zA-z]+)(_)([A-Z])(_)([a-zA-Z0-9]+)(_)([0-9]+)\\.([a-zA-z]+)$";
        final Function<String, PartExtractor> fun = RegexPartExtractor::new;
        parserCombinator = ParserCombinator.builder()
                .nextStage(new OverallFormatParserStage("^(.*)+_(.*)+_(.*)+_(.*)+\\.(.*)+$"))
                //.nextStage(new OverallFormatParserStage("(^[\\S\\s]+)_([\\S\\s])_([\\S\\s]+)_([\\S\\s]+)\\.([\\S\\s]+)$"))
                .nextStage(new PrefixParserStage("Test", fun.apply("^([a-zA-Z]+)_(.*)")))
                .nextStage(new SkipOneCharactersParserStage())
                .nextStage(new PortfolioCodeParserStage(fun.apply("([A-Z])_.*"), 'A', 'B', 'C'))
                .nextStage(new SkipOneCharactersParserStage())
                .nextStage(new DateFormatParserStage("ddMMyyyy", fun.apply("([a-zA-Z0-9]+)_.*")))
                .nextStage(new SkipOneCharactersParserStage())
                .nextStage(new DigitParseStage(2, fun.apply("^([0-9]+)\\..*")))
                .nextStage(new SkipOneCharactersParserStage())
                .nextStage(new FileExtensionParserStage("csv"))
                .build();
    }
    @Test
    public void testValidInputString() {
        Either<ParseError, ParseSuccess> successOrFailure = parserCombinator.apply("Test_A_07121987_99.csv");
        assertTrue(successOrFailure.isRight());
    }

    @Test
    public void testInValidInputString() {
        Either<ParseError, ParseSuccess> successOrFailure = parserCombinator.apply("AvaraKedavara.csv");
        assertTrue(successOrFailure.isLeft());
        assertEquals(ParseError.Reason.INVALID_OVERALL_FORMAT, successOrFailure.leftValue().getReason());
        assertEquals("File 'AvaraKedavara.csv' failed validation.File format should be 'Test_<portfoliocode>_<ddmmyyyy>_<2digit-sequencenumber>.csv'", successOrFailure.leftValue().getErrorMessage("AvaraKedavara.csv"));
    }

    @Test
    public void testInValid_BadPrefix() {
        Either<ParseError, ParseSuccess> successOrFailure = parserCombinator.apply("Hello_A_07121987_99.csv");
        assertTrue(successOrFailure.isLeft());
        assertEquals(ParseError.Reason.INVALID_PREFIX, successOrFailure.leftValue().getReason());
        assertEquals("File 'Hello_A_07121987_99.csv' failed validation. Prefix for the file should be 'Test' found 'Hello'.", successOrFailure.leftValue().getErrorMessage("Hello_A_07121987_99.csv"));
    }

    @Test
    public void testInValid_NoPrefix() {
        Either<ParseError, ParseSuccess> successOrFailure = parserCombinator.apply("_A_07121987_99.csv");
        assertTrue(successOrFailure.isLeft());
        assertEquals(ParseError.Reason.INVALID_PREFIX, successOrFailure.leftValue().getReason());
        assertEquals("File 'Hello_A_07121987_99.csv' failed validation. Prefix for the file should be 'Test' found 'none'.", successOrFailure.leftValue().getErrorMessage("Hello_A_07121987_99.csv"));
    }

    @Test
    public void testInValid_BadDateFormat() {
        Either<ParseError, ParseSuccess> successOrFailure = parserCombinator.apply("Test_A_7121987_99.csv");
        assertTrue(successOrFailure.isLeft());
        assertEquals(ParseError.Reason.INVALID_DATE_FORMAT, successOrFailure.leftValue().getReason());
        assertEquals("File 'Test_A_7121987_99.csv' failed validation. Valuation Date is not a valid date format 'ddmmyyyy'.", successOrFailure.leftValue().getErrorMessage("Test_A_7121987_99.csv"));
    }

    @Test
    public void testInValid_BadDate() {
        Either<ParseError, ParseSuccess> successOrFailure = parserCombinator.apply("Test_A_42121987_99.csv");
        assertTrue(successOrFailure.isLeft());
        assertEquals(ParseError.Reason.INVALID_DATE_FORMAT, successOrFailure.leftValue().getReason());
        assertEquals("File 'Test_A_42121987_99.csv' failed validation. Valuation Date is not a valid date format 'ddmmyyyy'.", successOrFailure.leftValue().getErrorMessage("Test_A_42121987_99.csv"));
    }

    @Test
    public void testInValid_BadSequenceDigit() {
        Either<ParseError, ParseSuccess> successOrFailure = parserCombinator.apply("Test_A_02121987_199.csv");
        assertTrue(successOrFailure.isLeft());
        assertEquals(ParseError.Reason.INVALID_SEQUENCE_DIGIT, successOrFailure.leftValue().getReason());
        assertEquals("File 'Test_A_02121987_199.csv' failed validation. Double digit sequence code expected but found 199 .", successOrFailure.leftValue().getErrorMessage("Test_A_02121987_199.csv"));
    }

    @Test
    public void testInValid_BadSequenceDigit_2() {
        Either<ParseError, ParseSuccess> successOrFailure = parserCombinator.apply("Test_A_02121987_0.csv");
        assertTrue(successOrFailure.isLeft());
        assertEquals(ParseError.Reason.INVALID_SEQUENCE_DIGIT, successOrFailure.leftValue().getReason());
        assertEquals("File 'Test_A_02121987_0.csv' failed validation. Double digit sequence code expected but found 0 .", successOrFailure.leftValue().getErrorMessage("Test_A_02121987_0.csv"));
    }

    @Test
    public void testInValid_FileExtension() {
        Either<ParseError, ParseSuccess> successOrFailure = parserCombinator.apply("Test_A_02121987_20.txt");
        assertTrue(successOrFailure.isLeft());
        assertEquals(ParseError.Reason.INVALID_FILE_EXTENSION, successOrFailure.leftValue().getReason());
        //"File '%s' failed validation. Invalid File format.Expected 'csv' found '%s'"
        assertEquals("File 'Test_A_02121987_20.txt' failed validation. Invalid File format.Expected 'csv' found 'txt'", successOrFailure.leftValue().getErrorMessage("Test_A_02121987_20.txt"));
    }
}
