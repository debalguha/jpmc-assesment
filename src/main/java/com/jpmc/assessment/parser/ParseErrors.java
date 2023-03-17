package com.jpmc.assessment.parser;

sealed class ParseErrorImpl implements ParseError{
    final String errorMessageTemplate;
    final String badData;
    final Reason reason;
    ParseErrorImpl(Reason reason, String errorMessageTemplate, String badData) {
        this.reason = reason;
        this.errorMessageTemplate = errorMessageTemplate;
        this.badData = badData;
    }

    public String getErrorMessage(String fileName) {
        return errorMessageTemplate.formatted(fileName, badData);
    }

    @Override
    public Reason getReason() {
        return this.reason;
    }
}
//INVALID_DATE_FORMAT("File '%s' failed validation. Valuation Date is not a valid date format 'ddmmyyyy'."),
public class ParseErrors {
    public static final class InvalidOverallDataFormat extends ParseErrorImpl {
        public InvalidOverallDataFormat(String badData) {
            super(Reason.INVALID_OVERALL_FORMAT, "File '%s' failed validation.File format should be 'Test_<portfoliocode>_<ddmmyyyy>_<2digit-sequencenumber>.csv'", badData);
        }
    }

    public static final class InvalidDateFormat extends ParseErrorImpl {
        public InvalidDateFormat(String badData) {
            super(Reason.INVALID_DATE_FORMAT, "File '%s' failed validation. Valuation Date is not a valid date format 'ddmmyyyy'.", badData);
        }
    }

    public static final class InvalidPrefix extends ParseErrorImpl {
        public InvalidPrefix(String badData) {
            super(Reason.INVALID_PREFIX, "File '%s' failed validation. Prefix for the file should be 'Test' found '%s'.", badData);
        }
    }

    public static final class InvalidPortfolioCode extends ParseErrorImpl {
        public InvalidPortfolioCode(String badData) {
            super(Reason.INVALID_PORTFOLIO_CODE, "File '%s' failed validation. PortfolioCode should be A/B/C found %s.", badData);
        }
    }

    public static final class InvalidFileExtension extends ParseErrorImpl {
        public InvalidFileExtension(String badData) {
            super(Reason.INVALID_FILE_EXTENSION, "File '%s' failed validation. Invalid File format.Expected 'csv' found '%s'", badData);
        }
    }

    public static final class InvalidSequenceDigit extends ParseErrorImpl {
        public InvalidSequenceDigit(String badData) {
            super(Reason.INVALID_SEQUENCE_DIGIT, "File '%s' failed validation. Double digit sequence code expected but found %s .", badData);
        }
    }
}


