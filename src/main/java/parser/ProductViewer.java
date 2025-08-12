package parser;

import java.nio.file.Path;
import java.util.*;

import parser.util.*;
import parser.model.*;

/**
 * Main class demonstrating CSV parsing and product filtering.
 * 
 * This class reads a CSV file containing product data, parses it into Product objects,
 * and displays filtered or processed product information.
 *
 * Usage example:
 * 
 *   java parser.ProductViewer <minPrice> <topN>
 * 
 * @author Angelito Deocadiz
 * @version 1.0
 */

public class ProductViewer {

    public static void main(String[] args) {
        try {

            FilterArgs params = ParseArgs.parseArgs(args);
            double minPrice = params.minPrice();
            int topN = params.topN();

            Path csv = Path.of("src/sample.csv");
            Path log = Path.of("src/output.txt");

            Processor viewer = new Processor(csv, log);
            Processor.initLogger(log);

            ParseCsv parser = new ParseCsv();

            List<Product> products = parser.parseAs(viewer.csvFile, true, row ->
                new Product(
                    row[0],
                    Double.parseDouble(row[1]),
                    row[2],
                    Boolean.parseBoolean(row[3])
                )
            );


            var filteredStock = Processor.filterProducts(products, minPrice);
            Processor.getLogger().printadd(String.format("===========All products that are in stock and cost more than %.2f===========",
                minPrice));
            filteredStock.forEach(p -> Processor.getLogger().printadd(Processor.formatProduct(p)));

            var filteredCategory = Processor.countPerCategory(products);
            Processor.getLogger().printadd("======================Number of products per category=====================");
            filteredCategory.forEach((cat, count) -> Processor.getLogger().printadd(cat + ": " + count));

            var filteredTopN = Processor.getTopNProducts(products, topN);
            Processor.getLogger().printadd(String.format("========Sort by price and print the top %d most expensive products==========",
                topN));
            filteredTopN.forEach(p -> Processor.getLogger().printadd(String.format("%s - %.2f - %s - In stock: %b",
                        p.productName(), p.price(), p.category(), p.inStock())));

            System.out.println("============================================================");
            System.out.println("Processing complete. You may also see the logs for details.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
