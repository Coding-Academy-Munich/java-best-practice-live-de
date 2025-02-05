package adventure_sk.game;

import adventure_sk.data.JsonLoader;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Map<String, Object>> simpleLocationsData = JsonLoader.loadData(
                "dungeon-locations.json");
        for (Map<String, Object> simpleLocation : simpleLocationsData) {
            System.out.println(simpleLocation);
        }
    }
}
