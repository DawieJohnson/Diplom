package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BurgerBasicTest {

    private Burger burger;

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient sauceIngredient;

    @Mock
    private Ingredient fillingIngredient;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();

        when(mockBun.getName()).thenReturn("test bun");
        when(mockBun.getPrice()).thenReturn(100f);

        when(sauceIngredient.getPrice()).thenReturn(50f);
        when(sauceIngredient.getName()).thenReturn("hot sauce");
        when(sauceIngredient.getType()).thenReturn(IngredientType.SAUCE);

        when(fillingIngredient.getPrice()).thenReturn(75f);
        when(fillingIngredient.getName()).thenReturn("cutlet");
        when(fillingIngredient.getType()).thenReturn(IngredientType.FILLING);
    }

    @Test
    public void testSetBuns() {
        burger.setBuns(mockBun);
        assertSame(mockBun, burger.bun);
    }

    @Test
    public void testAddIngredientIncreasesSize() {
        int initialSize = burger.ingredients.size();
        Ingredient newIngredient = mock(Ingredient.class);

        burger.addIngredient(newIngredient);

        assertEquals(initialSize + 1, burger.ingredients.size());
    }

    @Test
    public void testAddIngredientContainsAdded() {
        Ingredient newIngredient = mock(Ingredient.class);
        burger.addIngredient(newIngredient);

        assertTrue(burger.ingredients.contains(newIngredient));
    }

    @Test
    public void testRemoveIngredient() {
        burger.setBuns(mockBun);
        Ingredient ingredientToRemove = mock(Ingredient.class);
        burger.addIngredient(ingredientToRemove);
        int indexToRemove = burger.ingredients.size() - 1;

        burger.removeIngredient(indexToRemove);

        assertFalse(burger.ingredients.contains(ingredientToRemove));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIngredientWithInvalidIndex() {
        burger.removeIngredient(999);
    }

    @Test
    public void testMoveIngredientChangesOrder() {
        burger.setBuns(mockBun);

        burger.addIngredient(sauceIngredient);
        burger.addIngredient(fillingIngredient);

        burger.moveIngredient(0, 1);

        assertEquals(fillingIngredient, burger.ingredients.get(0));
        assertEquals(sauceIngredient, burger.ingredients.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMoveIngredientInvalidIndex() {
        burger.setBuns(mockBun);
        burger.addIngredient(mock(Ingredient.class));
        burger.moveIngredient(5, 0);
    }

    @Test
    public void testGetReceiptWithNoIngredientsNotNull() {
        burger.setBuns(mockBun);
        String receipt = burger.getReceipt();
        assertNotNull(receipt);
    }

    @Test
    public void testGetReceiptWithNoIngredientsContainsBunName() {
        when(mockBun.getName()).thenReturn("test bun name");
        burger.setBuns(mockBun);
        String receipt = burger.getReceipt();
        assertTrue(receipt.contains("test bun name"));
    }

    @Test
    public void testGetReceiptWithRealIngredientsNotNull() {
        Bun realBun = new Bun("real bun", 100f);
        Ingredient realSauce = new Ingredient(IngredientType.SAUCE, "real sauce", 50f);
        Ingredient realFilling = new Ingredient(IngredientType.FILLING, "real filling", 75f);

        Burger realBurger = new Burger();
        realBurger.setBuns(realBun);
        realBurger.addIngredient(realSauce);
        realBurger.addIngredient(realFilling);

        String receipt = realBurger.getReceipt();
        assertNotNull(receipt);
    }

    @Test
    public void testGetReceiptWithRealIngredientsContainsBunName() {
        Bun realBun = new Bun("real bun", 100f);
        Ingredient realSauce = new Ingredient(IngredientType.SAUCE, "real sauce", 50f);

        Burger realBurger = new Burger();
        realBurger.setBuns(realBun);
        realBurger.addIngredient(realSauce);

        String receipt = realBurger.getReceipt();
        assertTrue(receipt.contains("real bun"));
    }

    @Test
    public void testGetReceiptWithRealIngredientsContainsSauce() {
        Bun realBun = new Bun("real bun", 100f);
        Ingredient realSauce = new Ingredient(IngredientType.SAUCE, "real sauce", 50f);

        Burger realBurger = new Burger();
        realBurger.setBuns(realBun);
        realBurger.addIngredient(realSauce);

        String receipt = realBurger.getReceipt();
        assertTrue(receipt.contains("sauce"));
    }

    @Test
    public void testGetReceiptWithRealIngredientsContainsFilling() {
        Bun realBun = new Bun("real bun", 100f);
        Ingredient realFilling = new Ingredient(IngredientType.FILLING, "real filling", 75f);

        Burger realBurger = new Burger();
        realBurger.setBuns(realBun);
        realBurger.addIngredient(realFilling);

        String receipt = realBurger.getReceipt();
        assertTrue(receipt.contains("filling"));
    }

    @Test
    public void testGetReceiptWithRealIngredientsContainsPrice() {
        Bun realBun = new Bun("real bun", 100f);
        Ingredient realSauce = new Ingredient(IngredientType.SAUCE, "real sauce", 50f);

        Burger realBurger = new Burger();
        realBurger.setBuns(realBun);
        realBurger.addIngredient(realSauce);

        String receipt = realBurger.getReceipt();
        assertTrue(receipt.contains("Price:"));
    }
}