package parser.util;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import parser.model.*;

/**
 * The Processor class provides static utility methods to process
 * and analyze lists of Product instances.
 * 
 * It includes methods to filter products by price and stock status, count
 * products by category, and retrieve the top N most expensive products.
 *
 * Example usage:
 * <pre>
 *   var filteredStock = Processor.filterProducts(products, 100.0);
 *   var filteredCategory = Processor.countPerCategory(products);
 *   var filteredTopN = Processor.getTopNProducts(products, 5);
 * </pre>
 * 
 * @author YourName
 * @version 1.0
 */

public class Processor {
    public final Path csvFile;
    private static FileLogger logger;

    public Processor(Path csvFile, Path logFile) {
        this.csvFile = csvFile;
    }

    public static void initLogger(Path logFile) {
        logger = new FileLogger(logFile);
    }

    public static FileLogger getLogger() {
        return logger;
    }

    public static List<Product> filterProducts(List<Product> products, double minPrice) {
        
        var filteredProducts = products.stream()
                .filter(p -> p.price() > minPrice && p.inStock())
                .toList();

        logger.writeJson(filteredProducts, Path.of("src/filteredProducts.json"));

                
        return filteredProducts;
    }

    public static Map<String, Long> countPerCategory(List<Product> products) {
        
        var filteredCount = products.stream()
                .collect(Collectors.groupingBy(Product::category, Collectors.counting()));

        logger.writeJson(filteredCount, Path.of("src/filteredCount.json"));

        return filteredCount;
    }

    public static List<Product> getTopNProducts(List<Product> products, int n) {
        
        var filteredTopN = products.stream()
                .sorted(Comparator.comparingDouble(Product::price).reversed())
                .limit(n)
                .toList();

        logger.writeJson(filteredTopN, Path.of("src/filteredTopN.json"));

        return filteredTopN;
    }

    public static String formatProduct(Product p) {
        return String.format("%s - %.2f - %s - In stock: %b",
                p.productName(), p.price(), p.category(), p.inStock());
    }
    
}
