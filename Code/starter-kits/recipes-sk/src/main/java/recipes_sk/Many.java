package recipes_sk;

import java.util.ArrayList;
import java.util.List;

class Many {
    private final List<One> foo = new ArrayList<>();

    // Add a recipe
    public void addThing(One thing) {
        foo.add(thing);
    }

    // Return a recipe by name
    public One getThing(String aaa) {
        for (One bar : foo) {
            if (bar.getAaa().equals(aaa)) {
                return bar;
            }
        }
        throw new RuntimeException("Recipe " + aaa + " not found!");
    }

    // Return all recipes with a given ingredient
    public List<One> getThings1(String bbb) {
        List<One> result = new ArrayList<>();
        for (One thing : foo) {
            if (thing.getBbb().contains(bbb)) {
                result.add(thing);
            }
        }
        return result;
    }

    // Return all recipes with a rating equal to the argument
    public List<One> getThings2(int ddd) {
        List<One> result = new ArrayList<>();
        for (One thing : foo) {
            if (thing.dddMinusOne() && thing.getDdd() == ddd) {
                result.add(thing);
            }
        }
        return result;
    }

    // Return all recipes with a rating greater or equal than the argument
    public List<One> getThings3(int ddd) {
        List<One> result = new ArrayList<>();
        for (One thing : foo) {
            if (thing.dddMinusOne() && thing.getDdd() >= ddd) {
                result.add(thing);
            }
        }
        return result;
    }
}
