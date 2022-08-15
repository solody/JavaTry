package TryJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringTest {
    @Test
    void test() {
        String[] strings = "a||b".split("\\|\\|");
    }

    @Test
    void testIsEmpty() {
        String nullString = null;
        String emptyString = "";

        Assertions.assertEquals(true, emptyString.isEmpty());
        Assertions.assertThrowsExactly(NullPointerException.class, () -> {
            nullString.isEmpty();
        });
    }
}
