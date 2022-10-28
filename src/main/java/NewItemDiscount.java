
public class NewItemDiscount implements Discount {
    /**
     * Discount is 0%;
     */
    @Override
    public int calculate(int quantity) {
        return 0;
    }
}