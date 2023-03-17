package com.jpmc.assessment2.parser;

import java.util.Optional;

public interface PartExtractor {
    //"^([a-zA-z]+)_([A-Z])_([a-zA-Z0-9]+)_([0-9]+)\\.([a-zA-z]+)$"
    Optional<String> extractPart(String input);
}
