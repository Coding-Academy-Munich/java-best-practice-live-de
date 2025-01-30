// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Clean Code: Funktionen (Teil 1)</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// [Fasse Operationen, die logisch zusammengehören, als sorgfältig benannte
// Funktionen zusammen (CG,
// F.1)](https://isocpp.github.io/CppCoreGuidelines/CppCoreGuidelines#Rf-package)
//
// - Besser lesbar
// - Einfacher zu testen
// - Fehler sind weniger wahrscheinlich
// - Wird eher wiederverwendet

// %% [markdown]
//
// ## Die 1. Clean Code Regel für Funktionen
//
// - Funktionen sollten kurz sein
// - Kürzer als man meint!
// - Maximal 4 Zeilen!

// %% [markdown]
//
// ## C++ Core Guidelines
//
// - [Halte Funktionen kurz und einfach (CG
//   F.3)](https://isocpp.github.io/CppCoreGuidelines/CppCoreGuidelines#f3-keep-functions-short-and-simple)
//   - Funktionen sollten auf einen Bildschirm passen
//   - Große Funktionen sollten in kleinere, zusammenhängende und benannte
//     Funktionen aufgeteilt werden
//   - Funktionen mit einer bis fünf Zeilen sollten als normal angesehen werden

// %% [markdown]
//
// ## Konzentration auf eine Aufgabe
//
// - Funktionen sollten eine Aufgabe erfüllen ("do one thing")
// - Sie sollten diese Aufgabe gut erfüllen
// - Sie sollten nur diese Aufgabe erfüllen
// - [Eine Funktion sollte eine einzige logische Aufgabe erfüllen (CG
//   F.2)](https://isocpp.github.io/CppCoreGuidelines/CppCoreGuidelines#Rf-logical)

// %%
import java.util.ArrayList;
import java.util.List;

// %%
public static int doStuff(int a, int b, List<Integer> results) {
    // Get measurement from sensors based on config data...
    int measurement = a + b;
    // ... and perform a complex computation
    int newResult = measurement + 1;
    // ... save the result to the list of results
    if (newResult > 0) {
        results.add(newResult);
    }
    // ... print all results
    for (int result : results) {
        System.out.println(result);
    }
    // ... and return the result
    return newResult;
}

// %%
List<Integer> allResults = new ArrayList<>(List.of(12, 43));

// %%
int newResult = doStuff(2, 4, allResults);

// %%
newResult

// %%
allResults

// %% [markdown]
//
// - Messung, Auswertung und Validierung der Ergebnisse

// %%
int getMeasurement(int a, int b) {
    return a + b;
}

// %%
int computeDataForNextTimestep(int measurement) {
    return measurement + 1;
}

// %%
boolean isValidResult(int result) {
    return result > 0;
}

// %% [markdown]
//
// - Speichern und Drucken der Ergebnisse:

// %%
import java.util.List;

// %%
public static void saveResult(int newResult, List<Integer> results) {
    results.add(newResult);
}

// %%
public static void printResults(List<Integer> results) {
    for (int result : results) {
        System.out.println(result);
    }
}

// %% [markdown]
//
// - Zusammenführung der Funktionen:

// %%
public static int performMeasurementAndProcessResult(int a, int b, List<Integer> results) {
    int measurement = getMeasurement(a, b);
    int newResult = computeDataForNextTimestep(measurement);
    if (isValidResult(newResult)) {
        saveResult(newResult, results);
    }
    printResults(results);
    return newResult;
}

// %%
List<Integer> allResults = new ArrayList<>(List.of(12, 43));

// %%
int newResult = performMeasurementAndProcessResult(2, 4, allResults);

// %%
newResult

// %%
allResults

// %% [markdown]
//
// ### Fragen
//
// - Macht `performMeasurementAndProcessResult()` das Gleiche wie `doStuff()`?
// - Ist die Funktion `performMeasurementAndProcessResult()` wirklich besser?
// - Konzentriert sie sich auf eine Aufgabe?
// - Unterscheiden sich Ihre Aufgaben von `doStuff()`?
// - Warum (nicht)?

// %% [markdown]
//
// ## Hilfsmittel: Änderungsgründe
//
// - Welche möglichen Änderungsgründe gibt es?
// - Wie viele davon betreffen die jeweilige Funktion?

// %% [markdown]
//
// | Änderungsgrund        | `doStuff()` | `performMeasurementAndProcessResult()` |
// | --------------------- | :---------- | :------------------------------------- |
// | Messung               | ✓           | ❌ `getMeasurement()`                 |
// | Berechnung            | ✓           | ❌ `computeDataForNextTimestep()`     |
// | Validierung           | ✓           | ❌ `isValidResult()`                  |
// | Speichern             | ✓           | ❌ `saveResult()`                     |
// | Drucken               | ✓           | ❌ `printResults()`                   |
// | Neue/andere Tätigkeit | ✓           | ✓                                     |

// %% [markdown]
//
// # Abstraktionsebenen
//
// - Alles, was die Funktion in ihrem Rumpf tut, sollte eine (und nur eine)
//   Abstraktionsebene unterhalb der Funktion selbst sein.
// - Beispiel: `performMeasurementAndProcessResult()`
// - Gegenbeispiel: `createAndDistributeExam()`:

// %% [markdown]
//
// ```java
// void createAndDistributeExam(String subject) {
//     // high level abstraction
//     Exam exam = createExamUsingChatGPT(subject);
//
//     // low level abstractions
//     FileOutputStream file = new FileOutputStream(subject + "_exam.pdf");
//     writePdfHeader(file);
//     for (Question question : exam.getQuestions()) {
//         String pdfQuestion = convertQuestionToPdf(question);
//         file.write(pdfQuestion.getBytes());
//         file.write("\n".getBytes());
//     }
//
//     // higher level abstraction
//     distributeExamToStudents(file);
// }
// ```

// %% [markdown]
//
// ## Kontrolle der Abstraktionsebenen: "Um-Zu"-Absätze
//
// `performMeasurementAndProcessResult()`:
//
// Um eine Messung durchzuführen und das Ergebnis zu verarbeiten:
// - Hole ein Messergebnis
// - Berechne die Daten für den nächsten Zeitschritt
// - Speichere das Ergebnis, falls es gültig ist
// - Drucke alle Ergebnisse, unabhängig davon, ob das neue Ergebnis gültig ist

// %% [markdown]
//
// ## Kommentare als "Um-Zu"-Absätze
//
// - Beim Schreiben von Code können wir das "Um-Zu"-Muster verwenden, um die
//   Abstraktionsebenen zu kontrollieren
// - Wir können die "Um-Zu"-Absätze als Kommentare schreiben bevor wir den Code
//   schreiben
// - Meistens wird jeder Kommentar zu einer Funktion

// %%
import java.util.List;

// %%
public class OrderProcessor {
    public void processOrder(String orderID) {
        // Hole die Bestelldetails anhand der `orderID`
        // Validiere die Lagerverfügbarkeit für jeden Artikel in der Bestellung
        // Aktualisiere den Lagerbestand bei erfolgreicher Validierung
        // Erzeuge eine Lieferung für die Bestellung
        // Benachrichtige den Kunden mit den Lieferdetails
    }
}

// %% [markdown]
//
// ## Funktionen als "Um-Zu"-Absätze
//
// Wir können die "Um-Zu"-Absätze auch gleich als Funktionsaufrufe schreiben:

// %%
public class OrderProcessor {
    public void processOrder(String orderID) {
        // Hole die Bestelldetails anhand der `orderID`
        fetchOrderDetails(orderID);
        // Validiere die Lagerverfügbarkeit für jeden Artikel in der Bestellung
        validateStockAvailability(orderID);
        // Aktualisiere den Lagerbestand bei erfolgreicher Validierung
        updateInventory(orderID);
        // Erzeuge eine Lieferung für die Bestellung
        generateShipment(orderID);
        // Benachrichtige den Kunden mit den Lieferdetails
        notifyCustomer(orderID);
    }

    private void fetchOrderDetails(String orderID) {
        // ...
    }

    private void validateStockAvailability(String orderID) {
        // ...
    }

    private void updateInventory(String orderID) {
        // ...
    }

    private void generateShipment(String orderID) {
        // ...
    }

    private void notifyCustomer(String orderID) {
        // ...
    }
}

// %% [markdown]
//
// ## Die Step-Down-Regel
//
// - Wir wollen, dass sich der Code wie eine Erzählung von oben nach unten liest
// - Auf jede Funktion sollten die Funktionen eine Abstraktionsebene darunter
//   folgen

// %% [markdown]
//
// ## Mini-Workshop: Do one Thing
//
// In `code/starter-kits/salaries-sk` finden Sie eine Funktion
// `handle_money_stuff()`.
//
// Diese Funktion macht mehr als eine Sache.
//
// Teilen Sie sie in mehrere Funktionen auf, so dass jede nur eine Sache tut.
// Stellen Sie sicher, dass
// - jede Funktion ihre Aufgabe gut erfüllt und sich auf einer einzigen
//   Abstraktionsebene befindet,
// - alle Namen angemessen sind, und
// - der Code leicht zu verstehen ist.
//
// *Tipp:* Beginnen Sie damit, die Variablen gemäß den Kommentaren umzubenennen,
// um den Rest der Arbeit zu vereinfachen.
