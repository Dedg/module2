import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

class ShoppingCartTest {
    public static Stream appendFormattedTestData(){
        return new ArrayList(
                Arrays.asList(
                        new Object[]{"   SomeLine    ", 0, 14},
                        new Object[]{"SomeL ", 0, 5},
                        new Object[]{"  SomeLine ", 1, 10},
                        new Object[]{"SomeLine  ", -1, 9}
                )
        ).stream();
    }

    /**
     * Test of appendFormatted method, of class ShoppingCart.
     */
    @ParameterizedTest
    @MethodSource("appendFormattedTestData")
    public void appendFormattedTest(String formattedString, int align, int width) {
        StringBuilder sb = new StringBuilder();
        ShoppingCart.appendFormatted(sb, "SomeLine", align, width);
        assertEquals(sb.toString(), formattedString);
    }

    public static Stream testCalculateDiscountData(){
        return new ArrayList(
                Arrays.asList(
                        new Object[]{0, new Item("Title", 0.99, 1, ShoppingCart.ItemType.NEW)},
                        new Object[]{0, new Item("Title", 0.99, 10, ShoppingCart.ItemType.NEW)},
                        new Object[]{0, new Item("Title", 0.99, 11, ShoppingCart.ItemType.NEW)},
                        new Object[]{0, new Item("Title", 0.99, 1, ShoppingCart.ItemType.SECOND_FREE)},
                        new Object[]{50, new Item("Title", 0.99, 2, ShoppingCart.ItemType.SECOND_FREE)},
                        new Object[]{50, new Item("Title", 0.99, 9, ShoppingCart.ItemType.SECOND_FREE)},
                        new Object[]{51, new Item("Title", 0.99, 10, ShoppingCart.ItemType.SECOND_FREE)},
                        new Object[]{80, new Item("Title", 0.99, 301, ShoppingCart.ItemType.SECOND_FREE)},
                        new Object[]{70, new Item("Title", 0.99, 1, ShoppingCart.ItemType.SALE)},
                        new Object[]{70, new Item("Title", 0.99, 9, ShoppingCart.ItemType.SALE)},
                        new Object[]{71, new Item("Title", 0.99, 10, ShoppingCart.ItemType.SALE)},
                        new Object[]{80, new Item("Title", 0.99, 110, ShoppingCart.ItemType.SALE)},
                        new Object[]{0, new Item("Title", 0.99, 1, ShoppingCart.ItemType.REGULAR)},
                        new Object[]{0, new Item("Title", 0.99, 9, ShoppingCart.ItemType.REGULAR)},
                        new Object[]{1, new Item("Title", 0.99, 10, ShoppingCart.ItemType.REGULAR)},
                        new Object[]{80, new Item("Title", 0.99, 810, ShoppingCart.ItemType.REGULAR)}
                )
        ).stream();
    }

    /**
     * Test of calculateDiscount method, of class ShoppingCart.
     */
    @ParameterizedTest
    @MethodSource("testCalculateDiscountData")
    public void testCalculateDiscount(int discount, Item item) {
        assertEquals(discount, item.getDiscount());
    }
}