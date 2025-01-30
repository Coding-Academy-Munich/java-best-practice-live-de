// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Effective Java T2: Builder Pattern</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ### Problem: Viele Konstruktor-Parameter
//
// - Statische Fabriken und Konstruktoren skalieren schlecht
// - Große Anzahl von optionalen Parametern
// - Beispiel: `TravelPackage`-Klasse
//   - Einige erforderliche Felder
//   - Viele optionale Felder

// %% [markdown]
//
// ### Lösung 1: Teleskopierender Konstruktor
//
// - Muster: Untere Konstruktoren rufen obere Konstruktoren auf
// - Vorteil: Sicherstellung der Konsistenz
// - Nachteil: Schlechte Lesbarkeit und Wartbarkeit

// %%
public class TravelPackage {
    private final String destination;  // required
    private final int duration;        // required
    private final String accommodation;// optional
    private final String transportation;// optional
    private final boolean meals;       // optional
    private final boolean insurance;   // optional

    public TravelPackage(String destination, int duration) {
        this(destination, duration, "Standard Hotel");
    }

    public TravelPackage(String destination, int duration, String accommodation) {
        this(destination, duration, accommodation, "Flight");
    }

    public TravelPackage(String destination, int duration, String accommodation, String transportation) {
        this(destination, duration, accommodation, transportation, false);
    }

    public TravelPackage(String destination, int duration, String accommodation, String transportation, boolean meals) {
        this(destination, duration, accommodation, transportation, meals, false);
    }

    public TravelPackage(String destination, int duration, String accommodation, String transportation, boolean meals, boolean insurance) {
        this.destination = destination;
        this.duration = duration;
        this.accommodation = accommodation;
        this.transportation = transportation;
        this.meals = meals;
        this.insurance = insurance;
    }

    @Override
    public String toString() {
        return "TravelPackage{" +
                "destination='" + destination + '\'' +
                ", duration=" + duration +
                ", accommodation='" + accommodation + '\'' +
                ", transportation='" + transportation + '\'' +
                ", meals=" + meals +
                ", insurance=" + insurance +
                '}';
    }
}

// %%
TravelPackage europeanTour = new TravelPackage("Europe", 14, "Standard Hotel", "Flight", false, true);

// %%
europeanTour

// %% [markdown]
//
// ### Lösung 2: JavaBeans Pattern
//
// - Parametelloser Konstruktor und Setter-Methoden
// - Vorteil: Leichter zu lesen und zu schreiben
// - Nachteil: Keine Konsistenz, erzwingt Mutabilität

// %%
public class TravelPackage {
    private String destination;   // Required; no default value
    private int duration;         // Required; no default value
    private String accommodation = "Standard Hotel";
    private String transportation = "Flight";
    private boolean meals = false;
    private boolean insurance = false;

    public TravelPackage() { }

    public void setDestination(String val)     { destination = val; }
    public void setDuration(int val)           { duration = val; }
    public void setAccommodation(String val)   { accommodation = val; }
    public void setTransportation(String val)  { transportation = val; }
    public void setMeals(boolean val)          { meals = val; }
    public void setInsurance(boolean val)      { insurance = val; }

    @Override
    public String toString() {
        return "TravelPackage{" +
                "destination='" + destination + '\'' +
                ", duration=" + duration +
                ", accommodation='" + accommodation + '\'' +
                ", transportation='" + transportation + '\'' +
                ", meals=" + meals +
                ", insurance=" + insurance +
                '}';
    }
}

// %%
TravelPackage europeanTour = new TravelPackage();
europeanTour.setDestination("Europe");
europeanTour.setDuration(14);
europeanTour.setInsurance(true);

// %%
europeanTour

// %% [markdown]
//
// ### Lösung 3: Builder Pattern
//
// - Kombination von Sicherheit und Lesbarkeit
// - Erstellt ein Builder-Objekt, das die optionalen Parameter setzt
// - Führt eine `build`-Methode auf, um das finale Objekt zu erzeugen
// - Das erzeugte Objekt ist immutable

// %%
public class TravelPackage {
    private final String destination;
    private final int duration;
    private final String accommodation;
    private final String transportation;
    private final boolean meals;
    private final boolean insurance;

    public static class Builder {
        // Required parameters
        private final String destination;
        private final int duration;

        // Optional parameters - initialized to default values
        private String accommodation = "Standard Hotel";
        private String transportation = "Flight";
        private boolean meals = false;
        private boolean insurance = false;

        public Builder(String destination, int duration) {
            this.destination = destination;
            this.duration = duration;
        }

        public Builder accommodation(String val) { accommodation = val; return this; }
        public Builder transportation(String val) { transportation = val; return this; }
        public Builder meals(boolean val) { meals = val; return this; }
        public Builder insurance(boolean val) { insurance = val; return this; }

        public TravelPackage build() {
            return new TravelPackage(this);
        }
    }

    private TravelPackage(Builder builder) {
        destination = builder.destination;
        duration = builder.duration;
        accommodation = builder.accommodation;
        transportation = builder.transportation;
        meals = builder.meals;
        insurance = builder.insurance;
    }

    @Override
    public String toString() {
        return "TravelPackage{" +
                "destination='" + destination + '\'' +
                ", duration=" + duration +
                ", accommodation='" + accommodation + '\'' +
                ", transportation='" + transportation + '\'' +
                ", meals=" + meals +
                ", insurance=" + insurance +
                '}';
    }
}

// %%
TravelPackage europeanTour = new TravelPackage.Builder("Europe", 14)
        .accommodation("4-star Hotel")
        .transportation("Train")
        .meals(true)
        .insurance(true)
        .build();

// %%
europeanTour

// %% [markdown]
//
// ### Builder Pattern: Vorteile
//
// - Immutable Objekte
// - Leichte Lesbarkeit und Wartbarkeit
// - Named optional parameters
// - Mögliche Konsistenz-Checks

// %% [markdown]
//
// ### Builder Pattern: Nachteile
//
// - Mehr Code
// - Mangelnde Vertrautheit

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
// ## Workshop: Sandwich-Builder
//
// - Implementieren Sie ein System zur Erstellung von Sandwiches
// - Verwenden Sie das Builder-Pattern

// %% [markdown]
//
// ### Gegebene Klasse
//
// - `Sandwich`

// %%
import java.util.ArrayList;
import java.util.List;

public class Sandwich {
    private final String bread;
    private final List<String> proteins;
    private final List<String> cheeses;
    private final List<String> vegetables;
    private final List<String> sauces;
    private final boolean toasted;

    Sandwich(String bread, List<String> proteins, List<String> cheeses,
             List<String> vegetables, List<String> sauces, boolean toasted) {
        this.bread = bread;
        this.proteins = proteins;
        this.cheeses = cheeses;
        this.vegetables = vegetables;
        this.sauces = sauces;
        this.toasted = toasted;
    }

    @Override
    public String toString() {
        return "Sandwich{" +
                "bread='" + bread + '\'' +
                ", proteins=" + proteins +
                ", cheeses=" + cheeses +
                ", vegetables=" + vegetables +
                ", sauces=" + sauces +
                ", toasted=" + toasted +
                '}';
    }
}

// %% [markdown]
//
// ### Aufgaben
//
// 1. Implementieren Sie eine `SandwichBuilder`-Klasse
// 2. Testen Sie Ihre Implementierung

// %% [markdown]
//
// #### Aufgabe 1: `SandwichBuilder`
//
// - Implementieren Sie die `SandwichBuilder`-Klasse
// - Fügen Sie Methoden hinzu, um alle Eigenschaften des Sandwiches zu setzen
// - Implementieren Sie eine `build()`-Methode, die ein `Sandwich`-Objekt
//   zurückgibt

// %%
public class SandwichBuilder {
    private String bread = "White";
    private List<String> proteins = new ArrayList<>();
    private List<String> cheeses = new ArrayList<>();
    private List<String> vegetables = new ArrayList<>();
    private List<String> sauces = new ArrayList<>();
    private boolean toasted = false;

    public SandwichBuilder bread(String bread) {
        this.bread = bread;
        return this;
    }

    public SandwichBuilder addProtein(String protein) {
        this.proteins.add(protein);
        return this;
    }

    public SandwichBuilder addCheese(String cheese) {
        this.cheeses.add(cheese);
        return this;
    }

    public SandwichBuilder addVegetable(String vegetable) {
        this.vegetables.add(vegetable);
        return this;
    }

    public SandwichBuilder addSauce(String sauce) {
        this.sauces.add(sauce);
        return this;
    }

    public SandwichBuilder setToasted(boolean toasted) {
        this.toasted = toasted;
        return this;
    }

    public Sandwich build() {
        return new Sandwich(bread, proteins, cheeses, vegetables, sauces, toasted);
    }
}

// %% [markdown]
//
// #### Aufgabe 2: Testen Sie Ihre Implementierung
//
// - Erstellen Sie eine Instanz der `SandwichBuilder`-Klasse
// - Erstellen Sie verschiedene `Sandwich`-Objekte mithilfe des Builders
// - Stellen Sie sicher, dass die erstellten `Sandwich`-Objekte korrekt
//   initialisiert sind

// %%
Sandwich veggieSandwich = new SandwichBuilder()
    .bread("Whole Wheat")
    .addVegetable("Lettuce")
    .addVegetable("Tomato")
    .addVegetable("Cucumber")
    .addCheese("Swiss")
    .addSauce("Mustard")
    .setToasted(true)
    .build();

// %%
veggieSandwich

// %%
Sandwich clubSandwich = new SandwichBuilder()
    .bread("Sourdough")
    .addProtein("Turkey")
    .addProtein("Bacon")
    .addCheese("Cheddar")
    .addVegetable("Lettuce")
    .addVegetable("Tomato")
    .addSauce("Mayo")
    .setToasted(false)
    .build();

// %%
clubSandwich

// %% [markdown]
//
// ### Bonusaufgabe
//
// - Fügen Sie eine Methode `calories()` zur `Sandwich`-Klasse hinzu, die die
//   Gesamtkalorien des Sandwiches berechnet
// - Erweitern Sie den `SandwichBuilder` um eine Methode `maxCalories(int max)`,
//   die verhindert, dass ein Sandwich mit mehr als `max` Kalorien erstellt wird

// %%
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Sandwich {
    private final String bread;
    private final List<String> proteins;
    private final List<String> cheeses;
    private final List<String> vegetables;
    private final List<String> sauces;
    private final boolean toasted;

    // Calorie information for ingredients (simplified)
    private static final Map<String, Integer> CALORIE_MAP;

    static {
        CALORIE_MAP = new HashMap<>();
        CALORIE_MAP.put("White", 80);
        CALORIE_MAP.put("Whole Wheat", 70);
        CALORIE_MAP.put("Sourdough", 90);
        CALORIE_MAP.put("Turkey", 30);
        CALORIE_MAP.put("Bacon", 40);
        CALORIE_MAP.put("Ham", 35);
        CALORIE_MAP.put("Swiss", 50);
        CALORIE_MAP.put("Cheddar", 60);
        CALORIE_MAP.put("Lettuce", 5);
        CALORIE_MAP.put("Tomato", 10);
        CALORIE_MAP.put("Cucumber", 5);
        CALORIE_MAP.put("Mayo", 90);
        CALORIE_MAP.put("Mustard", 10);
    }

    Sandwich(String bread, List<String> proteins, List<String> cheeses,
             List<String> vegetables, List<String> sauces, boolean toasted) {
        this.bread = bread;
        this.proteins = proteins;
        this.cheeses = cheeses;
        this.vegetables = vegetables;
        this.sauces = sauces;
        this.toasted = toasted;
    }

    public int calories() {
        int total = CALORIE_MAP.getOrDefault(bread, 0);
        total += proteins.stream().mapToInt(p -> CALORIE_MAP.getOrDefault(p, 0)).sum();
        total += cheeses.stream().mapToInt(c -> CALORIE_MAP.getOrDefault(c, 0)).sum();
        total += vegetables.stream().mapToInt(v -> CALORIE_MAP.getOrDefault(v, 0)).sum();
        total += sauces.stream().mapToInt(s -> CALORIE_MAP.getOrDefault(s, 0)).sum();
        return total;
    }

    @Override
    public String toString() {
        return "Sandwich{" +
                "bread='" + bread + '\'' +
                ", proteins=" + proteins +
                ", cheeses=" + cheeses +
                ", vegetables=" + vegetables +
                ", sauces=" + sauces +
                ", toasted=" + toasted +
                ", calories=" + calories() +
                '}';
    }
}

// %%
public class SandwichBuilder {
    private String bread = "White";
    private List<String> proteins = new ArrayList<>();
    private List<String> cheeses = new ArrayList<>();
    private List<String> vegetables = new ArrayList<>();
    private List<String> sauces = new ArrayList<>();
    private boolean toasted = false;
    private int maxCalories = Integer.MAX_VALUE;

    public SandwichBuilder bread(String bread) {
        this.bread = bread;
        return this;
    }

    public SandwichBuilder addProtein(String protein) {
        this.proteins.add(protein);
        return this;
    }

    public SandwichBuilder addCheese(String cheese) {
        this.cheeses.add(cheese);
        return this;
    }

    public SandwichBuilder addVegetable(String vegetable) {
        this.vegetables.add(vegetable);
        return this;
    }

    public SandwichBuilder addSauce(String sauce) {
        this.sauces.add(sauce);
        return this;
    }

    public SandwichBuilder setToasted(boolean toasted) {
        this.toasted = toasted;
        return this;
    }

    public SandwichBuilder maxCalories(int max) {
        this.maxCalories = max;
        return this;
    }

    public Sandwich build() {
        Sandwich sandwich = new Sandwich(bread, proteins, cheeses, vegetables, sauces, toasted);
        if (sandwich.calories() > maxCalories) {
            throw new IllegalStateException("Sandwich exceeds maximum calorie limit");
        }
        return sandwich;
    }
}

// %%
// Test the new functionality
Sandwich lowCalSandwich = new SandwichBuilder()
    .bread("Whole Wheat")
    .addProtein("Turkey")
    .addCheese("Swiss")
    .addVegetable("Lettuce")
    .addVegetable("Tomato")
    .addSauce("Mustard")
    .maxCalories(300)
    .build();

// %%
lowCalSandwich

// %%
// This should throw an exception
try {
    Sandwich highCalSandwich = new SandwichBuilder()
        .bread("Sourdough")
        .addProtein("Bacon")
        .addProtein("Turkey")
        .addCheese("Cheddar")
        .addVegetable("Lettuce")
        .addSauce("Mayo")
        .maxCalories(300)
        .build();
} catch (IllegalStateException e) {
    System.out.println("Caught exception: " + e.getMessage());
}
