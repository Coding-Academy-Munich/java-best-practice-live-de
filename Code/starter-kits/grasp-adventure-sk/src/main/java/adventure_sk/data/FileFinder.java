package adventure_sk.data;

import java.nio.file.*;
import java.util.stream.Stream;

public class FileFinder {
    public static Path find(String name) throws java.io.IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(""))) {
            return paths
                    .filter(p -> p.getFileName().toString().equals(name))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("File not found"));
        }
    }
}
