// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Clean Code: Kommentare und Dokumentation</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ## Kommentare
//
// - Kommentare kompensieren unser Unvermögen, uns in Code auszudrücken.
// - Wenn möglich, drücke dich in Code aus, nicht in Kommentaren!
//   - Wann immer du einen Kommentar schreiben willst, prüfe, ob du es nicht im
//     Code tun kannst.
//   - Schreibe lieber Assertions oder Tests, wenn möglich.
//   - Verwende erklärende Variablen

// %%
// Checks if a person is eligible for a senior discount
boolean check(int n) {
    return n >= 65;
}

// %%
check(34)

// %%
check(70)


// %%
boolean isEligibleForSeniorDiscount(int age) {
    return age >= 65;
}

// %%
isEligibleForSeniorDiscount(34)

// %%
isEligibleForSeniorDiscount(70)

// %%
// Multiply the seconds in a day times the days of work.
double ds = 60 * 60 * 24 * 4;

// %%
ds

// %%
final double daysOfWork = 4;
final double secondsPerDay = 60 * 60 * 24;
double durationInSeconds = secondsPerDay * daysOfWork;


// %%
durationInSeconds


// %% [markdown]
//
// ## Wie Kommentare scheitern
//
// - Kommentare sind schwer zu pflegen
// - Deshalb lügen sie oft
// - Sie werden nicht geändert, wenn der Code aktualisiert wird
// - Sie werden nicht verschoben, wenn der Code verschoben wird

// %% [markdown]
// ```java
// # Check to see if the employee is eligible for full benefits
// if (employee.flags & HOURLY_FLAG) and (employee.age > 65):
//     ...
// ```
//
// versus
//
// ```java
// if employee.isEligibleForFullBenefits():
//     ...
// ```

// %% [markdown]
//
// ## Java Regeln für Dokumentations-Kommentare
//
// - Beginnen mit `/**`, werden mit `*/` beeendet
// - Der erste Satz sollte eine Zusammenfassung des Zwecks der Methode sein
//   - Javadoc fügt diese Zusammenfassung in die Methoden-Tabelle ein
// - Formatierung durch HTML-Tags
//   - `<a>` für Links
//   - `<p>` für Absätze
// - `{@link}` für Verweise auf andere Klassen (Inline-Tag)
// - Die erste Zeile, die mit `@` beginnt, beendet die Beschreibung
//   - `@param` für Parameter
//   - `@return` für den Rückgabewert
//   - `@throws` für Ausnahmen
// - Mehr Informationen in [Javadoc](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html)

// %%
/**
 * The roots of a quadratic equation
 */
record QuadraticRoots(double root1, double root2) {}

// %%
/**
 * Compute the roots of the quadratic equation ax^2 + bx + c == 0.
 *
 * @param a the coefficient of x^2
 * @param b the coefficient of x
 * @param c the constant term
 * @return the roots of the equation
 * @throws IllegalArgumentException if the equation has no real roots
 */
QuadraticRoots solveQuadraticEquation(double a, double b, double c) {
    double root = Math.sqrt(b * b - 4 * a * c);
    double d = 2 * a;
    return new QuadraticRoots((-b + root) / d, (-b - root) / d);
}

// %%
solveQuadraticEquation(1, 0, -1)

// %% [markdown]
//
// ## Gute Kommentare
//
// Kommentare sind gut, wenn sie
//
// - aus juristischen Gründen notwendig sind
// - Konzepte erklären, die nicht im Code ausgedrückt werden können
// - den Zweck des Codes erklären
// - Code erklären, den man nicht bereinigen kann (z.B. eine veröffentlichte
//   Schnittstelle)
// - veröffentlichte Schnittstellen dokumentieren (z.B. mit Doxygen)
// - `TODO`-Kommentare sind (wenn sie sparsam verwendet werden)
// - wichtige Aspekte betonen ("Dies ist sehr wichtig, weil...")

// %% [markdown]
//
// ## Schlechte Kommentare
//
// - Unklare Kommentare (Nuscheln)
//
// Angenommen, der folgende Kommentar ist tatsächlich richtig. Was sagt er uns?

// %%
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


// %%
try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(
            new FileInputStream("my-app.cfg"), StandardCharsets.UTF_8))) {
    // ...
} catch (FileNotFoundException e) {
    // Somebody else has already loaded the defaults.
    // Print a message.
    System.out.println("No config file.");
}


// %% [markdown]
//
// - Redundante Kommentare (dauern länger als der Code zu lesen, ohne klarer zu
//   sein)

// %%
import java.nio.charset.CharacterCodingException;

// %%
void readAndApplyConfiguration(String filename) throws FileNotFoundException, CharacterCodingException {
    // ...
}

// %%
// Read the configuration from file `my-app.cfg`. The file has to be readable and
// in a valid encoding.
// If the file is in a bad encoding, we print a message and ignore the attempt.
// If the file cannot be found we simply ignore the attempt without printing anything.
// If the file is indeed found, we read it and apply the configuration to the
// system.
try {
    readAndApplyConfiguration("my-app.cfg");
}
catch (CharacterCodingException e) {
    System.out.println("Bad character encoding.");
}
catch (FileNotFoundException e) {
    // Ignore the attempt to read the configuration file
}

// %% [markdown]
//
// - Irreführende Kommentare

// %%
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.stream.Collectors;

// %%
// Return a new list that is the concatenation of the elements in `list_1` and
// `list_2`.
ArrayList<Integer> concatenateLists(ArrayList<Integer> list1, ArrayList<Integer> list2) {
    if (list1.isEmpty()) {
        return list2;
    } else if (list2.isEmpty()) {
        return list1;
    } else {
        return Stream.concat(list1.stream(), list2.stream())
                     .collect(Collectors.toCollection(ArrayList::new));
    }
}

// %%
ArrayList<Integer> x = new ArrayList<>(List.of(1, 2));
ArrayList<Integer> y = new ArrayList<>(List.of(3, 4));
ArrayList<Integer> z = new ArrayList<>(List.of(1, 2, 3, 4));
ArrayList<Integer> empty = new ArrayList<>();

// %%
concatenateLists(x, y)

// %%
assert concatenateLists(x, y).equals(z);

// %%
assert concatenateLists(x, y) != z;

// %% [markdown]
//
// Aufgrund des Kommentars würde man folgendes Verhalten nicht erwarten:

// %%
assert concatenateLists(x, empty) == x;

// %%
List<Integer> x2 = concatenateLists(x, empty);

// %%
x2.add(3);

// %%
x

// %% [markdown]
//
// - Vorgeschriebene Kommentare (durch Style-Guides, nicht durch Gesetze)
// - Journal-Kommentare (Geschichte der Datei)

// %% [markdown]
// ```java
// // file: widget.java
// //
// // Changes made to the file:
// //
// // 2022-08-10: Added a frobnicator as proposed by Jane
// // 2022-08-11: Twiddled the frobnicator's parameters
// // 2022-08-12: Further tweaks to the frobnicator settings
// // 2022-08-13: Added flux compensation to the frobnicator
// // 2022-08-14: Improved flux compensation
// // 2022-09-03: Revisited flux compensation after discussion with Joe
// //
// class Frobnicator:
//     pass
// ```

// %% [markdown]
//
// - Inhaltsfreie Kommentare (Noise comments)

// %%
class FluxCompensator {
    // The constructor of the flux compensator class.
    FluxCompensator() {
        // ...
    }
}

// %%
// Hourly wage in US$
final int HOURLY_WAGE_IN_USD = 80;


// %% [markdown]
//
// - Positions-Markierungen

// %%
class MyVeryLargeClass {
    ////////////////////////////////////////
    // Initialization Methods
    ////////////////////////////////////////
    void init() {
        // ...
    }

    void initInAnotherWay() {
        // ...
    }

    ////////////////////////////////////////
    // Computations
    ////////////////////////////////////////
    void computeThis() {
        // ...
    }

    void computeThat() {
        // ...
    }

    ////////////////////////////////////////
    // State Updates
    ////////////////////////////////////////
    void setSomeState(int x) {
        // ...
    }
}

// %% [markdown]
//
// - Zuschreibungen und Namensnennungen

// %%
// Added by Jack <jack@example.org> on 2018-03-12
int someFunction(int x, int y) {
    return x + y;
}

// %% [markdown]
//
// - Auskommentierter Code
//   - Neigt dazu, nie gelöscht zu werden
//   - Unklar, warum er da ist: sollte er gelöscht oder wieder einkommentiert
//     werden?
//   - Sollte lieber gelöscht und bei Bedarf aus dem Versionskontrollsystem
//     wiederhergestellt werden

// %%
// int someFunction(int x, int y) {
//     return x + y;
// }

// %%
int someOtherFunction(int x, int y) {
    // int z = x + y;
    // return z;
    return 123;
}

// %% [markdown]
//
// - HTML-Kommentare
//
// - Im Clean-Code Buch wird von HTML-Kommentaren abgeraten, da sie nicht gut
//   lesbar sind
// - In Java werden aber üblicherweise Dokumentations-Kommentare in HTML-Form
//   geschrieben

// %% [markdown]
//
// - Nichtlokale Information

// %%
// This is set to its correct value by `frobFoo()` in file `frobnicator.java`.
class Constants {
    static int foo = -1;
}

// %% [markdown]
//
// - Zu viel Information

// %% [markdown]
//
// - Unklarer Bezug zum Code

// %%
// Adjust for target endianness and buffer size
int foo = Math.max((foo + 7) * 2, 256);
