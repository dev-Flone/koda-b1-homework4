package homework4.models;

public class Menu {
    private final String name;
    private final String description;
    private final long price;
    private int stock;

    public Menu(String name, String description, long price, int stock) {
        if (name == null || name.isBlank() || price <= 0 || stock < 0) {
            throw new IllegalArgumentException("Data menu tidak valid.");
        }
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public boolean reduceStock(int quantity) {
        if (quantity <= 0 || quantity > stock) {
            return false;
        }
        stock -= quantity;
        return true;
    }
}
