// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Refactoring und Tests</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// - Zum Refactoring brauchen wir Tests
//   - Sonst können wir nicht wissen, ob wir das Verhalten geändert haben
// - Aber: manche Tests erschweren das Refactoring

// %% [markdown]
//
// ## Tests für Refactoring
//
// - Schreiben Sie Tests, die das öffentliche Verhalten testen
// - Vermeiden Sie alle Tests, die sich auf Implementierungs-Details beziehen
//   - Auch für Unit-Tests
// - Dazu testen sich oft Methoden gegenseitig
// - Das ist OK!

// %%
import java.util.Stack;

// %%
public static void check(boolean result, String expression) {
    if (result) {
        System.out.print("Passed");
    } else {
        System.out.print("FAILED");
    }
    System.out.println(" test for '" + expression + "'");
}

// %% [markdown]
//
// **Testen Sie NICHT so!**

// %%
class StackTester<T> extends Stack<T> {
    // Access the protected data member of the stack
    public java.lang.Object[] getData() {
        return elementData;
    }

    // Access the actual number of elements in the stack
    public int getActualSize() {
        return elementCount;
    }
}

// %%
StackTester<Integer> unit = new StackTester<>();
unit.push(1);

check(unit.getActualSize() == 1, "stack size is 1");
check(unit.getData()[0].equals(1), "first element is 1");

int result = unit.pop();

check(result == 1, "pop result is 0");
check(unit.getActualSize() == 0, "size after popping is 0");

// %% [markdown]
//
// - Für `java.util.Stack` ist in der Spezifikation festgelegt, wie er implementiert ist
// - Aber in "normalem" Code könnte sich die Implementierung jederzeit ändern
// - Testen Sie statt dessen so:

// %%
import java.util.Stack;

// %%
Stack<Integer> unit = new Stack<>();
unit.push(1);

check(unit.size() == 1, "stack size is 1");
check(unit.peek() == 1, "top element is 1");

unit.pop();
check(unit.empty(), "stack is empty after popping");

// %% [markdown]
//
// - Diese Tests testen das öffentliche Verhalten
// - Das öffentliche Interface muss geeignet sein, diese Tests zu schreiben
//   - Versuchen Sie **nicht**, das durch Getter und Setter für jeden
//     Daten-Member zu erreichen
//   - Stattdessen sollten Sie Abfragen oder einen "abstrakten Zustand"
//     öffentlich machen
//   - Für den Stack sind das z.B. die `peek()` und `size()`-Methoden
// - Meistens macht das auch die normale Benutzung der Klasse einfacher
// - TDD ist ein Weg um das zu erreichen
//   - Aber: Schreiben Sie auch in TDD Tests für Feature-Inkremente, nicht für
//     Implementierungs-Inkremente

// %% [markdown]
//
// ## Workshop: Vorrangwarteschlange (Priority Queue)
//
// In diesem Workshop sollen Sie eine Vorrangwarteschlange testen, ohne sich auf
// ihre internen Implementierungsdetails zu verlassen.
//
// ### Hintergrund
//
// Eine Vorrangwarteschlange ist eine Datenstruktur, die Elemente mit
// zugehörigen Prioritäten speichert. Sie unterstützt zwei Hauptoperationen:
// - Enqueue: Füge ein Element mit einer gegebenen Priorität hinzu
// - Dequeue: Entferne und gib das Element mit der höchsten Priorität zurück
//
// Die Herausforderung besteht darin, zu überprüfen, dass die Vorrangwarteschlange
// die Reihenfolge der Elemente korrekt beibehält, ohne direkt auf ihre interne
// Struktur zuzugreifen.

// %% [markdown]
//
// Die folgende `PriorityQueue`-Klasse implementiert die übliche Schnittstelle
// für eine Vorrangwarteschlange mit einer einfachen Listen-basierten
// Repräsentation:
//
// - `void enqueue(T item, int priority)`: Füge ein Element mit der gegebenen
//   Priorität hinzu
// - `T dequeue()`: Entferne und gib das Element mit der höchsten Priorität zurück
// - `boolean isEmpty()`: Gib true zurück, wenn die Warteschlange leer ist, sonst
//   false

// %%
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// %%
public class PriorityQueue<T> {
    private List<PriorityItem<T>> items;

    public PriorityQueue() {
        this.items = new ArrayList<>();
    }

    public void enqueue(T item, int priority) {
        items.add(new PriorityItem<>(item, priority));
        items.sort((a, b) -> Integer.compare(b.priority, a.priority));
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return items.remove(0).item;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    private static class PriorityItem<T> {
        private final T item;
        private final int priority;

        PriorityItem(T item, int priority) {
            this.item = item;
            this.priority = priority;
        }
    }
}

// %% [markdown]
//
// Schreiben Sie einen Tests, die das korrekte Verhalten der
// Vorrangwarteschlange über ihre öffentliche Schnittstelle verifizieren. Ihre
// Tests sollten folgendes abdecken:
//
// 1. Grundlegende Funktionalität (`enqueue`, `dequeue`, `isEmpty`)
// 2. Korrekte Prioritäten-Reihenfolge beim Herausnehmen von Elementen mit `dequeue`
// 3. Umgang mit Elementen mit gleichen Prioritäten
// 4. Randfälle (leere Warteschlange, einzelnes Element)
//
// Denken Sie daran, dass Sie in Ihren Tests nicht auf die interne Struktur der
// Warteschlange zugreifen können!
//
// - Welche Strategien haben Sie verwendet, um die Prioritäten-Reihenfolge zu
//   testen, ohne auf die interne Struktur zuzugreifen?
// - Wie sicher sind Sie, dass Ihre Tests das Verhalten der Vorrangwarteschlange
//   vollständig verifizieren?

// %%
// %maven org.junit.jupiter:junit-jupiter-api:5.8.2
// %maven org.junit.jupiter:junit-jupiter-engine:5.8.2
// %maven org.junit.platform:junit-platform-launcher:1.9.3

// %%
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// %%
// %jars .
// %classpath testrunner-0.1.jar

// %%
import static testrunner.TestRunner.runTests;

// %%
class PriorityQueueTest {

    private PriorityQueue<String> queue;

    @BeforeEach
    void setUp() {
        queue = new PriorityQueue<>();
    }

    @Test
    void testIsEmptyOnNewQueue() {
        assertTrue(queue.isEmpty());
    }

    @Test
    void testIsNotEmptyAfterEnqueue() {
        queue.enqueue("Item", 1);
        assertFalse(queue.isEmpty());
    }

    @Test
    void testIsEmptyAfterDequeueLastItem() {
        queue.enqueue("Item", 1);
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    @Test
    void testSingleItemEnqueueDequeue() {
        queue.enqueue("Item", 1);
        assertEquals("Item", queue.dequeue());
    }

    @Test
    void testPriorityOrderWithTwoItems() {
        queue.enqueue("Low", 1);
        queue.enqueue("High", 2);
        assertEquals("High", queue.dequeue());
        assertEquals("Low", queue.dequeue());
    }

    @Test
    void testPriorityOrderWithMultipleItems() {
        queue.enqueue("Lowest", 1);
        queue.enqueue("Highest", 4);
        queue.enqueue("Medium", 2);
        queue.enqueue("High", 3);

        assertEquals("Highest", queue.dequeue());
        assertEquals("High", queue.dequeue());
        assertEquals("Medium", queue.dequeue());
        assertEquals("Lowest", queue.dequeue());
    }

    @Test
    void testEqualPriorities() {
        queue.enqueue("First", 1);
        queue.enqueue("Second", 1);

        String first = queue.dequeue();
        String second = queue.dequeue();

        assertTrue(
            (first.equals("First") && second.equals("Second")) ||
            (first.equals("Second") && second.equals("First"))
        );
    }

    @Test
    void testEnqueueDequeueMixedOperations() {
        queue.enqueue("A", 1);
        queue.enqueue("B", 3);
        assertEquals("B", queue.dequeue());
        queue.enqueue("C", 2);
        assertEquals("C", queue.dequeue());
        assertEquals("A", queue.dequeue());
    }

    @Test
    void testDequeueOnEmptyQueue() {
        assertThrows(IllegalStateException.class, () -> queue.dequeue());
    }

    @Test
    void testEnqueueDequeueEnqueueMaintainsCorrectOrder() {
        queue.enqueue("Low", 1);
        queue.enqueue("High", 2);
        assertEquals("High", queue.dequeue());
        queue.enqueue("Medium", 1);
        assertEquals("Low", queue.dequeue());
        assertEquals("Medium", queue.dequeue());
    }
}

// %%
runTests(PriorityQueueTest.class);

// %% [markdown]
//
// ### Heap-basierte Implementierung
//
// Die Listen-basierte Implementierung der Vorrangwarteschlange hat eine
// Komplexität von O(n) für das Einfügen und Entfernen von Elementen. Eine
// effizientere Implementierung verwendet einen Heap, um die Operationen in O(log
// n) durchzuführen.
//
// Hier ist eine einfache Heap-basierte Implementierung der Vorrangwarteschlange:

// %%
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriorityQueue<T> {
    private List<PriorityItem<T>> heap;
    private final Comparator<PriorityItem<T>> comparator;

    public PriorityQueue() {
        this.heap = new ArrayList<>();
        this.comparator = (a, b) -> Integer.compare(b.priority, a.priority);
    }

    public void enqueue(T item, int priority) {
        heap.add(new PriorityItem<>(item, priority));
        siftUp(heap.size() - 1);
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        T item = heap.get(0).item;
        int lastIndex = heap.size() - 1;
        heap.set(0, heap.get(lastIndex));
        heap.remove(lastIndex);
        if (!isEmpty()) {
            siftDown(0);
        }
        return item;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (comparator.compare(heap.get(index), heap.get(parentIndex)) >= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void siftDown(int index) {
        int size = heap.size();
        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int highest = index;

            if (leftChild < size && comparator.compare(heap.get(leftChild), heap.get(highest)) < 0) {
                highest = leftChild;
            }
            if (rightChild < size && comparator.compare(heap.get(rightChild), heap.get(highest)) < 0) {
                highest = rightChild;
            }

            if (highest == index) {
                break;
            }

            swap(index, highest);
            index = highest;
        }
    }

    private void swap(int i, int j) {
        PriorityItem<T> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private static class PriorityItem<T> {
        private final T item;
        private final int priority;

        PriorityItem(T item, int priority) {
            this.item = item;
            this.priority = priority;
        }
    }
}

// %% [markdown]
//
// Funktionieren Ihre Tests für die Listen-basierte Implementierung auch für die
// Heap-basierte Implementierung?
//
// *Hinweis:* Sie können im Notebook einfach die Zelle mit der neuen
// Implementierung der Vorrangwarteschlange ausführen und dann die Zellen mit
// Ihren Tests erneut ausführen.

// %% [markdown]
//
// Die Klasse `HeapPriorityQueueTest` ist eine exakte Kopie der Klasse
// `PriorityQueueTest`, aber sie ist hier nochmal angegeben, um das Testen der
// Heap-basierten Implementierung zu erleichtern.

// %%
class HeapPriorityQueueTest {

    private PriorityQueue<String> queue;

    @BeforeEach
    void setUp() {
        queue = new PriorityQueue<>();
    }

    @Test
    void testIsEmptyOnNewQueue() {
        assertTrue(queue.isEmpty());
    }

    @Test
    void testIsNotEmptyAfterEnqueue() {
        queue.enqueue("Item", 1);
        assertFalse(queue.isEmpty());
    }

    @Test
    void testIsEmptyAfterDequeueLastItem() {
        queue.enqueue("Item", 1);
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    @Test
    void testSingleItemEnqueueDequeue() {
        queue.enqueue("Item", 1);
        assertEquals("Item", queue.dequeue());
    }

    @Test
    void testPriorityOrderWithTwoItems() {
        queue.enqueue("Low", 1);
        queue.enqueue("High", 2);
        assertEquals("High", queue.dequeue());
        assertEquals("Low", queue.dequeue());
    }

    @Test
    void testPriorityOrderWithMultipleItems() {
        queue.enqueue("Lowest", 1);
        queue.enqueue("Highest", 4);
        queue.enqueue("Medium", 2);
        queue.enqueue("High", 3);

        assertEquals("Highest", queue.dequeue());
        assertEquals("High", queue.dequeue());
        assertEquals("Medium", queue.dequeue());
        assertEquals("Lowest", queue.dequeue());
    }

    @Test
    void testEqualPriorities() {
        queue.enqueue("First", 1);
        queue.enqueue("Second", 1);

        String first = queue.dequeue();
        String second = queue.dequeue();

        assertTrue(
            (first.equals("First") && second.equals("Second")) ||
            (first.equals("Second") && second.equals("First"))
        );
    }

    @Test
    void testEnqueueDequeueMixedOperations() {
        queue.enqueue("A", 1);
        queue.enqueue("B", 3);
        assertEquals("B", queue.dequeue());
        queue.enqueue("C", 2);
        assertEquals("C", queue.dequeue());
        assertEquals("A", queue.dequeue());
    }

    @Test
    void testDequeueOnEmptyQueue() {
        assertThrows(IllegalStateException.class, () -> queue.dequeue());
    }

    @Test
    void testEnqueueDequeueEnqueueMaintainsCorrectOrder() {
        queue.enqueue("Low", 1);
        queue.enqueue("High", 2);
        assertEquals("High", queue.dequeue());
        queue.enqueue("Medium", 1);
        assertEquals("Low", queue.dequeue());
        assertEquals("Medium", queue.dequeue());
    }
}

// %%
runTests(HeapPriorityQueueTest.class);
