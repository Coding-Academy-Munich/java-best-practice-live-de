// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Adventure: Version 1</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// Wie fangen wir an?

// %% [markdown]
//
// ## Niedrige Repräsentationslücke (Low Representational Gap)
//
// - Idee: Konzepte aus der Domäne in Code übertragen
// - Implementieren Sie ein Szenario aus einem Use Case
// - Nehmen Sie die Domänen-Konzepte als Kandidaten für die ersten Klassen her

// %% [markdown]
//
// - Use Case: "Spiel initialisieren"
// - Haupterfolgsszenario ohne Laden eines Spiels

// %% [markdown]
//
// ## Domänenmodell
//
// Hier ist noch einmal der relevante Teil des Domänenmodells:

// %% [markdown]
// <img src="img/adv-domain-03-small.png"
//      style="display:block;margin:auto;width:80%"/>
// %% [markdown]
//
// ## Statisches Designmodell

// %% [markdown]
// <img src="img/adv-world-cd-01.png"
//      style="display:block;margin:auto;width:50%"/>

// %% [markdown]
//
// ## Implementierung
//
// - Implementierung: `Code/completed/grasp-adventure/src/main/java/adventure/v1`
// - Starter-Kit: `Code/starter-kits/grasp-adventure-sk/`

// %%
import java.util.Objects;

// %%
public class LocationV0 {
    private final String name;
    private final String description;

    public LocationV0(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationV0 location = (LocationV0) o;
        return Objects.equals(name, location.name) &&
               Objects.equals(description, location.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}

// %% [markdown]
//
// ## Konstruktion von Location Instanzen
//
// - [Einfache Orte](./simple-locations.json)
// - [Dungeon](./dungeon-locations.json)

// %% [markdown]
//
// - Es kann sein, dass der Pfad unseres Notebooks
//   auf ein Elternverzeichnis des gesuchten Verzeichnisses zeigt
// - Deshalb suchen wir in allen Unterverzeichnissen nach der JSON-Datei:

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
Path simpleLocationsPath = FileFinder.find("simple-locations.json");

// %%
simpleLocationsPath.getFileName()

// %%
simpleLocationsPath.toAbsolutePath()

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
for (var room : simpleLocationsData) {
    System.out.println(room);
}

// %% [markdown]
//
// ### Erzeugen von Location Instanzen aus JSON Daten
//
// - Wir können eine statische Methode in der `Location` Klasse implementieren,
//   die eine `Location` Instanz aus einer Map erzeugt
// - Eine solche Methode nennt man eine Factory-Methode

// %%
public class Location {
    private final String name;
    private final String description;

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static Location fromDescription(Map<String, Object> description) {
        String name = (String) description.get("name");
        String desc = (String) description.getOrDefault("description", "");
        return new Location(name, desc);
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(name, location.name) &&
               Objects.equals(description, location.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}

// %%
Location.fromDescription(simpleLocationsData.get(0));

// %%
import java.util.stream.Collectors;

// %%
List<Location> locations = simpleLocationsData.stream()
    .map(Location::fromDescription)
    .collect(Collectors.toList());

// %%
locations

// %%
Map<String, Location> locationMap = simpleLocationsData.stream()
    .map(Location::fromDescription)
    .collect(Collectors.toMap(Location::getName, location -> location));

// %%
locationMap

// %% [markdown]
//
// ## Implementierung der World Klasse
//
// - Beliebige Anzahl von `Location`-Instanzen
// - Zugriff auf `Location`-Instanzen über Namen
// - Speicherung des initialen Ortsnamens

// %%
import java.util.HashMap;
import java.util.Map;

// %%
public class World {
    private final Map<String, Location> locations;
    private final String initialLocationName;

    public World(Map<String, Location> locations, String initialLocationName) {
        this.locations = new HashMap<>(locations);
        this.initialLocationName = initialLocationName;
    }

    public Map<String, Location> locations() {
        return locations;
    }

    public String initialLocationName() {
        return initialLocationName;
    }

    @Override
    public String toString() {
        return "World{" +
                "locations=" + locations +
                ", initialLocationName='" + initialLocationName + '\'' +
                '}';
    }
}

// %%
World myWorld = new World(locationMap, "Room 1");

// %%
myWorld

