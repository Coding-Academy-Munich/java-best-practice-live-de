package recipes_sk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManyTest {
    private Many many;

    @BeforeEach
    void setUp() {
        many = new Many();
        many.addThing(new One("recipe 1",
                Arrays.asList("ingredient 1", "ingredient 2"),
                "instructions...",
                5));
        many.addThing(new One("recipe 2",
                Arrays.asList("ingredient 1", "ingredient 3"),
                "do this...",
                4));
        many.addThing(new One("recipe 3",
                Arrays.asList("ingredient 2", "ingredient 3"),
                "do that...",
                3));
    }

    @Test
    void getThing() {
        One one = many.getThing("recipe 2");
        assertEquals("recipe 2", one.getAaa());
    }

    @Test
    void getThings1Test() {
        List<One> things = many.getThings1("ingredient 2");
        assertEquals(2, things.size());
        assertEquals("recipe 1", things.get(0).getAaa());
        assertEquals("recipe 3", things.get(1).getAaa());
    }

    @Test
    void getThings2Test() {
        List<One> things = many.getThings2(4);
        assertEquals(1, things.size());
        assertEquals("recipe 2", things.get(0).getAaa());
    }

    @Test
    void getThings3Test() {
        List<One> things = many.getThings3(4);
        assertEquals(2, things.size());
        assertEquals("recipe 1", things.get(0).getAaa());
        assertEquals("recipe 2", things.get(1).getAaa());
    }

    @Test
    void getThingsThrowsStuff() {
        assertThrows(RuntimeException.class, () -> many.getThing("nonexistent recipe"));
    }
}