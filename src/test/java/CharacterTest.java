import org.junit.jupiter.api.Test;

public class CharacterTest {
    @Test
    public void displayCharsAsInts() {
        final String val = "ABC123";
        val.chars().forEach(System.out::println);
    }
}
