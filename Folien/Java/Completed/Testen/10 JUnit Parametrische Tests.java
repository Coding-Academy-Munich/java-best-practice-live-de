// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>JUnit Parametrische Tests</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %%
boolean isLeapYear(int year) {
    return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
}

// %%
// %maven org.junit.jupiter:junit-jupiter-api:5.8.2
// %maven org.junit.jupiter:junit-jupiter-engine:5.8.2
// %maven org.junit.jupiter:junit-jupiter-params:5.8.2
// %maven org.junit.platform:junit-platform-launcher:1.9.3

// %%
// %jars .
// %classpath testrunner-0.1.jar

// %%
import static testrunner.TestRunner.runTests;

// %%
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// %%
class LeapYearTestsV1 {
    @Test
    void yearDivisibleBy4ButNot100IsLeapYear() {
        assertTrue(isLeapYear(2004));
    }

    @Test
    void yearDivisibleBy400IsLeapYear() {
        assertTrue(isLeapYear(2000));
    }

    @Test
    void yearsNotDivisibleBy4AreNotLeapYears() {
        assertFalse(isLeapYear(2001));
        assertFalse(isLeapYear(2002));
        assertFalse(isLeapYear(2003));
    }

    @Test
    void yearDivisibleBy100ButNot400IsNotLeapYear() {
        assertFalse(isLeapYear(1900));
    }
}


// %%
runTests(LeapYearTestsV1.class);


// %%
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

// %%
class LeapYearTestsV2 {
    @Test
    void yearDivisibleBy4ButNot100IsLeapYear() {
        assertTrue(isLeapYear(2004));
    }

    @Test
    void yearDivisibleBy400IsLeapYear() {
        assertTrue(isLeapYear(2000));
    }

    @ParameterizedTest
    @ValueSource(ints = {2001, 2002, 2003})
    void yearsNotDivisibleBy4IsNotALeapYear(int year) {
        assertFalse(isLeapYear(year));
    }

    @Test
    void yearDivisibleBy100ButNot400IsNotLeapYear() {
        assertFalse(isLeapYear(1900));
    }
}

// %%
runTests(LeapYearTestsV2.class);

// %%
class LeapYearTestsV3 {
    @ParameterizedTest
    @ValueSource(ints = {2001, 2002, 2003, 1900})
    void nonLeapYears(int year) {
        assertFalse(leapYear.isLeapYear(year));
    }

    @ParameterizedTest
    @ValueSource(ints = {2004, 2000})
    void leapYears(int year) {
        assertTrue(leapYear.isLeapYear(year));
    }
}

// %%
runTests(LeapYearTestsV3.class);

// %% [markdown]
//
// ## Andere Datenquellen
//
// - `@ValueSource` kann nur ein einziges Argument liefern
// - `@MethodSource` liefert Argument mittels (statischer) Factory-Methode
//   - Methodenname wir als String übergeben
//   - `Stream` oder `Iterable` von Argumenten
//   - `Arguments`-Objekte für komplexe Argumente
// - `@CsvSource`/`@CsvFileSource` liefern Argumente aus CSV-Daten

// %%
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.MethodSource;

// %%
class LeapYearTestsV4 {
    @ParameterizedTest
    @MethodSource("nonLeapYears")
    void nonLeapYears(int year) {
        assertFalse(isLeapYear(year));
    }

    static Stream<Integer> nonLeapYears() {
        return Stream.of(2001, 2002, 2003, 1900);
    }

    @ParameterizedTest
    @MethodSource
    void leapYears(int year) {
        assertTrue(isLeapYear(year));
    }

    static Stream<Integer> leapYears() {
        return Stream.of(2004, 2000);
    }
}

// %%
runTests(LeapYearTestsV4.class);

// %%
import org.junit.jupiter.params.provider.Arguments;

// %%
class LeapYearTestsV5 {
    @ParameterizedTest
    @MethodSource("years")
    void testLeapYear(int year, boolean expected) {
        assertEquals(expected, isLeapYear(year));
    }

    static Stream<Arguments> years() {
        return Stream.of(
            Arguments.of(2001, false),
            Arguments.of(2002, false),
            Arguments.of(2003, false),
            Arguments.of(1900, false),
            Arguments.of(2004, true),
            Arguments.of(2000, true)
        );
    }
}

// %%
runTests(LeapYearTestsV5.class);

// %%
import org.junit.jupiter.params.provider.CsvSource;

// %%
class LeapYearTestsV6 {
    @ParameterizedTest
    @CsvSource({
        "2001, false",
        "2002, false",
        "2003, false",
        "1900, false",
        "2004, true",
        "2000, true"
    })
    void testLeapYear(int year, boolean expected) {
        assertEquals(expected, isLeapYear(year));
    }
}

// %%
runTests(LeapYearTestsV6.class);

// %%
import org.junit.jupiter.params.provider.CsvFileSource;

// %%
// %classpath leap-years.jar

// %%
var leapYearJar = new java.util.jar.JarFile("leap-years.jar")

// %%
leapYearJar.stream().map(e -> e.getName()).forEach(System.out::println);

// %%
new BufferedReader(
    new InputStreamReader(
        leapYearJar.getInputStream(leapYearJar.entries().nextElement())))
            .lines().forEach(System.out::println);

// %%
class LeapYearTestsV7 {
    @ParameterizedTest
    @CsvFileSource(resources = "/leap-years.csv")
    void testLeapYear(int year, boolean expected) {
        assertEquals(expected, isLeapYear(year));
    }
}

// %%
runTests(LeapYearTestsV7.class);

// %% [markdown]
//
// ## Workshop: Parametrisierte Tests
//
// - Schreiben Sie parametrisierte Tests für die folgenden Funktionen.
// - Verwenden Sie dabei `@ValueSource`, `@MethodSource`, `@CsvSource` jeweils
//   mindestens einmal.

// %%
boolean isPrime(int n) {
    if (n <= 1) {
        return false;
    }
    for (int i = 2; i <= Math.sqrt(n); i++) {
        if (n % i == 0) {
            return false;
        }
    }
    return true;
}

// %%
class PrimeTests {
    @ParameterizedTest
    @ValueSource(ints = {1, 4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20})
    void testNonPrimes(int n) {
        assertFalse(isPrime(n));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19})
    void testPrimes(int n) {
        assertTrue(isPrime(n));
    }
}

// %%
boolean isPalindrome(String s) {
    return s.equals(new StringBuilder(s).reverse().toString());
}

// %%
class PalindromeTests {
    @ParameterizedTest
    @ValueSource(strings = {"", "a", "aa", "aba", "abba", "abcba"})
    void testPalindromes(String s) {
        assertTrue(isPalindrome(s));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab", "abc", "abcd", "abcde"})
    void testNonPalindromes(String s) {
        assertFalse(isPalindrome(s));
    }
}

// %%
boolean containsDigit(int n, int digit) {
    return String.valueOf(n).contains(String.valueOf(digit));
}

// %%
class ContainsDigitTests {
    @ParameterizedTest
    @MethodSource("digits")
    void testContainsDigit(int n, int digit, boolean expected) {
        assertEquals(expected, containsDigit(n, digit));
    }

    static Stream<Arguments> digits() {
        return Stream.of(
            Arguments.of(123, 1, true),
            Arguments.of(123, 2, true),
            Arguments.of(123, 3, true),
            Arguments.of(123, 4, false),
            Arguments.of(123, 5, false),
            Arguments.of(123, 6, false)
        );
    }
}

// %%
String substringFollowing(String s, String prefix) {
    int index = s.indexOf(prefix);
    if (index == -1) {
        return s;
    }
    return s.substring(index + prefix.length());
}

// %%
class SubstringFollowingTests {
    @ParameterizedTest
    @CsvSource({
        "Hello, He, llo",
        "Hello, Hel, lo",
        "Hello, Hello, ",
        "Hello, ello, Hello",
        "Hello, o, Hello"
    })
    void testSubstringFollowing(String s, String prefix, String expected) {
        assertEquals(expected, substringFollowing(s, prefix));
    }
}
