package parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

import parser.util.*;
import parser.model.*;


public class ProcessorTest extends SetupTest {

    @Test
    void testProductCreation() {
        Product p = new Product("Laptop", 1200.0, "Electronics", true);
        assertEquals("Laptop", p.productName());
        assertEquals(1200.0, p.price());
        assertEquals("Electronics", p.category());
        assertTrue(p.inStock());
    }

    List<Product> sampleProducts = List.of(
        new Product("Laptop", 1500, "Electronics", true),
        new Product("Mouse", 20, "Electronics", true),
        new Product("Speaker", 80, "Furniture", false),
        new Product("Desk", 300, "Furniture", true),
        new Product("PC", 50, "Electronics", true)
    );

    @Test
    void testFilterProducts() {
        var filtered = Processor.filterProducts(sampleProducts, 100);
        assertEquals(2, filtered.size());
        assertTrue(filtered.stream().allMatch(p -> p.price() > 100 && p.inStock()));
    }

    @Test
    void testCountPerCategory() {
        Map<String, Long> counts = Processor.countPerCategory(sampleProducts);
        assertEquals(3L, counts.get("Electronics"));
        assertEquals(2L, counts.get("Furniture"));
    }

    @Test
    void testGetTopNProducts() {
        var top3 = Processor.getTopNProducts(sampleProducts, 3);
        assertEquals(3, top3.size());
        assertEquals("Laptop", top3.get(0).productName());
        assertEquals("Desk", top3.get(1).productName());
        assertEquals("Speaker", top3.get(2).productName());

        var top10 = Processor.getTopNProducts(sampleProducts, 10);
        assertEquals(sampleProducts.size(), top10.size());
    }
}
