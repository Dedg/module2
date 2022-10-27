/**
 * item info
 */
class Item {
    private String title;
    private double price;
    private int quantity;
    private ShoppingCart.ItemType type;
    private int discount;
    private double total;

    Item(String title, double price, int quantity, ShoppingCart.ItemType type) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.discount = calculateDiscount();
        this.total = price * quantity * (100.00 - discount) / 100.00;
    }

    /**
     * Returns item's discount.
     * For NEW item discount is 0%;
     * For SECOND_FREE item discount is 50% if quantity > 1
     * For SALE item discount is 70%
     * For each full 10 not NEW items item gets additional 1% discount,
     * but not more than 80% total
     */
    private int calculateDiscount() {
        int discount = 0;
        switch (type) {
            case NEW:
                return 0;
            case REGULAR:
                discount = 0;
                break;
            case SECOND_FREE:
                if (quantity > 1)
                    discount = 50;
                break;
            case SALE:
                discount = 70;
                break;
        }
        if (discount < 80) {
            discount += quantity / 10;
            if (discount > 80)
                discount = 80;
        }
        return discount;
    }

    public int getDiscount() {
        return this.discount;
    }

    public double getTotal() {
        return this.total;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return this.price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public ShoppingCart.ItemType getType() {
        return type;
    }
}
