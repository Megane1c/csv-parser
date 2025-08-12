package parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import parser.util.*;
import parser.model.*;

public class ParseArgsTest {
    
    @Test
    void testValidArgs() {
        String[] args = {"50.0", "10"};
        FilterArgs params = ParseArgs.parseArgs(args);

        assertEquals(50.0, params.minPrice());
        assertEquals(10, params.topN());
    }

    @Test
    void testMissingArgs() {
        String[] args = {"50.0"};
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> ParseArgs.parseArgs(args)
        );
        assertTrue(ex.getMessage().contains("Usage"));
    }

    @Test
    void testNonNumericArgs() {
        String[] args = {"abc", "10"};
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> ParseArgs.parseArgs(args)
        );
        assertTrue(ex.getMessage().contains("Invalid number format"));
    }

    @Test
    void testNegativePriceArgs() {
        String[] args = {"-5.0", "10"};
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> ParseArgs.parseArgs(args)
        );
        assertTrue(ex.getMessage().contains("minPrice must be nonnegative"));
    }

    @Test
    void testNegativeTopNArgs() {
        String[] args = {"50.0", "-2"};
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> ParseArgs.parseArgs(args)
        );
        assertTrue(ex.getMessage().contains("topN must be nonnegative"));
    }
}
