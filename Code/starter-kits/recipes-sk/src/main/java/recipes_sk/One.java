package recipes_sk;

import java.util.ArrayList;
import java.util.List;

public class One {
    private final String aaa;
    private final List<String> bbb;
    private final String ccc;
    private final int ddd;

    public One(String aaa, List<String> ingredients, String ccc, int ddd) {
        this.aaa = aaa;
        this.bbb = new ArrayList<>(ingredients);
        this.ccc = ccc;
        this.ddd = ddd;
    }

    public One(String aaa, List<String> ingredients, String ccc) {
        this(aaa, ingredients, ccc, -1);
    }

    // Get the name of the recipe
    public String getAaa() {
        return aaa;
    }

    // Return the list of ingredients
    public List<String> getBbb() {
        return new ArrayList<>(bbb);
    }

    // Return the instructions
    public String getCcc() {
        return ccc;
    }

    // Get the rating
    public int getDdd() {
        return ddd;
    }

    // Return true if the recipe has a valid rating
    public boolean dddMinusOne() {
        return ddd != -1;
    }
}

