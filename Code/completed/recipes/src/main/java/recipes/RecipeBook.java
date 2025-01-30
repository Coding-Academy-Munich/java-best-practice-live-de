package recipes;

import java.util.ArrayList;
import java.util.List;

class RecipeBook {
    private final List<recipes.Recipe> recipes = new ArrayList<>();

    public void addRecipe(recipes.Recipe recipe) {
        recipes.add(recipe);
    }

    public recipes.Recipe getRecipeByName(String name) {
        for (recipes.Recipe recipe : recipes) {
            if (recipe.getName().equals(name)) {
                return recipe;
            }
        }
        throw new RuntimeException("Recipe " + name + " not found!");
    }

    public List<recipes.Recipe> getRecipesWithIngredient(String ingredient) {
        List<recipes.Recipe> result = new ArrayList<>();
        for (recipes.Recipe recipe : recipes) {
            if (recipe.getIngredients().contains(ingredient)) {
                result.add(recipe);
            }
        }
        return result;
    }

    public List<recipes.Recipe> getRecipesWithRating(int rating) {
        List<recipes.Recipe> result = new ArrayList<>();
        for (recipes.Recipe recipe : recipes) {
            if (recipe.hasRating() && recipe.getRating() == rating) {
                result.add(recipe);
            }
        }
        return result;
    }

    public List<recipes.Recipe> getRecipesByRatingBetterThan(int rating) {
        List<recipes.Recipe> result = new ArrayList<>();
        for (recipes.Recipe recipe : recipes) {
            if (recipe.hasRating() && recipe.getRating() >= rating) {
                result.add(recipe);
            }
        }
        return result;
    }
}
