// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Was sind gute Tests?</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// <img src="img/velocity-tests-03.png"
//      alt="Velocity vs. Tests 3"
//      style="width: 75%; margin-left: auto; margin-right: auto;"/>

// %% [markdown]
//
// ## Welche Eigenschaften sollte ein Test haben?
//
// <ul>
// <li class="fragment">Zeigt viele Fehler/Regressionen im Code auf</li>
// <li class="fragment">Gibt schnelle Rückmeldung</li>
// <li class="fragment">Ist deterministisch</li>
// <li class="fragment">Ist leicht zu warten und verstehen</li>
// <li class="fragment"><b>Unempfindlich gegenüber Refactorings</b></li>
// </ul>
//
// <p class="fragment">
//   Leider stehen diese Eigenschaften oft im Konflikt zueinander!
// </p>

// %% [markdown]
//
// ## Aufzeigen von Fehlern/Regressionen
//
// ### Einflüsse
//
// <ul>
//   <li class="fragment">Menge des abgedeckten Codes</li>
//   <li class="fragment">Komplexität des abgedeckten Codes</li>
//   <li class="fragment">Interaktion mit externen Systemen</li>
//   <li class="fragment">Signifikanz des abgedeckten Codes für die Domäne/das
//   System</li>
// </ul>

// %%

public class Item {
    private String name;
    private double price; // always positive

    public Item(String name, double price) {
        this.name = name;
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double value) {
        if (value < 0)
        {
            value = -value;
        }
        price = value;
    }

    @Override
    public String toString() {
        return String.format("Item(%s, %.2f)", name, price);
    }
}

// %%
import java.util.ArrayList;
import java.util.List;

// %%
public class Order {
    private List<Item> items;

    public Order(List<Item> itemList) {
        items = new ArrayList<>(itemList);
    }

    public List<Item> getItems() {
        return items;
    }

    public double getTotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return String.format("Order(%s), total = %.2f", items.toString(), getTotal());
    }
}


// %%
void check(boolean condition, String message) {
    if (!condition) {
        System.err.println(message);
    } else {
        System.out.println("Success.");
    }
}

// %%
void testItemName() {
    Item unit = new Item("Apple", 1.0);

    check(unit.getName().equals("Apple"), "Name does not match");
}

// %%
testItemName();

// %%
void testOrderTotal() {
    List<Item> items = new ArrayList<>();
    items.add(new Item("Apple", 1.0));
    items.add(new Item("Banana", -2.0));
    Order unit = new Order(items);

    double total = unit.getTotal();

    check(total == 3.0, "Total is not correct");
}

// %%
testOrderTotal();

// %%
void testOrderOutput() {
    List<Item> items = new ArrayList<>();
    items.add(new Item("Apple", 1.0));
    items.add(new Item("Banana", -2.0));
    Order unit = new Order(items);

    String output = unit.toString();

    check(output.equals("Order([Item(Apple, 1.00), Item(Banana, 2.00)]), total = 3.00"), "Output is not correct");
}

// %%
testOrderOutput();

// %% [markdown]
//
// ## Schnelle Rückmeldung
//
// ### Einflüsse
//
// - Menge des abgedeckten Codes
// - Komplexität/Anzahl Iterationen des abgedeckten Codes
// - Interaktion mit externen Systemen

// %% [markdown]
//
// ## Deterministisch
//
// <ul>
//   <li class="fragment">Gleicher Code führt immer zum gleichen Ergebnis</li>
//   <li class="fragment">Gründe für Nichtdeterminismus
//     <ul>
//       <li class="fragment">Zufallszahlen</li>
//       <li class="fragment">Zeit/Datum</li>
//       <li class="fragment">Interaktion mit externen Systemen</li>
//       <li class="fragment">Nicht initialisierte Variablen</li>
//       <li class="fragment">Kommunikation zwischen Tests</li>
//     </ul>
//   </li>
//   <li class="fragment">
//      Tests, die falsche Warnungen erzeugen sind nicht
//      hilfreich sondern schädlich!
//   </li>
// <ul>

// %%
import java.util.Random;

// %%
void testRandomBad() {
    Random random = new Random();
    int roll = random.nextInt(2) + 1;

    check(roll == 2, "Roll is not 2");
}

// %%
testRandomBad();

// %%
void testRandomBetter() {
    Random random = new Random(42);  // <= Fixed seed!
    int roll = random.nextInt(2) + 1;

    check(roll == 2, "Roll is not 2");
}

// %%
testRandomBetter();

// %%
import java.time.LocalDateTime;

void testDateBad() {
    LocalDateTime now = LocalDateTime.now();

    check(now.getYear() == 2024, "Year is not 2024");
    check(now.getSecond() % 2 == 0, "Second is not even");
}

// %%
testDateBad();

// %%
void testDateBetter() {
    LocalDateTime fixedDate = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

    check(fixedDate.getYear() == 2024, "Year is not 2024");
    check(fixedDate.getSecond() % 2 == 0, "Second is not even");
}

// %%
testDateBetter();

// %% [markdown]
//
// ## Leicht zu warten
//
// <ul>
//   <li>Einfache, standardisierte Struktur<br>
//     <table style="display:inline;margin:20px 20px;">
//     <tr><td style="text-align:left;width:60px;padding-left:15px;">Arrange</td>
//         <td style="text-align:left;width:60px;padding-left:15px;border-left:1px solid black;">Given</td>
//         <td style="text-align:left;width:600px;padding-left:15px;border-left:1px solid black;">
//           Bereite das Test-Environment vor</td></tr>
//     <tr><td style="text-align:left;padding-left:15px;">Act</td>
//         <td style="text-align:left;width:60px;padding-left:15px;border-left:1px solid black;">
//            When</td>
//         <td style="text-align:left;width:600px;padding-left:15px;border-left:1px solid black;">
//            Führe die getestete Aktion aus (falls vorhanden)</td></tr>
//     <tr><td style="text-align:left;padding-left:15px;">Assert</td>
//         <td style="text-align:left;width:60px;padding-left:15px;border-left:1px solid black;">
//            Then</td>
//         <td style="text-align:left;width:600px;padding-left:15px;border-left:1px solid black;">
//            Überprüfe die Ergebnisse</td></tr>
//     </table>
//   </li>
//   <li>Wenig Code
//     <ul>
//       <li>Wenig Boilerplate</li>
//       <li>Factories, etc. für Tests</li>
//     </ul>
//   </li>
// </ul>

// %% [markdown]
//
// ## Unempfindlich gegenüber Refactorings
//
// - Möglichst wenige falsche Positive!
// - Typischerweise vorhanden oder nicht, wenig Zwischenstufen
//
// ### Einflüsse
//
// - Bezug zu Domäne/System
// - Zugriff auf interne Strukturen

// %%
class VeryPrivate {
    private int secret = 42;
}

// %%
import java.lang.reflect.Field;

// %%
void testVeryPrivate() throws NoSuchFieldException, IllegalAccessException {
    VeryPrivate unit = new VeryPrivate();

    Class<?> veryPrivateClass = unit.getClass();
    Field secretField = veryPrivateClass.getDeclaredField("secret");
    secretField.setAccessible(true);
    int secretValue = (int) secretField.get(unit);
    check(secretValue == 42, "Secret is not 42");
}

// %%
testVeryPrivate();

// %% [markdown]
//
// Die folgenden Einflüsse stehen im Konflikt zueinander:
//
// - Erkennen von Fehlern/Regressionen
// - Schnelle Rückmeldung
// - Unempfindlich gegenüber Refactorings
//
// Die Qualität eines Tests hängt vom *Produkt* dieser Faktoren ab!

// %% [markdown]
//
// ## Wie finden wir den Trade-Off?
//
// - Unempfindlich gegenüber Refactorings kann *nie* geopfert werden
// - Wir müssen also einen Kompromiss finden zwischen
//   - Erkennen von Fehlern/Regressionen
//   - Schnelle Rückmeldung
//
// ### Typischerweise
//
// - Schnelles Feedback für die meisten Tests (Unit-Tests)
// - Gründliche Fehlererkennung für wenige Tests (Integrationstests)
