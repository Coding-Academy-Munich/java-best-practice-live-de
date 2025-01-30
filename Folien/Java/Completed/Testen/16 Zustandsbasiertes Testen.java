// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Zustandsbasiertes Testen</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ## Zustandsbasiertes Testen
//
// Kann man das Verhalten eines Objekts durch ein Zustandsdiagramm beschreiben,
// so kann man sich beim Testen an den Zuständen und Transitionen orientieren
//
// - Ein zustandsbasierter Test wird durch eine Folge von Events beschrieben,
//   die die Zustandsmaschine steuern
// - Die erwarteten Ergebnisse sind
//   - die Zustände (falls beobachtbar) und
//   - die Aktivitäten bzw. Ausgaben, die durch die Eingabe-Events verursacht
//     werden
// - Es gibt verschiedene Methoden, um fehlerhafte Aktivitäten bzw. Ausgaben und
//   falsche Zustandsübergänge zu finden (z.B. Transition Tour, Distinguishing
//   Sequence)

// %% [markdown]
//
// ## Beispiel: Ampel
//
// <img src="img/traffic-light.png"
//      alt="Zustandsmaschine für eine Ampel"
//      style="width: 50%; margin-left: auto; margin-right: auto;"/>

// %%
public class TrafficLight {
    private enum State { RED, YELLOW, GREEN }
    private State currentState = State.RED;

    public void change() {
        switch (currentState) {
            case RED:
                currentState = State.GREEN;
                break;
            case YELLOW:
                currentState = State.RED;
                break;
            case GREEN:
                currentState = State.YELLOW;
                break;
        }
    }

    public String getState() {
        return currentState.toString();
    }
}

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

// %%
public class TrafficLightTest {
    @Test
    public void testTrafficLightStateTransitions() {
        TrafficLight light = new TrafficLight();

        assertEquals("RED", light.getState());

        light.change();
        assertEquals("GREEN", light.getState());

        light.change();
        assertEquals("YELLOW", light.getState());

        light.change();
        assertEquals("RED", light.getState());
    }
}

// %%
runTests(TrafficLightTest.class);

// %%
public class TrafficLightTest2 {
    private TrafficLight light;

    @BeforeEach
    void setUp() {
        light = new TrafficLight();
    }

    @Test
    public void testInitialState() {
        assertEquals("RED", light.getState());
    }

    @Test
    public void testRedToGreen() {
        // Arrange
        assertEquals("RED", light.getState());

        // Act
        light.change();

        // Assert
        assertEquals("GREEN", light.getState());
    }

    @Test
    public void testGreenToYellow() {
        // Arrange
        light.change();
        assertEquals("GREEN", light.getState());

        // Act
        light.change();

        // Assert
        assertEquals("YELLOW", light.getState());
    }

    @Test
    public void testYellowToRed() {
        // Arrange
        light.change();
        light.change();
        assertEquals("YELLOW", light.getState());

        // Act
        light.change();

        // Assert
        assertEquals("RED", light.getState());
    }
}

// %%
runTests(TrafficLightTest2.class);

// %%
import java.util.stream.Stream;
import java.util.function.Consumer;


// %%
public class TrafficLightTest3 {
    private TrafficLight light;

    @BeforeEach
    void setUp() {
        light = new TrafficLight();
    }

    @ParameterizedTest
    @MethodSource
    public void transitionsFromRedState(StateTransitionTestCase testCase) {
        // Arrange
        assertEquals("RED", light.getState());

        // Act
        testCase.action.accept(light);

        // Assert
        assertEquals(testCase.expectedState, light.getState());
    }

    private static Stream<StateTransitionTestCase> transitionsFromRedState() {
        return Stream.of(
            new StateTransitionTestCase(
                "RED -- change --> GREEN",
                TrafficLight::change,
                "GREEN"
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    public void transitionsFromGreenState(StateTransitionTestCase testCase) {
        // Arrange
        light.change();
        assertEquals("GREEN", light.getState());

        // Act
        testCase.action.accept(light);

        // Assert
        assertEquals(testCase.expectedState, light.getState());
    }

    private static Stream<StateTransitionTestCase> transitionsFromGreenState() {
        return Stream.of(
            new StateTransitionTestCase(
                "GREEN -- change --> YELLOW",
                TrafficLight::change,
                "YELLOW"
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    public void transitionsFromYellowState(StateTransitionTestCase testCase) {
        // Arrange
        light.change();
        light.change();
        assertEquals("YELLOW", light.getState());

        // Act
        testCase.action.accept(light);

        // Assert
        assertEquals(testCase.expectedState, light.getState());
    }

    private static Stream<StateTransitionTestCase> transitionsFromYellowState() {
        return Stream.of(
            new StateTransitionTestCase(
                "YELLOW -- change --> RED",
                TrafficLight::change,
                "RED"
            )
        );
    }

    private static class StateTransitionTestCase {
        String description;
        java.util.function.Consumer<TrafficLight> action;
        String expectedState;

        StateTransitionTestCase(String description, Consumer<TrafficLight> action, String expectedState) {
            this.description = description;
            this.action = action;
            this.expectedState = expectedState;
        }

        @Override
        public String toString() {
            return description;
        }
    }
}

// %%
runTests(TrafficLightTest3.class);

// %% [markdown]
//
// ## Workshop: Zustandsbasiertes Testen eines Kaffeeautomaten
//
// Implementieren Sie eine `CoffeeMachine` Klasse mit folgenden Eigenschaften:
//
// - Zustände: `OFF`, `READY`, `BREWING`, `MAINTENANCE`
// - Methoden:
//   - `turnOn()`: Schaltet die Maschine ein (von `OFF` zu `READY`)
//   - `turnOff()`: Schaltet die Maschine aus (von jedem Zustand zu `OFF`)
//   - `brew()`: Startet den Brühvorgang (von `READY` zu `BREWING,` dann
//     automatisch zurück zu `READY`)
//   - `startMaintenance()`: Startet den Wartungsmodus (von `READY` zu
//     `MAINTENANCE`)
//   - `finishMaintenance()`: Beendet den Wartungsmodus (von `MAINTENANCE` zu
//     `READY`)
//   - `getState()`: Gibt den aktuellen Zustand zurück
//
// Schreiben Sie Tests, die alle möglichen Zustandsübergänge abdecken und
// überprüfen Sie, ob "unerlaubte" Übergänge korrekt behandelt werden, d.h. dass
// Aktionen den Zustand der Zustandsmaschine nicht ändern, wenn sie in Zuständen
// ausgeführt werden, für die keine Transition angegeben ist.

// %%
public class CoffeeMachine {
    public enum State {
        OFF, READY, BREWING, MAINTENANCE
    }

    private State currentState;

    public CoffeeMachine() {
        currentState = State.OFF;
    }

    public void turnOn() {
        if (currentState == State.OFF) {
            currentState = State.READY;
        }
    }

    public void turnOff() {
        currentState = State.OFF;
    }

    public void brew() {
        if (currentState == State.READY) {
            currentState = State.BREWING;
            // Simulate brewing process
            currentState = State.READY;
        }
    }

    public void startMaintenance() {
        if (currentState == State.READY) {
            currentState = State.MAINTENANCE;
        }
    }

    public void finishMaintenance() {
        if (currentState == State.MAINTENANCE) {
            currentState = State.READY;
        }
    }

    public State getState() {
        return currentState;
    }
}

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

// %%
import java.util.stream.Stream;
import java.util.function.Consumer;


// %%
class CoffeeMachineTest {
    private CoffeeMachine machine;

    @BeforeEach
    void setUp() {
        machine = new CoffeeMachine();
    }

    @Test
    void testInitialState() {
        assertEquals(CoffeeMachine.State.OFF, machine.getState());
    }

    @ParameterizedTest
    @MethodSource
    void transitionsFromOff(TransitionTestCase testCase) {
        assertEquals(CoffeeMachine.State.OFF, machine.getState());

        testCase.action.accept(machine);
        assertEquals(testCase.expectedState, machine.getState());
    }

    private static Stream<TransitionTestCase> transitionsFromOff() {
        return Stream.of(
            new TransitionTestCase(
                "OFF -- turnOn --> READY",
                machine -> machine.turnOn(),
                CoffeeMachine.State.READY
            ),
            new TransitionTestCase(
                "OFF -- turnOff --> OFF",
                machine -> machine.turnOff(),
                CoffeeMachine.State.OFF
            ),
            new TransitionTestCase(
                "OFF -- brew --> OFF",
                machine -> machine.brew(),
                CoffeeMachine.State.OFF
            ),
            new TransitionTestCase(
                "OFF -- startMaintenance --> OFF",
                machine -> machine.startMaintenance(),
                CoffeeMachine.State.OFF
            ),
            new TransitionTestCase(
                "OFF -- finishMaintenance --> OFF",
                machine -> machine.finishMaintenance(),
                CoffeeMachine.State.OFF
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void transitionsFromReady(TransitionTestCase testCase) {
        machine.turnOn();
        assertEquals(CoffeeMachine.State.READY, machine.getState());

        testCase.action.accept(machine);
        assertEquals(testCase.expectedState, machine.getState());
    }

    private static Stream<TransitionTestCase> transitionsFromReady() {
        return Stream.of(
            new TransitionTestCase(
                "READY -- turnOn --> READY",
                machine -> machine.turnOn(),
                CoffeeMachine.State.READY
            ),
            new TransitionTestCase(
                "READY -- turnOff --> OFF",
                machine -> machine.turnOff(),
                CoffeeMachine.State.OFF
            ),
            new TransitionTestCase(
                "READY -- brew --> READY",
                machine -> machine.brew(),
                CoffeeMachine.State.READY
            ),
            new TransitionTestCase(
                "READY -- startMaintenance --> MAINTENANCE",
                machine -> machine.startMaintenance(),
                CoffeeMachine.State.MAINTENANCE
            ),
            new TransitionTestCase(
                "READY -- finishMaintenance --> READY",
                machine -> machine.finishMaintenance(),
                CoffeeMachine.State.READY
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void transitionsFromMaintenance(TransitionTestCase testCase) {
        machine.turnOn();
        machine.startMaintenance();
        assertEquals(CoffeeMachine.State.MAINTENANCE, machine.getState());

        testCase.action.accept(machine);
        assertEquals(testCase.expectedState, machine.getState());
    }

    private static Stream<TransitionTestCase> transitionsFromMaintenance() {
        return Stream.of(
            new TransitionTestCase(
                "MAINTENANCE -- turnOn --> MAINTENANCE",
                machine -> machine.turnOn(),
                CoffeeMachine.State.MAINTENANCE
            ),
            new TransitionTestCase(
                "MAINTENANCE -- turnOff --> OFF",
                machine -> machine.turnOff(),
                CoffeeMachine.State.OFF
            ),
            new TransitionTestCase(
                "MAINTENANCE -- brew --> MAINTENANCE",
                machine -> machine.brew(),
                CoffeeMachine.State.MAINTENANCE
            ),
            new TransitionTestCase(
                "MAINTENANCE -- startMaintenance --> MAINTENANCE",
                machine -> machine.startMaintenance(),
                CoffeeMachine.State.MAINTENANCE
            ),
            new TransitionTestCase(
                "MAINTENANCE -- finishMaintenance --> READY",
                machine -> machine.finishMaintenance(),
                CoffeeMachine.State.READY
            )
        );
    }

    private static class TransitionTestCase {
        String description;
        java.util.function.Consumer<CoffeeMachine> action;
        CoffeeMachine.State expectedState;

        TransitionTestCase(String description, Consumer<CoffeeMachine> action, CoffeeMachine.State expectedState) {
            this.description = description;
            this.action = action;
            this.expectedState = expectedState;
        }

        @Override
        public String toString() {
            return description;
        }
    }
}

// %%
runTests(CoffeeMachineTest.class);

// %%
public class ObservableCoffeeMachine {
    public enum State {
        OFF, READY, BREWING, MAINTENANCE
    }

    private State currentState;
    private List<StateChangeObserver> observers = new ArrayList<>();

    public ObservableCoffeeMachine() {
        currentState = State.OFF;
    }

    public void addObserver(StateChangeObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(State oldState, State newState) {
        for (StateChangeObserver observer : observers) {
            observer.onStateChange(oldState, newState);
        }
    }

    private void changeState(State newState) {
        State oldState = currentState;
        currentState = newState;
        if (oldState != newState) {
            notifyObservers(oldState, newState);
        }
    }

    public void turnOn() {
        if (currentState == State.OFF) {
            changeState(State.READY);
        }
    }

    public void turnOff() {
        changeState(State.OFF);
    }

    public void brew() {
        if (currentState == State.READY) {
            changeState(State.BREWING);
            // Simulate brewing process
            changeState(State.READY);
        }
    }

    public void startMaintenance() {
        if (currentState == State.READY) {
            changeState(State.MAINTENANCE);
        }
    }

    public void finishMaintenance() {
        if (currentState == State.MAINTENANCE) {
            changeState(State.READY);
        }
    }

    public State getState() {
        return currentState;
    }

    public interface StateChangeObserver {
        void onStateChange(State oldState, State newState);
    }
}

// %%
import java.util.ArrayList;
import java.util.List;

// %%
class StateChangeSpy implements ObservableCoffeeMachine.StateChangeObserver {
    private List<ObservableCoffeeMachine.State> stateChanges = new ArrayList<>();

    @Override
    public void onStateChange(ObservableCoffeeMachine.State oldState, ObservableCoffeeMachine.State newState) {
        stateChanges.add(newState);
    }

    public List<ObservableCoffeeMachine.State> getStateChanges() {
        return new ArrayList<>(stateChanges);
    }

    public void reset() {
        stateChanges.clear();
    }
}

// %%
class ObservableCoffeeMachineTest {
    private ObservableCoffeeMachine machine;
    private StateChangeSpy spy;

    @BeforeEach
    void setUp() {
        machine = new ObservableCoffeeMachine();
        spy = new StateChangeSpy();
        machine.addObserver(spy);
    }

    @Test
    void testInitialState() {
        assertEquals(ObservableCoffeeMachine.State.OFF, machine.getState());
    }

    @ParameterizedTest
    @MethodSource
    void transitionsFromOff(TransitionTestCase testCase) {
        assertEquals(ObservableCoffeeMachine.State.OFF, machine.getState());
        spy.reset();

        testCase.action.accept(machine);
        assertEquals(testCase.expectedState, machine.getState());
        assertIterableEquals(testCase.expectedTransitions, spy.getStateChanges());
    }

    private static Stream<TransitionTestCase> transitionsFromOff() {
        return Stream.of(
            new TransitionTestCase(
                "OFF -- turnOn --> READY",
                machine -> machine.turnOn(),
                ObservableCoffeeMachine.State.READY,
                List.of(ObservableCoffeeMachine.State.READY)
            ),
            new TransitionTestCase(
                "OFF -- turnOff --> OFF",
                machine -> machine.turnOff(),
                ObservableCoffeeMachine.State.OFF
            ),
            new TransitionTestCase(
                "OFF -- brew --> OFF",
                machine -> machine.brew(),
                ObservableCoffeeMachine.State.OFF
            ),
            new TransitionTestCase(
                "OFF -- startMaintenance --> OFF",
                machine -> machine.startMaintenance(),
                ObservableCoffeeMachine.State.OFF
            ),
            new TransitionTestCase(
                "OFF -- finishMaintenance --> OFF",
                machine -> machine.finishMaintenance(),
                ObservableCoffeeMachine.State.OFF
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void transitionsFromReady(TransitionTestCase testCase) {
        machine.turnOn();
        assertEquals(ObservableCoffeeMachine.State.READY, machine.getState());
        spy.reset();

        testCase.action.accept(machine);
        assertEquals(testCase.expectedState, machine.getState());
        assertIterableEquals(testCase.expectedTransitions, spy.getStateChanges());
    }

    private static Stream<TransitionTestCase> transitionsFromReady() {
        return Stream.of(
            new TransitionTestCase(
                "READY -- turnOn --> READY",
                machine -> machine.turnOn(),
                ObservableCoffeeMachine.State.READY
            ),
            new TransitionTestCase(
                "READY -- turnOff --> OFF",
                machine -> machine.turnOff(),
                ObservableCoffeeMachine.State.OFF,
                List.of(ObservableCoffeeMachine.State.OFF)
            ),
            new TransitionTestCase(
                "READY -- brew --> READY",
                machine -> machine.brew(),
                ObservableCoffeeMachine.State.READY,
                List.of(ObservableCoffeeMachine.State.BREWING, ObservableCoffeeMachine.State.READY)
            ),
            new TransitionTestCase(
                "READY -- startMaintenance --> MAINTENANCE",
                machine -> machine.startMaintenance(),
                ObservableCoffeeMachine.State.MAINTENANCE,
                List.of(ObservableCoffeeMachine.State.MAINTENANCE)
            ),
            new TransitionTestCase(
                "READY -- finishMaintenance --> READY",
                machine -> machine.finishMaintenance(),
                ObservableCoffeeMachine.State.READY
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void transitionsFromMaintenance(TransitionTestCase testCase) {
        machine.turnOn();
        machine.startMaintenance();
        assertEquals(ObservableCoffeeMachine.State.MAINTENANCE, machine.getState());
        spy.reset();

        testCase.action.accept(machine);
        assertEquals(testCase.expectedState, machine.getState());
        assertIterableEquals(testCase.expectedTransitions, spy.getStateChanges());
    }

    private static Stream<TransitionTestCase> transitionsFromMaintenance() {
        return Stream.of(
            new TransitionTestCase(
                "MAINTENANCE -- turnOn --> MAINTENANCE",
                machine -> machine.turnOn(),
                ObservableCoffeeMachine.State.MAINTENANCE
            ),
            new TransitionTestCase(
                "MAINTENANCE -- turnOff --> OFF",
                machine -> machine.turnOff(),
                ObservableCoffeeMachine.State.OFF,
                List.of(ObservableCoffeeMachine.State.OFF)
            ),
            new TransitionTestCase(
                "MAINTENANCE -- brew --> MAINTENANCE",
                machine -> machine.brew(),
                ObservableCoffeeMachine.State.MAINTENANCE
            ),
            new TransitionTestCase(
                "MAINTENANCE -- startMaintenance --> MAINTENANCE",
                machine -> machine.startMaintenance(),
                ObservableCoffeeMachine.State.MAINTENANCE
            ),
            new TransitionTestCase(
                "MAINTENANCE -- finishMaintenance --> READY",
                machine -> machine.finishMaintenance(),
                ObservableCoffeeMachine.State.READY,
                List.of(ObservableCoffeeMachine.State.READY)
            )
        );
    }

    private static class TransitionTestCase {
        String description;
        Consumer<ObservableCoffeeMachine> action;
        ObservableCoffeeMachine.State expectedState;
        List<ObservableCoffeeMachine.State> expectedTransitions;

        TransitionTestCase(String description, Consumer<ObservableCoffeeMachine> action, ObservableCoffeeMachine.State expectedState, List<ObservableCoffeeMachine.State> expectedTransitions) {
            this.description = description;
            this.action = action;
            this.expectedState = expectedState;
            this.expectedTransitions = expectedTransitions;
        }

        TransitionTestCase(String description, Consumer<ObservableCoffeeMachine> action, ObservableCoffeeMachine.State expectedState) {
            this(description, action, expectedState, List.of());
        }

        @Override
        public String toString() {
            return description;
        }
    }
}

// %%
runTests(ObservableCoffeeMachineTest.class);

// %%
