package recipes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
    void recipeHasNoRatingByDefault() {
        Recipe recipe = new Recipe("Test Recipe", List.of(), "");
        assertEquals(-1, recipe.getRating());
    }

    @Nested
    class RecipeBookTest {
        private RecipeBook book;

        @BeforeEach
        void setUp() {
            book = new RecipeBook();
            book.addRecipe(new Recipe("recipe 1",
                    Arrays.asList("ingredient 1", "ingredient 2"),
                    "instructions...",
                    5));
            book.addRecipe(new Recipe("recipe 2",
                    Arrays.asList("ingredient 1", "ingredient 3"),
                    "do this...",
                    4));
            book.addRecipe(new Recipe("recipe 3",
                    Arrays.asList("ingredient 2", "ingredient 3"),
                    "do that...",
                    3));
        }

        @Test
        void getRecipeByName() {
            Recipe recipe2 = book.getRecipeByName("recipe 2");
            assertEquals("recipe 2", recipe2.getName());
        }

        @Test
        void getRecipesByIngredient() {
            List<Recipe> recipes = book.getRecipesWithIngredient("ingredient 2");
            assertEquals(2, recipes.size());
            assertEquals("recipe 1", recipes.get(0).getName());
            assertEquals("recipe 3", recipes.get(1).getName());
        }

        @Test
        void getRecipesByRating() {
            List<Recipe> recipes = book.getRecipesWithRating(4);
            assertEquals(1, recipes.size());
            assertEquals("recipe 2", recipes.get(0).getName());
        }

        @Test
        void getRecipesByRatingOrBetter() {
            List<Recipe> recipes = book.getRecipesByRatingBetterThan(4);
            assertEquals(2, recipes.size());
            assertEquals("recipe 1", recipes.get(0).getName());
            assertEquals("recipe 2", recipes.get(1).getName());
        }

        @Test
        void getRecipeByNameThrowsExceptionForNonexistentRecipe() {
            assertThrows(RuntimeException.class,
                    () -> book.getRecipeByName("nonexistent recipe"));
        }
    }
}
