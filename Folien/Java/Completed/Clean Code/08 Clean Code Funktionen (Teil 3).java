// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Clean Code: Funktionen (Teil 3)</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ## Weitere Regeln für Funktionen
//
// - Verwende beschreibende Namen
// - Vermeide versteckte Seiteneffekte

// %% [markdown]
//
// ## Versteckte Seiteneffekte

// %%
public class User {
    public boolean isValidPassword(String password) {
        // Implementation here
        return true;
    }

    public boolean hasSession() {
        // Implementation here
        return false;
    }

    public void initializeNewSession() {
        // Implementation here
    }
}

public class UserManager {
    public User findUserByName(String userName) {
        // Implementation here
        return new User();
    }
}

public class PasswordChecker {
    private UserManager userManager = new UserManager();

    public boolean checkPassword(String userName, String password) {
        User user = userManager.findUserByName(userName);
        if (user != null) {
            if (user.isValidPassword(password)) {
                if (user.hasSession()) {
                    user.initializeNewSession();
                }
                return true;
            }
        }
        return false;
    }
}

// %% [markdown]
//
// ## Command-Query Separation

// %%
public class DefaultValueManager {
    private static int defaultValue = -1;

    public static boolean badHasDefaultValue() {
        if (defaultValue >= 0) {
            return true;
        } else {
            defaultValue = 123;
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(defaultValue);
        System.out.println(badHasDefaultValue());
        System.out.println(defaultValue);
    }
}

// %%
public class DefaultValueManager {
    private static int defaultValue = -1;

    public static boolean hasDefaultValue() {
        return defaultValue >= 0;
    }

    public static void setDefaultValue() {
        defaultValue = 123;
    }

    public static void main(String[] args) {
        System.out.println(defaultValue);
        System.out.println(hasDefaultValue());
        System.out.println(defaultValue);
        setDefaultValue();
        System.out.println(defaultValue);
    }
}

// %% [markdown]
//
// ## Vermeide "Ausgabeargumente"
//
// Java hat keine "echten" Ausgabeargumente. Aber Modifikation von Objekten hat
// oft ähnliche Konsequenzen:

// %%
import java.util.List;

public class HitResult {
    public boolean collisionOccurred;

    public HitResult() {
        this.collisionOccurred = false;
    }
}

public class Obstacle {
    // Obstacle's definition
}

public class PlayerV1 {
    public void checkCollision(List<Obstacle> obstacles, HitResult hitResult) {
        // Complicated computation...
        hitResult.collisionOccurred = true;
    }
}

// %%
import java.util.List;

public class PlayerV1Demo {
    public static void main(String[] args) {
        PlayerV1 player = new PlayerV1();
        HitResult hitResult = new HitResult();
        List<Obstacle> obstacles = List.of(); // empty list for example

        player.checkCollision(obstacles, hitResult);
        if (hitResult.collisionOccurred) {
            System.out.println("Detected collision!");
        }
    }
}

// %%
import java.util.List;

public class Player {
    public HitResult checkCollision(List<Obstacle> obstacles) {
        // Complicated computation...
        return new HitResult(true);
    }
}

// %%
import java.util.List;

public class PlayerDemo {
    public static void main(String[] args) {
        Player player = new Player();
        HitResult hitResult = player.checkCollision(List.of());
        if (hitResult.collisionOccurred) {
            System.out.println("Detected collision!");
        }
    }
}

// %% [markdown]
//
// ## Fehlerbehandlung
//
// Verwende Ausnahmen zur Fehlerbehandlung (siehe später).

// %% [markdown]
//
// ## DRY: Don't Repeat Yourself
//
// - Versuche, duplizierten Code zu eliminieren.
//   - Wiederholung bläht den Code auf
//   - Wiederholung von Code erfordert mehrere Modifikationen für jede Änderung
// - Aber: oft ist duplizierter Code mit anderem Code durchsetzt
// - Berücksichtige den Bereich, in dem der Code DRY ist!

// %%
import java.util.Arrays;
import java.util.List;

public class StringSanitizer {
    public static String sanitizeUserNameV1(String userName) {
        List<String> forbiddenCharacters = Arrays.asList("&", "<", ">");
        List<String> replacementCharacters = Arrays.asList("&amp;", "&lt;", "&gt;");
        String sanitizedUserName = userName;

        for (int i = 0; i < forbiddenCharacters.size(); i++) {
            sanitizedUserName = sanitizedUserName.replace(forbiddenCharacters.get(i), replacementCharacters.get(i));
        }

        return sanitizedUserName;
    }

    public static void main(String[] args) {
        String sanitized = sanitizeUserNameV1("Hans & Franz <hf@example.com>");
        System.out.println(sanitized);
    }
}

// %%
public class AddressSanitizer {
    public static String sanitizeAddressV1(String address) {
        List<String> forbiddenCharacters = Arrays.asList("&", "<", ">");
        List<String> replacementCharacters = Arrays.asList("&amp;", "&lt;", "&gt;");
        for (int i = 0; i < forbiddenCharacters.size(); i++) {
            address = address.replace(forbiddenCharacters.get(i), replacementCharacters.get(i));
        }
        return address;
    }

    public static void main(String[] args) {
        System.out.println(sanitizeAddressV1("Hans & Franz <hf@example.com>"));
    }
}

// %%
public class StringSanitizer {
    public static String sanitizeString(String str) {
        String[] forbiddenCharacters = {"&", "<", ">"};
        String[] replacementCharacters = {"&amp;", "&lt;", "&gt;"};
        for (int i = 0; i < forbiddenCharacters.length; i++) {
            str = str.replace(forbiddenCharacters[i], replacementCharacters[i]);
        }
        return str;
    }
}

// %%
public class StringSanitizer {
    public static String sanitizeUserNameV2(String userName) {
        return sanitizeString(userName);
    }

    public static void main(String[] args) {
        System.out.println(sanitizeUserNameV2("Hans & Franz <hf@example.com>"));
    }
}

// %%
public class AddressSanitizer {
    public static String sanitizeAddressV2(String address) {
        return StringSanitizer.sanitizeString(address);
    }

    public static void main(String[] args) {
        String sanitizedAddress = sanitizeAddressV2("Hans & Franz <hf@example.com>");
        System.out.println(sanitizedAddress);
    }
}

// %% [markdown]
//
// ## Problematische Regeln aus dem Buch
//
// - Verwende wenige (oder keine) Argumente
// - Verwende keine booleschen Argumente (Flag-Argumente)

// %% [markdown]
//
// - Boolesche Argumente sind oft ein Hinweis auf eine fehlende Abstraktion
// - Wenn es zwei mögliche Werte gibt, gibt es oft auch mehr
//
// ### Beispiel
//
// Wähle ein Fahrzeug abhängig davon, ob es fliegen kann oder nicht.

// %%
public class VehicleChooser {
    public static String chooseVehicleV1(boolean canFly) {
        if (canFly) {
            return "plane";
        } else {
            return "car";
        }
    }

    public static void main(String[] args) {
        System.out.println(chooseVehicleV1(true));
    }
}

// %% [markdown]
//
// In einer neuen Iteration wollen wir auch Fahrzeuge, die schwimmen können:

// %%
public class VehicleChooser {
    public static String chooseVehicleV2(boolean canFly, boolean canSwim) {
        if (canSwim) {
            return "boat";
        } else if (canFly) {
            return "plane";
        } else {
            return "car";
        }
    }

    public static void main(String[] args) {
        System.out.println(chooseVehicleV2(true, true));
    }
}

// %% [markdown]
//
// ### Probleme
//
// - Jedes Fahrzeug, das schwimmen kann ist in dieser Version ein Schiff
//   - Das ist aber nicht korrekt, wenn `can_fly` auch wahr ist
//   - In diesem Fall sollte entweder `seaplane` zurückgegeben, oder ein Fehler gemeldet
//     werden
// - Der Fall, dass ein Fahrzeug fahren kann, ist ein impliziter Default, wenn alle
//   Argumente falsch sind

// %%
public class VehicleChooser {
    public static String chooseVehicleV3(boolean canFly, boolean canSwim) {
        if (canFly) {
            if (canSwim) {
                throw new IllegalArgumentException("No seaplanes available");
            } else {
                return "plane";
            }
        } else if (canSwim) {
            return "boat";
        } else {
            return "car";
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(chooseVehicleV3(true, true));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

// %% [markdown]
//
// - Diese Version löst das Problem mit Wasserflugzeugen, aber nicht den impliziten
//   Default
// - Was passiert, wenn wir `can_drive` hinzufügen?

// %%
public class VehicleChooser {
    public static String chooseVehicleV4(boolean canFly, boolean canSwim, boolean canDrive) {
        if (canFly) {
            return "plane";
        } else if (canSwim) {
            return "boat";
        } else if (canDrive) {
            return "car";
        } else {
            throw new IllegalArgumentException("No such vehicle available");
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(chooseVehicleV4(true, false, false));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

// %% [markdown]
//
// - Jetzt haben wir 8 mögliche Kombinationen von Argumenten, von denen nur 3
//   gültig sind
// - Aufrufe mit unbenannten Argumenten sind schwer zu lesen
// - Es ist oft besser, das z.B. mit einer Enumeration zu modellieren

// %%
public class VehicleChooser {
    public static String chooseVehicleV4(boolean canFly, boolean canSwim, boolean canDrive) {
        // Implementation here
        return "";
    }

    public static void main(String[] args) {
        System.out.println(chooseVehicleV4(true, false, false)); // ???
    }
}

// %%
public enum LocomotionType {
    FLYING,
    SWIMMING,
    DRIVING
}

// %%
public class VehicleChooser {
    public static String chooseVehicleV5(LocomotionType locomotionType) {
        switch (locomotionType) {
            case FLYING:
                return "plane";
            case SWIMMING:
                return "boat";
            case DRIVING:
                return "car";
            default:
                throw new IllegalArgumentException("No such vehicle available");
        }
    }

    public static void main(String[] args) {
        System.out.println(chooseVehicleV5(LocomotionType.DRIVING));
    }
}

// %%
System.out.println(VehicleChooser.chooseVehicleV5(LocomotionType.DRIVING));

// %%
System.out.println(VehicleChooser.chooseVehicleV5(LocomotionType.SWIMMING));

// Note: Java doesn't support bitwise operations on enums directly like C++.
// You'd need to implement a custom solution to combine multiple LocomotionTypes.

// %%
