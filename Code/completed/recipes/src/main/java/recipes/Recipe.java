package recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class Recipe {
    private final String name;
    private final List<String> ingredients;
    private final String instructions;
    private final int rating;

    public Recipe(String name, List<String> ingredients, String instructions, int rating) {
        this.name = name;
        this.ingredients = new ArrayList<>(ingredients);
        this.instructions = instructions;
        this.rating = rating;
    }

    public Recipe(String name, List<String> ingredients, String instructions) {
        this(name, ingredients, instructions, -1);
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public String getInstructions() {
        return instructions;
    }

    public int getRating() {
        return rating;
    }

    public boolean hasRating() {
        return rating != -1;
    }
}

