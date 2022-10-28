public class SecondFreeDiscount implements Discount {
    /**
     * Discount is 50% if quantity > 1
     * For each full 10 items gets additional 1% discount,
     * but not more than 80% total
     */
    @Override
    public int calculate(int quantity) {
        int discount = 0;
        if (quantity > 1)
            discount = 50;

        discount += quantity / 10;
        if (discount > 80)
            discount = 80;

        return discount;
    }
}