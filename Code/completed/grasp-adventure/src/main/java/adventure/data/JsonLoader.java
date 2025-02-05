package adventure.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Collections;


@SuppressWarnings("CallToPrintStackTrace")
public class JsonLoader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Map<String, Object>> loadData(String fileName) {
        try (InputStream inputStream = FileFinder.find(fileName).toFile().toURI().toURL().openStream()) {
            return parseJson(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static List<Map<String, Object>> loadDataFromResource(String fileName) {
        try (InputStream inputStream = JsonLoader.class.getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
            return parseJson(inputStream);
        } catch (IOException e2) {
            throw new RuntimeException("Error loading JSON file: " + fileName, e2);
        }
    }

    public static List<Map<String, Object>> parseJson(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static List<Map<String, Object>> parseJson(InputStream inputStream) {
        try {
            return objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

