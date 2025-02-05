// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>GRASP: Informations-Experte</b>
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
//   - `World` und `Location` Klassen
//   - `World` erzeugt alle `Location` Objekte
// - Nächster Schritt:
//   - Speichern von Information über die Verbindung zwischen den `Location`
//     Objekten
//   - Hilfreich dazu: Finden von Locations anhand ihres Namens
// - Frage:
//   - Wer findet `Location` Objekte anhand ihres Namens?

// %% [markdown]
//
// ## Kandidaten

// %% [markdown]
// <div style="float:left;margin:auto;padding:80px 0;width:25%">
// <ul>
// <li> <code>Player</code></li>
// <li> <code>Game</code></li>
// <li> <code>Pawn</code></li>
// <li> <code>Location</code></li>
// <li> <code>World</code></li>
// </ul>
// </div>
// <img src="img/adv-domain-03-small.png"
//      style="float:right;margin:auto;width:70%"/>

// %% [markdown]
//
// ## Informations-Experte (engl. "Information Expert", GRASP)
//
// ### Frage
//
// An welche Klasse sollen wir eine Verantwortung delegieren?
//
// ### Antwort
//
// An die Klasse, die die meisten Informationen hat, die für die Erfüllung der
// Verantwortung notwendig sind.

// %% [markdown]
//
// ## Wer ist der Informationsexperte?

// %% [markdown]
// <div style="float:left;margin:auto;padding:80px 0;width:25%">
// <ul>
// <li> <strike><code>Player</code></strike></li>
// <li> <strike><code>Game</code></strike></li>
// <li> <strike><code>Pawn</code></strike></li>
// <li> <strike><code>Location</code></strike></li>
// <li> <b><code>World</code></b></li>
// </ul>
// </div>
// <img src="img/adv-domain-03-small.png"
//      style="float:right;margin:auto;width:70%"/>

// %%
// %maven com.fasterxml.jackson.core:jackson-databind:2.17.2

// %%
// %jars .
// %classpath json-loader-0.1.jar

// %%
import jsonloader.JsonLoader;

// %%
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Collections;

// %%
JsonLoader.loadData("simple-locations.json")

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

// %%
public record World(Map<String, Location> locations, String initialLocationName) {
    public World(Map<String, Location> locations, String initialLocationName) {
        this.locations = new HashMap<>(locations);
        this.initialLocationName = initialLocationName;
    }

    public Location getLocationByName(String name) {
        return locations.get(name);
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

// %%
World world = World.fromJsonFile("simple-locations.json");

// %%
world

// %%
world.getLocationByName("Room 1");

// %%
world.getLocationByName("Room 2");

// %% [markdown]
//
// - `Code/completed/grasp-adventure/src/main/java/adventure/v2` entspricht
//   unserem aktuellen Stand
