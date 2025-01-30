// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>GoF: Strategy Pattern</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ### Zweck
//
// - Austauschbare Algorithmen / austauschbares Verhalten
// - Algorithmen unabhängig von Klassen, die sie verwenden

// %% [markdown]
//
// ### Auch bekannt als
//
// Policy

// %% [markdown]
//
// ### Motivation
//
// - Wir wollen einen Text in einem Feld mit begrenzter Breite darstellen
// - Dafür gibt es verschiedene Möglichkeiten:
//   - Abschneiden nach einer bestimmten Anzahl von Zeichen (mit/ohne Ellipse)
//   - Umbruch nach einer bestimmten Anzahl von Zeichen
//     - Umbruch mitten im Wort
//     - Umbruch bei Leerzeichen (greedy/dynamische Programmierung)

// %% [markdown]
//
// ## Struktur
//
// <img src="img/pat_strategy.png"
//      style="display:block;margin:auto;width:80%"/>
// %% [markdown]
//
// ## Teilnehmer
//
// - `Strategy`
//   - gemeinsames Interface für alle unterstützten Algorithmen
// - `ConcreteStrategy`
//   - implementiert den Algorithmus
// - `Context`
//   - wird mit einem `ConcreteStrategy`-Objekt konfiguriert
//   - kennt sein `Strategy`-Objekt
//   - optional: Interface, das der Strategie Zugriff die Kontext-Daten ermöglicht

// %%
import java.util.*;

// %%
public interface Strategy {
    float algorithmInterface();
}

// %%
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public float contextInterface() {
        return strategy.algorithmInterface();
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}

// %%
public class ConcreteStrategyA implements Strategy {
    @Override
    public float algorithmInterface() {
        return 1.5f;
    }
}
// %%
public class ConcreteStrategyB implements Strategy {
    @Override
    public float algorithmInterface() {
        return 2.0f;
    }
}

// %%
Context context = new Context(new ConcreteStrategyA());

// %%
System.out.println("Strategy A: " + context.contextInterface());

// %%
context.setStrategy(new ConcreteStrategyB());

// %%
System.out.println("Strategy B: " + context.contextInterface());

// %% [markdown]
//
// ### Interaktionen
//
// - Strategie und Kontext interagieren, um den gewählten Algorithmus zu implementieren.
//   - Kontext kann Daten an Strategie übergeben
//   - Kontext kann sich selber an Strategie übergeben
// - Ein Kontext leitet Anfragen seiner Clients an seine Strategie weiter. [...]

// %% [markdown]
//
// ### Implementierung
//
// - `ConcreteStrategy` benötigt effizienten Zugriff auf alle benötigten Daten
// - ...

// %% [markdown]
//
// ## Beispielcode: Textumbruch für ein Blog

// %%
import java.util.ArrayList;
import java.util.List;

public interface TextWrapStrategy {
    List<String> wrap(String text, int width);
}

// %%
public class TruncationStrategy implements TextWrapStrategy {
    @Override
    public List<String> wrap(String text, int width) {
        if (text.length() <= width) {
            return List.of(text);
        }
        return List.of(text.substring(0, width - 3) + "...");
    }
}

// %%
class BreakAnywhereStrategy implements TextWrapStrategy {
    @Override
    public List<String> wrap(String text, int width) {
        String remainingText = text;
        List<String> lines = new ArrayList<>();
        while (remainingText.length() > width) {
            lines.add(remainingText.substring(0, width));
            remainingText = remainingText.substring(width);
        }
        lines.add(remainingText);
        return lines;
    }
}

// %%
class BreakOnSpaceStrategy implements TextWrapStrategy {
    @Override
    public List<String> wrap(String text, int width) {
        List<String> lines = new ArrayList<>();
        String remainingText = text;
        while (remainingText.length() > width) {
            int pos = remainingText.lastIndexOf(' ', width);
            if (pos == -1) {
                pos = width;
            }
            lines.add(remainingText.substring(0, pos));
            remainingText = remainingText.substring(pos + 1);
        }
        lines.add(remainingText);
        return lines;
    }
}

// %%
class BlogPost {
    private final String author;
    private final String title;
    private final String text;

    public BlogPost(String author, String title, String text) {
        this.author = author;
        this.title = title;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}

// %%
class Blog {
    private final List<BlogPost> posts = new ArrayList<>();
    private TextWrapStrategy strategy;

    public Blog(TextWrapStrategy strategy) {
        this.strategy = strategy;
    }

    public void print(int width) {
        for (BlogPost post : posts) {
            System.out.println("-".repeat(width));
            System.out.println("Title: " + post.getTitle());
            System.out.println("Author: " + post.getAuthor());
            for (String line : strategy.wrap(post.getText(), width)) {
                System.out.println(line);
            }
            System.out.println("-".repeat(width));
        }
    }

    public void addPost(BlogPost post) {
        posts.add(post);
    }

    public void setStrategy(TextWrapStrategy strategy) {
        this.strategy = strategy;
    }
}

// %%
String firstPost = "The quick brown fox jumps over the lazy dog. Lorem ipsum dolor sit amet, "
                    + "consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et "
                    + "dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco "
                    + "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in "
                    + "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
                    + "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt "
                    + "mollit anim id est laborum.";
String secondPost = "To be or not to be that is the question. Whether 'tis nobler in the mind to suffer "
                    + "the slings and arrows of outrageous fortune or to take arms against a sea of "
                    + "troubles and by opposing end them. To die, to sleep no more and by a sleep to say we "
                    + "end the heart-ache and the thousand natural shocks that flesh is heir to. 'Tis a "
                    + "consummation devoutly to be wish'd. To die, to sleep to sleep perchance to dream. "
                    + "Ay, there's the rub. For in that sleep of death what dreams may come when we have "
                    + "shuffled off this mortal coil must give us pause.";

// %%
Blog blog = new Blog(new TruncationStrategy());

// %%
blog.addPost(new BlogPost("John Doe", "My first post", firstPost));
blog.addPost(new BlogPost("Jane Doe", "My second post", secondPost));

// %%
blog.print(40);

// %%
blog.setStrategy(new BreakAnywhereStrategy());

// %%
blog.print(40);

// %%
blog.setStrategy(new BreakOnSpaceStrategy());

// %%
blog.print(40);

// %% [markdown]
//
// ### Anwendbarkeit
//
// - Konfiguration von Objekten mit einer von mehreren Verhaltensweisen
// - Verschiedene Varianten eines Algorithmus
// - Kapseln von Daten mit Algorithmus (Client muss Daten nicht kennen)
// - Vermeidung von bedingten Anweisungen zur Auswahl eines Algorithmus

// %% [markdown]
//
// ### Konsequenzen
//
// - Familien wiederverwendbarer, verwandter Algorithmen
// - Alternative zu Vererbung
// - Auswahl einer Strategie ohne bedingte Anweisungen
// - Context/Clients muss die möglichen Strategien kennen
// - Kommunikations-Overhead zwischen Strategie und Kontext
// - Erhöhte Anzahl von Objekten

// %% [markdown]
//
// ### Java Implementierungs-Tipp
//
// In Java kann das Strategy Pattern oft einfach durch ein Funktions-Objekt als
// Member implementiert werden:

// %%
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.*;

// %%
class FunBlog {
    private List<BlogPost> posts = new ArrayList<>();
    private BiFunction<String, Integer, List<String>> strategy;

    public FunBlog(BiFunction<String, Integer, List<String>> strategy) {
        this.strategy = strategy;
    }

    public void print(int width) {
        for (BlogPost post : posts) {
            System.out.println("-".repeat(width));
            System.out.println("Title: " + post.getTitle());
            System.out.println("Author: " + post.getAuthor());
            for (String line : strategy.apply(post.getText(), width)) {
                System.out.println(line);
            }
            System.out.println("-".repeat(width));
        }
    }

    public void addPost(BlogPost post) {
        posts.add(post);
    }

    public void setStrategy(BiFunction<String, Integer, List<String>> strategy) {
        this.strategy = strategy;
    }
}
// %%
public class Fun1 {
    public static List<String> truncateLines(String text, int width) {
        if (text.length() <= width) {
            return List.of(text);
        }
        return List.of(text.substring(0, width - 3) + "...");
    }
}


// %% [markdown]
//
// - Hier haben wir eine Funktion `TruncateLines()` definiert, die die gleiche
//   Funktionalität hat wie unsere `TruncationStrategy`


// %%
FunBlog blog = new FunBlog(Fun1::truncateLines);

// %%
blog.addPost(new BlogPost("John Doe", "My first post", firstPost));
blog.addPost(new BlogPost("Jane Doe", "My second post", secondPost));

// %%
blog.print(40);

// %%
blog.setStrategy((text, width) -> {
    if (text.length() <= width) {
        return List.of(text);
    }
    return List.of(text.substring(0, width - 3) + "...");
});

// %%
blog.print(40);

// %% [markdown]
//
// ## Mini-Workshop: Vorhersagen
//
// Sie wollen ein System schreiben, das Vorhersagen für Aktienkurse treffen kann.
//
// Schreiben Sie dazu eine Klasse `Predictor` mit einer Methode
//
// ```java
// float predict(List<Float> values)
// ```
//
// Verwenden Sie das Strategy Pattern, um mindestens zwei verschiedene
// Vorhersage-Varianten zu ermöglichen:
//
// - Die Vorhersage ist der Mittelwert aller Werte aus `values`
// - Die Vorhersage ist der letzte Wert in `values` (oder 0, wenn `values` leer ist)
//
// Testen Sie Ihre Implementierung mit einigen Beispieldaten.
