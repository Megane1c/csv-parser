package parser.util;

import java.io.IOException;
import java.nio.file.*;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for logging console output to a file.
 * 
 * Provides methods to print messages to the console and simultaneously
 * append them to a specified log file.
 * 
 * Example usage:
 * <pre>
 *   FileLogger logger = new FileLogger(Path.of("output.txt"));
 *   logger.printadd("Logging a message");
 * </pre>
 * 
 * @author Angelito Deocadiz
 * @version 1.0
 */

public class FileLogger {
    private final Path logFile;
    private static final ObjectMapper mapper = new ObjectMapper();

    public FileLogger(Path logFile) {
        this.logFile = logFile;
    }

    // Print and append
    public void printadd(String line) {
        System.out.println(line);
        try {
            Files.writeString(logFile, line + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }

    public void writeJson(Object data, Path jsonFile) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile.toFile(), data);
        } catch (IOException e) {
            printadd("Failed to write JSON output: " + e.getMessage());
        }
    }
}
