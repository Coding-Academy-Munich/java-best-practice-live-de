// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Refactoring: Extrahiere Funktion</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ### Extrahiere Funktion
//
// - Anwendung:
//   - Funktion ist zu groß
//   - Funktion ist zu komplex
//   - Funktion ist schwer zu verstehen
//   - Funktion hat zu viele Aufgaben
// - Invers zu *Inline Function*

// %%
import java.util.ArrayList;
import java.util.List;

// %%
class OrderLine {
    String product;
    int quantity;
    double price;

    OrderLine(String product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
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

// %% [markdown]
//
// ## Beispiel

// %%
import java.util.Formatter;

// %%
class ReceiptPrinter {
    static void printReceipt(List<OrderLine> orderLines) {
        // Print order lines
        for (OrderLine orderLine : orderLines) {
            System.out.printf("%-12s %4d x %6.2f€%n",
                orderLine.product, orderLine.quantity, orderLine.price);
        }
        // Compute total
        double total = 0.0;
        for (OrderLine orderLine : orderLines) {
            total += orderLine.quantity * orderLine.price;
        }
        // Print total
        System.out.printf("Total: %.2f€%n", total);
    }
}

// %%
ReceiptPrinter.printReceipt(makeOrderLines());

// %%
class ReceiptPrinter {
    static void printReceipt(List<OrderLine> orderLines) {
        for (OrderLine orderLine : orderLines) {
            System.out.printf("%-12s %4d x %6.2f€%n",
                orderLine.product, orderLine.quantity, orderLine.price);
        }
        double total = computeTotal(orderLines);    // <-- call new function
        System.out.printf("Total: %.2f€%n", total);
    }

    static double computeTotal(List<OrderLine> orderLines) {
        double total = 0.0;
        for (OrderLine orderLine : orderLines) {
            total += orderLine.quantity * orderLine.price;
        }
        return total;
    }
}

// %%
ReceiptPrinter.printReceipt(makeOrderLines());

// %% [markdown]
//
// #### Motivation
//
// - Jedes Code-Fragment, das man nicht unmittelbar versteht, sollte in eine
//   Funktion extrahiert werden
// - Name der Funktion spiegelt wieder, **was** die Intention des Codes ist
// - Das kann zu Funktionen führen, die nur eine Zeile Code enthalten
// - Es ist dabei besonders wichtig, dass die Funktionen **gute** Namen haben
// - Kommentare in Funktionen, die sagen, was der nächste Code-Block macht,
//   sind ein Hinweis auf eine mögliche Extraktion

// %% [markdown]
//
// #### Mechanik
//
// - Erzeuge eine neue Funktion
//   - Benenne sie nach der Intention des Codes
// - Kopiere den extrahierten Code in die neue Funktion
// - Übergebe alle Variablen, die die Funktion benötigt, als Parameter
//   - Wenn vorher deklarierte Variablen nur in der neuen Funktion verwendet
//     werden, können sie auch als lokale Variablen in der neuen Funktion
//     deklariert werden
// - Breche die Extraktion ab, falls die Funktion zu viele Parameter bekommt
//   - Wende stattdessen andere Refactorings an, die die Extraktion einfacher
//     machen (Split Variables, Replace Temp with Query, ...)

// %% [markdown]
//
// ### Beispiel für fehlgeschlagene Extraktion
//
// - Siehe `code/completed/invoice/v2` für ein Beispiel einer Extraktion,
//   die abgebrochen wird
//   - IntelliJ kommt beim Refactoring dieser Funktion in eine Endlosschleife
//     und erzeugt ungültigen Code, wenn man sie abbricht
// - Siehe `code/completed/invoice/v3` für eine erfolgreiche
//   Extraktion (nach etwas Refactoring)

// %% [markdown]
//
// ## Workshop: Extrahiere Funktion
//
// - Wenden Sie das "Extrahiere Funktion"-Refactoring auf die Methode `processUserData`
//   an:

// %%
import java.util.ArrayList;
import java.util.List;

// %%
class UserDataProcessor {
    static class User {
        String name;
        int age;

        User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    public static void processUserData(String userData) {
        // Convert string data to User objects
        String[] userStrings = userData.split(";");
        List<User> users = new ArrayList<>();
        for (String userString : userStrings) {
            String[] parts = userString.split(",");
            users.add(new User(parts[0], Integer.parseInt(parts[1])));
        }

        // Calculate average age
        int totalAge = 0;
        for (User user : users) {
            totalAge += user.age;
        }
        double averageAge = (double) totalAge / users.size();

        // Print report
        System.out.println("User Report:");
        for (User user : users) {
            System.out.println(user.name + " is " + user.age + " years old");
        }
        System.out.printf("Average age: %.1f years%n", averageAge);
    }

    public static void run() {
        String userData = "Alice,30;Bob,25;Charlie,35";
        processUserData(userData);
    }
}

// %%
new UserDataProcessor().run()

// %%
import java.util.ArrayList;
import java.util.List;

class UserDataProcessor {
    static class User {
        String name;
        int age;

        User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    public static void processUserData(String userData) {
        List<User> users = parseUserData(userData);
        double averageAge = calculateAverageAge(users);
        printReport(users, averageAge);
    }

    private static List<User> parseUserData(String userData) {
        String[] userStrings = userData.split(";");
        List<User> users = new ArrayList<>();
        for (String userString : userStrings) {
            String[] parts = userString.split(",");
            users.add(new User(parts[0], Integer.parseInt(parts[1])));
        }
        return users;
    }

    private static double calculateAverageAge(List<User> users) {
        int totalAge = 0;
        for (User user : users) {
            totalAge += user.age;
        }
        return (double) totalAge / users.size();
    }

    private static void printReport(List<User> users, double averageAge) {
        System.out.println("User Report:");
        for (User user : users) {
            System.out.println(user.name + " is " + user.age + " years old");
        }
        System.out.printf("Average age: %.1f years%n", averageAge);
    }

    public static void run() {
        String userData = "Alice,30;Bob,25;Charlie,35";
        processUserData(userData);
    }
}

// %%
new UserDataProcessor().run()

// %%
