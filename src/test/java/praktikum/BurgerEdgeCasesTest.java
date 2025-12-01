package praktikum;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class BurgerEdgeCasesTest {

    @Test
    public void testBurgerInitialState() {
        Burger burger = new Burger();
        assertNull(burger.bun);
        assertNotNull(burger.ingredients);
        assertTrue(burger.ingredients.isEmpty());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMoveIngredientInvalidIndex() {
        Burger burger = new Burger();
        burger.setBuns(Mockito.mock(Bun.class));
        burger.moveIngredient(0, 5);
    }

    @Test
    public void testGetPriceWithZeroCost() {
        Bun bun = new Bun("free bun", 0f);
        Ingredient ingredient = new Ingredient(IngredientType.SAUCE, "free sauce", 0f);

        Burger burger = new Burger();
        burger.setBuns(bun);
        burger.addIngredient(ingredient);

        assertEquals(0f, burger.getPrice(), 0.01f);
    }

    @Test
    public void testIngredientTypeEnum() {
        // Покрываем enum
        assertEquals("SAUCE", IngredientType.SAUCE.name());
        assertEquals("FILLING", IngredientType.FILLING.name());
        assertEquals(2, IngredientType.values().length);
    }
}