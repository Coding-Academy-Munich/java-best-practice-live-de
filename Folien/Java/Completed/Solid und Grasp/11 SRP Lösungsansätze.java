// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>SRP: Lösungsansätze</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ## Ein Änderungsgrund?
//
// <img src="img/book_01.png"
//      style="display:block;margin:auto;width:35%"/>
//

// %% [markdown]
//
// ## Verletzung des SRPs
//
// <img src="img/book_02.png"
//      style="display:block;margin:auto;width:60%"/>

// %%
import java.util.*;

// %%
public class Book {
    public Book(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public void print() {
        // Lots of code that handles the printer
        System.out.println("Printing " + title + " to printer.");
    }

    public void save() {
        // Lots of code that handles the database
        System.out.println("Saving " + title + " to database.");
    }

    private String title;
    private String author;
    private int pages;
}

// %%
Book book = new Book("Clean Code", "Robert C. Martin", 464);

// %%
book.print();

// %%
book.save();

// %% [markdown]
//
// ## Auflösung der SRP-Verletzung (Version 1a)
//
// Vorschlag von Robert C. Martin in Clean Architecture:
//
// <img src="img/book_resolution_1a_srp.png"
//      style="display:block;margin:auto;width:40%"/>

// %%
public class BookV1 {
    public BookV1(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPages() { return pages; }

    private String title;
    private String author;
    private int pages;
}

// %%
public class BookPrinterV1a {
    public BookPrinterV1a(BookV1 book) {
        this.book = book;
    }

    public void print() {
        // Lots of code that handles the printer
        System.out.println("Printing " + book.getTitle() + " to printer.");
    }

    private BookV1 book;
}

// %%
public class BookDatabaseV1a {
    public BookDatabaseV1a(BookV1 book) {
        this.book = book;
    }

    public void save() {
        // Lots of code that handles the database
        System.out.println("Saving " + book.getTitle() + " to database.");
    }

    private BookV1 book;
}

// %%
BookV1 bookV1 = new BookV1("Clean Code", "Robert C. Martin", 464);

// %%
BookPrinterV1a bookPrinterV1a = new BookPrinterV1a(bookV1);

// %%
bookPrinterV1a.print();

// %%
BookDatabaseV1a bookDatabaseV1a = new BookDatabaseV1a(bookV1);

// %%
bookDatabaseV1a.save();

// %% [markdown]
//
// ## Auflösung der SRP-Verletzung (Version 1a mit Fassade)
//
// <img src="img/book_resolution_1a_srp_facade.png"
//      style="display:block;margin:auto;width:50%"/>

// %%
public class BookPrinterFacadeV1a {
    public BookPrinterFacadeV1a(BookV1 book) {
        this.bookPrinter = new BookPrinterV1a(book);
        this.bookDatabase = new BookDatabaseV1a(book);
    }

    public void print() {
        bookPrinter.print();
    }

    public void save() {
        bookDatabase.save();
    }

    private BookPrinterV1a bookPrinter;
    private BookDatabaseV1a bookDatabase;
}

// %%
BookPrinterFacadeV1a bookPrinterFacadeV1a = new BookPrinterFacadeV1a(bookV1);
// %%
bookPrinterFacadeV1a.print();

// %%
bookPrinterFacadeV1a.save();


// %% [markdown]
//
// ## Auflösung der SRP-Verletzung (Version 1b)
//
// <img src="img/book_resolution_1_srp.png"
//      style="display:block;margin:auto;width:50%"/>

// %%
public class BookPrinterV1b {
    public void print(BookV1 book) {
        // Lots of code that handles the printer
        System.out.println("Printing " + book.getTitle() + " to printer.");
    }
}

// %%
public class BookDatabaseV1b {
    public void save(BookV1 book) {
        // Lots of code that handles the database
        System.out.println("Saving " + book.getTitle() + " to database.");
    }
}

// %%
BookV1 bookV1 = new BookV1("Clean Code", "Robert C. Martin", 464);

// %%
BookPrinterV1b bookPrinterV1b = new BookPrinterV1b();

// %%
bookPrinterV1b.print(bookV1);

// %%
BookDatabaseV1b bookDatabaseV1b = new BookDatabaseV1b();

// %%
bookDatabaseV1b.save(bookV1);

// %% [markdown]
//
// ## Auflösung der SRP-Verletzung (Version 1b mit Facade)
//
// <img src="img/book_resolution_1_srp_facade.png"
//      style="display:block;margin:auto;width:50%"/>

// %%
public class BookFacadeV1b {
    public BookFacadeV1b(BookV1 book) {
        this.book = book;
    }

    public void print() {
        bookPrinter.print(book);
    }

    public void save() {
        bookDatabase.save(book);
    }

    private BookV1 book;
    private BookPrinterV1b bookPrinter = new BookPrinterV1b();
    private BookDatabaseV1b bookDatabase = new BookDatabaseV1b();
}

// %%
BookFacadeV1b bookFacadeV1 = new BookFacadeV1b(bookV1);

// %%
bookFacadeV1.print();

// %%
bookFacadeV1.save();

// %% [markdown]
//
// ## Auflösung der SRP-Verletzung (Version 2)
//
// <img src="img/book_resolution_2_srp.png"
//      style="display:block;margin:auto;width:60%"/>

// %%
import java.util.*;

// %%
// Forward reference...
public class BookV2 {
    public String getTitle() { return title; }
}

// %%
public class BookPrinterV2 {
    public void print(BookV2 book) {
        // Lots of code that handles the printer
        System.out.println("Printing " + book.getTitle() + " to printer.");
    }
}

// %%
public class BookDatabaseV2 {
    public void save(BookV2 book) {
        // Lots of code that handles the database
        System.out.println("Saving " + book.getTitle() + " to database.");
    }
};

// %%
public class BookV2 {
    public BookV2(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPages() { return pages; }

    public void print() { bookPrinter.print(this); }
    public void save() { bookDatabase.save(this); }

    private String title;
    private String author;
    private int pages;

    private BookPrinterV2 bookPrinter = new BookPrinterV2();
    private BookDatabaseV2 bookDatabase = new BookDatabaseV2();
}

// %%
BookV2 bookV2 = new BookV2("Clean Code", "Robert C. Martin", 464);

// %%
bookV2.print();

// %%
bookV2.save();

// %% [markdown]
//
// ## Vergleich
//
// <div>
// <img src="img/book_resolution_1a_srp.png"
//      style="float:left;padding:5px;width:40%"/>
// <img src="img/book_resolution_2_srp.png"
//      style="float:right;padding:5px;width:50%"/>
// </div>

// %% [markdown]
//
// ## Workshop: Wetter-App
//
// Sie arbeiten an einer vielseitigen Wetteranwendung namens WeatherWise. Die
// WeatherWise App bietet ihren Benutzern aktuelle Wetterinformationen aus
// verschiedenen Online-Plattformen. Über die Anzeige der aktuellen Bedingungen
// hinaus ermöglicht die App den Benutzern, die Vorhersage in verschiedenen
// visuellen Formaten anzuzeigen, und sie protokolliert Fehler für alle Probleme
// während des Datenabrufs oder der Analyse.
//
// Während WeatherWise für seine Funktionen gut ankommt, sieht sich das
// Entwicklungsteam mit Herausforderungen bei der Wartung und Erweiterung der
// Anwendung konfrontiert. Die Entwickler haben festgestellt, dass die
// Kernklasse, `Weather`, zunehmend komplex wird. Sie behandelt alles von der
// Datenbeschaffung bis zur Datendarstellung. Diese Komplexität erschwert die
// Einführung neuer Funktionen, ohne dass dabei die Gefahr besteht, Fehler
// einzuführen.
//
// Ihre Aufgabe: Refaktorisieren Sie die Klasse `Weather`, indem Sie
// sicherstellen, dass jede Klasse im System dem Single Responsibility Principle
// entspricht. Damit legen Sie die Grundlage für eine wartbarere und
// skalierbarere Anwendung.


// %% [markdown]
//
// ### Klassendiagramm der Wetter-App
//
// <img src="img/weather_app_class.png"
//      style="display:block;margin:auto;width:40%"/>

// %% [markdown]
//
// ### RunWeatherApp() Sequenzendiagramm
//
// <img src="img/weather_app_sequence.png"
//      style="display:block;margin:auto;width:30%"/>

// %%
import java.util.ArrayList;
import java.util.List;

// %%
public class Weather {
    public void fetchDataFromSource() {
        // Simulating fetching data from some source
        rawData = "Sunny, 25°C";
        System.out.println("Data fetched from source.");
    }

    public void parseData() {
        // Simulate data parsing
        if (rawData.isEmpty()) {
            logError("No data available");
            return;
        }
        data = List.of(10.0, 12.0, 8.0, 15.0, 20.0, 22.0, 25.0);
        System.out.println("Data parsed: " + formatData());
    }

    public void displayInFormatA() {
        // Simulating one display format
        if (data.isEmpty()) {
            logError("No data available");
            return;
        }
        System.out.println("Format A: " + formatData());
    }

    public void displayInFormatB() {
        // Simulating another display format
        if (data.isEmpty()) {
            logError("No data available");
            return;
        }
        System.out.println("Format B: === " + formatData() + " ===");
    }

    public void logError(String errorMsg) {
        // Simulating error logging
        System.out.println("Error: " + errorMsg);
    }

    private String formatData() {
        StringBuilder sb = new StringBuilder();
        for (double d : data) {
            sb.append(d).append(", ");
        }
        return sb.toString();
    }

    private String rawData = "";
    private List<Double> data = new ArrayList<>();
}

// %%
public void runWeatherApp(boolean introduceError) {
        Weather w = new Weather();
        w.fetchDataFromSource();
        if (!introduceError) {
            w.parseData();
        }
        w.displayInFormatA();
        w.displayInFormatB();
}

// %%
runWeatherApp(false);

// %%
runWeatherApp(true);

// %% [markdown]
//
// ### Implementierung nach Auflösung der SRP-Verletzungen:

// %% [markdown]
//
// ### Klassendiagramm der Wetter-App
//
// <img src="img/weather_app_class_srp.png"
//      style="display:block;margin:auto;width:80%"/>


// %% [markdown]
//
// ### RunWeatherApp() Sequenzendiagramm
//
// <img src="img/weather_app_sequence_srp.png"
//      style="display:block;margin:auto;width:75%"/>

// %%
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// %%
public class WeatherErrorLogger {
    // Assume that the logger has internal state and this cannot be static.
    public void logError(String errorMsg) {
        System.out.println("Error: " + errorMsg);
    }
}

// %%
public class WeatherData {
    public WeatherData(List<Double> data) {
        this.data = data;
    }

    public WeatherData() {
        this.data = new ArrayList<>();
    }

    public List<Double> getData() {
        return data;
    }

    public String formattedData() {
        if (data.isEmpty()) {
            throw new RuntimeException("No data available!");
        }
        return data.stream()
                   .map(Object::toString)
                   .collect(Collectors.joining(", "));
    }

    private List<Double> data;
}

// %%
class WeatherDataSource {
    public WeatherDataSource(WeatherErrorLogger errorLogger) {
        this.errorLogger = errorLogger;
    }

    public void fetchData() {
        // Simulating fetching data from some source
        rawData = "Sunny, 25°C";
        hasData = true;
        System.out.println("Data fetched from source.");
    }

    public String getRawData() {
        if (hasData) {
            return rawData;
        } else {
            errorLogger.logError("WeatherDataSource has no data!");
            return "";
        }
    }

    private String rawData = "";
    private boolean hasData = false;
    private final WeatherErrorLogger errorLogger;
}

// %%
class WeatherDataParser {
    public WeatherDataParser(WeatherErrorLogger errorLogger) {
        this.errorLogger = errorLogger;
    }

    public WeatherData parse(String rawData) {
        // Simulate data parsing
        if (rawData.isEmpty()) {
            errorLogger.logError("No data available for parsing!");
            return new WeatherData();
        }
        List<Double> data = List.of(10.0, 12.0, 8.0, 15.0, 20.0, 22.0, 25.0);
        System.out.println("Data parsed.");
        return new WeatherData(data);
    }

    private WeatherErrorLogger errorLogger;
}

// %%
class WeatherDisplay {
    public WeatherDisplay(WeatherErrorLogger errorLogger) {
        this.errorLogger = errorLogger;
    }

    public void displayInFormatA(WeatherData data) {
        try {
            String formattedData = data.formattedData();
            System.out.println("Format A: " + formattedData);
        } catch (RuntimeException e) {
            errorLogger.logError("In displayInFormatA: " + e.getMessage());
        }
    }

    public void displayInFormatB(WeatherData data) {
        try {
            String formattedData = data.formattedData();
            System.out.println("Format B: === " + formattedData + " ===");
        } catch (RuntimeException e) {
            errorLogger.logError("In displayInFormatB: " + e.getMessage());
        }
    }

    private WeatherErrorLogger errorLogger;
}

// %%
public void runWeatherAppSrp(boolean introduceError) {
        WeatherErrorLogger errorLogger = new WeatherErrorLogger();
        WeatherDataParser parser = new WeatherDataParser(errorLogger);

        WeatherDataSource dataSource = new WeatherDataSource(errorLogger);
        if (!introduceError) {
            dataSource.fetchData();
        }

        WeatherData weatherData = parser.parse(dataSource.getRawData());

        WeatherDisplay weatherDisplay = new WeatherDisplay(errorLogger);
        weatherDisplay.displayInFormatA(weatherData);
        weatherDisplay.displayInFormatB(weatherData);
}

// %%
runWeatherAppSrp(false);

// %%
runWeatherAppSrp(true);

// %% [markdown]
//
// - Mit diesem refaktorierten Code erfüllt jede Klasse das Single
//   Responsibility Prinzip.
// - Jede Klasse behandelt eine eigene Verantwortlichkeit: Datenbeschaffung,
//   Datenanalyse, Datenanzeige und Fehlerprotokollierung.
// - Diese Trennung ermöglicht eine einfachere Wartung, Tests und potenzielle
//   zukünftige Erweiterungen.
