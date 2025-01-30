// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Refactoring: Extrahiere Variable</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ### Extrahiere Variable

// %%
import java.util.ArrayList;
import java.util.List;

// %%
class OrderLine {
    String product;
    int quantity;
    double price;

    public OrderLine(String product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
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
public class Refactoring {
    public static double computeTotal(List<OrderLine> orderLines) {
        double total = 0.0;
        for (OrderLine orderLine : orderLines) {
            total += orderLine.quantity * orderLine.price;
        }
        return total;
    }
}

// %%
Refactoring.computeTotal(makeOrderLines())

// %%
public class Refactoring {
    public static double computeTotal(List<OrderLine> orderLines) {
        double total = 0.0;
        for (OrderLine orderLine : orderLines) {
            final double orderLinePrice = orderLine.quantity * orderLine.price;
            //           ^^^^^^^^^^^^^^ new variable
            total += orderLinePrice;
        }
        return total;
    }
}

// %%
Refactoring.computeTotal(makeOrderLines())

// %% [markdown]
//
// #### Motivation
//
// - Hilft dabei, komplexe Ausdrücke zu verstehen
//   - Erklärende Variablen/Konstanten
// - Debugging oft einfacher

// %% [markdown]
//
// #### Mechanik
//
// - Stelle sicher, dass der Ausdruck frei von Seiteneffekten ist
// - Erzeuge eine neue konstante Variable
// - Initialisiere sie mit dem Ausdruck
// - Ersetze den Ausdruck durch die Variable
// - Teste das Programm
