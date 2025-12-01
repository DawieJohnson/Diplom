package praktikum;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class BurgerTest {

    private Burger burger;

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient mockIngredient;

    // Параметры для тестирования
    private final String bunName;
    private final float bunPrice;
    private final int ingredientCount;
    private final float ingredientPrice;
    private final float expectedPrice;

    public BurgerTest(String bunName, float bunPrice, int ingredientCount, float ingredientPrice, float expectedPrice) {
        this.bunName = bunName;
        this.bunPrice = bunPrice;
        this.ingredientCount = ingredientCount;
        this.ingredientPrice = ingredientPrice;
        this.expectedPrice = expectedPrice;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"black bun", 100f, 0, 0f, 200f},
                {"white bun", 150f, 1, 50f, 350f},
                {"red bun", 200f, 2, 50f, 500f},
        };
    }

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();

        when(mockBun.getName()).thenReturn(bunName);
        when(mockBun.getPrice()).thenReturn(bunPrice);

        when(mockIngredient.getPrice()).thenReturn(ingredientPrice);
        when(mockIngredient.getName()).thenReturn("test ingredient");
        when(mockIngredient.getType()).thenReturn(IngredientType.SAUCE);

        burger.setBuns(mockBun);

        for (int i = 0; i < ingredientCount; i++) {
            burger.addIngredient(mockIngredient);
        }
    }

    @Test
    public void testSetBuns() {
        assertSame(mockBun, burger.bun);
    }

    @Test
    public void testAddIngredient() {
        int initialSize = burger.ingredients.size();
        Ingredient newIngredient = mock(Ingredient.class);

        burger.addIngredient(newIngredient);

        assertEquals(initialSize + 1, burger.ingredients.size());
        assertTrue(burger.ingredients.contains(newIngredient));
    }

    @Test
    public void testRemoveIngredient() {
        // Добавляем ингредиент для удаления
        Ingredient ingredientToRemove = mock(Ingredient.class);
        burger.addIngredient(ingredientToRemove);

        burger.removeIngredient(burger.ingredients.size() - 1);

        assertFalse(burger.ingredients.contains(ingredientToRemove));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIngredientWithInvalidIndex() {
        burger.removeIngredient(999);
    }

    @Test
    public void testMoveIngredient() {
        // Создаем отдельный бургер для тестирования перемещения
        Burger testBurger = new Burger();
        testBurger.setBuns(mockBun);

        Ingredient ing1 = mock(Ingredient.class);
        Ingredient ing2 = mock(Ingredient.class);

        testBurger.addIngredient(ing1);
        testBurger.addIngredient(ing2);

        // Сохраняем ссылки до перемещения
        Ingredient firstBefore = testBurger.ingredients.get(0);
        Ingredient secondBefore = testBurger.ingredients.get(1);

        testBurger.moveIngredient(0, 1);

        // Проверяем что элементы поменялись местами
        assertEquals(secondBefore, testBurger.ingredients.get(0));
        assertEquals(firstBefore, testBurger.ingredients.get(1));
    }

    @Test
    public void testGetPrice() {
        float actualPrice = burger.getPrice();
        assertEquals(expectedPrice, actualPrice, 0.01f);
    }

    @Test
    public void testGetReceipt() {
        String receipt = burger.getReceipt();

        assertNotNull(receipt);
        assertTrue(receipt.contains(bunName));
        assertTrue(receipt.contains("Price:"));
    }

    @Test
    public void testGetReceiptWithNoIngredients() {
        Burger emptyBurger = new Burger();
        emptyBurger.setBuns(mockBun);

        String receipt = emptyBurger.getReceipt();

        assertNotNull(receipt);
        assertTrue(receipt.contains(bunName));
    }

    @Test
    public void testGetReceiptWithRealIngredients() {
        // Используем реальные объекты для полного покрытия
        Bun realBun = new Bun("real bun", 100f);
        Ingredient realSauce = new Ingredient(IngredientType.SAUCE, "real sauce", 50f);
        Ingredient realFilling = new Ingredient(IngredientType.FILLING, "real filling", 75f);

        Burger realBurger = new Burger();
        realBurger.setBuns(realBun);
        realBurger.addIngredient(realSauce);
        realBurger.addIngredient(realFilling);

        String receipt = realBurger.getReceipt();

        assertNotNull(receipt);
        assertTrue(receipt.contains("real bun"));
        assertTrue(receipt.contains("sauce"));
        assertTrue(receipt.contains("filling"));
        assertTrue(receipt.contains("Price:"));
    }
}