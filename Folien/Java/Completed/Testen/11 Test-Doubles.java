// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Test-Doubles</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ## Test Doubles
//
// - Test Double: Vereinfachte Version einer Abhängigkeit im System
//   - z.B. Ersetzen einer Datenbankabfrage durch einen fixen Wert
// - Test Doubles sind wichtig zum Vereinfachen von Tests
// - Sie benötigen typischerweise ein Interface, das sie implementieren
// - Aber: zu viele oder komplexe Test Doubles machen Tests unübersichtlich
//   - Was wird von einem Test eigentlich getestet?

// %% [markdown]
//
// ## Arten von Test Doubles
//
// - Ausgehende Abhängigkeiten ("Mocks")
//   - Mocks
//   - Spies
// - Eingehende Abhängigkeiten ("Stubs")
//   - Dummies
//   - Stubs
//   - Fakes

// %% [markdown]
//
// ## Dummy
//
// - Objekt, das nur als Platzhalter dient
// - Wird übergeben, aber nicht verwendet
// - In Java manchmal `null`
// - Auch für ausgehende Abhängigkeiten

// %% [markdown]
//
// ## Stub
//
// - Objekt, das eine minimale Implementierung einer Abhängigkeit bereitstellt
// - Gibt typischerweise immer den gleichen Wert zurück
// - Wird verwendet um
//  - komplexe Abhängigkeiten zu ersetzen
//  - Tests deterministisch zu machen

// %% [markdown]
//
// ## Fake
//
// - Objekt, das eine einfachere Implementierung einer Abhängigkeit bereitstellt
// - Kann z.B. eine In-Memory-Datenbank sein
// - Wird verwendet um
//   - Tests zu beschleunigen
//   - Konfiguration von Tests zu vereinfachen

// %% [markdown]
//
// ## Spy
//
// - Objekt, das Informationen über die Interaktion mit ihm speichert
// - Wird verwendet um
//   - zu überprüfen, ob eine Abhängigkeit korrekt verwendet wird

// %% [markdown]
//
// ## Mock
//
// - Objekt, das Information über die erwartete Interaktion speichert
// - Typischerweise deklarativ konfigurierbar
// - Automatisierte Implementierung von Spies
// - Wird verwendet um
//   - zu überprüfen, ob eine Abhängigkeit korrekt verwendet wird

// %%
interface DataSource {
    public int GetValue();
}

// %%
interface DataSink {
    public void SetValue(int value);
}

// %%
class Processor {
    DataSource source;
    DataSink sink;

    public Processor(DataSource source, DataSink sink) {
        this.source = source;
        this.sink = sink;
    }

    public void Process() {
        int value = source.GetValue();
        sink.SetValue(value);
    }
}

// %%
class DataSourceStub implements DataSource {
    public int GetValue() { return 42; }
}

// %%
public class DataSinkSpy implements DataSink
{
    public List<Integer> Values = new ArrayList<>();

    public void SetValue(int value) {
        Values.add(value);
    }
}

// %%
void Check(boolean condition) {
    if (!condition) {
        System.out.println("Test failed!");
    } else {
        System.out.println("Success!");
    }
}

// %%
void TestProcessor() {
    DataSourceStub source = new DataSourceStub();
    DataSinkSpy sink = new DataSinkSpy();
    Processor processor = new Processor(source, sink);

    processor.Process();

    Check(sink.Values.size() == 1);
    Check(sink.Values.get(0) == 42);
}

// %%
TestProcessor();

// %% [markdown]
//
// ## Typischer Einsatz von Test Doubles
//
// - Zugriff auf Datenbank, Dateisystem
// - Zeit, Zufallswerte
// - Nichtdeterminismus
// - Verborgener Zustand

// %% [markdown]
//
// ## Workshop: Test Doubles
//
// Wir haben die folgenden Interfaces, die von der Funktion `test_me()`
// verwendet werden:

// %%
public interface IService1
{
    int GetValue();
}

// %%
public interface IService2
{
    void SetValue(int value);
}

// %%
public void TestMe(int i, int j, IService1 service1, IService2 service2)
{
    int value = 0;
    if (i > 0) {
        value = service1.GetValue();
    }
    if (j > 0) {
        service2.SetValue(value);
    }
}

// %% [markdown]
//
// Welche Arten von Test-Doubles brauchen Sie um die Funktion `test_me()` für
// die angegebenen Werte von `i` und `j` zu testen?
//
// | i | j | Service1 | Service2 |
// |---|---|----------|----------|
// | 0 | 0 |          |          |
// | 0 | 1 |          |          |
// | 1 | 0 |          |          |
// | 1 | 1 |          |          |

// %% [markdown]
//
// | i | j | Service1 | Service2 |
// |---|---|----------|----------|
// | 0 | 0 | Dummy    | Dummy    |
// | 0 | 1 | Dummy    | Spy/Mock |
// | 1 | 0 | Stub     | Dummy    |
// | 1 | 1 | Stub     | Spy/Mock |

// %% [markdown]
//
// Implementieren Sie die entsprechenden Doubles und schreiben Sie die Tests

// %%
public class Service1Stub implements IService1
{
    public int GetValue() { return 42; }
}

// %%
public class Service2Spy implements IService2
{
    public List<Integer> Values = new ArrayList<>();

    public void SetValue(int value) { Values.add(value); }
}

// %%
public void TestTestMe_0_0()
{
    TestMe(0, 0, null, null);
}

// %%
public void TestTestMe_0_1()
{
    Service2Spy service2 = new Service2Spy();
    TestMe(0, 1, null, service2);
    Check(service2.Values.size() == 1);
    Check(service2.Values.get(0) == 0);
}

// %%
public void TestTestMe_1_0()
{
    Service1Stub service1 = new Service1Stub();
    TestMe(1, 0, service1, null);
}

// %%
public void TestTestMe_1_1()
{
    Service1Stub service1 = new Service1Stub();
    Service2Spy service2 = new Service2Spy();
    TestMe(1, 1, service1, service2);
    Check(service2.Values.size() == 1);
    Check(service2.Values.get(0) == 42);
}
