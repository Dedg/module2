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
        this.discount = new DiscountFactory().getDiscount(type).calculate(quantity);
        this.total = price * quantity * (100.00 - discount) / 100.00;
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
