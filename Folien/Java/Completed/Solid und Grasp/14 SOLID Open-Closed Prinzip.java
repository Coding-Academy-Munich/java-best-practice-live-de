// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>SOLID: Open-Closed Prinzip</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// # Open-Closed Prinzip (SOLID)
//
// Klassen sollen
//
// - Offen für Erweiterung
// - Geschlossen für Modifikation
//
// sein.

// %%
public enum MovieKindV0 {
    REGULAR,
    CHILDREN
}

// %%
public class MovieV0 {
    public MovieV0(String title, MovieKindV0 kind) {
        this.title = title;
        this.kind = kind;
    }

    public String getTitle() {
        return title;
    }

    public MovieKindV0 getKind() {
        return kind;
    }

    public double computePrice() {
        switch (kind) {
            case REGULAR:
                return 4.99;
            case CHILDREN:
                return 5.99;
            default:
                return 0.0;
        }
    }

    public void printInfo() {
        System.out.println(title + " costs " + computePrice());
    }

    private final String title;
    private final MovieKindV0 kind;
}

// %%
MovieV0 m1 = new MovieV0("Casablanca", MovieKindV0.REGULAR);
MovieV0 m2 = new MovieV0("Shrek", MovieKindV0.CHILDREN);

// %%
m1.printInfo();
m2.printInfo();

// %% [markdown]
//
// <img src="img/movie_v0.png" alt="MovieV0"
//      style="display:block;margin:auto;width:50%"/>


// %% [markdown]
//
// Was passiert, wenn wir eine neue Filmart hinzufügen wollen?

// %%
public enum MovieKind {
    REGULAR,
    CHILDREN,
    NEW_RELEASE
}

// %%
public class MovieV1 {
    public MovieV1(String title, MovieKind kind) {
        this.title = title;
        this.kind = kind;
    }

    public String getTitle() {
        return title;
    }

    public MovieKind getKind() {
        return kind;
    }

    public double computePrice() {
        switch (kind) {
            case REGULAR:
                return 4.99;
            case CHILDREN:
                return 5.99;
            case NEW_RELEASE:
                return 6.99;
            default:
                return 0.0;
        }
    }

    public void printInfo() {
        System.out.println(title + " costs " + computePrice());
    }

    private final String title;
    private final MovieKind kind;
}

// %%
MovieV1 m1 = new MovieV1("Casablanca", MovieKind.REGULAR);
MovieV1 m2 = new MovieV1("Shrek", MovieKind.CHILDREN);
// MovieV1 m3 = new MovieV1("Brand New", MovieKind.NEW_RELEASE);

// %%
m1.printInfo();
m2.printInfo();
// m3.printInfo();

// %% [markdown]
//
// <img src="img/movie_v1.png" alt="MovieV1"
//      style="display:block;margin:auto;width:50%"/>

// %% [markdown]
//
// ## OCP-Verletzung
//
// - Neue Filmarten erfordern Änderungen an `MovieV1`
// - `MovieV1` ist nicht geschlossen für Modifikation

// %% [markdown]
//
// ## Auflösung (Versuch 1: Vererbung)
//
// - Neue Filmarten werden als neue Klassen implementiert
// - `MovieV2` wird abstrakt
// - `MovieV2` ist geschlossen für Modifikation

// %%
public abstract class MovieV2 {
    public MovieV2(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public abstract double computePrice();

    public void printInfo() {
        System.out.println(title + " costs " + computePrice());
    }

    private final String title;
}

// %%
public class RegularMovie extends MovieV2 {
    public RegularMovie(String title) {
        super(title);
    }

    @Override
    public double computePrice() {
        return 4.99;
    }
}

// %%
public class ChildrenMovie extends MovieV2 {
    public ChildrenMovie(String title) {
        super(title);
    }

    @Override
    public double computePrice() {
        return 5.99;
    }
}

// %%
public class NewReleaseMovie extends MovieV2 {
    public NewReleaseMovie(String title) {
        super(title);
    }

    @Override
    public double computePrice() {
        return 6.99;
    }
}

// %%
RegularMovie m1 = new RegularMovie("Casablanca");
ChildrenMovie m2 = new ChildrenMovie("Shrek");
NewReleaseMovie m3 = new NewReleaseMovie("Brand New");

// %%
m1.printInfo();
m2.printInfo();
m3.printInfo();

// %% [markdown]
//
// <img src="img/movie_v2.png" alt="MovieV0"
//      style="display:block;margin:auto;width:100%"/>

// %% [markdown]
//
// - `MovieV2` ist offen für Erweiterung
// - Neue Filmarten können hinzugefügt werden, ohne die bestehenden Klassen zu
//   ändern
// - Aber: Die Vererbungshierarchie umfasst die ganze Klasse
//   - Nur eine Art von Variabilität
// - Was ist, wenn wir für andere Zwecke eine andere Klassifikation brauchen?
//   - Z.B. DVD, BluRay, Online?
// - Mehrfachvererbung?
// - Produkt von Klassen?
//   - `ChildrenDVD`, `ChildrenBluRay`, `ChildrenOnline`, ...

// %% [markdown]
//
// ## Bessere Auflösung: Strategy Pattern
//
// - Das Strategy-Pattern erlaubt es uns, die Vererbung auf kleinere Teile der
//   Klasse anzuwenden
// - In fast allen Fällen ist das die bessere Lösung!
// - Vererbung ist ein sehr mächtiges Werkzeug
// - Aber je kleiner und lokaler wir unsere Vererbungshierarchien halten, desto
//   besser

// %% [markdown]
//
// ## Workshop: Smart Home Device Control System
//
// In diesem Workshop arbeiten wir mit einem Szenario, das ein Smart Home
// Gerätesteuerungssystem betrifft. Die Herausforderung? Das vorhandene System
// verletzt das OCP, und es liegt an uns, das zu korrigieren.

// %% [markdown]
//
// ### Szenario
//
// Stellen Sie sich ein Smart-Home-System vor. Dieses System steuert verschiedene
// Geräte: Lichter, Thermostate, Sicherheitskameras und intelligente Schlösser.
// Jede Art von Gerät hat ihren eigenen einzigartigen Steuermechanismus und
// Automatisierungsregeln.
//
// Nun muss das Steuerungssystem des Smart Homes diese Geräte verwalten. Das
// Problem mit dem aktuellen System ist die Verwendung eines Enums zur Bestimmung
// des Gerätetyps und basierend darauf seiner Steuermethode. Dieser Ansatz ist
// nicht skalierbar und verletzt das OCP. Was passiert, wenn ein neuer Typ von
// Smart-Gerät zum Zuhause hinzugefügt wird? Oder was passiert, wenn sich der
// Steuermechanismus für Thermostate ändert? Die aktuelle Code-Struktur erfordert
// Änderungen an mehreren Stellen.

// %%
import java.util.ArrayList;
import java.util.List;

// %%
public enum DeviceType {
    LIGHT,
    THERMOSTAT,
    SECURITY_CAMERA,
    SMART_LOCK
}

// %%
public class DeviceV0 {
    public DeviceV0(DeviceType type) {
        this.type = type;
    }

    public String control() {
        switch (type) {
            case LIGHT:
                return "Turning light on/off.";
            case THERMOSTAT:
                return "Adjusting temperature.";
            case SECURITY_CAMERA:
                return "Activating motion detection.";
            case SMART_LOCK:
                return "Locking/Unlocking door.";
            default:
                return "Unknown device control!";
        }
    }

    public String getStatus() {
        switch (type) {
            case LIGHT:
                return "Light is on/off.";
            case THERMOSTAT:
                return "Current temperature: 22°C.";
            case SECURITY_CAMERA:
                return "Camera is active/inactive.";
            case SMART_LOCK:
                return "Door is locked/unlocked.";
            default:
                return "Unknown device status!";
        }
    }

    private final DeviceType type;
}

// %%
List<DeviceV0> devicesOriginal = new ArrayList<>();
devicesOriginal.add(new DeviceV0(DeviceType.LIGHT));
devicesOriginal.add(new DeviceV0(DeviceType.THERMOSTAT));
devicesOriginal.add(new DeviceV0(DeviceType.SECURITY_CAMERA));

// %%
public static void manageDevices(List<DeviceV0> devices) {
    for (DeviceV0 device : devices) {
        System.out.println(device.control() + " " + device.getStatus());
    }
}

// %%
manageDevices(devicesOriginal);

// %% [markdown]
//
// - Beseitigen Sie das Problem mit der OCP-Verletzung im vorhandenen Code
// - Sie können entweder den vorhandenen Code ändern oder eine neue Lösung von
//   Grund auf neu erstellen

// %%
import java.util.ArrayList;
import java.util.List;

// %%
public interface Device {
    String control();
    String getStatus();
}

// %%
public class Light implements Device {
    @Override
    public String control() {
        return "Turning light on/off.";
    }

    @Override
    public String getStatus() {
        return "Light is on/off.";
    }
}

// %%
public class Thermostat implements Device {
    @Override
    public String control() {
        return "Adjusting temperature.";
    }

    @Override
    public String getStatus() {
        return "Current temperature: 22°C.";
    }
}

// %%
public class SecurityCamera implements Device {
    @Override
    public String control() {
        return "Activating motion detection.";
    }

    @Override
    public String getStatus() {
        return "Camera is active/inactive.";
    }
}

// %%
public class SmartLock implements Device {
    @Override
    public String control() {
        return "Locking/Unlocking door.";
    }

    @Override
    public String getStatus() {
        return "Door is locked/unlocked.";
    }
}

// %%
List<Device> devicesRefactored = new ArrayList<>();
devicesRefactored.add(new Light());
devicesRefactored.add(new Thermostat());
devicesRefactored.add(new SecurityCamera());

// %%
public static void manageDevices(List<Device> devices) {
    for (Device device : devices) {
        System.out.println(device.control() + " " + device.getStatus());
    }
}

// %%
manageDevices(devicesRefactored);
