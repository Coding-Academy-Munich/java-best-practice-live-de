// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>JUnit Fixtures</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ## Probleme mit einfachen Tests
//
// - Kompliziertes Setup (und Teardown)
//   - Wiederholung von Code
//   - Schwer zu warten und verstehen
// - Viele ähnliche Tests

// %%
class Dependency1 {
    public Dependency1(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int value() {
        return i + 2 * j;
    }

    private int i;
    private int j;
}

// %%
class Dependency2 {
    public Dependency2(Dependency1 d1, int k) {
        this.d1 = d1;
        this.k = k + d1.value();
    }

    public int value() {
        return d1.value() + 3 * k;
    }

    private Dependency1 d1;
    public int k;
}

// %%
public class MyClass {
    public MyClass(Dependency2 d2, int m) {
        this.d2 = d2;
        this.m = m;
    }

    public int value() {
        if (m < - 10) {
            return d2.value() + 2 * m;
        } else if (m < 0) {
            return d2.value() - 3 * m;
        } else if (m < 10) {
            return d2.value() + 4 * m;
        } else {
            return d2.value() - 5 * m;
        }
    }

    public void releaseResources() {
        // Assume it is important to call this method to release resources
        d2 = null;
    }

    private Dependency2 d2;
    private int m;
}

// %%
// %maven org.junit.jupiter:junit-jupiter-api:5.8.2
// %maven org.junit.jupiter:junit-jupiter-engine:5.8.2
// %maven org.junit.platform:junit-platform-launcher:1.9.3

// %%
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

// %%
class MyClassTest1 {
    @Test
    public void testValue1() {
        Dependency1 d1 = new Dependency1(1, 2);
        Dependency2 d2 = new Dependency2(d1, 3);
        MyClass unit = new MyClass(d2, -11);

        assertEquals(7, unit.value());

        unit.releaseResources();
    }

    @Test
    public void testValue2() {
        Dependency1 d1 = new Dependency1(1, 2);
        Dependency2 d2 = new Dependency2(d1, 4);
        MyClass unit = new MyClass(d2, -1);

        assertEquals(35, unit.value());

        unit.releaseResources();
    }

    @Test
    public void testValue3() {
        Dependency1 d1 = new Dependency1(1, 2);
        Dependency2 d2 = new Dependency2(d1, 3);
        MyClass unit = new MyClass(d2, 1);

        assertEquals(33, unit.value());

        unit.releaseResources();
    }

    @Test
    public void testValue4() {
        Dependency1 d1 = new Dependency1(1, 2);
        Dependency2 d2 = new Dependency2(d1, 3);
        MyClass unit = new MyClass(d2, 11);

        assertEquals(-26, unit.value());

        unit.releaseResources();
    }
}

// %%
// %jars .
// %classpath testrunner-0.1.jar

// %%
import static testrunner.TestRunner.runTests;

// %%
runTests(MyClassTest1.class)

// %%
class MyClassTest2 {
    private void setupDependencies() {
        Dependency1 d1 = new Dependency1(1, 2);
        d2 = new Dependency2(d1, 3);
    }

    private Dependency2 d2;

    @Test
    public void testValue1() {
        setupDependencies();
        MyClass unit = new MyClass(d2, -11);

        assertEquals(7, unit.value());

        unit.releaseResources();
    }

    @Test
    public void testValue2() {
        setupDependencies();
        MyClass unit = new MyClass(d2, -1);

        assertEquals(32, unit.value());

        unit.releaseResources();
    }

    @Test
    public void testValue3() {
        setupDependencies();
        MyClass unit = new MyClass(d2, 1);

        assertEquals(33, unit.value());

        unit.releaseResources();
    }

    @Test
    public void testValue4() {
        setupDependencies();
        MyClass unit = new MyClass(d2, 11);

        assertEquals(-26, unit.value());

        unit.releaseResources();
    }
}

// %%
runTests(MyClassTest2.class)


// %%
class MyClassTest3 {
    MyClassTest3() {
        Dependency1 d1 = new Dependency1(1, 2);
        d2 = new Dependency2(d1, 3);
    }

    @Test
    public void testValue1() {
        MyClass unit = new MyClass(d2, -11);
        // d2.k = 4;
        assertEquals(7, unit.value());
        unit.releaseResources();
    }

    @Test
    public void testValue2() {
        MyClass myClass = new MyClass(d2, -1);
        assertEquals(32, myClass.value());
        myClass.releaseResources();
    }

    private Dependency2 d2;
}

// %%
runTests(MyClassTest3.class)

// %% [markdown]
//
// ## Testklassen und Fixtures
//
// - Testklassen können
//   - mehrere Tests zusammenfassen
//   - gemeinsamen Zustand für die Tests bereitstellen
// - `@BeforeEach` für Setup (Initialisierung)
// - `@AfterEach` für Teardown (Freigabe)
// - `@BeforeAll` und `@AfterAll` für Setup und Teardown auf Klassenebene

// %%
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

// %%
public class JUnitFixturesTest {
    private List<String> testList;

    @BeforeAll
    public static void setUpClass() {
        System.out.println("Setting up the test class");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("Tearing down the test class");
    }

    @BeforeEach
    public void setUp() {
        testList = new ArrayList<>();
        testList.add("test");
        System.out.println("Setting up a test");
    }

    @AfterEach
    public void tearDown() {
        testList = null;
        System.out.println("Tearing down a test");
    }

    @Test
    public void testListContents() {
        assertEquals(1, testList.size());
        assertTrue(testList.contains("test"));
    }

    @Test
    public void testListOperations() {
        testList.add("another");
        assertEquals(2, testList.size());
    }
}

// %%
runTests(JUnitFixturesTest.class)

// %%
class MyClassTest4 {
    @BeforeEach
    public void setupDependencies() {
        Dependency1 d1 = new Dependency1(1, 2);
        d2 = new Dependency2(d1, 3);
    }

    @AfterEach
    public void releaseResources() {
        d2 = null;
    }

    @Test
    public void testValue1() {
        MyClass unit = new MyClass(d2, -11);
        assertEquals(7, unit.value());
    }

    @Test
    public void testValue2() {
        MyClass unit = new MyClass(d2, -1);
        assertEquals(32, unit.value());
    }

    @Test
    public void testValue3() {
        MyClass unit = new MyClass(d2, 1);
        assertEquals(33, unit.value());
    }

    @Test
    public void testValue4() {
        MyClass unit = new MyClass(d2, 11);
        assertEquals(-26, unit.value());
    }

    private Dependency2 d2;
}

// %%
runTests(MyClassTest4.class)


// %% [markdown]
//
// ## Workshop: JUnit Fixtures für einen Musik-Streaming-Dienst
//
// In diesem Workshop werden wir Tests für ein einfaches Musik-Streaming-System
// mit JUnit implementieren.
//
// In diesem System haben wir die Klassen `User`, `Song`, `PlaylistEntry` und `Playlist`.
// - Die Klasse `User` repräsentiert einen Benutzer des Streaming-Dienstes.
// - Ein `Song` repräsentiert ein Musikstück, das im Streaming-Dienst verfügbar ist.
// - Ein `PlaylistEntry` repräsentiert einen Eintrag in einer Playlist, also ein
//   Musikstück und die Anzahl der Wiedergaben.
// - Eine `Playlist` repräsentiert eine Sammlung von Musikstücken, also eine Liste
//   von `PlaylistEntry`-Objekten.

// %%
public class User {
    private String username;
    private String email;
    private boolean isPremium;

    public User(String username, String email, boolean isPremium) {
        this.username = username;
        this.email = email;
        this.isPremium = isPremium;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isPremium() {
        return isPremium;
    }
}

// %%
public class Song {
    private String title;
    private String artist;
    private int durationInSeconds;

    public Song(String title, String artist, int durationInSeconds) {
        this.title = title;
        this.artist = artist;
        this.durationInSeconds = durationInSeconds;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }
}

// %%
public class PlaylistEntry {
    private Song song;
    private int playCount;

    public PlaylistEntry(Song song) {
        this.song = song;
        this.playCount = 0;
    }

    public void incrementPlayCount() {
        playCount++;
    }

    public Song getSong() {
        return song;
    }

    public int getPlayCount() {
        return playCount;
    }
}

// %%
public class Playlist {
    private List<PlaylistEntry> entries = new ArrayList<>();
    private User owner;
    private String name;

    public Playlist(User owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public void addSong(Song song) {
        entries.add(new PlaylistEntry(song));
    }

    public int getTotalDuration() {
        return entries.stream()
                    .mapToInt(entry -> entry.getSong().getDurationInSeconds())
                    .sum();
    }

    public int getTotalPlayCount() {
        return entries.stream()
                    .mapToInt(PlaylistEntry::getPlayCount)
                    .sum();
    }

    public boolean canAddMoreSongs() {
        if (owner.isPremium()) {
            return true;
        }
        return entries.size() < 100;  // Non-premium users limited to 100 songs
    }

    public List<PlaylistEntry> getEntries() {
        return new ArrayList<>(entries);
    }

    public String getName() {
        return name;
    }
}

// %% [markdown]
//
// Implementieren Sie Tests für dieses System mit JUnit. Verwenden Sie dabei Fixtures,
// um die Tests zu strukturieren und Code-Duplikation zu vermeiden.
//
// Beachten Sie bei der Implementierung der Tests die folgenden Aspekte:
// - Grundlegende Funktionen wie das Hinzufügen von Songs zu einer Playlist
// - Berechnung der Gesamtdauer einer Playlist
// - Unterschiedliche Benutzerrechte (Premium vs. Nicht-Premium)
// - Begrenzung der Playlist-Größe für Nicht-Premium-Benutzer
// - Zählung der Wiedergaben von Songs
//
// Bewerten Sie Ihre Tests anhand der Kriterien für gute Unit-Tests, die wir
// in früheren Lektionen besprochen haben.

// %%
class PlaylistEntryTest {
    private Song song;
    private PlaylistEntry entry;

    @BeforeEach
    public void setup() {
        song = new Song("Test Song", "Test Artist", 180);
        entry = new PlaylistEntry(song);
    }

    @Test
    public void testInitialPlayCount() {
        assertEquals(0, entry.getPlayCount());
    }

    @Test
    public void testIncrementPlayCount() {
        entry.incrementPlayCount();
        entry.incrementPlayCount();
        assertEquals(2, entry.getPlayCount());
    }
}

// %%
class PlaylistBasicsTest {
    private User user;
    private Song song1;
    private Song song2;
    private Playlist playlist;

    @BeforeEach
    public void setup() {
        user = new User("Alice", "alice@example.com", false);
        song1 = new Song("Song 1", "Artist 1", 180);
        song2 = new Song("Song 2", "Artist 2", 240);
        playlist = new Playlist(user, "My Playlist");
    }

    @Test
    public void testPlaylistInitiallyEmpty() {
        assertEquals(0, playlist.getEntries().size());
    }

    @Test
    public void testAddSong() {
        playlist.addSong(song1);
        playlist.addSong(song2);
        assertEquals(2, playlist.getEntries().size());
    }

    @Test
    public void testTotalDurationForEmptyPlaylist() {
        assertEquals(0, playlist.getTotalDuration());
    }

    @Test
    public void testTotalDurationForNonEmptyPlaylist() {
        playlist.addSong(song1);
        playlist.addSong(song2);
        assertEquals(420, playlist.getTotalDuration());
    }
}

// %%
class PlaylistLimitsTest {
    private User premiumUser;
    private User nonPremiumUser;
    private Song song;
    private Playlist premiumPlaylist;
    private Playlist nonPremiumPlaylist;

    @BeforeEach
    public void setup() {
        premiumUser = new User("premium", "premium@example.com", true);
        nonPremiumUser = new User("regular", "regular@example.com", false);
        song = new Song("Test Song", "Test Artist", 180);
        premiumPlaylist = new Playlist(premiumUser, "Premium Playlist");
        nonPremiumPlaylist = new Playlist(nonPremiumUser, "Regular Playlist");
    }

    @Test
    public void testCanAddMoreSongsForPremiumUser() {
        for (int i = 0; i < 150; i++) {
            premiumPlaylist.addSong(song);
        }
        assertTrue(premiumPlaylist.canAddMoreSongs());
    }

    @Test
    public void testCanAddMoreSongsForNonPremiumUser() {
        for (int i = 0; i < 99; i++) {
            nonPremiumPlaylist.addSong(song);
        }
        assertTrue(nonPremiumPlaylist.canAddMoreSongs());
        nonPremiumPlaylist.addSong(song);
        assertFalse(nonPremiumPlaylist.canAddMoreSongs());
    }

    @Test
    public void testTotalPlayCount() {
        premiumPlaylist.addSong(song);
        premiumPlaylist.getEntries().get(0).incrementPlayCount();
        premiumPlaylist.getEntries().get(0).incrementPlayCount();
        assertEquals(2, premiumPlaylist.getTotalPlayCount());
    }
}

// %%
runTests(PlaylistBasicsTest.class)
