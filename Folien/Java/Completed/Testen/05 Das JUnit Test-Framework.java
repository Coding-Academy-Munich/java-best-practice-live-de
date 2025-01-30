// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Das JUnit Test-Framework</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ## Was ist JUnit?
//
// - JUnit ist ein modernes Test-Framework für Java
// - Open-Source
// - Einfach in Projekte zu integrieren
// - Viele Features
// - Struktur ähnlich zu xUnit-Test-Frameworks

// %% [markdown]
//
// ## Features von JUnit
//
// - Verwaltung von Tests und Test-Suites
// - Assertion-Bibliothek für Testfälle
// - Ausführung von Tests (Test Runner)
// - Versetzen des SuT in einen definierten Zustand (Test Fixtures)
// - Unterstützung für Parameterized Tests

// %% [markdown]
//
// ## Assertions in JUnit
//
// - `assertTrue`, `assertFalse` um Bedingungen zu prüfen
// - `assertEquals`, `assertNotEquals` um Werte zu prüfen
// - `assertSame`, `assertNotSame` um auf Referenzen zu prüfen
// - `assertNull`, `assertNotNull` um auf `null` zu prüfen
// - `assertThrows` um Exceptions zu prüfen

// %%
// %maven org.junit.jupiter:junit-jupiter-api:5.8.2

// %%
import static org.junit.jupiter.api.Assertions.*;

// %%
assertTrue(5 > 3);

// %%
assertFalse(2 > 5);

// %%
// assertTrue(1 > 4);

// %%
assertEquals(4, 2 + 2);

// %%
assertNotEquals(5, 2 + 2);

// %%
String str1 = new String("Hello");
String str2 = new String("Hello");

// %%
assertEquals(str1, str2);

// %%
assertTrue(str1.equals(str2));

// %%
// assertTrue("Hello".equals("World"));

// %%
// assertEquals("Hello", "World");


// %%
// assertSame("Hello", "Hello");

// %%
// assertSame(str1, str2);

// %%
assertSame(str1, str1);

// %%
// assertNotSame(str1, str1);

// %%
assertNull(null);

// %%
// assertNull(0);

// %%
assertNotNull(123);

// %% [markdown]
//
// ## Einschub: Lambda-Ausdrücke
//
// - Lambda-Ausdrücke sind eine Möglichkeit, Funktionen als Argumente zu
//   übergeben.
// - (In Java sind Lambdas streng genommen eine kompakte Syntax für die Implementierung
//   von Interfaces mit nur einer Methode, sogenannten Funktionalen Interfaces.)

// %%
interface MyFunction {
    int apply(int x, int y);
}

// %%
class MyAddFunction implements MyFunction {
    public int apply(int x, int y) {
        return x + y;
    }
}

// %%
MyFunction add = new MyAddFunction();

// %%
add.apply(2, 3);

// %%
add = (x, y) -> { return x + y; };

// %%
add.apply(2, 3);

// %%
add = (x, y) -> x + y;

// %%
add.apply(2, 3);

// %%
assertThrows(ArithmeticException.class, () -> {
    int result = 1 / 0;
});

// %%
// assertThrows(ArithmeticException.class, () -> 1 / 0);

// %%
int divide(int x, int y) {
    return x / y;
}

// %%
assertThrows(ArithmeticException.class, () -> divide(1, 0));

// %%
// assertThrows(ArithmeticException.class, () -> {
//     int result = 1 / 1;
// });

// %% [markdown]
//
// ## Test-Klassen
//
// - Tests werden in Klassen organisiert
// - `@Test`-Annotation an Methoden um Tests zu definieren
// - Assertions wie oben besprochen

// %%
// %maven org.junit.jupiter:junit-jupiter-api:5.8.2

// %%
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

// %%
public class JUnitBasicsTest {
    @Test
    public void testAddition() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testTrueCondition() {
        assertTrue(5 > 3);
    }

    @Test
    public void testFalseCondition() {
        assertFalse(2 > 5);
    }

    @Test
    public void testException() {
        assertThrows(ArithmeticException.class, () -> {
            int result = 1 / 0;
        });
    }
}

// %%
JUnitBasicsTest tests = new JUnitBasicsTest();

// %%
tests.testAddition()

// %%
// %maven org.junit.jupiter:junit-jupiter-engine:5.8.2
// %maven org.junit.platform:junit-platform-launcher:1.9.0

// %%
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

// %%
LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
    .selectors(selectClass(JUnitBasicsTest.class))
    .build();
Launcher launcher = LauncherFactory.create();
SummaryGeneratingListener listener = new SummaryGeneratingListener();

// %%
launcher.registerTestExecutionListeners(listener);
launcher.execute(request);

// %%
// Use StringWriter and PrintWriter to capture the summary output
StringWriter stringWriter = new StringWriter();
PrintWriter printWriter = new PrintWriter(stringWriter);
listener.getSummary().printTo(printWriter);

// %%
// Print the captured summary
System.out.println(stringWriter.toString());


// %% [markdown]
//
// Falls Sie Mehr Kontrolle über die Ausgabe wollen:

// %%
long testFoundCount = listener.getSummary().getTestsFoundCount();
long testFailedCount = listener.getSummary().getTestsFailedCount();

// %%
System.out.println("Total tests: " + testFoundCount);
System.out.println("Failed tests: " + testFailedCount);
System.out.println(testFailedCount == 0 ? "All tests passed!" : "Some tests failed.");

// %%
listener.getSummary().getFailures().forEach(failure -> {
    System.out.println("Failure in test: " + failure.getTestIdentifier().getDisplayName());
    System.out.println("Reason: " + failure.getException());
});

// %% [markdown]
//
// ## Workshop: JUnit Basics im Notebook
//
// In diesem Workshop sollen Sie eine einfache Testklasse schreiben und die
// Tests mit JUnit ausführen.
//
// Hier ist der Code, den Sie testen sollen:

// %%
public class SimpleMath {
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        return a / b;
    }
}

// %% [markdown]
//
// - Schreiben Sie Tests, die die Methoden der Klasse `SimpleMath` überprüfen.
// - Sie können dabei die folgende Klasse `SimpleMathTest` erweitern.

// %%
public class SimpleMathTest {
    @Test
    public void testAddition() {
        SimpleMath math = new SimpleMath();
        assertEquals(4, math.add(2, 2));
    }

    @Test
    public void testSubtraction() {
        SimpleMath math = new SimpleMath();
        assertEquals(0, math.subtract(2, 2));
    }

    @Test
    public void testMultiplication() {
        SimpleMath math = new SimpleMath();
        assertEquals(6, math.multiply(2, 3));
    }

    @Test
    public void testDivision() {
        SimpleMath math = new SimpleMath();
        assertEquals(2, math.divide(6, 3));
    }

    @Test
    public void testDivisionByZero() {
        SimpleMath math = new SimpleMath();
        assertThrows(ArithmeticException.class, () -> math.divide(1, 0));
    }
}

// %% [markdown]
//
// - Mit dem folgenden Code können Sie die Tests ausführen.

// %%
// %maven org.junit.jupiter:junit-jupiter-engine:5.8.2
// %maven org.junit.platform:junit-platform-launcher:1.9.0

// %%
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

// %%
void runTests() {
    LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
        .selectors(selectClass(SimpleMathTest.class))
        .build();
    Launcher launcher = LauncherFactory.create();
    SummaryGeneratingListener listener = new SummaryGeneratingListener();

    launcher.registerTestExecutionListeners(listener);
    launcher.execute(request);

    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    listener.getSummary().printTo(printWriter);

    System.out.println(stringWriter.toString());
}

// %%
runTests();
