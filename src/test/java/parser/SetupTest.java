package parser;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import parser.util.*;

public abstract class SetupTest {

    @BeforeAll
    static void setupLogger() {
        Processor.initLogger(Path.of("src/output.txt"));
    }

    @AfterAll
    static void cleanUpFiles() throws IOException {
        // Delete the log file
        Path logFile = Path.of("src/output.txt");
        Files.deleteIfExists(logFile);

        // Delete all .json files
        Path srcDir = Path.of("src");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(srcDir, "*.json")) {
            for (Path entry : stream) {
                Files.deleteIfExists(entry);
            }
        }
    }


}
