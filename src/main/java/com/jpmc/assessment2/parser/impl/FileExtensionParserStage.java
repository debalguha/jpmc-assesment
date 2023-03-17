package com.jpmc.assessment2.parser.impl;

import com.jpmc.assessment2.parser.ParseSuccess;
import com.jpmc.assessment2.parser.util.Either;
import com.jpmc.assessment2.parser.ParserStage;
import com.jpmc.assessment2.parser.error.ParseError;
import com.jpmc.assessment2.parser.error.impl.ParseErrorImpl;

public class FileExtensionParserStage implements ParserStage {
    final static String ERROR_TEMPLATE = "File '%s' failed validation. Invalid File format.Expected 'csv' found '%s'";
    final String validExtension;
    public FileExtensionParserStage(String validExtension) {
        this.validExtension = validExtension;
    }

    @Override
    public Either<ParseError, ParseSuccess> parse(String input) {
        return validExtension.equals(input)
                ? Either.right(new ParseSuccess(nextSequence(input, validExtension.length())))
                : Either.left((new ParseErrorImpl(ERROR_TEMPLATE, ParseError.Reason.INVALID_FILE_EXTENSION, input)));
    }

}
