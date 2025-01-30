// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Finden von Tests: BVA und Partitionen</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// # Strategien zum Finden von (guten) Tests
//
// - Analyse von Randwerten (Boundary Value Analysis, BVA)
// - Partitionierung
// - Zustandsbasiertes Testen
// - Kontrollflussbasiertes Testen
// - Richtlinien
// - Kenntnis häufiger Fehler in Software
// - (Kenntnis häufiger Probleme von Tests (Test Smells))


// %% [markdown]
//
// ## Boundary Value Analysis
//
// - Viele Fehler treten am "Rand" des Definitionsbereichs von Funktionen
//   oder Methoden auf
// - Eine gute Strategie zum effizienten Testen ist es daher, derartige
//   Randwerte zu betrachten
//   - Der/die letzten gültigen Werte
//   - Werte, die gerade außerhalb des Definitionsbereichs liegen
// - Ist z.B. eine Funktion für ganzzahlige Werte zwischen 0 und 5
//   definiert, so kann sie mit Eingaben -1, 0, 5, 6 getestet werden

// %% [markdown]
//
// ## Boundary Value Analysis
//
// ### Vorteil:
//
// - Man konzentriert sich auf empirisch häufige Fehlerquellen
//
// ### Schwierigkeiten:
//
// - Bei vielen Bereichen ist nicht klar, was "Randwerte" sind
//   - (Allerdings lassen sich oft alternative Kriterien finden, z.B.
//     Länge von Collection-Argumenten)
// - Werte außerhalb des Definitionsbereichs können manchmal zu undefiniertem
//   Verhalten führen
// - Bei Funktionen mit vielen Parametern gibt es eine kombinatorische
//   Explosion von Randwerten

// %% [markdown]
//
// ## Partitionierung
//
// - Argumente von Funktionen, Ein/Ausgabe des Programms und Zustände
//   von Klassen können oft in Äquivalenzklassen unterteilt werden, sodass…
//   - Das Verhalten für Elemente aus der gleichen Äquivalenzklasse ähnlich
//     ist (z.B. den gleichen Kontrollflusspfad nimmt)
//   - Elemente aus unterschiedlichen Klassen verschiedenes Verhalten zeigen
//   - Beispiel: Die Argumente der sqrt-Funktion können unterteilt werden in
//       - Positive Zahlen und 0
//       - Negative Zahlen
//   - Eine feinere Unterteilung wäre zusätzlich in Quadratzahlen und Nicht-Quadratzahlen
// - Eine derartige Äquivalenzklasse heißt Partition (oder Domäne)


// %% [markdown]
//
// ## Partitionierung
//
// Finde Partitionen für das getestete Element und teste die folgenden Elemente:
//
// - Einen Wert aus der "Mitte" der Partition
// - Einen Wert auf oder nahe jeder Partitionsgrenze
//
// Häufig findet man Partitionen durch BVA.
//
// Beispiel: Um die Quadratwurzelfunktion zu testen, schreibe Tests für:
// - `sqrt(0.0)`
// - `sqrt(2.0)`
// - `sqrt(-2.0)`

// %% [markdown]
//
// ## Black-Box vs. White-Box Partitionierung
//
// - Wir können die Partitionierung sowohl als Black-Box- als auch als
//   White-Box-Technik verwenden
// - Bei der Black-Box-Partitionierung betrachten wir nur die Spezifikation
//   der Funktion
// - Bei der White-Box-Partitionierung betrachten wir auch die Implementierung
//   der Funktion
// - Die White-Box-Partitionierung kann uns helfen, Partitionen zu finden, die
//   auf spezifischen Kontrollflusspfaden durch die Funktion basieren
// - In vielen Fällen führen beide Vorgehensweisen zum gleichen Ergebnis

// %% [markdown]
//
// ## Beispiel: Discount-Berechnung
//
// - In einem Online-Shop haben wir eine Funktion, die den Rabatt für einen
//   Einkauf berechnet
// - Sie hat folgende Spezifikation:
//   - Eingabe: Gesamtbetrag des Einkaufs
//   - Rückgabewert: Preis nach Abzug des Rabatts
//   - Für Einkäufe unter 50€ gibt es keinen Rabatt
//   - Der maximale Rabatt wird ab 200€ Einkaufswert gewährt und beträgt 20%
// - Welche Partitionen können wir für diese Funktion identifizieren?
// - Wie können wir Werte aus jeder Partition testen?

// %% [markdown]
//
// - Black-Box Partitionen:
//   - Einkaufswert < 50€
//   - 50€ ≤ Einkaufswert < 200€
//   - Einkaufswert ≥ 200€
// - Testfälle:
//   - Einkaufswert = 0€
//   - Einkaufswert = 25€
//   - Einkaufswert = 49.99€
//   - Einkaufswert = 50€ (?)
//   - Einkaufswert = 100€
//   - Einkaufswert = 199.99€
//   - Einkaufswert = 200€
//   - Einkaufswert = 250€

// %% [markdown]
//
// | Einkaufswert | Test für den Ausgabewert y    |
// |--------------|:-----------------------------:|
// | $0€$         | $y = 0€$                      |
// | $25€$        | $y = 0€$                      |
// | $49.99€$     | $y = 0€$                      |
// | $50€$        | $0€ \leq y < 50€ \cdot 20\%$  |
// | $100€$       | $0€ < y < 100€ \cdot 20\%$    |
// | $199.99€$    | $0€ < y < 199.99€ \cdot 20\%$ |
// | $200€$       | $y = 200€ \cdot 20\%$         |
// | $250€$       | $y = 250€ \cdot 20\%$         |

// %% [markdown]
//
// - Hier ist jetzt die konkrete Implementierung der Funktion.

// %%
public static class DiscountCalculator {
    public static double calculateDiscount(double purchaseAmount) {
        if (purchaseAmount < 0) {
            throw new IllegalArgumentException("Purchase amount cannot be negative");
        }

        if (purchaseAmount < 50) {
            return 0;
        } else if (purchaseAmount < 100) {
            return purchaseAmount * 0.1;
        } else if (purchaseAmount < 200) {
            return purchaseAmount * 0.15;
        } else {
            return Math.min(purchaseAmount * 0.2, 100);
        }
    }
}

// %% [markdown]
//
// - White-Box Partitionen:
//   - Einkaufswert < 0€
//   - 0€ ≤ Einkaufswert < 50€
//   - 50€ ≤ Einkaufswert < 100€
//   - 100€ ≤ Einkaufswert < 200€
//   - 200€ ≤ Einkaufswert < 500€ (Maximaler Rabatt von 100€)
//   - Einkaufswert ≥ 500€

// %%
// %maven org.junit.jupiter:junit-jupiter-api:5.8.2
// %maven org.junit.jupiter:junit-jupiter-engine:5.8.2
// %maven org.junit.jupiter:junit-jupiter-params:5.8.2
// %maven org.junit.platform:junit-platform-launcher:1.9.3

// %%
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

// %%
// %jars .
// %classpath testrunner-0.1.jar

// %%
import static testrunner.TestRunner.runTests;

// %%
class DiscountCalculatorBlackBoxTest {
    @ParameterizedTest
    @ValueSource(doubles = {0, 25, 49.99})
    void testPartitionBelow50(double purchaseAmount) {
        assertEquals(0, DiscountCalculator.calculateDiscount(purchaseAmount));
    }

    @ParameterizedTest
    @ValueSource(doubles = {50, 100, 199.99})
    void testPartition50to200(double purchaseAmount) {
        assertTrue(0 < DiscountCalculator.calculateDiscount(purchaseAmount));
        assertTrue(DiscountCalculator.calculateDiscount(purchaseAmount) <= purchaseAmount * 0.2);
    }

    @ParameterizedTest
    @ValueSource(doubles = {200, 250})
    void testPartitionAbove200(double purchaseAmount) {
        assertEquals(purchaseAmount * 0.2, DiscountCalculator.calculateDiscount(purchaseAmount), 0.001);
    }
}

// %%
runTests(DiscountCalculatorBlackBoxTest.class);

// %%
class DiscountCalculatorWhiteBoxTest {
    @ParameterizedTest
    @ValueSource(doubles = {-1, -100, -1000})
    void testNegativePurchaseAmount(double purchaseAmount) {
        assertThrows(IllegalArgumentException.class, () -> DiscountCalculator.calculateDiscount(purchaseAmount));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 25, 49.99})
    void testPartitionBelow50(double purchaseAmount) {
        assertEquals(0, DiscountCalculator.calculateDiscount(purchaseAmount));
    }

    @ParameterizedTest
    @ValueSource(doubles = {50, 75, 99.99})
    void testPartition50to200(double purchaseAmount) {
        assertEquals(purchaseAmount * 0.1, DiscountCalculator.calculateDiscount(purchaseAmount), 0.001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {100, 150, 199.99})
    void testPartition100to200(double purchaseAmount) {
        assertEquals(purchaseAmount * 0.15, DiscountCalculator.calculateDiscount(purchaseAmount), 0.001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {200, 250, 499.99})
    void testPartitionAbove200(double purchaseAmount) {
        assertEquals(purchaseAmount * 0.2, DiscountCalculator.calculateDiscount(purchaseAmount), 0.001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {500, 1000, 10000})
    void testPartitionAbove500(double purchaseAmount) {
        assertEquals(100, DiscountCalculator.calculateDiscount(purchaseAmount));
    }
}

// %%
runTests(DiscountCalculatorWhiteBoxTest.class);

// %% [markdown]
//
// ## Workshop: Partitionierung
//
// In diesem Workshop werden wir die gelernten Techniken auf eine einfache
// Funktion anwenden. Wir werden eine Funktion `validateAndFormatPhoneNumber`
// implementieren und testen.
//
// Die Funktion soll eine US-Amerikanische Telefonnummer validieren und im
// Format `(XXX) XXX-XXXX` zurückgeben.
//
// - Die Telefonnummer darf nicht `null` oder leer sein, sonst wird eine
//   `IllegalArgumentException` geworfen.
// - Die Telefonnummer besteht aus 10 oder 11 Ziffern.
// - Bei 11 Ziffern muss die Telefonnummer mit einer `1` beginnen.
// - Wenn eine Telefonnummer weniger als 10 oder mehr als 11 Ziffern hat, wird
//   eine `IllegalArgumentException` geworfen.

// %%
public static class PhoneNumberFormatter {
    public static String validateAndFormatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }

        String digitsOnly = phoneNumber.replaceAll("\\D", "");

        if (digitsOnly.length() < 10 || digitsOnly.length() > 11) {
            throw new IllegalArgumentException("Phone number must have 10 or 11 digits");
        }

        if (digitsOnly.length() == 11 && !digitsOnly.startsWith("1")) {
            throw new IllegalArgumentException("11-digit numbers must start with 1");
        }

        String areaCode = digitsOnly.substring(digitsOnly.length() - 10, digitsOnly.length() - 7);
        String firstPart = digitsOnly.substring(digitsOnly.length() - 7, digitsOnly.length() - 4);
        String secondPart = digitsOnly.substring(digitsOnly.length() - 4);

        return String.format("(%s) %s-%s", areaCode, firstPart, secondPart);
    }
}

// %%
PhoneNumberFormatter.validateAndFormatPhoneNumber("1234567890")

// %%
// %maven org.junit.jupiter:junit-jupiter-api:5.8.2
// %maven org.junit.jupiter:junit-jupiter-engine:5.8.2
// %maven org.junit.jupiter:junit-jupiter-params:5.8.2
// %maven org.junit.platform:junit-platform-launcher:1.9.3

// %%
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

// %%
// %jars .
// %classpath testrunner-0.1.jar

// %%
import static testrunner.TestRunner.runTests;


// %% [markdown]
//
// - Identifizieren Sie Äquivalenzklassen für die Eingaben der Funktion.
// - Implementieren Sie Testfälle für jede Partition.
// - Welche Black-Box-Partitionen können Sie identifizieren?
// - Welche White-Box-Partitionen können Sie identifizieren?

// %%
class PhoneNumberFormatterTest {
    // Black-box partitioning tests
    @Test
    void testValidTenDigitNumber() {
        assertEquals("(123) 456-7890", PhoneNumberFormatter.validateAndFormatPhoneNumber("1234567890"));
    }

    @Test
    void testValidElevenDigitNumber() {
        assertEquals("(234) 567-8901", PhoneNumberFormatter.validateAndFormatPhoneNumber("12345678901"));
    }

    @Test
    void testInvalidLengthNumber() {
        assertThrows(IllegalArgumentException.class, () -> PhoneNumberFormatter.validateAndFormatPhoneNumber("123456789"));
    }

    @Test
    void testInvalidElevenDigitNumber() {
        assertThrows(IllegalArgumentException.class, () -> PhoneNumberFormatter.validateAndFormatPhoneNumber("21234567890"));
    }

    @Test
    void testNullInput() {
        assertThrows(IllegalArgumentException.class, () -> PhoneNumberFormatter.validateAndFormatPhoneNumber(null));
    }

    @Test
    void testEmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> PhoneNumberFormatter.validateAndFormatPhoneNumber(""));
    }

    // White-box partitioning tests
    @ParameterizedTest
    @ValueSource(strings = {"1234567890", "(123) 456-7890", "123-456-7890", "123.456.7890"})
    void testValidTenDigitNumbersWithDifferentFormats(String input) {
        assertEquals("(123) 456-7890", PhoneNumberFormatter.validateAndFormatPhoneNumber(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"11234567890", "1-123-456-7890", "1 (123) 456-7890"})
    void testValidElevenDigitNumbersWithDifferentFormats(String input) {
        assertEquals("(123) 456-7890", PhoneNumberFormatter.validateAndFormatPhoneNumber(input));
    }

    @Test
    void testNumberWithNonDigitCharacters() {
        assertEquals("(123) 456-7890", PhoneNumberFormatter.validateAndFormatPhoneNumber("a1b2c3d4e5f6g7h8i9j0"));
    }
}


// %%
runTests(PhoneNumberFormatterTest.class);

// %%
