// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Clean Code: Namen (Teil 2)</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ## Namensregeln für Java
//
// Namen entsprechen den
// [Java Naming Conventions](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html)

// %% [markdown]
//
// ## Clean Code Namensregeln
//
// Gute Namen
//
// - sind selbsterklärend
// - kommunizieren die Intention
// - sind aussprechbar und durchsuchbar
// - beschreiben das Problem, nicht die Implementierung
// - vermeiden Kodierungen (ungarische Notation) und Füllwörter
// - verwenden die richtige Wortart (lexikalische Kategorie)
// - verwenden die Regeln für Umfang und Länge (Scope-Length Rules)
// - vermeiden Disinformation und benennen eine sinnvolle Unterscheidung

// %% [markdown]
//
// ## Selbsterklärende Namen

// %%
int d = 0;

// %%
int elapsedTimeInDays = 0;

// %% [markdown]
//
// ## Kommuniziere Intention
//
// Namen sollen Absicht, Verhalten, Existenzberechtigung reflektieren

// %%
import java.util.ArrayList;
import java.util.List;

// %%
List<Integer> myList = new ArrayList<>(List.of(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31));

// %%
List<Integer> dpmLst = new ArrayList<>(List.of(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31));

// %%
List<Integer> daysPerMonth = new ArrayList<>(List.of(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31));

// %% [markdown]
//
// ## Aussprechbare Namen
//
// - Ermöglichen einfache Kommunikation
// - Sind oft auch besser zu suchen

// %%
List<Integer> hwCrsrPxy = new ArrayList<>(List.of(0, 0));

// %%
List<Integer> hardwareCursorPosition = new ArrayList<>(List.of(0, 0));

// %% [markdown]
//
// ## Beschreibe Problem, nicht Implementierung
//
// Vermeide Namen, die sich auf Implementierungsdetails beziehen:
// - Sie verraten nicht, warum der Code so geschrieben wurde, wie er geschrieben
//   ist
// - Aber die Kommunikation der Intention hinter dem Code hat höchste Priorität!

// %%
int addElements(List<Integer> lst) {
    return lst.stream().mapToInt(Integer::intValue).sum();
}

// %%
addElements(daysPerMonth); // Seems reasonable


// %%
int computeYearlySalary(List<Integer> monthlySalaries) {
    return monthlySalaries.stream().mapToInt(Integer::intValue).sum();
}

// %%
computeYearlySalary(daysPerMonth); // WHAT?!?

// %% [markdown]
//
// ## Vermeide Kodierungen
//
// Verwende keine ungarische Notation:

// %%
int i_days = 12;
int i_month = 3;

// %% [markdown]
//
// ## Vermeide Kodierungen
//
// Verwende keine Präfixe für Attribute:

// %%
public class MyClass {
    public int m_Days;
    public int m_months;

    public MyClass(int days, int months) {
        m_Days = days;
        m_months = months;
    }
}

// %%
public class MyClass {
    public int days;
    public int months;

    public MyClass(int days, int months) {
        this.days = days;
        this.months = months;
    }
}

// %% [markdown]
//
// ## Vermeide Kodierungen
//
// Vermeiden Sie Präfixe wie C/I: CClass, IInterface

// %%
public interface MyInterface {
    void sayHello();
}

// %%
public class MyClass implements MyInterface {
    public void sayHello() {
        System.out.println("Hello");
    }
}

// %% [markdown]
//
// ## Verwende die richtige lexikalische Kategorie
//
// - Klassen und Variablen: Substantive oder Substantivphrasen
// - Funktionen: Verben oder Verbphrasen
// - Enums: oft Adjektive
// - Boolesche Variablen und Funktionen: oft Prädikate: `is...`, `has...`

// %%
public class GoToTheServer {
    public void connection() {
        // ...
    }

    public boolean serverAvailability() {
        return true;
    }
}

// %%
GoToTheServer server = new GoToTheServer();
server.connection();

// %%
public class ServerConnection {
    public void connect() {
        // ...
    }

    public boolean isServerAvailable() {
        return true;
    }
}

// %% [markdown]
//
// ## Vermeide Füllwörter
//
// Vermeide Wörter, die keine Bedeutung haben, wie Data, Info

// %%
class ProductObject {
}

// %% [markdown]
//
// ## Regeln für Umfang und Länge (Scope-Length Rules)
//
// - Verwenden Sie lange Namen für lange Scopes, kurze Namen für kurze Scopes
// - Verwenden Sie kurze Namen für sehr häufig verwendete Klassen und Methoden

// %%
class FixedSizeOrderedCollectionIndexedByInts {
}

// %%
class Array {
}

// %% [markdown]
//
// ## Workshop: Namen
//
// In diesem Workshop wenden Sie die Clean Code Namensregeln an, die wir gerade
// gelernt haben. Sie werden mit mehreren Code-Schnipseln konfrontiert, die
// gegen eine oder mehrere dieser Regeln verstoßen.
//
// Ihre Aufgabe:
// 1. Identifizieren Sie, welche Namensregeln in jedem Beispiel verletzt werden.
// 2. Schreiben Sie den Code mit verbesserten Namen um, die den
//    Clean Code-Prinzipien folgen.
// 3. Seien Sie bereit, Ihre Änderungen zu erklären und zu begründen, warum sie
//    die Lesbarkeit und Wartbarkeit des Codes verbessern.
//
// Denken Sie an die Schlüsselprinzipien:
// - Namen sollten selbsterklärend und die Absicht offenbaren
// - Verwenden Sie aussprechbare und durchsuchbare Namen
// - Vermeiden Sie Kodierungen und Füllwörter
// - Verwenden Sie die richtige Wortart
// - Wenden Sie die Umfangs-Längen-Regeln angemessen an

// %%
int secondsInADay = 86400;

// %%
List<String> theList = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry"));

// %%
List<String> fruits = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry"));

// %%
boolean flag = false;  // Indicates if the user is logged in

// %%
boolean isUserLoggedIn = false;

// %%
public void stuffManager(String s) {
    // Process the input string
}

// %%
public void processInput(String input) {
    // Process the input string
}

// %%
class DataInfoManager {
    private String strName;
    private int iAge;
}

// %%
class PersonalInfo {
    private String name;
    private int age;
}

// %%
public boolean chkandvldtinpt(String s) {
    // Check if input is valid
    return s != null && !s.isEmpty();
}

// %%
public boolean isInputValid(String input) {
    // Check if input is valid
    return input != null && !input.isEmpty();
}

// %%
int x = 0;  // Loop counter for processing array elements

// %%
int arrayIndex = 0;

// %%
class MakeAndManageEmployees {
    // Methods for employee management
}

// %%
class EmployeeManager {
    // Methods for employee management
}

// %%
public void setvalue(int v) {
    // Set the value of an internal variable
}

// %%
public void setValue(int newValue) {
    // Set the value of an internal variable
}

// %%
boolean processSuccessful = false;

// %%
boolean wasProcessSuccessful = false;

// %%
class IUserInterface {
    // Methods for user interaction
}

// %%
interface UserInterface {
    // Methods for user interaction
}
