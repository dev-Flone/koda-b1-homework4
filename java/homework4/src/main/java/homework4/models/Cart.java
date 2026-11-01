package homework4.models;

public class Cart {
    private final Menu menu;
    private int quantity;

    public Cart(Menu menu, int quantity) {
        if (menu == null || quantity <= 0) {
            throw new IllegalArgumentException("Item keranjang tidak valid.");
        }
        this.menu = menu;
        this.quantity = quantity;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih dari 0.");
        }
        this.quantity = quantity;
    }
}
