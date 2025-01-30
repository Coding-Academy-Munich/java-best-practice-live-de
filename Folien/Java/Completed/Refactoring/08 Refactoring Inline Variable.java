// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Refactoring: Inline Variable</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ### Inline Variable

// %%
import java.util.List;
import java.util.ArrayList;

// %%
public class OrderLine {
    public String product;
    public int quantity;
    public double price;

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
    public static double orderLinePrice(OrderLine orderLine) {
        return orderLine.quantity * orderLine.price;
    }

    public static double computeTotal(List<OrderLine> orderLines) {
        double total = 0.0;
        for (OrderLine orderLine : orderLines) {
            double orderLinePrice1 = orderLinePrice(orderLine);
            total += orderLinePrice1;
        }
        return total;
    }
}

// %%
Refactoring.computeTotal(makeOrderLines())


// %%
public class Refactoring {
    public static double orderLinePrice(OrderLine orderLine) {
        return orderLine.quantity * orderLine.price;
    }

    public static double computeTotal(List<OrderLine> orderLines) {
        double total = 0.0;
        for (OrderLine orderLine : orderLines) {
            total += orderLinePrice(orderLine); // <-- inline variable
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
// - Manchmal kommuniziert der Name der Variable nicht mehr als der Ausdruck
//   selbst
// - Manchmal verhindert eine Variable das Refactoring von anderem Code

// %% [markdown]
//
// #### Mechanik
//
// - Stelle sicher, dass der Initialisierungs-Ausdruck frei von Seiteneffekten
//   ist
// - Falls die Variable nicht schon konstant ist, mache sie konstant und teste
//   das Programm
// - Finde die erste Referenz auf die Variable
// - Ersetze die Variable durch ihren Initialisierungs-Ausdruck
// - Teste das Programm
// - Wiederhole für jede Referenz auf die Variable
