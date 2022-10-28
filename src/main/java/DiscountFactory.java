public class DiscountFactory {
    public Discount getDiscount(ShoppingCart.ItemType type){
        return switch (type) {
            case NEW -> new NewItemDiscount();
            case REGULAR -> new RegularDiscount();
            case SECOND_FREE -> new SecondFreeDiscount();
            case SALE -> new SaleDiscount();
        };
    }
}
