import com.jpmc.assessment.parser.ParseError;
import com.jpmc.assessment.parser.ParseErrors;
import com.jpmc.assessment2.parser.ParseSuccess;
import com.jpmc.assessment.parser.ParserCombinator;
import com.jpmc.assessment.parser.impl.*;
import com.jpmc.assessment2.parser.util.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserCombinatorTest {

    ParserCombinator parserCombinator;

    @BeforeEach
    public void setup() {
        parserCombinator = ParserCombinator.builder()
                .nextStage(new OverallFormatParserStage(Pattern.compile("^.*_.*_.*_.*\\..*$"), ParseErrors.InvalidOverallDataFormat::new))
                .nextStage(new StringPrefixParserStage("Test", ParseErrors.InvalidPrefix::new))
                .nextStage(new SkipCharacterParserStage(1))
                .nextStage(new PortfolioCodeParserStage(ParseErrors.InvalidPortfolioCode::new, 'A', 'B', 'C'))
                .nextStage(new SkipCharacterParserStage(1))
                .nextStage(new DateFormatParserStage("ddMMyyyy", ParseErrors.InvalidDateFormat::new))
                .nextStage(new SkipCharacterParserStage(1))
                .nextStage(new DigitParserStage(2, "^(\\d+)\\..*", ParseErrors.InvalidSequenceDigit::new))
                .nextStage(new SkipCharacterParserStage(1))
                .nextStage(new StringSuffixParserStage("csv", ParseErrors.InvalidFileExtension::new))
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
    }

    @Test
    public void testInValid_BadDateFormat() {
        Either<ParseError, ParseSuccess> successOrFailure = parserCombinator.apply("Test_A_7121987_99.csv");
        assertTrue(successOrFailure.isLeft());
        assertEquals(ParseError.Reason.INVALID_DATE_FORMAT, successOrFailure.leftValue().getReason());
    }

    @Test
    public void testInValid_BadDate() {
        Either<ParseError, ParseSuccess> successOrFailure = parserCombinator.apply("Test_A_42121987_99.csv");
        assertTrue(successOrFailure.isLeft());
        assertEquals(ParseError.Reason.INVALID_DATE_FORMAT, successOrFailure.leftValue().getReason());
    }

    @Test
    public void testInValid_BadSequenceDigit() {
        Either<ParseError, ParseSuccess> successOrFailure = parserCombinator.apply("Test_A_02121987_199.csv");
        assertTrue(successOrFailure.isLeft());
        assertEquals(ParseError.Reason.INVALID_SEQUENCE_DIGIT, successOrFailure.leftValue().getReason());
    }

    @Test
    public void testInValid_BadSequenceDigit_2() {
        Either<ParseError, ParseSuccess> successOrFailure = parserCombinator.apply("Test_A_02121987_0.csv");
        assertTrue(successOrFailure.isLeft());
        assertEquals(ParseError.Reason.INVALID_SEQUENCE_DIGIT, successOrFailure.leftValue().getReason());
    }

    @Test
    public void testInValid_FileExtension() {
        Either<ParseError, ParseSuccess> successOrFailure = parserCombinator.apply("Test_A_02121987_20.txt");
        assertTrue(successOrFailure.isLeft());
        assertEquals(ParseError.Reason.INVALID_FILE_EXTENSION, successOrFailure.leftValue().getReason());
    }
}
