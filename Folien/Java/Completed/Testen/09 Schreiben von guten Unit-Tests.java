// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Schreiben von guten Unit-Tests</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ## Mechanik von Unit-Tests
//
// Unit-Tests sollen
// - automatisiert sein: keine manuelle Interaktion
// - selbsttestend sein: Pass/Fail
// - feingranular sein
// - schnell sein
// - isoliert sein
// - zu jedem Zeitpunkt erfolgreich ausführbar sein

// %% [markdown]
//
// ## Einfache Struktur!
//
// <ul>
//   <li>Einfache, standardisierte Struktur<br>&nbsp;<br>
//     <table style="display:inline;margin:20px 20px;">
//     <tr><td style="text-align:left;width:60px;padding-left:15px;">Arrange</td>
//         <td style="text-align:left;width:60px;padding-left:15px;border-left:1px solid
//         black;">Given</td> <td
//         style="text-align:left;width:800px;padding-left:15px;border-left:1px solid
//         black;">
//           Bereite das Test-Environment vor</td></tr>
//     <tr><td style="text-align:left;padding-left:15px;">Act</td>
//         <td style="text-align:left;width:60px;padding-left:15px;border-left:1px solid
//         black;">
//            When</td>
//         <td style="text-align:left;width:800px;padding-left:15px;border-left:1px
//         solid black;">
//            Führe die getestete Aktion aus (falls vorhanden)</td></tr>
//     <tr><td style="text-align:left;padding-left:15px;">Assert</td>
//         <td style="text-align:left;width:60px;padding-left:15px;border-left:1px solid
//         black;">
//            Then</td>
//         <td style="text-align:left;width:800px;padding-left:15px;border-left:1px
//         solid black;">
//            Überprüfe die Ergebnisse</td></tr>
//     </table>
//     <br>&nbsp;
//   </li>
//   <li>Wenig Code
//     <ul>
//       <li>Wenig Boilerplate</li>
//       <li>Factories, etc. für Tests</li>
//     </ul>
//   </li>
// </ul>

// %%
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// %%
public static void check(boolean condition) {
    if (!condition) {
        System.out.println("Test failed!");
    } else {
        System.out.println("Success!");
    }
}

// %%
public static void testInsert() {
    // Arrange
    List<Integer> x = new ArrayList<>(Arrays.asList(1, 2, 3));
    List<Integer> y = new ArrayList<>(Arrays.asList(10, 20));

    // Act
    x.addAll(y);

    // Assert
    check(x.equals(Arrays.asList(1, 2, 3, 10, 20)));
}


// %%
testInsert();

// %% [markdown]
//
// - Wie viele Tests wollen wir haben?
// - Wie viele Werte wollen wir überprüfen?

// %% [markdown]
//
// ## Versuch: Erschöpfendes Testen
//
// - Wir schreiben erschöpfende Tests, d.h. Tests, die alle möglichen Eingaben eines
//   Programms abdecken

// %% [markdown]
//
// - Erschöpfendes Testen ist nicht möglich
// - Beispiel Passworteingabe:
//   - Angenommen, Passwörter mit maximal 20 Zeichen sind zulässig,
//     80 Eingabezeichen sind erlaubt (große und kleine Buchstaben, Sonderzeichen)
//   - Das ergibt $80^{20}$ = 115.292.150.460.684.697.600.000.000.000.000.000.000
//     mögliche Eingaben
//   - Bei 10ns für einen Test würde man ca. $10^{24}$ Jahre brauchen, um alle Eingaben
//     zu testen
//   - Das Universum ist ungefähr $1.4 \times 10^{10}$ Jahre alt

// %% [markdown]
//
// ## Effektivität und Effizienz von Tests
//
// - Unit-Tests sollen effektiv und effizient sein
//   - Effektiv: Die Tests sollen so viele Fehler wie möglich finden
//   - Effizient: Wir wollen die größte Anzahl an Fehlern mit der geringsten Anzahl
//     an möglichst einfachen Tests finden
// - Effizienz ist wichtig, da Tests selbst Code sind, der gewartet werden muss und
//   Fehler enthalten kann

// %% [markdown]
//
// ## Wie schreibt man gute Unit-Tests?
//
// - Teste beobachtbares Verhalten, nicht Implementierung
// - Bevorzuge Tests von Werten gegenüber Tests von Zuständen
// - Bevorzuge Tests von Zuständen gegenüber Tests von Interaktion
// - Verwende Test-Doubles dann (aber auch nur dann), wenn eine Abhängigkeit
//   "eine Rakete abfeuert"
// - (Diese Regeln setzen voraus, dass der Code solche Tests erlaubt)

// %% [markdown]
//
// ### Warum Tests von beobachtbarem Verhalten, nicht Implementierung?
//
// Beobachtbares Verhalten
// - ist leichter zu verstehen
// - ist stabiler als Implementierung
// - entspricht eher dem Kundennutzen

// %% [markdown]
//
// ## Teste beobachtbares Verhalten, nicht Implementierung
//
// - Abstrahiere so weit wie möglich von Implementierungsdetails
//   - Auch auf Unit-Test Ebene
// - Oft testen sich verschiedene Methoden gegenseitig
// - Dies erfordert manchmal die Einführung von zusätzlichen Methoden
//     - Diese Methoden sollen für Anwender sinnvoll sein, nicht nur für Tests
//     - Oft "abstrakter Zustand" von Objekten
//     - **Nicht:** konkreten Zustand öffentlich machen

// %%
import java.util.ArrayList;
import java.util.List;

// %%
class Stack {
    private List<Integer> items = new ArrayList<>();

    public void push(int newItem) {
        items.add(newItem);
    }

    public int pop() {
        if (items.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return items.remove(items.size() - 1);
    }

    // Good extension: useful, doesn't expose implementation
    public int size() {
        return items.size();
    }

    // Bad extension: exposes implementation
    public List<Integer> getItems() {
        return items;
    }
}

// %% [markdown]
//
// ### Tests, wenn nur `Push()` und `Pop()` verfügbar sind

// %%
void testStack1() {
    Stack s = new Stack();
    s.push(5);
    check(s.pop() == 5);
}

// %%
testStack1();

// %%
void testStack2() {
    Stack s = new Stack();
    s.push(5);
    s.push(10);
    check(s.pop() == 10);
    check(s.pop() == 5);
}

// %%
testStack2();

// %%
void testStack3() {
    Stack s = new Stack();
    try {
        s.pop();
        check(false);
    } catch (IllegalStateException e) {
        check(true);
    }
}

// %%
testStack3();

// %% [markdown]
//
// ### Tests, wenn `size()` verfügbar ist

// %%
void testStackWithSize1() {
    Stack s = new Stack();
    check(s.size() == 0);
}

// %%
testStackWithSize1();

// %%
void testStackWithSize2() {
    Stack s = new Stack();
    s.push(5);
    s.push(10);
    check(s.size() == 2);
}

// %%
testStackWithSize2();

// %% [markdown]
//
// ### Tests, wenn `getItems()` verfügbar ist


// %%
void testStackWithGetItems() {
    Stack s = new Stack();
    s.push(5);
    s.push(10);
    check(s.getItems().equals(List.of(5, 10)));
}

// %%
testStackWithGetItems();

// %% [markdown]
//
// ## Werte > Zustand > Interaktion
//
// - Verständlicher
// - Leichter zu testen
// - Oft stabiler gegenüber Refactorings
//
// Ausnahme: Testen von Protokollen

// %% [markdown]
//
// ### Funktionen/Werte

// %%
static int add(int x, int y) { return x + y; }

// %%
check(add(2, 3) == 5);

// %% [markdown]
//
// ### Zustand

// %%
class Adder {
    private int x;
    private int y;
    private int result;

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getResult() { return result; }

    public void add() { result = x + y; }
}


// %%
void testAdder() {
    Adder adder = new Adder();
    adder.setX(2);
    adder.setY(3);

    adder.add();

    check(adder.getResult() == 5);
}

// %%
testAdder();

// %% [markdown]
//
// ### Seiteneffekt/Interaktion
//
// - Mit Interface

// %%
interface AbstractAdder {
    void add(int x, int y);
}

// %%
class InteractionAdder {
    private final AbstractAdder adder;

    public InteractionAdder(AbstractAdder adder) {
        this.adder = adder;
    }

    public void add(int x, int y) {
        adder.add(x, y);
    }
}


// %% [markdown]
//
// Test benötigt Mock/Spy

// %%
import java.util.ArrayList;
import java.util.List;
import static java.util.AbstractMap.SimpleEntry;

// %%
class AdderSpy implements AbstractAdder {
    private final List<SimpleEntry<Integer, Integer>> calls = new ArrayList<>();

    public List<SimpleEntry<Integer, Integer>> getCalls() {
        return calls;
    }

    @Override
    public void add(int x, int y) {
        calls.add(new SimpleEntry<>(x, y));
    }
}

// %%
void testAdderWithSpy() {
    AdderSpy spy = new AdderSpy();
    InteractionAdder adder = new InteractionAdder(spy);
    adder.add(2, 3);

    check(spy.getCalls().size() == 1);
    check(spy.getCalls().get(0).getKey() == 2);
    check(spy.getCalls().get(0).getValue() == 3);
}

// %%
testAdderWithSpy();

// %% [markdown]
//
// ### Seiteneffekt/Interaktion
//
// - Ohne Interface

// %%
class SideEffectAdder {
    static void addAndPrint(int x, int y) {
        System.out.println("Result: " + (x + y));
    }
}

// %%
SideEffectAdder.addAndPrint(1, 2);

// %%
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

// %%
String addAndCaptureOutput(int x, int y) {
    // Save the original System.out
    PrintStream originalOut = System.out;

    // Create a stream to hold the output
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(byteArrayOutputStream);

    // Set the standard output to use our stream
    System.setOut(ps);

    // Call the method
    SideEffectAdder.addAndPrint(x, y);

    // Flush the stream and get the output as a string
    System.out.flush();
    String output = byteArrayOutputStream.toString().trim();

    // Restore the original System.out
    System.setOut(originalOut);

    return output;
}

// %%
addAndCaptureOutput(2, 3)

// %%
check(addAndCaptureOutput(2, 3).equals("Result: 5"));


// %% [markdown]
//
// ## Wie schreibt man testbaren Code?
//
// - Umwandeln von weniger testbarem in besser testbaren Stil
//   - Beobachtbarkeit (`code/completed/observable-state-machine`)
//   - Keine globalen oder statischen Daten
//   - Unveränderliche Datenstrukturen (Werte)
// - Gutes objektorientiertes Design
//   - Hohe Kohäsion
//   - Geringe Kopplung, Management von Abhängigkeiten
// - Etc.

// %% [markdown]
//
// ## Prozess
//
// - Iteratives Vorgehen
//   - Kleine Schritte mit Tests
// - Test-Driven Development (TDD)
//   - Schreiben von Tests vor Code

// %% [markdown]
//
// ## Mini-Workshop: Bessere Testbarkeit
//
// - Wie können Sie Tests für die folgenden Funktionen/Klassen schreiben?
// - Wie können Sie die folgenden Funktionen/Klassen verbessern, um sie besser
//   testbar zu machen?
// - Was für Nachteile ergeben sich dadurch?

// %%
import java.util.*;

// %%
class Counter {
    private static int c = 0;
    public static int count() {
        return c++;
    }
}

// %%
for (int i = 0; i < 3; ++i) {
    System.out.println(Counter.count());
}

// %%
class Counter2 {
    private int c = 0;
    public int invoke() { return c++; }
}

// %%
Counter2 counter = new Counter2();

// %%
for (int i = 0; i < 3; ++i) {
    System.out.println(counter.invoke());
}

// %%
enum State {
    OFF,
    ON
}

// %%
class Switch {
    private State state = State.OFF;

    public void toggle() {
        state = state == State.OFF ? State.ON : State.OFF;
        System.out.println("Switch is " + (state == State.OFF ? "OFF" : "ON"));
    }
}

// %%
Switch s = new Switch();

// %%
for (int i = 0; i < 3; ++i) {
    s.toggle();
}

// %%
class SwitchWithGetter {
    private State state = State.OFF;

    public void toggle() {
        state = state == State.OFF ? State.ON : State.OFF;
        System.out.println("Switch is " + (state == State.OFF ? "OFF" : "ON"));
    }

    public State getState() { return state; }
}

// %%
SwitchWithGetter sg = new SwitchWithGetter();

// %%
for (int i = 0; i < 3; ++i) {
    sg.toggle();
}

// %%
System.out.println("Switch is " + (sg.getState() == State.OFF ? "OFF" : "ON"));

// %%
import java.util.function.Consumer;

class ObservableSwitch {
    private State state = State.OFF;
    private List<Consumer<State>> observers = new ArrayList<>();

    public void toggle() {
        state = state == State.OFF ? State.ON : State.OFF;
        notify(state);
    }

    public void registerObserver(Consumer<State> f) { observers.add(f); }

    private void notify(State s) {
        for (Consumer<State> f : observers) {
            f.accept(s);
        }
    }
}

// %%
ObservableSwitch os = new ObservableSwitch();

// %%
os.registerObserver(s ->
    System.out.println("Switch is " + (s == State.OFF ? "OFF" : "ON")));

// %%
for (int i = 0; i < 3; ++i) {
    os.toggle();
}

// %%
static void printFib(int n) {
    int a = 0;
    int b = 1;
    for (int i = 0; i < n; ++i) {
        System.out.println("fib(" + i + ") = " + b);
        int tmp = a;
        a = b;
        b = tmp + b;
    }
}

// %%
printFib(5);

// %%
static int fib1(int n) {
    int a = 0;
    int b = 1;
    for (int i = 0; i < n; ++i) {
        int tmp = a;
        a = b;
        b = tmp + b;
    }
    return b;
}

// %%
static void printFib1(int n) {
    for (int i = 0; i < n; ++i) {
        System.out.println("fib(" + i + ") = " + fib1(i));
    }
}

// %%
printFib1(5);

// %%
import java.util.function.BiConsumer;

static void fibGen(int n, BiConsumer<Integer, Integer> f) {
    int a = 0;
    int b = 1;
    for (int i = 0; i < n; ++i) {
        f.accept(i, b);
        int tmp = a;
        a = b;
        b = tmp + b;
    }
}

// %%
static void printFib2(int n) {
    fibGen(n, (i, x) ->
        System.out.println("fib(" + i + ") = " + x));
}

// %%
printFib2(5);
