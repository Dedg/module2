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
                        new Object[]{0, ShoppingCart.ItemType.NEW, 1},
                        new Object[]{0, ShoppingCart.ItemType.NEW, 10},
                        new Object[]{0, ShoppingCart.ItemType.NEW, 11},
                        new Object[]{0, ShoppingCart.ItemType.SECOND_FREE, 1},
                        new Object[]{50, ShoppingCart.ItemType.SECOND_FREE, 2},
                        new Object[]{50, ShoppingCart.ItemType.SECOND_FREE, 9},
                        new Object[]{51, ShoppingCart.ItemType.SECOND_FREE, 10},
                        new Object[]{80, ShoppingCart.ItemType.SECOND_FREE, 301},
                        new Object[]{70, ShoppingCart.ItemType.SALE, 1},
                        new Object[]{70, ShoppingCart.ItemType.SALE, 9},
                        new Object[]{71, ShoppingCart.ItemType.SALE, 10},
                        new Object[]{80, ShoppingCart.ItemType.SALE, 110},
                        new Object[]{0, ShoppingCart.ItemType.REGULAR, 1},
                        new Object[]{0, ShoppingCart.ItemType.REGULAR, 9},
                        new Object[]{1, ShoppingCart.ItemType.REGULAR, 10},
                        new Object[]{80, ShoppingCart.ItemType.REGULAR, 810}
                )
        ).stream();
    }

    /**
     * Test of calculateDiscount method, of class ShoppingCart.
     */
    @ParameterizedTest
    @MethodSource("testCalculateDiscountData")
    public void testCalculateDiscount(int discount, ShoppingCart.ItemType type, int quantity) {
        assertEquals(discount, ShoppingCart.calculateDiscount(type, quantity));
    }
}