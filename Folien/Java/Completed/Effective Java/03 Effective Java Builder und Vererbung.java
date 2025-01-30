// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Effective Java: Builder und Vererbung</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ### Builder Pattern: Beispiel in einer Klassenhierarchie
//
// - Abstract Builder-Klasse
// - Konkrete Builder-Klassen für Unterklassen
// - Covarianter Ergebnis-Typ von `build()` (`build()` gibt Typ der Unterklasse
//   zurück)
// - Simuliertes self-type Idiom

// %%
import java.util.EnumSet;
import java.util.Set;
import java.util.Objects;

// %%
public abstract class Bicycle {
    public enum Component { LIGHTS, FENDERS, RACK, COMPUTER }
    final Set<Component> components;
    final String frame;
    final int wheelSize;

    // f-bounded polymorphism ----vvvvvvvvvvvvvvvvvvvv-----------------
    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Component> components = EnumSet.noneOf(Component.class);
        String frame;
        int wheelSize;

        public T addComponent(Component component) {
            components.add(Objects.requireNonNull(component));
            return self();            // <== Simulated self-type idiom
        }

        public T frame(String val) {
            frame = val;
            return self();            // <== Simulated self-type idiom
        }

        public T wheelSize(int val) {
            wheelSize = val;
            return self();            // <== Simulated self-type idiom
        }

        // ------vvvvvvv------------- Covariant return typing ----------
        abstract Bicycle build();

        protected abstract T self();  // <== Simulated self-type idiom
    }

    Bicycle(Builder<?> builder) {
        components = builder.components.clone();
        frame = builder.frame;
        wheelSize = builder.wheelSize;
    }

    @Override
    public String toString() {
        return "components=" + components +
                ", frame='" + frame + '\'' +
                ", wheelSize=" + wheelSize;
    }
}

// %%
public class MountainBike extends Bicycle {
    public enum Suspension { HARDTAIL, FULL }
    private final Suspension suspension;

    // f-bounded polymorphism ---------vvvvvvvvvvvvvvvvvvvvvvvvv-------
    public static class Builder extends Bicycle.Builder<Builder> {
        private Suspension suspension = Suspension.HARDTAIL;

        public Builder suspension(Suspension val) {
            suspension = val;
            return this;                 // <== No more subclassing
        }

        @Override
        public MountainBike build() {     // <== Covariant return typing
            return new MountainBike(this);
        }

        @Override protected Builder self() { return this; }
    }

    private MountainBike(Builder builder) {
        super(builder);
        suspension = builder.suspension;
    }

    @Override
    public String toString() {
        return "MountainBike{" + super.toString() +
                ", suspension=" + suspension +
                '}';
    }
}

// %%
public class RoadBike extends Bicycle {
    private final int gears;

    public static class Builder extends Bicycle.Builder<Builder> {
        private int gears = 18;

        public Builder gears(int val) {
            gears = val;
            return this;
        }

        @Override public RoadBike build() {
            return new RoadBike(this);
        }

        @Override protected Builder self() { return this; }
    }

    private RoadBike(Builder builder) {
        super(builder);
        gears = builder.gears;
    }

    @Override
    public String toString() {
        return "RoadBike{" + super.toString() +
                ", gears=" + gears +
                '}';
    }
}

// %%
MountainBike mtb = new MountainBike.Builder()
        .frame("Aluminum")
        .wheelSize(29)
        .suspension(MountainBike.Suspension.FULL)
        .addComponent(Bicycle.Component.COMPUTER)
        .build();

// %%
mtb

// %%
RoadBike roadBike = new RoadBike.Builder()
        .frame("Carbon")
        .wheelSize(28)
        .gears(22)
        .addComponent(Bicycle.Component.COMPUTER)
        .build();

// %%
roadBike


// %% [markdown]
//
// ### Zusammenfassung
//
// - Builder Pattern ideal für viele Konstruktor-Parameter
// - Vereint die Vorteile des teleskopierenden Konstruktors und des JavaBeans
//   Patterns
// - Leicht lesbar, leicht wartbar, sicher
// - Möglich für Klassenhierarchien

// %% [markdown]
//
// ## Workshop: Escape Room Design Builder
//
// - Implementieren Sie ein System zur Erstellung von Escape Rooms
// - Verwenden Sie eine Klassenhierarchie und das Builder-Pattern

// %% [markdown]
//
// ### Gegebene Klassen
//
// - `Room` (Superklasse)
// - `PuzzleRoom`, `PhysicalChallengeRoom`, `StoryRoom` (Subklassen von `Room`)

// %%
public abstract class Room {
    public enum Component { LIGHTS, FENDERS, RACK, COMPUTER }
    final Set<Component> components;
    final String frame;
    final int width;
    final int height;
    final int difficultyLevel;
    final int timeLimit;

    Room(Set<Component> components, String frame, int width, int height, int difficultyLevel, int timeLimit) {
        this.components = components;
        this.frame = frame;
        this.width = width;
        this.height = height;
        this.difficultyLevel = difficultyLevel;
        this.timeLimit = timeLimit;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getDifficultyLevel() { return difficultyLevel; }
    public int getTimeLimit() { return timeLimit; }
    public Set<Component> getComponents() { return components; }
    public String getFrame() { return frame; }

    @Override public String toString() {
        return String.format(
            "width=%d, height=%d, difficultyLevel=%d, timeLimit=%d, components=%s, frame=%s",
            width, height, difficultyLevel, timeLimit, components, frame);
    }
}

// %%
public class PuzzleRoom extends Room {
    private final int numberOfPuzzles;

    PuzzleRoom(
            Set<Component> components, String frame, int width, int height,
            int difficultyLevel, int timeLimit, int numberOfPuzzles) {
        super(components, frame, width, height, difficultyLevel, timeLimit);
        this.numberOfPuzzles = numberOfPuzzles;
    }

    public int getNumberOfPuzzles() { return numberOfPuzzles; }

    @Override public String toString() {
        return String.format("PuzzleRoom(%s, numberOfPuzzles=%d)", super.toString(), numberOfPuzzles);
    }
}

// %%
public class PhysicalChallengeRoom extends Room {
    private final String equipmentRequired;

    PhysicalChallengeRoom(
            Set<Component> components, String frame, int width, int height,
            int difficultyLevel, int timeLimit, String equipmentRequired) {
        super(components, frame, width, height, difficultyLevel, timeLimit);
        this.equipmentRequired = equipmentRequired;
    }

    public String getEquipmentRequired() { return equipmentRequired; }

    @Override public String toString() {
        return String.format("PhysicalChallengeRoom(%s, equipmentRequired=%s)", super.toString(), equipmentRequired);
    }
}

// %%
public class StoryRoom extends Room {
    private final String narrativeComplexity;

    StoryRoom(
            Set<Component> components, String frame, int width, int height,
            int difficultyLevel, int timeLimit, String narrativeComplexity) {
        super(components, frame, width, height, difficultyLevel, timeLimit);
        this.narrativeComplexity = narrativeComplexity;
    }

    public String getNarrativeComplexity() { return narrativeComplexity; }

    @Override public String toString() {
        return String.format("StoryRoom(%s, narrativeComplexity=%s)", super.toString(), narrativeComplexity);
    }
}

// %%
public abstract class RoomBuilder<T extends RoomBuilder<T>> {
    EnumSet<Room.Component> components = EnumSet.noneOf(Room.Component.class);
    String frame;
    int width;
    int height;
    int difficultyLevel;
    int timeLimit;

    public T addComponent(Room.Component component) {
        components.add(Objects.requireNonNull(component));
        return self();
    }

    public T frame(String val) {
        frame = val;
        return self();
    }

    public T width(int val) {
        width = val;
        return self();
    }

    public T height(int val) {
        height = val;
        return self();
    }

    public T difficultyLevel(int val) {
        difficultyLevel = val;
        return self();
    }

    public T timeLimit(int val) {
        timeLimit = val;
        return self();
    }

    abstract Room build();

    protected abstract T self();
}

// %% [markdown]
//
// ### Aufgaben
//
// 1. Implementieren Sie die `PuzzleRoomBuilder`-Klasse
// 2. Implementieren Sie die `PhysicalChallengeRoomBuilder`-Klasse
// 3. Implementieren Sie die `StoryRoomBuilder`-Klasse
// 4. Testen Sie Ihre Implementierung

// %% [markdown]
//
// #### Aufgabe 1: `PuzzleRoomBuilder`
//
// - Implementieren Sie die `PuzzleRoomBuilder`-Klasse
// - Stellen Sie sicher, dass alle Methoden der `RoomBuilder`-Superklasse implementiert sind
// - Fügen Sie zusätzlich Methoden hinzu, um die Anzahl der Puzzles zu setzen

// %%
public class PuzzleRoomBuilder extends RoomBuilder<PuzzleRoomBuilder> {
    private int numberOfPuzzles = 5; // Default

    public PuzzleRoomBuilder numberOfPuzzles(int val) {
        numberOfPuzzles = val;
        return this;
    }

    @Override
    public PuzzleRoom build() {
        return new PuzzleRoom(
            components, frame, width, height, difficultyLevel, timeLimit, numberOfPuzzles);
    }

    @Override
    protected PuzzleRoomBuilder self() {
        return this;
    }
}

// %% [markdown]
//
// #### Aufgabe 2: `PhysicalChallengeRoomBuilder`
//
// - Implementieren Sie die `PhysicalChallengeRoomBuilder`-Klasse
// - Stellen Sie sicher, dass alle Methoden der `RoomBuilder`-Superklasse implementiert sind
// - Fügen Sie zusätzlich Methoden hinzu, um das benötigte Equipment zu setzen

// %%
public class PhysicalChallengeRoomBuilder extends RoomBuilder<PhysicalChallengeRoomBuilder> {
    private String equipmentRequired = "Ropes, Ladders, Hurdles"; // Default

    public PhysicalChallengeRoomBuilder equipmentRequired(String val) {
        equipmentRequired = val;
        return this;
    }

    @Override
    public PhysicalChallengeRoom build() {
        return new PhysicalChallengeRoom(
            components, frame, width, height, difficultyLevel, timeLimit, equipmentRequired);
    }

    @Override
    protected PhysicalChallengeRoomBuilder self() {
        return this;
    }
}

// %% [markdown]
//
// #### Aufgabe 3: `StoryRoomBuilder`
//
// - Implementieren Sie die `StoryRoomBuilder`-Klasse
// - Stellen Sie sicher, dass alle Methoden der `RoomBuilder`-Superklasse implementiert sind
// - Fügen Sie zusätzlich Methoden hinzu, um die narrative complexity zu setzen

// %%
public class StoryRoomBuilder extends RoomBuilder<StoryRoomBuilder> {
    private String narrativeComplexity = "High"; // Default

    public StoryRoomBuilder narrativeComplexity(String val) {
        narrativeComplexity = val;
        return this;
    }

    @Override
    public StoryRoom build() {
        return new StoryRoom(
            components, frame, width, height, difficultyLevel, timeLimit, narrativeComplexity);
    }

    @Override
    protected StoryRoomBuilder self() {
        return this;
    }
}

// %% [markdown]
//
// #### Aufgabe 4: Testen Sie Ihre Implementierung
//
// - Erstellen Sie Instanzen der `PuzzleRoomBuilder`,
//   `PhysicalChallengeRoomBuilder` und `StoryRoomBuilder`
// - Erstellen Sie `Room`-Objekte mithilfe der Builder-Klassen
// - Stellen Sie sicher, dass die erstellten `Room`-Objekte korrekt
//   initialisiert sind

// %%
PuzzleRoom puzzleRoom = new PuzzleRoomBuilder()
    .addComponent(Room.Component.LIGHTS)
    .frame("Wood")
    .width(5)
    .height(10)
    .difficultyLevel(3)
    .timeLimit(60)
    .numberOfPuzzles(5)
    .build();

// %%
puzzleRoom

// %%
PhysicalChallengeRoom physicalChallengeRoom = new PhysicalChallengeRoomBuilder()
    .addComponent(Room.Component.FENDERS)
    .frame("Metal")
    .width(6)
    .height(12)
    .difficultyLevel(4)
    .timeLimit(45)
    .equipmentRequired("Ropes, Ladders, Hurdles")
    .build();

// %%
physicalChallengeRoom


// %%
StoryRoom storyRoom = new StoryRoomBuilder()
    .addComponent(Room.Component.RACK)
    .frame("Glass")
    .width(8)
    .height(15)
    .difficultyLevel(2)
    .timeLimit(75)
    .narrativeComplexity("High")
    .build();

// %%
storyRoom
