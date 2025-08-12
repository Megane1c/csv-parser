package parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.List;

import parser.util.*;
import parser.model.*;


public class ParseCsvTest extends SetupTest {

    @Test
    void testNegativePrice() throws IOException {
        String csv = "ProductName,Price,Category,InStock\n" +
                     "TestProduct,-5.0,Category,true\n";

        Path file = Files.createTempFile("negative-price", ".csv");
        Files.writeString(file, csv);

        ParseCsv parser = new ParseCsv();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            parser.parseAs(file, true, row ->
                new Product(
                    row[0],
                    Double.parseDouble(row[1]),
                    row[2],
                    Boolean.parseBoolean(row[3])
                ));
        });

        assertTrue(ex.getMessage().contains("Price cannot be negative"));
    }

    @Test
    void testBlankCategory() throws IOException {
        String csv = "ProductName,Price,Category,InStock\n" +
                     "TestProduct,10.0,   ,true\n";

        Path file = Files.createTempFile("blank-category", ".csv");
        Files.writeString(file, csv);

        ParseCsv parser = new ParseCsv();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            parser.parseAs(file, true, row ->
                new Product(
                    row[0],
                    Double.parseDouble(row[1]),
                    row[2],
                    Boolean.parseBoolean(row[3])
                ));
        });

        assertTrue(ex.getMessage().contains("Category cannot be null or blank"));
    }

    @Test
    void testMalformedRow() throws IOException {
        String csv = "ProductName,Price,Category,InStock\n" +
                     "TestProduct,10.0,true\n";  // 3 columns

        Path file = Files.createTempFile("malformed-row", ".csv");
        Files.writeString(file, csv);

        ParseCsv parser = new ParseCsv();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            parser.parseAs(file, true, row ->
                new Product(
                    row[0],
                    Double.parseDouble(row[1]),
                    row[2],
                    Boolean.parseBoolean(row[3])
                ));
        });

        assertTrue(ex.getMessage().contains("has 3 columns but expected 4"));
    }

    @Test
    void testValidInput() throws IOException {
        String csv = "ProductName,Price,Category,InStock\n" +
                     "TestProduct,25.0,Electronics,true\n";

        Path file = Files.createTempFile("valid-input", ".csv");
        Files.writeString(file, csv);

        ParseCsv parser = new ParseCsv();

        List<Product> products = parser.parseAs(file, true, row ->
                new Product(
                    row[0],
                    Double.parseDouble(row[1]),
                    row[2],
                    Boolean.parseBoolean(row[3])
                ));

        assertEquals(1, products.size());
        Product p = products.get(0);
        assertEquals("TestProduct", p.productName());
        assertEquals(25.0, p.price());
        assertEquals("Electronics", p.category());
        assertTrue(p.inStock());
    }
}
