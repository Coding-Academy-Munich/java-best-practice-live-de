// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>SOLID: OCP (Teil 2)</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ## Wiederholung: OCP-Verletzung
//
// <img src="img/movie_v0.png" alt="MovieV0"
//      style="display:block;margin:auto;width:50%"/>

// %% [markdown]
//
// ## Lösungsversuch 1: Vererbung
//
// <img src="img/movie_v2.png" alt="MovieV2"
//      style="display:block;margin:auto;width:70%"/>

// %% [markdown]
//
// - OCP ist erfüllt
// - Großer Scope der Vererbung
//   - Preisberechnung ist das wichtigste an Filmen?
// - Nur eindimensionale Klassifikation
// - Keine Möglichkeit, Preisschema zu wechseln

// %% [markdown]
//
// ## Lösungsversuch 2: Strategie-Muster
//
// <img src="img/movie_v3.png" alt="MovieV3"
//      style="display:block;margin:auto;width:80%"/>

// %% [markdown]
//
// - OCP ist erfüllt
// - Vererbung ist auf die Preisberechnung beschränkt
// - Mehrdimensionale Klassifikation ist einfach
// - Preisschema kann zur Laufzeit gewechselt werden

// %% [markdown]
//
// ## Implementierung

// %%
import java.util.ArrayList;
import java.util.List;

// %%
class Movie {}

// %%
interface PricingStrategy {
    double computePrice(Movie movie);
}

// %%
class RegularPriceStrategy implements PricingStrategy {
    @Override
    public double computePrice(Movie movie) {
        return 4.99;
    }
}

// %%
class ChildrenPriceStrategy implements PricingStrategy {
    @Override
    public double computePrice(Movie movie) {
        return 5.99;
    }
}

// %%
class NewReleasePriceStrategy implements PricingStrategy {
    @Override
    public double computePrice(Movie movie) {
        return 6.99;
    }
}

// %%
class Movie {
    private String title;
    private PricingStrategy pricingStrategy;

    public Movie(String title, PricingStrategy pricingStrategy) {
        this.title = title;
        this.pricingStrategy = pricingStrategy;
    }

    public double computePrice() {
        return pricingStrategy.computePrice(this);
    }

    public void printInfo() {
        System.out.println(title + " costs " + computePrice());
    }
}

// %%
List<Movie> movies = new ArrayList<>();
movies.add(new Movie("Casablanca", new RegularPriceStrategy()));
movies.add(new Movie("Shrek", new ChildrenPriceStrategy()));
movies.add(new Movie("Brand New", new NewReleasePriceStrategy()));

// %%
for (Movie movie : movies) {
    movie.printInfo();
}

// %% [markdown]
//
// ## Workshop: Berechnung von ÖPNV-Fahrpreisen
//
// In einer modernen Stadt stehen verschiedene öffentliche Verkehrsmittel zur
// Verfügung – Busse, U-Bahnen, Züge, Boote, etc. Jedes dieser Verkehrsmittel
// hat seine eigene Methode zur Fahrpreisberechnung. Zum Beispiel können
// Bustarife auf Pauschalpreisen basieren, U-Bahnen können auf
// Entfernungstarifen basieren und Boote können Premiumtarife für
// landschaftlich reizvolle Strecken haben.

// %% [markdown]
//
// Sie haben ein rudimentäres Fahrpreisberechnungssystem, das den Fahrpreis
// basierend auf dem Verkehrsmittel bestimmt. Leider verstößt dieses System
// gegen das OCP, da es ohne Modifikation nicht für die Erweiterung geöffnet
// ist. Jedes Mal, wenn ein neues Verkehrsmittel hinzugefügt werden muss, muss
// das Kernsystem geändert werden.
//
// Ihre Aufgabe ist es, das System so zu refaktorisieren, dass es dem OCP
// entspricht. Genauer gesagt, werden Sie die `switch`-Anweisung aus der
// Fahrpreisberechnungslogik entfernen. Das Ziel ist es, das System leicht
// erweiterbar zu machen, so dass neue Verkehrsmittel hinzugefügt werden können,
// ohne den vorhandenen Code zu ändern.

// %%
import java.util.*;

// %%
enum TransportType {
    BUS,
    SUBWAY,
    TRAIN,
    BOAT
}

// %%
class Transport {
    private TransportType type;

    public Transport(TransportType type) {
        this.type = type;
    }

    public double calculateFare(double distance) {
        switch (type) {
            case BUS: return 2.50; // flat rate
            case SUBWAY: return 1.50 + (distance * 0.20); // base rate + per km
            case TRAIN: return 5.00 + (distance * 0.15); // base rate + per km
            case BOAT: return 10.00; // premium rate
            default: return 0.0;
        }
    }
}

// %%
Transport bus = new Transport(TransportType.BUS);
System.out.println("Bus fare: $" + bus.calculateFare(10));

// %%
Transport subway = new Transport(TransportType.SUBWAY);
System.out.println("Subway fare: $" + subway.calculateFare(10));

// %%
Transport train = new Transport(TransportType.TRAIN);
System.out.println("Train fare: $" + train.calculateFare(10));

// %%
Transport boat = new Transport(TransportType.BOAT);
System.out.println("Boat fare: $" + boat.calculateFare(10));

// %%
import java.util.*;

// %%
interface FareCalculationStrategy {
    double calculateFare(double distance);
}


// %%
class BusFare implements FareCalculationStrategy {
    @Override
    public double calculateFare(double distance) {
        return 2.50; // flat rate
    }
}

// %%
class SubwayFare implements FareCalculationStrategy {
    @Override
    public double calculateFare(double distance) {
        return 1.50 + (distance * 0.20); // base rate + per km
    }
}

// %%
class TrainFare implements FareCalculationStrategy {
    @Override
    public double calculateFare(double distance) {
        return 5.00 + (distance * 0.15); // base rate + per km
    }
}

// %%
class BoatFare implements FareCalculationStrategy {
    @Override
    public double calculateFare(double distance) {
        return 10.00; // premium rate
    }
}

// %%
class Transport {
    private FareCalculationStrategy fareStrategy;

    public Transport(FareCalculationStrategy fareStrategy) {
        this.fareStrategy = fareStrategy;
    }

    public double computeFare(double distance) {
        return fareStrategy.calculateFare(distance);
    }
}

// %%
Transport bus = new Transport(new BusFare());
System.out.println("Bus fare: $" + bus.computeFare(10));

// %%
Transport subway = new Transport(new SubwayFare());
System.out.println("Subway fare: $" + subway.computeFare(10));

// %%
Transport train = new Transport(new TrainFare());
System.out.println("Train fare: $" + train.computeFare(10));

// %%
Transport boat = new Transport(new BoatFare());
System.out.println("Boat fare: $" + boat.computeFare(10));

// %% [markdown]
//
// ## Extra-Workshop: Smart Home Device Control System mit Strategy
//
// In einem früheren Workshop haben wir ein System zur Kontrolle von Smart Home
// Devices implementiert.
//
// Lösen Sie das OCP-Problem für dieses System mit dem Strategy-Muster.

// %%
interface DeviceStrategy {
    void control();
    void getStatus();
}

// %%
class LightStrategy implements DeviceStrategy {
    @Override
    public void control() {
        System.out.println("Light control");
    }

    @Override
    public void getStatus() {
        System.out.println("Light status");
    }
}

// %%
class ThermostatStrategy implements DeviceStrategy {
    @Override
    public void control() {
        System.out.println("Thermostat control");
    }

    @Override
    public void getStatus() {
        System.out.println("Thermostat status");
    }
}

// %%
class SecurityCameraStrategy implements DeviceStrategy {
    @Override
    public void control() {
        System.out.println("Security camera control");
    }

    @Override
    public void getStatus() {
        System.out.println("Security camera status");
    }
}
// %%
class SmartLockStrategy implements DeviceStrategy {
    @Override
    public void control() {
        System.out.println("Smart lock control");
    }

    @Override
    public void getStatus() {
        System.out.println("Smart lock status");
    }
}

// %%
import java.util.ArrayList;
import java.util.List;

// %%
class SmartHomeDevice {
    private DeviceStrategy strategy;

    public SmartHomeDevice(DeviceStrategy strategy) {
        this.strategy = strategy;
    }

    public void control() {
        strategy.control();
    }

    public void getStatus() {
        strategy.getStatus();
    }
}

// %%
import java.util.*;

// %%
List<SmartHomeDevice> devices = new ArrayList<>();

// %%
devices.add(new SmartHomeDevice(new LightStrategy()));
devices.add(new SmartHomeDevice(new ThermostatStrategy()));
devices.add(new SmartHomeDevice(new SecurityCameraStrategy()));
devices.add(new SmartHomeDevice(new SmartLockStrategy()));

// %%
for (SmartHomeDevice device : devices) {
    device.control();
    device.getStatus();
}
