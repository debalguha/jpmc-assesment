package com.jpmc.assessment2.parser;

public class ParseSuccess {
    final String remaining;


    public ParseSuccess(String remaining) {
        this.remaining = remaining;
    }

    public String remaining() {
        return this.remaining;
    }
}
