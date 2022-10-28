public class SaleDiscount implements Discount {
    /**
     * Discount is 70%
     * For each full 10 items gets additional 1% discount,
     * but not more than 80% total
     */
    @Override
    public int calculate(int quantity) {
        int discount = 70;

        discount += quantity / 10;
        if (discount > 80)
            discount = 80;

        return discount;
    }
}