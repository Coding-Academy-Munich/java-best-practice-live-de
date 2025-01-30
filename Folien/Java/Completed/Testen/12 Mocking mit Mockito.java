// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Mocking mit Mockito</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// # Das Mockito Mocking Framework
//
// - [Mockito](https://site.mockito.org) ist ein Mocking-Framework für Java
// - Ermöglicht das Erstellen von Mock-Objekten

// %%
// %maven org.junit.jupiter:junit-jupiter-api:5.8.2
// %maven org.junit.jupiter:junit-jupiter-engine:5.8.2
// %maven org.junit.platform:junit-platform-launcher:1.9.3
// %maven org.mockito:mockito-core:4.11.+

// %%
// %jars .
// %classpath testrunner-0.1.jar

// %%
import static testrunner.TestRunner.runTests;

// %% [markdown]
//
// ## Beispiel: Mocken einer Liste
//
// - Erstellen eines Mock-Objekts für eine Liste
// - Implementiert alle Methoden des `List`-Interfaces
// - Kann verwendet werden, um Methodenaufrufe zu überprüfen

// %%
import java.util.List;

// %%
import static org.mockito.Mockito.*;

// %%
List mockedList = mock(List.class);

// %%
mockedList.add("Hello!");

// %%
verify(mockedList).add("Hello!");

// %%
verify(mockedList).add("Hello!");

// %%
// verify(mockedList).add("World!");

// %% [markdown]
//
// - Überprüfen der Anzahl der Aufrufe:

// %%
List mockedList = mock();  // Mockito >= 4.10

// %%
mockedList.add("Hello!");

// %%
verify(mockedList).add("Hello!");

// %%
mockedList.add("Hello!");

// %%
// verify(mockedList).add("Hello!");

// %%
verify(mockedList, times(2)).add("Hello!");

// %% [markdown]
//
// - Überprüfen, dass eine Methode nicht aufgerufen wurde:

// %%
List mockedList = mock();

// %%
verify(mockedList, never()).clear();

// %% [markdown]
//
// - Argument Matcher:
//   - `any()`, `anyString()`, ...
//   - `isNull()`, `endsWith()`, `argThat()`, ...

// %%
List mockedList = mock();
mockedList.add("Hello!");

// %%
verify(mockedList).add(anyString());

// %%
verify(mockedList).add(notNull());

// %%
verify(mockedList).add(endsWith("lo!"));

// %%
// verify(mockedList).add(startsWith("No"));


// %%
List mockedList = mock();
mockedList.add(null);

// %%
verify(mockedList).add(isNull());

// %% [markdown]
//
// - Mindest- und Maximalanzahl von Aufrufen:

// %%
List mockedList = mock();

// %%
mockedList.add("Once!");
mockedList.add("Twice!");
mockedList.add("Twice!");
mockedList.add("Three times!");
mockedList.add("Three times!");
mockedList.add("Three times!");

// %%
verify(mockedList, atLeastOnce()).add("Once!");

// %%
verify(mockedList, atLeastOnce()).add("Twice!");

// %%
verify(mockedList, atLeast(2)).add("Twice!");

// %%
verify(mockedList, atMost(3)).add("Three times!");

// %% [markdown]
//
// ## Stubbing
//
// - Manchmal ist es notwendig, das Verhalten eines Mock-Objekts zu definieren
// - Mit `when()` und `thenReturn()` und `thenThrow()` kann das Verhalten festgelegt werden

// %%
List mockedList = mock();

// %%
when(mockedList.get(0)).thenReturn("Hello!");

// %%
when(mockedList.get(1)).thenThrow(new RuntimeException("No Value!"));

// %%
mockedList.get(0)

// %%
// mockedList.get(1)

// %%
verify(mockedList).get(0)

// %%
// verify(mockedList).get(1)

// %% [markdown]
//
// ## Verwendung von Mockito in Tests
//
// - In JUnit-Tests können Mock-Objekte verwendet werden, um Abhängigkeiten zu
//   simulieren
// - Die `verify()`-Methode wirft eine Exception, wenn sie einen Fehler findet
// - Das führt zu einem fehlgeschlagenen Test

// %%
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// %%
class ListTest {
    @Test
    void testList() {
        List mockedList = mock(List.class);

        mockedList.add("Hello!");

        verify(mockedList).add("Hello!");
    }

    @Test
    void failedTest() {
        List mockedList = mock(List.class);

        mockedList.add("Hello!");

        verify(mockedList).add("World!");
    }
}

// %%
runTests(ListTest.class);


// %% [markdown]
//
// ## Workshop: Test eines Steuerungssystems für ein Raumschiff
//
// In diesem Workshop arbeiten Sie mit einem einfachen Steuerungssystem für ein
// Raumschiff. Das System interagiert mit verschiedenen Sensoren und einem
// Funksender. Ihre Aufgabe ist es, Tests für dieses System mit Mockito zu
// schreiben.
//
// Hier ist eine einfache Implementierung unseres Raumschiff-Steuerungssystems:

// %%
interface TemperatureSensor {
    double getTemperature();
}

// %%
interface FuelSensor {
    double getFuelLevel();
}

// %%
interface RadioTransmitter {
    void transmit(String message);
}

// %%
class SpacecraftControlSystem {
    private TemperatureSensor tempSensor;
    private FuelSensor fuelSensor;
    private RadioTransmitter radio;

    public SpacecraftControlSystem(TemperatureSensor tempSensor, FuelSensor fuelSensor, RadioTransmitter radio) {
        this.tempSensor = tempSensor;
        this.fuelSensor = fuelSensor;
        this.radio = radio;
    }

    public void checkAndReportStatus() {
        double temp = tempSensor.getTemperature();
        double fuel = fuelSensor.getFuelLevel();

        if (temp > 100) {
            radio.transmit("Warning: High temperature!");
        }

        if (fuel < 10) {
            radio.transmit("Warning: Low fuel!");
        }

        radio.transmit("Status: Temperature " + temp + ", Fuel " + fuel);
    }
}

// %% [markdown]
//
// ## Beispiel
//
// So könnten Sie dieses System verwenden:

// %%
class RealTemperatureSensor implements TemperatureSensor {
    public double getTemperature() {
        return 75.0; // Simulated temperature reading
    }
}

// %%
class RealFuelSensor implements FuelSensor {
    public double getFuelLevel() {
        return 50.0; // Simulated fuel level
    }
}

// %%
class RealRadioTransmitter implements RadioTransmitter {
    public void transmit(String message) {
        System.out.println("Transmitting: " + message);
    }
}

// %%
TemperatureSensor realTempSensor = new RealTemperatureSensor();
FuelSensor realFuelSensor = new RealFuelSensor();
RadioTransmitter realRadio = new RealRadioTransmitter();

// %%
SpacecraftControlSystem spacecraft = new SpacecraftControlSystem(
                                            realTempSensor, realFuelSensor, realRadio);

// %%
spacecraft.checkAndReportStatus();

// %% [markdown]
//
// Ihre Aufgabe ist es, Tests für das `SpacecraftControlSystem` unter Verwendung
// von Mockito zu schreiben. Implementieren Sie die folgenden Testfälle:
//
// 1. Testen des normalen Betriebs:
//    - Überprüfen Sie den normalen Betrieb des Raumschiffs, wenn die Temperatur
//      normal und der Kraftstoffstand ausreichend ist.
// 2. Testen der Warnung bei hoher Temperatur:
//    - Überprüfen Sie, dass das Raumschiff eine Warnung bei einer Temperatur
//      über 100 Grad überträgt.
// 3. Testen der Warnung bei niedrigem Kraftstoffstand:
//    - Überprüfen Sie, dass das Raumschiff eine Warnung bei einem Kraftstoffstand
//      unter 10 überträgt.
// 4. Testen mehrerer Warnungen:
//    - Überprüfen Sie, dass das Raumschiff sowohl eine Warnung bei hoher Temperatur
//      als auch bei niedrigem Kraftstoffstand überträgt, wenn beide Bedingungen
//      erfüllt sind.

// %% [markdown]
//
// #### Bonusaufgabe:
//
// 5. Testen der Fehlerbehandlung:
//    - Ändern Sie das `SpacecraftControlSystem`, um Ausnahmen von den Sensoren
//      zu behandeln, und schreiben Sie einen Test, um dieses Verhalten zu überprüfen.

// %%
// %maven org.junit.jupiter:junit-jupiter-api:5.8.2
// %maven org.junit.jupiter:junit-jupiter-engine:5.8.2
// %maven org.junit.platform:junit-platform-launcher:1.9.3
// %maven org.mockito:mockito-core:4.11.+

// %%
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// %%
class SpacecraftControlSystemTest {
    @Test
    void testNormalOperation() {
        TemperatureSensor tempSensor = mock(TemperatureSensor.class);
        FuelSensor fuelSensor = mock(FuelSensor.class);
        RadioTransmitter radio = mock(RadioTransmitter.class);

        when(tempSensor.getTemperature()).thenReturn(75.0);
        when(fuelSensor.getFuelLevel()).thenReturn(50.0);

        SpacecraftControlSystem spacecraft = new SpacecraftControlSystem(tempSensor, fuelSensor, radio);
        spacecraft.checkAndReportStatus();

        verify(radio).transmit("Status: Temperature 75.0, Fuel 50.0");
        verify(radio, never()).transmit("Warning: High temperature!");
        verify(radio, never()).transmit("Warning: Low fuel!");
    }

    @Test
    void testHighTemperatureWarning() {
        TemperatureSensor tempSensor = mock(TemperatureSensor.class);
        FuelSensor fuelSensor = mock(FuelSensor.class);
        RadioTransmitter radio = mock(RadioTransmitter.class);

        when(tempSensor.getTemperature()).thenReturn(110.0);
        when(fuelSensor.getFuelLevel()).thenReturn(50.0);

        SpacecraftControlSystem spacecraft = new SpacecraftControlSystem(tempSensor, fuelSensor, radio);
        spacecraft.checkAndReportStatus();

        verify(radio).transmit("Warning: High temperature!");
        verify(radio).transmit("Status: Temperature 110.0, Fuel 50.0");
        verify(radio, never()).transmit("Warning: Low fuel!");
    }

    @Test
    void testLowFuelWarning() {
        TemperatureSensor tempSensor = mock(TemperatureSensor.class);
        FuelSensor fuelSensor = mock(FuelSensor.class);
        RadioTransmitter radio = mock(RadioTransmitter.class);

        when(tempSensor.getTemperature()).thenReturn(75.0);
        when(fuelSensor.getFuelLevel()).thenReturn(5.0);

        SpacecraftControlSystem spacecraft = new SpacecraftControlSystem(tempSensor, fuelSensor, radio);
        spacecraft.checkAndReportStatus();

        verify(radio).transmit("Warning: Low fuel!");
        verify(radio).transmit("Status: Temperature 75.0, Fuel 5.0");
        verify(radio, never()).transmit("Warning: High temperature!");
    }

    @Test
    void testMultipleWarnings() {
        TemperatureSensor tempSensor = mock(TemperatureSensor.class);
        FuelSensor fuelSensor = mock(FuelSensor.class);
        RadioTransmitter radio = mock(RadioTransmitter.class);

        when(tempSensor.getTemperature()).thenReturn(110.0);
        when(fuelSensor.getFuelLevel()).thenReturn(5.0);

        SpacecraftControlSystem spacecraft = new SpacecraftControlSystem(tempSensor, fuelSensor, radio);
        spacecraft.checkAndReportStatus();

        verify(radio).transmit("Warning: High temperature!");
        verify(radio).transmit("Warning: Low fuel!");
        verify(radio).transmit("Status: Temperature 110.0, Fuel 5.0");
    }
}

// %%
runTests(SpacecraftControlSystemTest.class);

// %% [markdown]
// ## Bonusaufgabe: Fehlerbehandlung

// %%
class SpacecraftControlSystemWithExceptionHandling {
    private TemperatureSensor tempSensor;
    private FuelSensor fuelSensor;
    private RadioTransmitter radio;

    public SpacecraftControlSystemWithExceptionHandling(TemperatureSensor tempSensor, FuelSensor fuelSensor, RadioTransmitter radio) {
        this.tempSensor = tempSensor;
        this.fuelSensor = fuelSensor;
        this.radio = radio;
    }

    public void checkAndReportStatus() {
        try {
            double temp = tempSensor.getTemperature();
            double fuel = fuelSensor.getFuelLevel();

            if (temp > 100) {
                radio.transmit("Warning: High temperature!");
            }

            if (fuel < 10) {
                radio.transmit("Warning: Low fuel!");
            }

            radio.transmit("Status: Temperature " + temp + ", Fuel " + fuel);
        } catch (Exception e) {
            radio.transmit("Error: Sensor malfunction - " + e.getMessage());
        }
    }
}

// %%
class SpacecraftControlSystemWithExceptionHandlingTest {
    @Test
    void testExceptionHandling() {
        TemperatureSensor tempSensor = mock(TemperatureSensor.class);
        FuelSensor fuelSensor = mock(FuelSensor.class);
        RadioTransmitter radio = mock(RadioTransmitter.class);

        when(tempSensor.getTemperature()).thenThrow(new RuntimeException("Temperature sensor failure"));
        when(fuelSensor.getFuelLevel()).thenReturn(50.0);

        SpacecraftControlSystemWithExceptionHandling spacecraft = new SpacecraftControlSystemWithExceptionHandling(tempSensor, fuelSensor, radio);
        spacecraft.checkAndReportStatus();

        verify(radio).transmit("Error: Sensor malfunction - Temperature sensor failure");
        verify(radio, never()).transmit(startsWith("Status:"));
        verify(radio, never()).transmit("Warning: High temperature!");
        verify(radio, never()).transmit("Warning: Low fuel!");
    }
}
