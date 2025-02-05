// -*- coding: utf-8 -*-
// %% [markdown]
// <!--
// clang-format off
// -->
//
// <div style="text-align:center; font-size:200%;">
//  <b>SOLID: Interface-Segregations-Prinzip</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ## SOLID : Interface-Segregations-Prinzip
//
// - Kein Client einer Klasse `C` sollte von Methoden abhängen, die er nicht
//   benutzt.
// - Wenn das nicht der Fall ist
// - Unterteile die Schnittstelle von `C` in mehrere unabhängige Schnittstellen.
// - Ersetze `C` in jedem Client durch die vom Client tatsächlich verwendeten
//   Schnittstellen.

// %%
public class Car {
    public void drive() {
        System.out.println("Accelerating.");
    }

    public void repair() {
        System.out.println("Repairing.");
    }
}

// %%
public class Driver {
    public void drive(Car car) {
        car.drive();
    }
}

// %%
public class Mechanic {
    public void workOn(Car car) {
        car.repair();
    }
}

// %%
Driver d = new Driver();
Mechanic m = new Mechanic();
Car c = new Car();

// %%
d.drive(c);

// %%
m.workOn(c);

// %%
public interface Drivable {
    void drive();
}

// %%
public interface Repairable {
    void repair();
}

// %%
public class Car2 implements Drivable, Repairable {
    @Override
    public void drive() {
        System.out.println("Accelerating.");
    }

    @Override
    public void repair() {
        System.out.println("Repairing.");
    }
}
// %%
public class Driver2 {
    public void drive(Drivable car) {
        car.drive();
    }
}

// %%
public class Mechanic2 {
    public void workOn(Repairable car) {
        car.repair();
    }
}

// %%
Driver2 d2 = new Driver2();
Mechanic2 m2 = new Mechanic2();
Car2 c2 = new Car2();

// %%
d2.drive(c2);

// %%
m2.workOn(c2);

// %% [markdown]
//
// ## Workshop:
//
// In diesem Workshop werden wir an einem Restaurant-Management-System arbeiten.
//
// Stellen Sie sich vor, Sie haben den Code eines Restaurant-Management-Systems
// erhalten. Das System hat derzeit eine einzige Schnittstelle
// `RestaurantOperations`, die alle Operationen definiert, die in einem
// Restaurant durchgeführt werden können. Verschiedene Rollen im Restaurant, wie
// der Kunde, der Koch, der Kassierer und der Hausmeister, verwenden alle
// dieselbe Schnittstelle, aber jede Rolle verwendet nur einen Teil ihrer
// Funktionen.
//
// Ihre Aufgabe ist es, dieses System so umzubauen, dass es dem
// Interface-Segregations-Prinzip entspricht. Das bedeutet, dass kein Client
// gezwungen werden sollte, von Schnittstellen abhängig zu sein, die er nicht
// verwendet.

// %% [markdown]
//
// 1. Identifizieren Sie, welche Operationen für welche Rollen relevant sind.
// 2. Teilen Sie das `RestaurantOperations`-Interface in kleinere,
//    rollenspezifische Schnittstellen auf.
// 3. Passen Sie die `Restaurant`-Klasse und die rollenbasierten Client-Klassen
//    (`Customer`, `Chef`, `Cashier`, `Janitor`) an die neuen Schnittstellen an.
// 4. Stellen Sie sicher, dass jede Client-Klasse nur über die für ihre Rolle
//    relevanten Operationen Bescheid weiß.

// %%
public interface RestaurantOperations {
    void placeOrder();
    void cookOrder();
    void calculateBill();
    void cleanTables();
}

// %%
public class Restaurant implements RestaurantOperations {
    @Override
    public void placeOrder() {
        System.out.println("Order has been placed.");
    }

    @Override
    public void cookOrder() {
        System.out.println("Order is being cooked.");
    }

    @Override
    public void calculateBill() {
        System.out.println("Bill is being calculated.");
    }

    @Override
    public void cleanTables() {
        System.out.println("Tables are being cleaned.");
    }
}

// %%
public class Customer {
    private RestaurantOperations restaurant;

    public Customer(RestaurantOperations r) {
        this.restaurant = r;
    }

    public void makeOrder() {
        restaurant.placeOrder();
        restaurant.calculateBill();
    }
}
// %%
public class Chef {
    private RestaurantOperations restaurant;

    public Chef(RestaurantOperations r) {
        this.restaurant = r;
    }

    public void prepareFood() {
        restaurant.cookOrder();
    }
}

// %%
public class Cashier {
    private RestaurantOperations restaurant;

    public Cashier(RestaurantOperations r) {
        this.restaurant = r;
    }

    public void generateBill() {
        restaurant.calculateBill();
    }
}

// %%
public class Janitor {
    private RestaurantOperations restaurant;

    public Janitor(RestaurantOperations r) {
        this.restaurant = r;
    }

    public void clean() {
        restaurant.cleanTables();
    }
}

// %%
Restaurant restaurant = new Restaurant();
Customer customer = new Customer(restaurant);
Chef chef = new Chef(restaurant);
Cashier cashier = new Cashier(restaurant);
Janitor janitor = new Janitor(restaurant);

// %%
customer.makeOrder();
chef.prepareFood();
cashier.generateBill();
janitor.clean();

// %%
public interface Ordering {
    void placeOrder();
}

// %%
public interface Cooking {
    void cookOrder();
}

// %%
public interface Billing {
    void calculateBill();
}

// %%
public interface Cleaning {
    void cleanTables();
}

// %%
public class Restaurant implements Ordering, Cooking, Billing, Cleaning {
    @Override
    public void placeOrder() {
        System.out.println("Order has been placed.");
    }

    @Override
    public void cookOrder() {
        System.out.println("Order is being cooked.");
    }

    @Override
    public void calculateBill() {
        System.out.println("Bill is being calculated.");
    }

    @Override
    public void cleanTables() {
        System.out.println("Tables are being cleaned.");
    }
}

// %%
public class Customer {
    private Ordering ordering;
    private Billing billing;

    public Customer(Ordering o, Billing b) {
        this.ordering = o;
        this.billing = b;
    }

    public void makeOrder() {
        ordering.placeOrder();
        billing.calculateBill();
    }
}

// %%
public class Chef {
    private Cooking cooking;

    public Chef(Cooking c) {
        this.cooking = c;
    }

    public void prepareFood() {
        cooking.cookOrder();
    }
}

// %%
public class Cashier {
    private Billing billing;

    public Cashier(Billing b) {
        this.billing = b;
    }

    public void generateBill() {
        billing.calculateBill();
    }
}

// %%
public class Janitor {
    private Cleaning cleaning;

    public Janitor(Cleaning c) {
        this.cleaning = c;
    }

    public void clean() {
        cleaning.cleanTables();
    }
}

// %%
Restaurant restaurant = new Restaurant();
Customer customer = new Customer(restaurant, restaurant);
Chef chef = new Chef(restaurant);
Cashier cashier = new Cashier(restaurant);
Janitor janitor = new Janitor(restaurant);

// %%
customer.makeOrder();
chef.prepareFood();
cashier.generateBill();
janitor.clean();
