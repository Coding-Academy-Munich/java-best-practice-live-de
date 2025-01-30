// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Clean Code: Namen (Teil 3)</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ## Desinformation und sinnvolle Unterscheidungen
//
// - Namen bedeuten etwas
// - Desinformation:
//   - Die Bedeutung des Namens impliziert etwas anderes als der Programmcode:

// %%
boolean verifyConfiguration = false;

// %%
if (verifyConfiguration) {
    System.out.println("Deleting configuration files...");
}

// %%
import java.util.Objects;

// %%
import java.util.Objects;

// %%
class Triple {
    public int first;
    public int second;
    public int third;

    public Triple(int first, int second, int third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
}

// %%
new Triple(1, 2, 3)

// %% [markdown]
//
// ## Regeln zur Vermeidung von Disinformation
//
// - Nimm keinen Typ in einen Variablennamen auf, wenn die Variable nicht von
//   diesem Typ ist
//   - Meistens: Gib überhaupt keinen Typ in einem Variablennamen an

// %%
int vectorOfCards = 0;

// %%
int numCards = 0;

// %%
import java.util.ArrayList;

// %%
ArrayList<Integer> cardArray = new ArrayList<>();

// %%
ArrayList<Integer> cardDeck = new ArrayList<>();

// %% [markdown]
//
// ## Regeln zur Vermeidung von Disinformation
//
// - Sei vorsichtig mit Namen, die sich nur geringfügig unterscheiden

// %%
boolean isMeleeDefenceAvailable = true;
boolean isMeleeDefenseAvailable = false;

// %%
// Ooops...
isMeleeDefenceAvailable == isMeleeDefenseAvailable

// %% [markdown]
//
// ## Sinnvolle Unterscheidungen
//
// - Sei bei der Namensgebung konsistent

// %%
int numberOfObjects = 10;
int numBuyers = 12;
int nTransactions = 2;

// %%
int numObjects = 10;
int numBuyers = 12;
int numTransactions = 2;

// %%
int nObjects = 10;
int nBuyers = 12;
int nTransactions = 2;

// %% [markdown]
//
// ## Sinnvolle Unterscheidungen
//
// - Verwende Namen, die die Bedeutung der Konzepte so klar wie möglich ausdrücken

// %%
String animal1 = "Fluffy";
String animal2 = "Garfield";

// %%
String janesDog = "Fluffy";
String jonsCat = "Garfield";

// %%
final int INCLUDE_NONE = 0;
final int INCLUDE_FIRST = 1;
final int INCLUDE_SECOND = 2;
final int INCLUDE_BOTH = 3;

// %%
final int INCLUDE_NO_DATE = 0;
final int INCLUDE_START_DATE = 1;
final int INCLUDE_END_DATE = 2;
final int INCLUDE_START_AND_END_DATE = 3;

// %%
enum DatesToInclude {
    NONE,
    START,
    END,
    START_AND_END;
}

// %%
DatesToInclude.START

// %%
enum DatesToInclude {
    START,
    END,
}

// %%
EnumSet<DatesToInclude> datesToInclude = EnumSet.of(DatesToInclude.START);

// %%
datesToInclude

// %% [markdown]
//
// ## Sinnvolle Unterscheidungen
//
// - Verwende denselben Namen für dasselbe Konzept

// %%
class OrderLine {
    public int quantity;
    public int pricePerItem;

    public OrderLine(int quantity, int pricePerItem) {
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
    }

    public int getTotalCost() {
        return quantity * pricePerItem;
    }
}

// %% [markdown]
//
// ## Workshop: Entfernen von Namensverletzungen
//
// - Analysieren Sie die folgenden Code-Snippets und korrigieren Sie die Namensverletzungen
// - Beachten Sie dabei die Regeln, die wir besprochen haben

// %% [markdown]
//
// ### Beispiel 1

// %%
boolean isUserActive = false;

// %%
if (isUserActive) {
    System.out.println("Deleting user account...");
}

// %%
boolean shouldDeleteUser = false;

if (shouldDeleteUser) {
    System.out.println("Deleting user account...");
}

// %% [markdown]
//
// ### Beispiel 2

// %%
ArrayList<Integer> itemList = new ArrayList<>();

// %%
ArrayList<Integer> items = new ArrayList<>();

// %% [markdown]
//
// ### Beispiel 3

// %%
// Syntax of data is correct
boolean isDataValidated = true;
// Contents of the data has been verified
boolean isDataVerified = false;

// %%
boolean isSyntaxCorrect = true;
boolean isDataVerified = false;

// %% [markdown]
//
// ### Beispiel 4

// %%
int itemCount = 5;
int numberOfStudents = 10;
int numTeachers = 3;

// %%
int numItems = 5;
int numStudents = 10;
int numTeachers = 3;

// %% [markdown]
//
// ## Workshop: Namen in existierendem Code
//
// - Analysieren Sie ein Programm, an dem Sie arbeiten ob die Namen gut sind
// - Verbessern Sie die Namen, falls das möglich ist
//   - Achten Sie aber darauf, dass Sie nicht gegen die Coding-Standards
//     des Projekts verstoßen
// - Verbessert sich die Lesbarkeit des Codes?
// - Diskutieren Sie Ihre Ergebnisse mit Ihren Kollegen
