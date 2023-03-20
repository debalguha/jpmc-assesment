package com.jpmc.assessment2.parser;


import com.jpmc.assessment2.parser.error.ParseError;
import com.jpmc.assessment2.parser.util.Either;

import java.io.File;
import java.util.logging.Logger;

public class FileNameValidator {
    private static final Logger LOGGER = Logger.getLogger(FileNameValidator.class.getName());
    public static void main(String args[]) {
        final boolean argumentValidationResult = validateCommandLine(args);
        if(!argumentValidationResult) {
            LOGGER.warning("Invalid program argument. Usage:: java -jar fileNameValidator.jar <file_path>");
            System.exit(1);
        } else {
            final String filePath = args[0];
            final String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
            Either<ParseError, ParseSuccess> validationResult = ParserCombinatorFactory.buildOneForFileNameValidation()
                    .apply(fileName);
            if(validationResult.isRight()) {
                LOGGER.info(String.format("File %s passed validation", fileName));
            } else {
                LOGGER.severe(validationResult.leftValue().getErrorMessage(fileName));
            }

        }
    }

    private static boolean validateCommandLine(String args[]) {
        if(args == null) {
            return false;
        } else if(args.length != 1) {
            return false;
        } else if(args[0].isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
