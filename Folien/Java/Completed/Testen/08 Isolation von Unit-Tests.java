// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Isolation von Unit-Tests</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ## Unit-Test
//
// - Testet einen kleinen Teil des Codes (eine **"Unit"**)
// - Hat kurze Laufzeit
// - *Ist isoliert*

// %% [markdown]
//
// ## Was ist eine "Unit"?
//
// - Ein Verhalten?
//   - *Unit of behavior*
//   - Teil eines Szenarios/Use-Cases/...
//   - Ursprüngliche Intention von Kent Beck
// - Ein Code-Bestandteil?
//   - *Unit of code*
//   - Oft Unit = Klasse
//   - In der Literatur weit verbreitete Ansicht

// %% [markdown]
//
// ## Was bedeutet "isolierter" Test?
//
// - Keine Interaktion zwischen Tests?
//   - Isolierte Testfälle
//   - Klassische Unit-Tests (Detroit School, Kent Beck)
// - Keine Interaktion zwischen getesteter Einheit und dem Rest des Systems?
//   - Abhängigkeiten werden durch einfache Simulationen ersetzt (Test-Doubles)
//   - London School

// %% [markdown]
//
// ## Isolierte Testfälle (Detroit School)
//
// - Jeder Testfall ist unabhängig von den anderen
// - Tests können in beliebiger Reihenfolge ausgeführt werden
// - Tests können parallel ausgeführt werden

// %% [markdown]
//
// ### Gegenbeispiel: Nicht isolierte Testfälle

// %%
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// %%
public static void check(boolean condition) {
    if (!condition) {
        System.out.println("Test failed!");
    } else {
        System.out.println("Success!");
    }
}

// %%
public class TimeUtils {
    public static LocalDateTime globalTime = LocalDateTime.now();

    public static void printTime(String prefix, LocalDateTime time) {
        System.out.println(prefix + ": " + time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}

// %%
TimeUtils.printTime("The time is", LocalDateTime.now());

// %%
class Test1 {
    public static void run() {
        LocalDateTime now = LocalDateTime.now();
        TimeUtils.printTime("Test 1 global_time: ", TimeUtils.globalTime);
        TimeUtils.printTime("Test 1 now:         ", now);
        check(now.isAfter(TimeUtils.globalTime));
    }
}

// %%
class Test2 {
    public static void run() {
        LocalDateTime now = LocalDateTime.now();
        TimeUtils.globalTime = now.plusSeconds(1);
        TimeUtils.printTime("Test 2 global_time: ", TimeUtils.globalTime);
        TimeUtils.printTime("Test 2 now:         ", now);
        check(now.isBefore(TimeUtils.globalTime));
    }
}

// %%
Test1.run();
Test2.run();

// %%
Test2.run();
Test1.run();

// %% [markdown]
//
// ## Gründe für nicht isolierte Testfälle
//
// - Veränderlicher globaler/statischer Zustand
// - Veränderliche externe Ressourcen (Dateien, Datenbanken, Netzwerk, ...)

// %% [markdown]
//
// ## Isolation der getesteten Unit
//
// - Die getestete Unit wird von allen anderen Units isoliert
// - Test-Doubles für alle Abhängigkeiten

// %% [markdown]
//
// ### Gegenbeispiel: Nicht isolierte Unit
//
// - Verkäufer von Veranstaltungs-Tickets
// - Konkrete Klasse `Event` repräsentiert eine Veranstaltung

// %%
import java.util.HashMap;
import java.util.Map;

// %%
class Event {
    public Event(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() { return name; }

    public int getCapacity() { return capacity; }

    public void purchase(int numTickets) {
        if (numTickets > capacity) {
            throw new IllegalStateException("Not enough capacity");
        }
        capacity -= numTickets;
    }

    private String name;
    private int capacity;
}

// %%
class TicketOffice {
    public void addEvent(Event event) {
        events.put(event.getName(), event);
    }

    public Event getEvent(String eventName) {
        return events.get(eventName);
    }

    public boolean purchaseTickets(String eventName, int numTickets) {
        if (events.containsKey(eventName)) {
            try {
                events.get(eventName).purchase(numTickets);
                return true;
            } catch (IllegalStateException e) {
                System.err.println("Cannot purchase " + numTickets + " tickets for " + eventName);
                System.err.println(e.getMessage());
                return false;
            }
        }
        return false;
    }

    private Map<String, Event> events = new HashMap<>();
}

// %%
class TestPurchaseTickets {
    public static void run() {
        TicketOffice ticketOffice = new TicketOffice();
        Event event = new Event("Java Conference", 100);
        ticketOffice.addEvent(event);

        boolean result = ticketOffice.purchaseTickets("Java Conference", 10);

        check(result == true);
        check(ticketOffice.getEvent("Java Conference").getCapacity() == 90);
    }
}

// %%
TestPurchaseTickets.run();

// %%
interface Event {
    String getName();
    int getCapacity();
    void purchase(int numTickets);
}

// %%
class ConcreteEvent implements Event {
    public ConcreteEvent(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public String getName() { return name; }

    @Override
    public int getCapacity() { return capacity; }

    @Override
    public void purchase(int numTickets) {
        if (numTickets > capacity) {
            throw new IllegalStateException("Not enough capacity");
        }
        capacity -= numTickets;
    }

    private String name;
    private int capacity;
}

// %%
class TicketOffice {
    public void addEvent(Event event) {
        events.put(event.getName(), event);
    }

    public Event getEvent(String eventName) {
        return events.get(eventName);
    }

    public boolean purchaseTickets(String eventName, int numTickets) {
        if (events.containsKey(eventName)) {
            try {
                events.get(eventName).purchase(numTickets);
                return true;
            } catch (IllegalStateException e) {
                System.err.println("Cannot purchase " + numTickets + " tickets for " + eventName);
                System.err.println(e.getMessage());
                return false;
            }
        }
        return false;
    }

    private Map<String, Event> events = new HashMap<>();
}

// %% [markdown]
//
// ### Isolation von `TicketOffice` für Tests
//
// - Entkopplung von allen Abhängigkeiten
// - `EventMock`-Implementierung für Events

// %%
import java.util.ArrayList;
import java.util.List;

// %%
class EventMock implements Event {
    public List<Integer> purchaseArgs = new ArrayList<>();

    @Override
    public String getName() { return "Java Conference"; }
    @Override
    public int getCapacity() { return 90; }
    @Override
    public void purchase(int numTickets) { purchaseArgs.add(numTickets); }
}

// %%
class TestPurchaseTickets {
    public static void run() {
        TicketOffice ticketOffice = new TicketOffice();
        EventMock eventMock = new EventMock();
        ticketOffice.addEvent(eventMock);

        boolean result = ticketOffice.purchaseTickets("Java Conference", 10);

        check(result == true);
        check(ticketOffice.getEvent("Java Conference").getCapacity() == 90);
        check(eventMock.purchaseArgs.equals(List.of(10)));
    }
}

// %%
TestPurchaseTickets.run();

// %% [markdown]
//
// ## Vorteile der Isolation der getesteten Unit
//
// - Einfache Struktur der Tests
//   - Jeder Test gehört zu genau einer Unit
// - Genaue Identifikation von Fehlern
// - Aufbrechen von Abhängigkeiten/des Objektgraphen

// %% [markdown]
//
// ## Nachteile der Isolation der getesteten Unit
//
// - Potenziell höherer Aufwand (z.B. Mocks)
// - Fehler in der Interaktion zwischen Units werden nicht gefunden
// - Verleiten zum Schreiben von "Interaktionstests"
// - **Risiko von Kopplung an Implementierungsdetails**

// %% [markdown]
//
// ## Empfehlung
//
// - Verwenden Sie isolierte Unit-Tests (Detroit School)
// - Isolieren Sie Abhängigkeiten, die "eine Rakete starten"
//   - nicht-deterministisch (z.B. Zufallszahlen, aktuelle Zeit, aktuelles Datum)
//   - langsam
//   - externe Systeme (z.B. Datenbank)
// - Isolieren Sie Abhängigkeiten, die ein komplexes Setup benötigen

// %% [markdown]
//
// ## Workshop: Virtuelle Universität
//
// - Im `code`-Ordner finden Sie eine Implementierung eins sehr einfachen
//   Verwaltungssystems für eine Universität:
// - Es gibt Kurse, Professoren, die die Kurse halten, Studenten, die Aufgaben
//   bearbeiten und abgeben müssen.
// - Der Code ist in `code/starter-kits/virtual-university-sk` zu finden.
// - Die `Main.java`-Datei illustriert, wie die Klassen zusammenarbeiten und
//   verwendet werden können.

// %% [markdown]
//
// - Identifizieren Sie, welche Klassen und Methoden zu den "wertvollsten"
//   Unit-Tests führen.
// - Implementieren Sie diese Unit-Tests mit JUnit.
//   - Idealerweise implementieren sie Tests für alle Klassen, die sinnvolle
//     Tests haben.
//   - Falls Sie dafür nicht genug Zeit haben, können Sie auch nur Tests für
//     einen Teil des Codes schreiben.
//   - Die Klasse `Student` ist ein ganz guter Startpunkt, weil sie eine sehr
//     begrenzte Funktionalität hat, die Sie mit relativ wenigen Tests abdecken
//     können.
// - Sind Ihre Tests isoliert?
//   - Nach der Detroit- oder London-School?

// %% [markdown]
//
// - Falls Sie Ihre Tests nach der Detroit-School isoliert haben:
//   - Überlegen Sie, wie Sie das System überarbeiten müssten, um die Klassen in
//     Tests vollständig zu isolieren, also im London School Stil zu testen.
