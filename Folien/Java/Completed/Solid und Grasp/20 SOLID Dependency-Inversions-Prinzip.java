// -*- coding: utf-8 -*-
// %% [markdown]
// <!--
// clang-format off
// -->
//
// <div style="text-align:center; font-size:200%;">
//  <b>SOLID: Dependency-Inversions-Prinzip</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// # Abhängigkeiten
//
// - Wir müssen zwei Arten von Abhängigkeiten unterscheiden:
//   - Daten- und Kontrollfluss
//   - Quellcode-Abhängigkeiten
// - Daten- und Kontrollfluss-Abhängigkeiten sind inhärent in der Logik
// - Quellcode-Abhängigkeiten können wir durch die Architektur kontrollieren

// %% [markdown]
//
// ## Beispiel
//
// - Modul `MyModule.java` schreibt Daten in eine Datenbank
// - Datenfluss: von `MyModule.java` zur Datenbank
// - Quellcode-Abhängigkeit: `MyModule.java` hängt von der Datenbank (`Database.java`) ab

// %% [markdown]
//
// Modul `MyModule.java`:

// %%
import java.util.ArrayList;
import java.util.List;

// %%
public class Database {
    public List<String> Execute(String query, String data) {
        // Simulate database interaction
        List<String> result = new ArrayList<>();
        if (query.startsWith("SELECT")) {
            result.add("Data from the database");
        } else if (query.startsWith("INSERT")) {
            System.out.println("Inserted: " + data);
        }
        return result;
    }
}

// %%
public class MyDomainClassV1 {
    private Database db = new Database();

    public void performWork(String data) {
        data = "Processed: " + data;
        db.Execute("INSERT INTO my_table VALUES (?)", data);
    }

    public List<String> retrieveResult() {
        return db.Execute("SELECT * FROM my_table", "");
    }
}

// %%
MyDomainClassV1 myDomainObjectV1 = new MyDomainClassV1();

// %%
myDomainObjectV1.performWork("Hello World");

// %%
System.out.println(myDomainObjectV1.retrieveResult());

// %% [markdown]
//
// Die Quellcode-Abhängigkeit geht in die gleiche Richtung wie der Datenfluss:
//
// `MyModule.java` ⟹ `Database.java`
//
// <img src="img/db-example-01.png"
//      style="display:block;margin:auto;width:75%"/>

// %% [markdown]
//
// Wir würden derartige Abhängigkeiten im Kern unsere Anwendung gerne vermeiden
//
// - Einfacher zu testen
// - Einfacher zu erweitern
// - Einfacher externe Abhängigkeiten zu ersetzen
// - Einfacher den Code zu verstehen
// - ...

// %% [markdown]
//
// <img src="img/db-example-02.png"
//      style="display:block;margin:auto;width:75%"/>

// %% [markdown]
//
// - Modul `MyModule.java`:
//   - Keine Abhängigkeit mehr zu `Database.java`
//   - Adapter Pattern

// %%
public interface AbstractDatabaseAdapter {
    void saveObject(String data);
    List<String> retrieveData();
}

// %%
public class MyDomainClassV2 {
    private AbstractDatabaseAdapter db;

    public MyDomainClassV2(AbstractDatabaseAdapter db) {
        this.db = db;
    }

    public void performWork(String data) {
        data = "Processed: " + data;
        db.saveObject(data);
    }

    public List<String> retrieveResult() {
        return db.retrieveData();
    }
}

// %% [markdown]
//
// - Modul `ConcreteDatabaseAdapter.java`:
//   - Implementiert `AbstractDatabaseAdapter` für `Database.java`
//   - Hängt von `Database.java` ab

// %%
public class ConcreteDatabaseAdapter implements AbstractDatabaseAdapter {
    private Database db = new Database();

    @Override
    public void saveObject(String data) {
        db.Execute("INSERT INTO my_table VALUES (?)", data);
    }

    @Override
    public List<String> retrieveData() {
        return db.Execute("SELECT * FROM my_table", "");
    }
}

// %% [markdown]
//
// - Modul `Main.java`:

// %%
AbstractDatabaseAdapter dbAdapter = new ConcreteDatabaseAdapter();

// %%
MyDomainClassV2 myDomainObjectV2 = new MyDomainClassV2(dbAdapter);

// %%
myDomainObjectV2.performWork("Hello World");

// %%
myDomainObjectV2.retrieveResult();

// %% [markdown]
//
// # SOLID: Dependency Inversion Prinzip
//
// - Die Kernfunktionalität eines Systems hängt nicht von seiner Umgebung ab
//   - **Konkrete Artefakte hängen von Abstraktionen ab** (nicht umgekehrt)
//   - **Instabile Artefakte hängen von stabilen Artefakten ab** (nicht umgekehrt)
//   - **Äußere Schichten** der Architektur **hängen von inneren Schichten ab**
//     (nicht umgekehrt)
//   - Klassen/Module hängen von Abstraktionen (z. B. Schnittstellen) ab,
//     nicht von anderen Klassen/Modulen
// - Abhängigkeitsinversion (Dependency Inversion) erreicht dies durch die Einführung
//   von Schnittstellen, die "die Abhängigkeiten umkehren"

// %% [markdown]
//
// ### Vorher
// <img src="img/dependency-01.png"
//      style="display:block;margin:auto;width:75%"/>
//
// ### Nachher
// <img src="img/dependency-02.png"
//      style="display:block;margin:auto;width:75%"/>

// %% [markdown]
//
// <img src="img/dip-01.png"
//      style="display:block;margin:auto;width:95%"/>

// %% [markdown]
//
// <img src="img/dip-02.png"
//      style="display:block;margin:auto;width:95%"/>

// %% [markdown]
//
// <img src="img/dip-03.png"
//      style="display:block;margin:auto;width:95%"/>
// %% [markdown]
//
// ## Workshop: Wetterbericht
//
// Wir haben ein Programm geschrieben, das einen Wetterbericht von einem Server
// abruft. Leider ist dabei die Abhängigkeit zum Server vom Typ
// `LegacyWeatherServer` hart kodiert. Aufgrund der Popularität des Programms
// müssen wir jedoch mit einem neuen Typ von Server `NewWeatherServer`
// kompatibel werden. Dazu refaktorisieren wir den Code nach dem
// Dependency-Inversion-Prinzip und Implementieren dann einen zusätzlichen
// Adapter für `NewWeatherServer`.
//
// - Führen Sie eine Abstraktion ein, um die Abhängigkeit umzukehren
// - Schreiben Sie eine konkrete Implementierung der Abstraktion für
//   `LegacyWeatherServer`
// - Testen Sie die Implementierung
// - Implementieren Sie einen Adapter für `NewWeatherServer`
// - Testen Sie den Adapter

// %%
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// %%
public class WeatherReport {
    private final double temperature;
    private final double humidity;

    public WeatherReport(double temperature, double humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }
}

// %%
public class LegacyWeatherServer {
    public WeatherReport getWeatherReport() {
        Random random = ThreadLocalRandom.current();
        return new WeatherReport(20.0 + 10.0 * random.nextDouble(), 0.5 + 0.5 * random.nextDouble());
    }
}

// %%
public class NewWeatherServer {
    public WeatherReport fetchWeatherData() {
        Random random = ThreadLocalRandom.current();
        double temperature = 10.0 + 20.0 * random.nextDouble();
        double humidity = 0.7 + 0.4 * random.nextDouble();
        return new WeatherReport(temperature, humidity);
    }
}

// %%
public class WeatherReporter {
    private final LegacyWeatherServer server;

    public WeatherReporter(LegacyWeatherServer server) {
        this.server = server;
    }

    public String report() {
        WeatherReport report = server.getWeatherReport();
        if (report.getTemperature() > 25.0) {
            return "It's hot";
        } else {
            return "It's not hot";
        }
    }
}

// %%
LegacyWeatherServer server = new LegacyWeatherServer();
WeatherReporter reporter = new WeatherReporter(server);

// %%
System.out.println(reporter.report());

// %%
public interface WeatherDataSource {
    WeatherReport getWeatherReport();
}

// %%
public class DiWeatherReporter {
    private final WeatherDataSource dataSource;

    public DiWeatherReporter(WeatherDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String report() {
        WeatherReport report = dataSource.getWeatherReport();
        if (report.getTemperature() > 25.0) {
            return "It's hot";
        } else {
            return "It's not hot";
        }
    }
}

// %%
public class LegacyWeatherServerAdapter implements WeatherDataSource {
    private final LegacyWeatherServer server;

    public LegacyWeatherServerAdapter(LegacyWeatherServer server) {
        this.server = server;
    }

    @Override
    public WeatherReport getWeatherReport() {
        return server.getWeatherReport();
    }
}

// %%
DiWeatherReporter reporter = new DiWeatherReporter(new LegacyWeatherServerAdapter(server));

// %%
System.out.println(reporter.report());

// %%
public class NewWeatherServerAdapter implements WeatherDataSource {
    private final NewWeatherServer server;

    public NewWeatherServerAdapter(NewWeatherServer server) {
        this.server = server;
    }

    @Override
    public WeatherReport getWeatherReport() {
        return server.fetchWeatherData();
    }
}

// %%
NewWeatherServer newServer = new NewWeatherServer();
DiWeatherReporter newReporter = new DiWeatherReporter(new NewWeatherServerAdapter(newServer));

// %%
System.out.println(newReporter.report());
