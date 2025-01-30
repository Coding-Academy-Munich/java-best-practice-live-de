// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Effective Java T1: Statische Fabrikmethoden</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ## Statische Fabrikmethoden
//
// - Alternative zu öffentlichen Konstruktoren
// - Statische Methode, die eine Instanz zurückgibt
// - Viele Vorteile, aber auch Nachteile gegenüber Konstruktoren

// %%
import java.util.function.BiFunction;

// %%
public class Color {
    private final int red;
    private final int green;
    private final int blue;

    private Color(int red, int green, int blue) {               // <= private constructor
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static Color fromRgb(int red, int green, int blue) { // <= static factory method
        return new Color(red, green, blue);
    }

    @Override public String toString() {
        return String.format(
            "Color: r=%03d g=%03d b=%03d hex=#%02X%02X%02X id=%d",
            red, green, blue, red, green, blue, System.identityHashCode(this));
    }
}

// %%
Color.fromRgb(100, 150, 200)


// %%
public class Color {
    private final int red;
    private final int green;
    private final int blue;

    private Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static Color fromRgb(int red, int green, int blue) {
        return new Color(red, green, blue);
    }

    public static Color fromHexString(String hex) {
        BiFunction<String, Integer, Integer> d2s = (s, i) -> Integer.parseInt(s.substring(i, i + 2), 16);
        return new Color(d2s.apply(hex, 1), d2s.apply(hex, 3), d2s.apply(hex, 5));
    }

    public static Color white() {
        return new Color(255, 255, 255);
    }

    @Override public String toString() {
        return String.format(
            "Color: r=%03d g=%03d b=%03d hex=#%02X%02X%02X id=%d",
            red, green, blue, red, green, blue, System.identityHashCode(this));
    }
}

// %%
Color.white()

// %%
Color.fromRgb(100, 150, 200);

// %%
Color.fromHexString("#6496C8");

// %% [markdown]
//
// ## Vorteile statischer Fabrikmethoden
//
// 1. Aussagekräftige Namen
// 2. Nicht jeder Aufruf erzeugt neues Objekt
// 3. Können Untertypen zurückgeben
// 4. Rückgabetyp kann sich mit Parametern ändern
// 5. Klasse des zurückgegebenen Objekts muss nicht `public` sein

// %% [markdown]
//
// ### Aussagekräftige Namen
//
// - `Point` kann konstruiert werden aus:
//   - Kartesischen Koordinaten $(x, y)$
//   - Polarkoordinaten $(r, θ)$
// - Beide Konstruktoren haben dieselbe Signatur
// - Factory Methoden:
//   - `fromCartesian(double x, double y)`
//   - `fromRadial(double r, double theta)`

// %%
public class Point {
    private double x;
    private double y;

    // Private constructor
    private Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Static factory method for Cartesian coordinates
    public static Point fromCartesian(double x, double y) {
        return new Point(x, y);
    }

    // Static factory method for Radial coordinates
    public static Point fromRadial(double r, double theta) {
        double x = r * Math.cos(theta);
        double y = r * Math.sin(theta);
        return new Point(x, y);
    }

    // Getters and setters for Cartesian coordinates
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Getters and setters for Radial coordinates
    public double getR() {
        return Math.sqrt(x * x + y * y);
    }

    public void setR(double r) {
        double theta = getTheta();
        this.x = r * Math.cos(theta);
        this.y = r * Math.sin(theta);
    }

    public double getTheta() {
        return Math.atan2(y, x);
    }

    public void setTheta(double theta) {
        double r = getR();
        this.x = r * Math.cos(theta);
        this.y = r * Math.sin(theta);
    }

    // Method to set both r and theta at once
    public void setPolar(double r, double theta) {
        this.x = r * Math.cos(theta);
        this.y = r * Math.sin(theta);
    }

    @Override
    public String toString() {
        return String.format("Point(x=%.2f, y=%.2f, r=%.2f, theta=%.2f)", x, y, getR(), getTheta());
    }
}

// %% [markdown]
//
// #### Konstruktion aus kartesischen Koordinaten

// %%
Point.fromCartesian(1, 0)

// %%
Point.fromCartesian(0, 1)

// %%
Point p = Point.fromCartesian(0, 1);
p

// %%
p.setTheta(0);
p

// %% [markdown]
//
// #### Konstruktion aus Polarkoordinaten

// %%
Point.fromCartesian(1, 1)

// %%
Point.fromRadial(2, 0)

// %%
Point.fromRadial(2, Math.PI / 4)

// %% [markdown]
//
// ### Wiederverwendung von Instanzen (Interned Instances)

// %%
public class Color {
    private final int red;
    private final int green;
    private final int blue;
    // HashMap to cache interned instances
    private static final Map<String, Color> CACHE = new HashMap<>();

    private Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static Color fromHexString(String hex) {
        BiFunction<String, Integer, Integer> d2s = (s, i) -> Integer.parseInt(s.substring(i, i + 2), 16);
        // Use cache for interned instances
        if (!CACHE.containsKey(hex)) {
            CACHE.put(hex, new Color(d2s.apply(hex, 1), d2s.apply(hex, 3), d2s.apply(hex, 5)));
        }
        return CACHE.get(hex);
    }

    public static Color fromRgb(int red, int green, int blue) {
        // Delegate to fromHexString to use cache
        return Color.fromHexString(String.format("#%02X%02X%02X", red, green, blue));
    }

    // We already fixed this in the initial implementation
    public static Color white() {
        return Color.fromRgb(255, 255, 255);
    }

    @Override public String toString() {
        return String.format(
            "Color: r=%03d g=%03d b=%03d hex=#%02X%02X%02X id=%d",
            red, green, blue, red, green, blue, System.identityHashCode(this));
    }
}

// %%
Color.white()

// %%
Color.fromRgb(255, 255, 255)

// %%
Color.fromHexString("#FFFFFF")

// %%
Color.fromRgb(100, 150, 200)

// %%
Color.fromHexString("#6496C8")

// %% [markdown]
//
// ### Zurückgeben von Untertypen, dynamischer Rückgabetyp
//
// - Statische Fabrikmethoden können Untertypen zurückgeben
// - Der Rückgabetyp kann sich für verschiedene Parameter ändern
// - Beispiele:
//   - `Collections`-Framework
//   - Figuren in einem Computerspiel

// %%
interface Character {
    String getCharacterClass();
}

// %%
class Fighter implements Character {
    @Override
    public String getCharacterClass() {
        return "Fighter";
    }
}

// %%
class Mage implements Character {
    @Override
    public String getCharacterClass() {
        return "Mage";
    }
}


// %%
import java.util.Set;

// %%
interface Character {
    String getCharacterClass();

    static Character create(Set<String> abilities) {
        if (abilities.contains("cast spells")) {
            return new Mage();
        }
        return new Fighter();
    }
}

// %%
Character fighter = Character.create(Set.of());

// %%
fighter.getCharacterClass()

// %%
Character mage = Character.create(Set.of("cast spells"));

// %%
mage.getCharacterClass()

// %%
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

// %% [markdown]
//
// ### `Character`-Interface mit Registrierung von Klassen

// %%
interface Character {
    String getCharacterClass();

    class CharacterClassInfo {
        Set<String> requiredAbilities;
        Class<? extends Character> characterClass;

        CharacterClassInfo(
                Set<String> requiredAbilities, Class<? extends Character> characterClass) {
            this.requiredAbilities = requiredAbilities;
            this.characterClass = characterClass;
        }
    }

    List<CharacterClassInfo> registeredClasses = new ArrayList<>();

    static void registerCharacterClass(
            Set<String> requiredAbilities, Class<? extends Character> characterClass) {
        registeredClasses.add(new CharacterClassInfo(requiredAbilities, characterClass));
    }

    static Character create(Set<String> abilities) {
        for (CharacterClassInfo info : registeredClasses) {
            if (abilities.containsAll(info.requiredAbilities)) {
                try {
                    Constructor<? extends Character> constructor = info.characterClass.getDeclaredConstructor();
                    return constructor.newInstance();
                } catch (Exception e) {
                    System.err.println("Error creating instance of " + info.characterClass.getName() + ": " + e.getMessage());
                }
            }
        }
        throw new IllegalArgumentException("No suitable character class found for the given abilities.");
    }
}

// %%
public class Fighter implements Character {
    @Override
    public String getCharacterClass() {
        return "Fighter";
    }
}

// %%
public class Mage implements Character {
    @Override
    public String getCharacterClass() {
        return "Mage";
    }
}

// %%
Character.registerCharacterClass(Set.of("cast spells"), Mage.class);
Character.registerCharacterClass(Set.of("fight"), Fighter.class);

// %%
Character fighter = Character.create(Set.of("fight"));

// %%
fighter.getCharacterClass()


// %%
Character mage = Character.create(Set.of("cast spells", "fly"));

// %%
mage.getCharacterClass()

// %%
Character warMage = Character.create(Set.of("cast spells", "fight"))

// %%
warMage.getCharacterClass()

// %%
// Character thief = Character.create(Set.of("steal"));

// %%
// Register a default class
Character.registerCharacterClass(Set.of(), Fighter.class);

// %%
Character thief = Character.create(Set.of("steal"));

// %%
thief.getCharacterClass()

// %% [markdown]
//
// ### Privater Rückgabetyp
//
// - Beispiel: Collections-Framework

// %%
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

// %%
List<String> strings = List.of("to", "be", "or", "not", "to", "be");

// %%
strings

// %% [markdown]
//
// - Dokumentation von
//   [List.of](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/util/List.html#of())

// %%
strings.getClass()

// %%
strings.getClass().accessFlags()

// %%
ArrayList.class.accessFlags()

// %% [markdown]
//
// ## Nachteile statischer Fabrikmethoden
//
// - Klassen ohne öffentliche Konstruktoren nicht ableitbar
// - Schwerer zu finden als Konstruktoren

// %% [markdown]
//
// ## Übliche Namen für statische Fabrikmethoden
//
// - `from`: Typumwandlung oder beschriebene Instanz
// - `of`: Aggregation mehrerer Parameter
// - `valueOf`: Alternative zu `from` und `of`
// - `instance` oder `getInstance`: Beschriebene Instanz
// - `create` oder `newInstance`: Neue Instanz
// - `get[Type]`: Wie `getInstance`, aber in anderer Klasse
// - `new[Type]`: Wie `newInstance`, aber in anderer Klasse
// - `type`: Alternative zu `get[Type]` und `new[Type]`

// %%
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.time.Instant;

// %%
Date.from(Instant.now())

// %%
List.of("a", "b", "c")

// %% [markdown]
//
// ## Workshop: Music Playlist Generator
//
// - Implementieren Sie eines Systems zur Generierung von Musik-Playlists
// - Verwenden Sie dabei statische Fabrikmethoden

// %% [markdown]
//
// ### Gegebene Klassen und Interfaces
//
// - `Song` (Interface)
// - `Mp3Song`, `WavSong` (Implementierungen von `Song`)
// - `Genre`, `Mood` (Enums)
// - `UserPreferences`
// - `Playlist`

// %%
public enum Genre { ROCK, POP, CLASSICAL }
public enum Mood { HAPPY, SAD, ENERGETIC, RELAXED }

// %%
public interface Song {
    String getTitle();
    Genre getGenre();
    Mood getMood();
}

// %%
public class Mp3Song implements Song {
    private String title;
    private Genre genre;
    private Mood mood;

    public Mp3Song(String title, Genre genre, Mood mood) {
        this.title = title;
        this.genre = genre;
        this.mood = mood;
    }

    @Override public String getTitle() { return title; }
    @Override public Genre getGenre() { return genre; }
    @Override public Mood getMood() { return mood; }

    @Override public String toString() {
        return String.format("Mp3Song(%s, %s, %s)", title, genre, mood);
    }
}

// %%
public class WavSong implements Song {
    private String title;
    private Genre genre;
    private Mood mood;

    public WavSong(String title, Genre genre, Mood mood) {
        this.title = title;
        this.genre = genre;
        this.mood = mood;
    }

    @Override public String getTitle() { return title; }
    @Override public Genre getGenre() { return genre; }
    @Override public Mood getMood() { return mood; }

    @Override public String toString() {
        return String.format("WavSong(%s, %s, %s)", title, genre, mood);
    }
}

// %%
import java.util.*;

// %%
public class UserPreferences {
    private Set<Genre> genres = new HashSet<>();
    private Set<Mood> moods = new HashSet<>();

    public void addGenre(Genre genre) { genres.add(genre); }
    public void addMood(Mood mood) { moods.add(mood); }
    public Set<Genre> getGenres() { return genres; }
    public Set<Mood> getMoods() { return moods; }
}

// %%
public class Playlist {
    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song) { songs.add(song); }
    public List<Song> getSongs() { return songs; }

    @Override public String toString() {
        return "Playlist" + songs.toString();
    }
}

// %%
public static class Songs {
    public static List<Song> availableSongs = List.of(
        new Mp3Song("Bohemian Rhapsody", Genre.ROCK, Mood.ENERGETIC),
        new WavSong("Stairway to Heaven", Genre.ROCK, Mood.RELAXED),
        new Mp3Song("Imagine", Genre.POP, Mood.RELAXED),
        new WavSong("Billie Jean", Genre.POP, Mood.ENERGETIC),
        new Mp3Song("Symphony No. 5", Genre.CLASSICAL, Mood.ENERGETIC),
        new WavSong("Für Elise", Genre.CLASSICAL, Mood.RELAXED),
        new Mp3Song("Hotel California", Genre.ROCK, Mood.RELAXED),
        new WavSong("Like a Rolling Stone", Genre.ROCK, Mood.ENERGETIC),
        new Mp3Song("Yesterday", Genre.POP, Mood.SAD),
        new WavSong("Piano Concerto No. 1", Genre.CLASSICAL, Mood.HAPPY)
    );
}

// %% [markdown]
//
// ### Aufgaben
//
// 1. Implementieren Sie die statische Fabrikmethode `Song.fromGenreAndMood`
// 2. Implementieren Sie die statische Fabrikmethode `Playlist.fromSongs`
// 3. Implementieren Sie die statische Fabrikmethode `Playlist.fromUserPreferences`
//
// (Im Folgenden finden Sie genauere Beschreibungen der Aufgaben.)

// %% [markdown]
//
// #### Aufgabe 1: `Song.fromGenreAndMood`
//
// - Implementieren Sie die Methode in der `Song`-Schnittstelle
// - Wählen Sie zufällig ein passendes Lied aus `Songs.availableSongs`
// - Wenn kein passendes Lied gefunden wird, werfen Sie eine `IllegalArgumentException`

// %% [markdown]
//
// #### Aufgabe 2: `Playlist.fromSongs`
//
// - Implementieren Sie die Methode in der `Playlist`-Klasse
// - Erstellen Sie eine neue `Playlist` und fügen Sie alle übergebenen Lieder hinzu

// %% [markdown]
//
// #### Aufgabe 3: `Playlist.fromUserPreferences`
//
// - Implementieren Sie die Methode in der `Playlist`-Klasse
// - Erstellen Sie eine `Playlist` basierend auf den Präferenzen des Benutzers
// - Wählen Sie für jede Kombination von Genre und Mood ein zufälliges Lied aus
// - Verwenden Sie die `Song.fromGenreAndMood`-Methode

// %% [markdown]
//
// ### Testen Sie Ihre Implementierung
//
// - Erstellen Sie `UserPreferences` mit verschiedenen Genres und Moods
// - Generieren Sie Playlists mit Ihren implementierten Methoden
// - Überprüfen Sie, ob die generierten Playlists den Benutzervorlieben entsprechen

// %%
import java.util.*;

// %%
public interface Song {
    String getTitle();
    Genre getGenre();
    Mood getMood();

    static Song fromGenreAndMood(Genre genre, Mood mood) {
        List<Song> matchingSongs = Songs.availableSongs.stream()
            .filter(song -> song.getGenre() == genre && song.getMood() == mood)
            .toList();

        if (matchingSongs.isEmpty()) {
            throw new IllegalArgumentException("No song found for given genre and mood");
        }

        return matchingSongs.get(new Random().nextInt(matchingSongs.size()));
    }
}

// %%
public class Playlist {
    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song) { songs.add(song); }
    public List<Song> getSongs() { return songs; }

    @Override public String toString() {
        return "Playlist" + songs.toString();
    }

    public static Playlist fromSongs(List<Song> songs) {
        Playlist playlist = new Playlist();
        songs.forEach(playlist::addSong);
        return playlist;
    }

    public static Playlist fromUserPreferences(UserPreferences preferences) {
        Playlist playlist = new Playlist();
        for (Genre genre : preferences.getGenres()) {
            for (Mood mood : preferences.getMoods()) {
                try {
                    Song song = Song.fromGenreAndMood(genre, mood);
                    playlist.addSong(song);
                } catch (IllegalArgumentException e) {
                    // No song found for this combination, continue to the next
                }
            }
        }
        return playlist;
    }
}

// %%
UserPreferences preferences = new UserPreferences();
preferences.addGenre(Genre.ROCK);
preferences.addGenre(Genre.POP);
preferences.addMood(Mood.ENERGETIC);
preferences.addMood(Mood.RELAXED);

// %%
Playlist playlist = Playlist.fromUserPreferences(preferences);
System.out.println(playlist);

// %%
Song rock = Song.fromGenreAndMood(Genre.ROCK, Mood.ENERGETIC);
System.out.println(rock);

// %%
Playlist customPlaylist = Playlist.fromSongs(Arrays.asList(
    Song.fromGenreAndMood(Genre.ROCK, Mood.ENERGETIC),
    Song.fromGenreAndMood(Genre.POP, Mood.RELAXED),
    Song.fromGenreAndMood(Genre.CLASSICAL, Mood.HAPPY)
));
System.out.println(customPlaylist);

// %%
