package parser.util;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * CSV parser utility class.
 * 
 * Example usage:
 * <pre>
 *   ParseCsv parser = new ParseCsv();
 *   List<Product> products = parser.parseAs(filePath, true, Product::fromCsvRow);
 * </pre>
 * 
 * @author Angelito Deocadiz
 * @version 1.0
 */

public class ParseCsv {

    private final char delimiter;

    // Default value
    public ParseCsv() {
        this(',');
    }

    public ParseCsv(char delimiter) {
        this.delimiter = delimiter;
    }

    private List<String[]> parse(Path filePath, boolean skipHeader) throws IOException {
        List<String[]> rows = new ArrayList<>();

        // Count header columns
        try (var header = Files.lines(filePath)) {
            String headerLine = header.findFirst()
                .orElseThrow(() -> new IOException("Empty file"));
            String[] headers = parseLine(headerLine);
            final int expectedColumns = headers.length;

            // Read line
            try (var lines = Files.lines(filePath)) {
                var stream = skipHeader ? lines.skip(1) : lines;
                final AtomicInteger lineNum = new AtomicInteger(skipHeader ? 2 : 1);

                stream.filter(line -> !line.trim().isEmpty())
                    .forEach(line -> {
                        int currentLine = lineNum.get();
                        String[] parsed = parseLine(line);
                        String productName = parsed[0].trim();
                        double price = Double.parseDouble(parsed[1].trim());
                        String category = parsed[2].trim();

                        if (parsed.length != expectedColumns) {
                            throw new IllegalArgumentException(
                                "Line " + currentLine + " has " + parsed.length + " columns but expected " + expectedColumns);
                        }

                        if (price < 0) {
                            throw new IllegalArgumentException("Line " + currentLine + ": Price cannot be negative");
                            }

                        if (productName == null || productName.isBlank()) {
                            throw new IllegalArgumentException("Line " + currentLine + ": Product name cannot be null or blank");
                        }

                        if (category == null || category.isBlank()) {
                            throw new IllegalArgumentException("Line " + currentLine + ": Category cannot be null or blank");
                        }
                        
                        rows.add(parsed);
                        lineNum.incrementAndGet();
                    });

            }
        }

        return rows;
    }

    private String[] parseLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        // Iterate through the line
        for (int i = 0; i < line.length(); i++) {
            char currChar = line.charAt(i);
            
            if (currChar == delimiter) {
                result.add(current.toString().trim());
                current.setLength(0);
            } else {
                current.append(currChar);
            }
        }

        result.add(current.toString().trim());
        return result.toArray(new String[0]);
    }

    /**
     * Maps CSV rows -> typed objects
     */
    public <T> List<T> parseAs(Path filePath, boolean skipHeader, Function<String[], T> mapper) throws IOException {
        List<String[]> csvRow = parse(filePath, skipHeader);
        List<T> result = new ArrayList<>();
        for (String[] row : csvRow) {
            result.add(mapper.apply(row));
        }
        return result;
    }
}
