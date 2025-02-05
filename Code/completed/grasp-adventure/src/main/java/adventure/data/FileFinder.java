package adventure.data;

import java.nio.file.*;
import java.util.stream.Stream;

public class FileFinder {
    public static Path find(String name) throws java.io.IOException {
        Path startPath = Path.of("").toAbsolutePath();
        try (Stream<Path> paths = Files.walk(startPath)) {
            return paths
                    .filter(p -> p.getFileName().toString().equals(name))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(String.format("File '%s' not found in '%s'.", name, startPath)));
        }
    }
}
