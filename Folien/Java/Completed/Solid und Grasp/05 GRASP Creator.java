// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>GRASP: Creator</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// - Use Case "Spiel initialisieren"
// - Bisher:
//   - `World`- und `Location`-Klassen
//   - Attribute und Getter
// - Frage:
//   - Wer erzeugt die `Location`-Instanzen?

// %% [markdown]
//
// ## Kandidaten
//
// <div style="float:left;margin:auto;padding:80px 0;width:25%">
// <ul>
// <li> <code>Player</code></li>
// <li> <code>Game</code></li>
// <li> <code>Pawn</code></li>
// <li> <code>Location</code></li>
// <li> <code>World</code></li>
// <li> Eine andere Klasse?</li>
// </ul>
// </div>
// <img src="img/adv-domain-03-small.png"
//      style="float:right;margin:auto;width:70%"/>

// %% [markdown]
//
// ## Das Creator Pattern (GRASP)
//
// ### Frage
//
// - Wer ist verantwortlich für die Erzeugung eines Objekts?
//
// ### Antwort
//
// Klasse `A` bekommt die Verantwortung, ein Objekt der Klasse `B` zu erzeugen,
// wenn eine oder mehrere der folgenden Bedingungen zutreffen:
//
// - `A` enthält `B` (oder ist Eigentümer von `B`)
// - `A` verwaltet `B` (registriert, zeichnet auf)
// - `A` verwendet `B` intensiv
// - `A` hat die initialisierenden Daten, die `B` benötigt

// %% [markdown]
//
// ### Bemerkung
//
// - Factory ist oft eine Alternative zu Creator

// %% [markdown]
//
// ## Creator
//
// <div style="float:left;margin:auto;padding:80px 0;width:25%">
// <ul>
// <li> <strike><code>Player</code></strike></li>
// <li> <strike><code>Game</code></strike></li>
// <li> <code>Pawn</code></li>
// <li> <code>Location</code></li>
// <li> <b><code>World</code></b></li>
// <li> <strike>Eine andere Klasse?</strike></li>
// </ul>
// </div>
// <img src="img/adv-domain-03-small.png"
//      style="float:right;margin:auto;width:70%"/>

// %%
import java.nio.file.*;
import java.util.stream.Stream;

// %%
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

// %%
// %maven com.fasterxml.jackson.core:jackson-databind:2.17.2

// %%
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Collections;

// %%
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
            e.printStackTrace();
            return Collections.emptyList(); // Return an empty list in case of an exception
        }
    }
}

// %%
List<Map<String, Object>> simpleLocationsData = JsonLoader.loadData("simple-locations.json");

// %%
public record Location(String name, String description) {

    public static Location fromData(Map<String, Object> data) {
        String name = (String) data.get("name");
        String desc = (String) data.getOrDefault("description", "");
        return new Location(name, desc);
    }

    @Override
    public String toString() {
        return "Location{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }
}

// %%
import java.util.stream.Collectors;

public record World(Map<String, Location> locations, String initialLocationName) {
    public World(Map<String, Location> locations, String initialLocationName) {
        this.locations = new HashMap<>(locations);
        this.initialLocationName = initialLocationName;
    }

    public static World fromJsonFile(String fileName) {
        List<Map<String, Object>> locationData = JsonLoader.loadData(fileName);
        return fromLocationData(locationData);
    }

    public static World fromLocationData(
            List<Map<String, Object>> locationData) {
        Map<String, Location> locations = locationData.stream().map(Location::fromData).collect(
                Collectors.toMap(Location::name, location -> location));
        String initialLocationName = (String) locationData.get(0).get("name");
        return new World(locations, initialLocationName);
    }

    @Override
    public String toString() {
        String initialLocationLine = "  Initial location name: '" + initialLocationName + '\'' + "\n";
        String locationLines = locations.values().stream().map(location -> "    " + location.toString()).collect(
                Collectors.joining("\n"));
        return "World{\n" + initialLocationLine + "  Locations:\n" + locationLines + '}';
    }
}

// %% [markdown]
//
// - Wir können die `World`-Klasse jetzt verwenden.

// %%
World world = World.fromJsonFile("simple-locations.json");

// %%
System.out.println(world);
