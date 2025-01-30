// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Refactoring: Inline Funktion</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ### Inline Function
//
// - Anwendung
//   - Wenn eine Funktion nicht mehr tut als ihr Rumpf und der Name nicht mehr
//     aussagt als der Rumpf
//   - Wenn wir eine schlecht strukturierte Hilfsfunktion haben
// - Invers zu *Extrahiere Funktion*

// %%
import java.util.List;

// %%
public class OrderLine {
    String product;
    int quantity;
    double price;

    OrderLine(String product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "OrderLine{" + quantity + " x " + product + " @ $" + price + "}";
    }
}

// %%
static List<OrderLine> makeOrderLines() {
    List<OrderLine> orderLines = new ArrayList<>();
    orderLines.add(new OrderLine("Apple", 2, 0.5));
    orderLines.add(new OrderLine("Banana", 3, 0.3));
    orderLines.add(new OrderLine("Orange", 1, 0.8));
    return orderLines;
}

// %%
public class OrderLineProcessor {
    public static double orderLinePrice(OrderLine orderLine) {
        return orderLine.getPrice();
    }

    public static double orderLineQuantity(OrderLine orderLine) {
        return orderLine.getQuantity();
    }

    public static double computeTotal(List<OrderLine> orderLines) {
        double total = 0.0;
        for (OrderLine orderLine : orderLines) {
            total += orderLineQuantity(orderLine) * orderLinePrice(orderLine);
        }
        return total;
    }
}

// %%
OrderLineProcessor.computeTotal(makeOrderLines());

// %%
public class OrderLineProcessor {
    public static double computeTotal(List<OrderLine> orderLines) {
        double total = 0.0;
        for (OrderLine orderLine : orderLines) {
            total += orderLine.getQuantity() * orderLine.getPrice(); // <-- inline functions
        }
        return total;
    }
}

// %%
OrderLineProcessor.computeTotal(makeOrderLines());

// %% [markdown]
//
// #### Motivation
//
// - Manchmal ist eine Funktion nicht leichter zu verstehen als ihr Rumpf
// - Manchmal sind die von einer Funktion verwendeten Hilfsfunktionen nicht gut
//   strukturiert
// - Generell: Potentiell anwendbar, wenn man aufgrund zu vieler Indirektionen
//   den Überblick verliert

// %% [markdown]
//
// #### Mechanik
//
// - Überprüfe, dass die Funktion nicht virtuell ist
//   - Eine virtuelle Funktion, die von Unterklassen überschrieben wird, können
//     wir nicht entfernen, ohne die Semantik des Programms zu ändern
// - Finde alle Aufrufe der Funktion
// - Ersetze jeden Aufruf durch den Rumpf der Funktion
// - Test nach jedem Schritt!
// - Entferne die Funktionsdefinition
// - Brich ab, falls Schwierigkeiten wegen Rekursion, mehrerer
//   `return`-Anweisungen, etc. auftreten

// %% [markdown]
//
// ## Workshop: Inline Function
//
// In der folgenden Klasse wurden Hilfsfunktionen verwendet, die die Berechnung
// nicht übersichtlicher gestalten.
//
// Entfernen Sie diese Hilfsfunktionen mit dem *Inline Function*-Refactoring.

// %%
class SimpleMathOperations {
    public static int add(int a, int b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static int calculate(int x, int y, int z) {
        int result = add(x, y);
        result = multiply(result, 2);
        result = subtract(result, z);
        return result;
    }

    public static void run() {
        System.out.println(calculate(5, 3, 4));
    }
}

// %%
SimpleMathOperations.run();

// %%
class SimpleMathOperationsV1 {
    public static int calculate(int x, int y, int z) {
        int result = x + y;
        result = result * 2;
        result = result - z;
        return result;
    }

    public static void run() {
        System.out.println(calculate(5, 3, 4));
    }
}

// %%
SimpleMathOperationsV1.run();

// %%
class SimpleMathOperationsV2 {
    public static void calculate(int x, int y, int z) {
        System.out.println((x + y) * 2 - z);
    }
}

// %%
SimpleMathOperationsV2.calculate(5, 3, 4);
