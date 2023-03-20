JPMC File name validator
==============================

The file name validator is built around a tear down concept of parser combinator, wherein each parsable unit is processed 
by its own parser in a piecemeal fashion and a final combinator combines and invokes them sequentially. For this particular
assessment there 7 parsers are created and sequenced as per the overall parsing requirement. 

Each parser strips off it's relevant part from the input string and validates according to its logic and returns either
a success or an error. A functional disjunction/Sum type "Either" has been used to return a valid value from every parser.
The "Either" type progresses as long as the parser emits a success(a "Right" value), but short circuits it's processing 
anytime it sees there is an error (a "Left" value) within its context.

The parsers use a customized extractor to extract it's relevant part. Use of an extractor decouples the logic of how to extract
the relevant part from actually validating the relevant part. The individual parsers (A ParserStage) strips the input if 
successful and forwards the remaining to the next one in line.

Parser combinator allows customization of parsing logic easily by combining/sequencing the stages as requirements change.    

## Building and running

## Two ways of running the program
    - ./mvnw compile exec:java -Dexec.mainClass="com.jpmc.assessment2.parser.FileNameValidator" -Dexec.args="<path_of_the_file>"
    - java -jar target/jpmc-assesment-1.0-SNAPSHOT-jar-with-dependencies.jar <path_of_the_file>

## Please ensure at least java 8 is installed
