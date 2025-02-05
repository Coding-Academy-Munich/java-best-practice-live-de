// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Bibliothekssystem: Einführung</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// # Bibliotheks-Verwaltungssystem
//
// - System zur Verwaltung von Benutzern und Medienbestand in Bibliotheken
// - Sowohl für Bibliothekare als auch Benutzer
// - Aktivitäten: Registrierung, Suche, Ausleihe, Rückgabe, Strafzahlungen
// - Suche von Medien in anderen Bibliotheken und Online-Repositories
// - Empfehlungen für Benutzer
// - Verschiedene Oberflächen (Web, App, Terminal)

// %% [markdown]
//
// - Wie könnte das Domänenmodell für ein solches System aussehen?
// - Welche Konzepte gibt es?
// - Welche Use-Cases/Anwendungsfälle gibt es?

// %% [markdown]
//
// ## Domänenmodell: Konzepte
//
// - Medien
//   - Bücher, Videos, Musik, ...
//   - Unterschiedliche Metadaten für verschiedene Medien
// - Benutzer
//   - Mitglieder, Besucher
//   - verschiedene Typen von Mitgliedern: Kinder, Studenten, Senioren, ...
//   - Unterschiedliche Privilegien
//   - Aktivitäten: Ausleihen von Medien, Rückgabe, Suche, Strafzahlungen
// - Bibliothekare
//   - Verwalten von Benutzern und Medien
//   - Aktivitäten: Registrierung, Suche, Ausleihe, Rückgabe, Strafzahlungen

// %% [markdown]
//
// ## Aktivitäten (Bibliothekar)
//
// - Verwalten von Mitgliedern (Registrierung, Löschen, Suche, Modifikation,
//   ...)
// - Verwaltung von Medien (Hinzufügen, Löschen, Suche, Modifikation ...)
// - Ausleihen und Rückgabe von Medien (Bibliotheks-Seite)
// - Veranlassen von Erinnerungen, Strafzahlungen
// - Anzeige bisheriger Aktivitäten (Hinzufügen, Ausleihen, ...) für alle
//   Benutzer

// %% [markdown]
//
// ## Aktivitäten (Benutzer)
//
// - Registrierung, Abmeldung, Mitteilung von Adressänderungen
// - Suche nach Medien
// - Ausleihen und Rückgabe von Medien (Benutzer-Seite)
// - Anzeige der bisherigen Aktivitäten (Ausleihen, Rückgaben, Strafzahlungen)
//   für den Benutzer

// %% [markdown]
//
// ## Workshop: Bibliothekssystem (Setup)
//
// Starter Kit: `code/starter-kits/library-sk`
//
// - Versuchen Sie das Starter Kit zu kompilieren und auszuführen.
// - Fügen Sie eine Klasse `Book` hinzu, die ein Attribut `title` hat.
// - Fügen Sie einen Getter für das Attribut `title` hinzu.
// - Schreiben Sie einen Test, der überprüft, dass der Getter funktioniert.
// - Erstellen Sie ein Buch im Hauptprogramm und geben Sie den Titel aus.
// - Entfernen Sie die Dummy-Klasse `DeleteMe` und die Tests dieser Klasse.
//   Stellen Sie sicher, dass Sie das Projekt immer noch bauen und das
//   Hauptprogramm und die Tests ausführen können.
//
// **Hinweis**: Das ist natürlich kein sinnvoller Test, er dient nur dazu, dass
// Sie mit der Infrastruktur, die wir in diesem Kurs verwenden, vertraut werden.

// %% [markdown]
//
// ## Workshop: Bibliotheks-Verwaltungssystem (Teil 1)
//
// - Entwickeln Sie ein erstes Domänenmodell für das Bibliotheks-Verwaltungssystem
//   - Sie können z.B. ein Klassendiagramm verwenden oder einfach nur eine Liste
//     von Klassen und Attributen
// - Welche Klassen in Ihrem Domänenmodell haben Assoziationen zu
//   - Mitgliedern?
//   - Büchern?

// %% [markdown]
//
// - Verwenden Sie das Creator Pattern um zu entscheiden, welche Klasse die
//   Verantwortung für das Erstellen von Mitgliedern und welche die Verantwortung
//   für das Erstellen von Büchern hat
// - Verwenden Sie das Information Expert Pattern um zu entscheiden, welche Klasse
//   die Verantwortung für das Suchen von Mitgliedern und welche die Verantwortung
//   für das Suchen von Büchern hat
// - Implementieren Sie diesen Teil des Domänenmodells in Java
// - Versuchen Sie dabei das Prinzip der niedrigen Repräsentationslücke anzuwenden

// %%
import java.util.ArrayList;
import java.util.List;

// %%
class Member {
    private String name;
    private String address;
    private String email;

    public Member(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Member(" + name + ", " + address + ", " + email  + ")";
    }
}

// %%
class Book {
    private String title;
    private String isbn;

    public Book(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        return "Book(" + title + ", " + isbn + ")";
    }
}

// %%
class LibrarySystem {
    private List<Member> members;
    private List<Book> books;

    public LibrarySystem() {
        members = new ArrayList<>();
        books = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Members:\n");
        for (Member member : members) {
            result.append("  ").append(member.getName()).append("\n");
        }
        result.append("Books:\n");
        for (Book book : books) {
            result.append("  ").append(book.getTitle()).append("\n");
        }
        return result.toString();
    }

    public void addMember(String name, String address, String email) {
        Member member = new Member(name, address, email);
        members.add(member);
    }

    public void addBook(String title, String isbn) {
        Book book = new Book(title, isbn);
        books.add(book);
    }

    public Member findMember(String name) {
        for (Member member : members) {
            if (member.getName().equals(name)) {
                return member;
            }
        }
        return null;
    }

    public Book findBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }
}

// %%
LibrarySystem library = new LibrarySystem();

// %%
library.addMember("Max Mustermann", "Musterstraße 1", "max@example.com");

// %%
library.addBook("Design Patterns", "978-0-20163-361-0");

// %%
library.findMember("Max Mustermann");

// %%
library.findBook("Design Patterns");

// %%
library;
