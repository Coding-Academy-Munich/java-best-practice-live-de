// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Geleitetes Kata: Primfaktorzerlegung</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ## Geleitetes Kata: Primfaktorzerlegung
//
// - Eine Übung zu TDD, die zeigt, wie man durch Tests auf eine einfache
//   Implementierung eines Algorithmus geführt werden kann
// - Wichtig ist die Vorgehensweise: Tests sollen das Design treiben
// - Ziel: Lernen inkrementell und iterativ zu arbeiten!

// %% [markdown]
//
// ## Geleitetes Kata: Primfaktorzerlegung
//
// Schreiben Sie eine Funktion
//
// ```java
// void computeAndWritePrimes(long n);
// ```
// die die Primfaktoren von n in aufsteigender Reihenfolge auf dem Bildschirm
// ausgibt.
//
// Mehrfach vorkommende Primfaktoren sind in der Ausgabe mehrmals enthalten.
//
// Die Primfaktoren sind durch Kommas getrennt.

// %%
// %maven org.junit.jupiter:junit-jupiter-api:5.8.2
// %maven org.junit.jupiter:junit-jupiter-engine:5.8.2
// %maven org.junit.jupiter:junit-jupiter-params:5.8.2
// %maven org.junit.platform:junit-platform-launcher:1.9.3

// %%
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

// %%
import java.util.List;
import java.util.ArrayList;

// %%
// %jars .
// %classpath testrunner-0.1.jar

// %%
import static testrunner.TestRunner.runTests;

// %% [markdown]
//
// Test-Klasse `PrimeFactorsTest`

// %%
class PrimeFactorsTest {
    @Test
    void testPrimeFactorsOf1() {
        assertEquals(List.of(), primeFactors(1));
    }
}

// %%
runTests(PrimeFactorsTest.class);

// %% [markdown]
//
// Funktion `primeFactors`

// %%
List<Integer> primeFactors(int n) {
    return List.of();
}

// %%
runTests(PrimeFactorsTest.class)

// %%
class PrimeFactorsTest {
    @Test
    void testPrimeFactorsOf1() {
        assertEquals(List.of(), primeFactors(1));
    }

    @Test
    void testPrimeFactorsOf2() {
        assertEquals(List.of(2), primeFactors(2));
    }
}

// %%
runTests(PrimeFactorsTest.class)

// %%
List<Integer> primeFactors(long n) {
    if (n == 2) {
        return List.of(2);
    }
    return List.of();
}

// %%
runTests(PrimeFactorsTest.class)

// %%
class PrimeFactorsTest {
    @Test
    void testPrimeFactorsOf1() {
        assertEquals(List.of(), primeFactors(1));
    }

    @Test
    void testPrimeFactorsOf2() {
        assertEquals(List.of(2), primeFactors(2));
    }

    @Test
    void testPrimeFactorsOf3() {
        assertEquals(List.of(3), primeFactors(3));
    }
}

// %%
runTests(PrimeFactorsTest.class)

// %%
List<Integer> primeFactors(long n) {
    if (n == 2) {
        return List.of(2);
    }
    if (n == 3) {
        return List.of(3);
    }
    return List.of();
}

// %%
runTests(PrimeFactorsTest.class)

// %%
List<Integer> primeFactors(long n) {
    List<Integer> result = new ArrayList<Integer>();
    if (n == 2) {
        result.add(2);
    }
    if (n == 3) {
        result.add(3);
    }
    return result;
}

// %%
runTests(PrimeFactorsTest.class)

// %%
List<Integer> primeFactors(long n) {
    List<Integer> result = new ArrayList<Integer>();
    if (n % 2 == 0) {
        result.add(2);
    }
    if (n % 3 == 0) {
        result.add(3);
    }
    return result;
}

// %%
runTests(PrimeFactorsTest.class)

// %%
class PrimeFactorsTest {
    @Test
    void testPrimeFactorsOf1() {
        assertEquals(List.of(), primeFactors(1));
    }

    @Test
    void testPrimeFactorsOf2() {
        assertEquals(List.of(2), primeFactors(2));
    }

    @Test
    void testPrimeFactorsOf3() {
        assertEquals(List.of(3), primeFactors(3));
    }

    @Test
    void testPrimeFactorsOf4() {
        assertEquals(List.of(2, 2), primeFactors(4));
    }
}

// %%
runTests(PrimeFactorsTest.class)

// %%
List<Integer> primeFactors(long n) {
    List<Integer> result = new ArrayList<Integer>();
    if (n % 2 == 0) {
        result.add(2);
    }
    if (n % 3 == 0) {
        result.add(3);
    }
    if (n % 4 == 0) {
        result.add(2);
    }
    return result;
}

// %%
runTests(PrimeFactorsTest.class)

// %%
List<Integer> primeFactors(long n) {
    ArrayList<Integer> result = new ArrayList();
    if (n % 2 == 0) {
        result.add(2);
        n /= 2;
        if (n % 2 == 0)
            result.add(2);
    }
    if (n % 3 == 0)
        result.add(3);
    return result;
}

// %%
runTests(PrimeFactorsTest.class)

// %%
List<Integer> primeFactors(long n) {
    ArrayList<Integer> result = new ArrayList();
    while (n % 2 == 0) {
        result.add(2);
        n /= 2;
    }
    if (n % 3 == 0)
        result.add(3);
    return result;
}

// %%
runTests(PrimeFactorsTest.class)

// %%
List<Integer> primeFactors(int n) {
    ArrayList<Integer> result = new ArrayList();
    while (n % 2 == 0) {
        result.add(2);
        n /= 2;
    }
    while (n % 3 == 0) {
        result.add(3);
        n /= 3;
    }
    return result;
}

// %%
runTests(PrimeFactorsTest.class)

// %%
class PrimeFactorsTest {
    @Test
    void testPrimeFactorsOf1() {
        assertEquals(List.of(), primeFactors(1));
    }

    @Test
    void testPrimeFactorsOf2() {
        assertEquals(List.of(2), primeFactors(2));
    }

    @Test
    void testPrimeFactorsOf3() {
        assertEquals(List.of(3), primeFactors(3));
    }

    @Test
    void testPrimeFactorsOf4() {
        assertEquals(List.of(2, 2), primeFactors(4));
    }

    @Test
    void testPrimeFactorsOf5() {
        assertEquals(List.of(5), primeFactors(5));
    }
}

// %%
runTests(PrimeFactorsTest.class)

// %%
List<Integer> primeFactors(int n) {
    ArrayList<Integer> result = new ArrayList();
    while (n % 2 == 0) {
        result.add(2);
        n /= 2;
    }
    while (n % 3 == 0) {
        result.add(3);
        n /= 3;
    }
    while (n % 5 == 0) {
        result.add(5);
        n /= 5;
    }
    return result;
}

// %%
runTests(PrimeFactorsTest.class)

// %%
List<Integer> primeFactors(int n) {
    ArrayList<Integer> result = new ArrayList();
    for (int factor = 2; factor <= n; factor ++)
        while (n % factor == 0) {
            result.add(factor);
            n /= factor;
        }
    return result;
}

// %%
runTests(PrimeFactorsTest.class)

// %%
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

// %%
class PrimeFactorsTest {
    @ParameterizedTest
    @MethodSource("primeFactorArgs")
    void testPrimeFactors(List<Integer> expected, int n) {
        assertEquals(expected, primeFactors(n));
    }

    static Stream<Arguments> primeFactorArgs() {
        return Stream.of(
            Arguments.of(List.of(), 1),
            Arguments.of(List.of(2), 2),
            Arguments.of(List.of(3), 3),
            Arguments.of(List.of(2, 2), 4),
            Arguments.of(List.of(5), 5),
            Arguments.of(List.of(2, 3), 6),
            Arguments.of(List.of(2, 2, 3, 5, 7, 11, 13, 17), 1021020),
            Arguments.of(List.of(2027, 2029), 2027 * 2029),
            Arguments.of(List.of(7907, 7919), 7907 * 7919)
        );
    }
}

// %%
runTests(PrimeFactorsTest.class)

// %%
class PrimeFactorsTest {
    @ParameterizedTest
    @MethodSource("primeFactorArgs")
    void testPrimeFactors(List<Integer> factors) {
        int n = factors.stream().reduce(1, (a, b) -> a * b);
        assertEquals(factors, primeFactors(n));
    }

    static Stream<List<Integer>> primeFactorArgs() {
        return Stream.of(
            List.of(),
            List.of(2),
            List.of(3),
            List.of(2, 2),
            List.of(5),
            List.of(2, 3),
            List.of(2, 2, 3, 5, 7, 11, 13, 17),
            List.of(2027, 2029),
            List.of(7907, 7919)
        );
    }
}

// %%
runTests(PrimeFactorsTest.class)

// %% [markdown]
//
// Test-Klasse `TestComputeAndFormatPrimes`

// %%
class TestComputeAndFormatPrimes {
    @ParameterizedTest
    @MethodSource("primeFactorArgs")
    void testPrimeFactors(String expected, int n) {
        assertEquals(expected, computeAndFormatPrimes(n));
    }

    static Stream<Arguments> primeFactorArgs() {
        return Stream.of(
            Arguments.of("", 1),
            Arguments.of("2", 2),
            Arguments.of("3", 3),
            Arguments.of("2,2", 4),
            Arguments.of("5", 5),
            Arguments.of("2,3", 6),
            Arguments.of("2,2,3,5,7,11,13,17", 1021020),
            Arguments.of("2027,2029", 2027 * 2029),
            Arguments.of("7907,7919", 7907 * 7919)
        );
    }
}

// %%
runTests(TestComputeAndFormatPrimes.class);

// %% [markdown]
//
// Funktion `computeAndFormatPrimes`

// %%
import java.util.stream.Collectors;

// %%
String computeAndFormatPrimes(int n) {
    List<Integer> factors = primeFactors(n);
    return factors.stream()
                .map(m -> m.toString() )
                .collect(Collectors.joining( "," ));
}

// %%
computeAndFormatPrimes(1021020);

// %% [markdown]
//
// Funktion `computeAndWritePrimes`

// %%
void computeAndWritePrimes(int n) {
    System.out.println(computeAndFormatPrimes(n));
}

// %%
computeAndWritePrimes(1021020);

// %% [markdown]
//
// ## Kata: FizzBuzz
//
// Schreiben Sie eine Funktion
// ```java
// void printFizzBuzz(int n);
// ```
// die die Zahlen von 1 bis `n` auf dem Bildschirm ausgibt aber dabei
//
// - jede Zahl, die durch 3 teilbar ist, durch `Fizz` ersetzt
// - jede Zahl, die durch 5 teilbar ist, durch `Buzz` ersetzt
// - jede Zahl, die durch 3 und 5 teilbar ist, durch `FizzBuzz` ersetzt
//

// %% [markdown]
//
// Zum Beispiel soll `fizz_buzz(16)` die folgende Ausgabe erzeugen:
//
// ```plaintext
// 1
// 2
// Fizz
// 4
// Buzz
// Fizz
// 7
// 8
// Fizz
// Buzz
// 11
// Fizz
// 13
// 14
// FizzBuzz
// 16
// ```

// %%
public class FizzBuzz {
    public static void printFizzBuzz(int n) {
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                System.out.println("FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        // Example usage
        printFizzBuzz(16);
    }
}

// %%
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

// %%
public class FizzBuzz {
    public static List<String> generateFizzBuzz(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Input must be non-negative");

        List<String> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0)
                result.add("FizzBuzz");
            else if (i % 3 == 0)
                result.add("Fizz");
            else if (i % 5 == 0)
                result.add("Buzz");
            else
                result.add(String.valueOf(i));
        }
        return result;
    }

    public static void printFizzBuzz(int n, PrintWriter output) {
        List<String> fizzBuzzList = generateFizzBuzz(n);
        for (String item : fizzBuzzList) {
            output.println(item);
        }
    }

    public static void main(String[] args) {
        // Example usage
        printFizzBuzz(16, new PrintWriter(System.out, true));

        // Example usage with custom output
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printFizzBuzz(16, printWriter);
        System.out.println("Custom output:");
        System.out.println(stringWriter.toString());
    }
}

// %%
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// %%
class FizzBuzzTest {
    @Test
    void generateFizzBuzz_ReturnsCorrectSequenceFor15() {
        List<String> expected = Arrays.asList(
            "1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz"
        );
        List<String> result = FizzBuzz.generateFizzBuzz(15);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
        "1, 1",
        "3, Fizz",
        "5, Buzz",
        "15, FizzBuzz"
    })
    void generateFizzBuzz_ReturnsCorrectValueForSpecificNumbers(int number, String expected) {
        List<String> result = FizzBuzz.generateFizzBuzz(number);
        assertEquals(expected, result.get(result.size() - 1));
    }

    @Test
    void generateFizzBuzz_ReturnsEmptyListForZero() {
        List<String> result = FizzBuzz.generateFizzBuzz(0);
        assertTrue(result.isEmpty());
    }

    @Test
    void generateFizzBuzz_ThrowsIllegalArgumentExceptionForNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> FizzBuzz.generateFizzBuzz(-1));
    }

    @Test
    void generateFizzBuzz_ReturnsCorrectSequenceFor100() {
        List<String> result = FizzBuzz.generateFizzBuzz(100);
        assertEquals(100, result.size());
        assertEquals("1", result.get(0));
        assertEquals("2", result.get(1));
        assertEquals("Fizz", result.get(2));
        assertEquals("4", result.get(3));
        assertEquals("Buzz", result.get(4));
        assertEquals("Fizz", result.get(5));
        assertEquals("FizzBuzz", result.get(14));
        assertEquals("FizzBuzz", result.get(29));
        assertEquals("FizzBuzz", result.get(44));
        assertEquals("Buzz", result.get(99));
    }

    @Test
    void printFizzBuzz_WritesToProvidedPrintWriter() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        FizzBuzz.printFizzBuzz(5, printWriter);
        String[] result = stringWriter.toString().trim().split(System.lineSeparator());
        assertArrayEquals(new String[]{"1", "2", "Fizz", "4", "Buzz"}, result);
    }
}
