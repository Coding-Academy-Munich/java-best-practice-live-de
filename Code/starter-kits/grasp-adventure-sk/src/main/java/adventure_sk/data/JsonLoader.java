package adventure_sk.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Collections;

public class JsonLoader {
    public static List<Map<String, Object>> loadData(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Object>> simpleLocations = objectMapper.readValue(
                    FileFinder.find(fileName).toFile(),
                    new TypeReference<List<Map<String, Object>>>() {}
            );
            return simpleLocations;
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            return Collections.emptyList(); // Return an empty list in case of an exception
        }
    }
}
