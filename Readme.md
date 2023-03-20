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




This project is example of using [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway) as an edge
service with a Spring Boot application. Spring Cloud Gateway provides means for routing an incoming request to a
matching downstream service.

Gateway is a suitable replacement for [Spring Cloud Netflix Zuul](https://spring.io/projects/spring-cloud-netflix) since
the latter module is now in maintenance mode starting Spring Cloud Greenwich (2.1.0) release train. Spring Cloud will
continue to support Zuul for a period of at least a year from the general availability of the Greenwich release train.
Putting a module in the maintenance mode means that the Spring Cloud will no longer add any new feature but will fix
blocker bugs and security issues.
