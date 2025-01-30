// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Clean Code: Namen (Teil 1)</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// Namen sind ein mächtiges Kommunikationsmittel.
//
// - Sie sind überall im Programm zu finden
// - Sie verbinden den Code mit Domänen-Konzepten.

// %%
double foo(double a, double b) {
    if (b > 40.0) {
        throw new IllegalArgumentException("Not allowed!");
    }
    return 40.0 * a + 60.0 * b;
}

// %%
foo(40, 10)

// %%
public static final double REGULAR_PAY_PER_HOUR = 40.0;
public static final double OVERTIME_PAY_PER_HOUR = 60.0;
public static final double MAX_ALLOWED_OVERTIME = 40.0;

// %%
public static double computeTotalSalary(double regularHoursWorked, double overtimeHoursWorked) {
    if (overtimeHoursWorked > MAX_ALLOWED_OVERTIME) {
        throw new IllegalArgumentException("Not allowed!");
    }
    double regularPay = regularHoursWorked * REGULAR_PAY_PER_HOUR;
    double overtimePay = overtimeHoursWorked * OVERTIME_PAY_PER_HOUR;
    return regularPay + overtimePay;
}


// %%
computeTotalSalary(40, 10)

// %%
int severity = 30; // Is this high or low?

// %%
severity

// %% [markdown]
//
// ### Konstanten
//
// - Namen für besondere Werte
// - Klarer, als die Werte direkt zu verwenden
// - Direkter Bezug zwischen Name und Wert
// - Wenig Information über alle möglichen Werte
// - Wenig Bezug zum Typsystem

// %%
public static final int SEVERITY_HIGH = 30;

// %% [markdown]
//
// ### Enumerationen
//
// - Alternative zu Konstanten
// - Dokumentieren, welche Werte erwartet werden
// - Bessere Typsicherheit
// - Weniger Flexibilität

// %%
public enum Severity {
    HIGH,
    MEDIUM,
    LOW
}

// %%
Severity severity = Severity.HIGH;

// %% [markdown]
//
// ### Klassen und Structs

// %%
import static java.util.AbstractMap.SimpleEntry;

// %%
public static SimpleEntry<Integer, String> analyzeReview(String text) {
    return new SimpleEntry<>(5, "Generally positive");
}

// %%
SimpleEntry<Integer, String> result = analyzeReview("Some review text");
System.out.println("Score: " + result.getKey() + ", Sentiment: " + result.getValue());


// %%
public class AnalysisResult {
    private int score;
    private String sentiment;

    public AnalysisResult(int score, String sentiment) {
        this.score = score;
        this.sentiment = sentiment;
    }

    public int getScore() {
        return score;
    }

    public String getSentiment() {
        return sentiment;
    }

    public String toString() {
        return "Score: " + getScore() + ", Sentiment: " + getSentiment();
    }
}

// %%
AnalysisResult analyzeReview(String text) {
    return new AnalysisResult(5, "Overall positive");
}

// %%
AnalysisResult result = analyzeReview("Some review text");
System.out.println(result);

// %%
enum Score {
    HIGH,
    MODERATELY_HIGH,
    MEDIUM,
    MODERATELY_LOW,
    LOW,
    UNKNOWN,
}

// %%
public class AnalysisResult {
    private Score score;
    private String sentiment;

    public AnalysisResult(Score score, String sentiment) {
        this.score = score;
        this.sentiment = sentiment;
    }

    public Score getScore() {
        return score;
    }

    public String getSentiment() {
        return sentiment;
    }

    public String toString() {
        return "Score: " + getScore() + ", Sentiment: " + getSentiment();
    }
}

// %%
AnalysisResult analyzeReview(String text) {
    return new AnalysisResult(Score.MODERATELY_HIGH, "Overall positive");
}

// %%
AnalysisResult result = analyzeReview("Some review text");
System.out.println(result);

// %% [markdown]
//
// ## Was ist ein guter Name?
//
// - Präzise (sagt was er meint, meint was er sagt)
// - Beantwortet
//   - Warum gibt es diese Variable (Funktion, Klasse, Modul, Objekt...)?
//   - Was macht sie?
//   - Wie wird sie verwendet?
//
// Gute Namen sind schwer zu finden!

// %% [markdown]
//
// ## Was ist ein schlechter Name?
//
// - Braucht einen Kommentar
// - Verbreitet Disinformation
// - Entspricht nicht den Namensregeln

// %% [markdown]
//
// ## Workshop: Rezepte
//
// In `code/starter-kits/recipes-sk` ist ein Programm, mit denen sich ein
// Kochbuch verwalten lässt. Leider hat der Programmierer sehr schlechte Namen
// verwendet, dadurch ist das Programm schwer zu verstehen.
//
// Ändern Sie die Namen so, dass das Programm leichter verständlich wird.
//
// ### Hinweis
//
// Verwenden Sie die Refactoring-Tools Ihrer Entwicklungsumgebung!
