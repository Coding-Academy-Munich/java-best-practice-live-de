package adventure.data;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonLoaderTest {
    final static String simpleJsonFile = """
        [
            {
                "name": "Room 1",
                "description": "A small room",
                "connections": {"north": "Room 2"}
            },
            {
                "name": "Room 2",
                "description": "A large room",
                "connections": {"south": "Room 1"}
            }
        ]
        """;

    private static void assertSimpleJsonWasCorrectlyParsed(List<Map<String, Object>> result) {
        assertNotNull(result);
        assertEquals(2, result.size());

        Map<String, Object> room1 = result.get(0);
        assertEquals("Room 1", room1.get("name"));
        assertEquals("A small room", room1.get("description"));
        assertEquals(Map.of("north", "Room 2"), room1.get("connections"));

        Map<String, Object> room2 = result.get(1);
        assertEquals("Room 2", room2.get("name"));
        assertEquals("A large room", room2.get("description"));
        assertEquals(Map.of("south", "Room 1"), room2.get("connections"));
    }

    @Test
    void parseSimpleJson() {
        List<Map<String, Object>> result = JsonLoader.parseJson(simpleJsonFile);

        assertSimpleJsonWasCorrectlyParsed(result);
    }

    @Test
    void parseSimpleJsonFromInputStream() {
        InputStream inputStream = new ByteArrayInputStream(simpleJsonFile.getBytes());
        List<Map<String, Object>> result = JsonLoader.parseJson(inputStream);

        assertSimpleJsonWasCorrectlyParsed(result);
    }

    @Test
    void parseEmptyJson() {
        List<Map<String, Object>> result = JsonLoader.parseJson("[]");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void parseInvalidJson() {
        // Don't spam notifications about caught exceptions to the terminal.
        System.setErr(new PrintStream(new ByteArrayOutputStream()));
        List<Map<String, Object>> result = JsonLoader.parseJson("invalid json");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
