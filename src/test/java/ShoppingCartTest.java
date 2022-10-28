import org.junit.jupiter.api.Test;
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


    /**
     * Test of formatTicket method, of class ShoppingCart.
     */
    @Test
    public void testFormatTicket() {
        ShoppingCart cart = new ShoppingCart();
        assertEquals("No items.", cart.formatTicket());
        cart.addItem("Apple", 0.99, 5, ShoppingCart.ItemType.NEW);
        assertEquals("# Item  Price Quan. Discount Total \n" +
                "----------------------------------\n" +
                "1 Apple  $.99     5        - $4.95 \n" +
                "----------------------------------\n" +
                "1                            $4.95 ", cart.formatTicket());
        cart.addItem("Banana", 20.00, 4, ShoppingCart.ItemType.SECOND_FREE);
        assertEquals("# Item    Price Quan. Discount  Total \n" +
                "-------------------------------------\n" +
                "1 Apple    $.99     5        -  $4.95 \n" +
                "2 Banana $20.00     4      50% $40.00 \n" +
                "-------------------------------------\n" +
                "2                              $44.95 ", cart.formatTicket());
        cart.addItem("A long piece of toilet paper", 17.20, 1, ShoppingCart.ItemType.SALE);
        assertEquals("# Item                          Price Quan. Discount  Total \n" +
                "-----------------------------------------------------------\n" +
                "1 Apple                          $.99     5        -  $4.95 \n" +
                "2 Banana                       $20.00     4      50% $40.00 \n" +
                "3 A long piece of toilet paper $17.20     1      70%  $5.16 \n" +
                "-----------------------------------------------------------\n" +
                "3                                                    $50.11 ", cart.formatTicket());
        cart.addItem("Nails", 2.00, 500, ShoppingCart.ItemType.REGULAR);
        assertEquals("# Item                          Price Quan. Discount   Total \n" +
                "------------------------------------------------------------\n" +
                "1 Apple                          $.99     5        -   $4.95 \n" +
                "2 Banana                       $20.00     4      50%  $40.00 \n" +
                "3 A long piece of toilet paper $17.20     1      70%   $5.16 \n" +
                "4 Nails                         $2.00   500      50% $500.00 \n" +
                "------------------------------------------------------------\n" +
                "4                                                    $550.11 ", cart.formatTicket());
    }
}