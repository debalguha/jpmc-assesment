package com.jpmc.assessment2.parser;

import com.jpmc.assessment2.parser.error.ParseError;
import com.jpmc.assessment2.parser.util.Either;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;

public class ParserCombinator implements Function<String, Either<ParseError, ParseSuccess>> {
    final Queue<ParserStage> stages;

    private ParserCombinator(Queue<ParserStage> stages) {
        this.stages = stages;
    }

    @Override
    public Either<ParseError, ParseSuccess> apply(String input) {
        Either<ParseError, ParseSuccess> init = Either.right(new ParseSuccess(input));
        return stages.stream()
                .reduce(init, (e1, e2) -> e1.flatMap(s -> e2.parse(s.remaining())), (a, b) -> a);
    }

    public static ParserCombinatorBuilder builder() {
        return new ParserCombinatorBuilder();
    }

    public static final class ParserCombinatorBuilder {
        final Queue<ParserStage> stageBuildUp;

        private ParserCombinatorBuilder() {
            this.stageBuildUp = new LinkedList<>();
        }

        public ParserCombinatorBuilder nextStage(ParserStage parserStage) {
            stageBuildUp.offer(parserStage);
            return this;
        }

        public ParserCombinator build() {
            return new ParserCombinator(new LinkedList<>(stageBuildUp));
        }
    }
}
