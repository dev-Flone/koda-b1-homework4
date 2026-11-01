package homework4.services;

import java.util.ArrayList;
import java.util.List;

import homework4.models.Cart;
import homework4.models.Menu;

public class CartService {
    private final List<Cart> items = new ArrayList<>();

    public List<Cart> getItems() {
        return List.copyOf(items);
    }

    public boolean addItem(Menu menu, int quantity) {
        if (menu == null || quantity <= 0) {
            System.out.println("Menu atau jumlah tidak valid.");
            return false;
        }

        Cart existingItem = items.stream()
                .filter(item -> item.getMenu() == menu)
                .findFirst()
                .orElse(null);
        int existingQuantity = existingItem == null ? 0 : existingItem.getQuantity();
        if (quantity > menu.getStock() - existingQuantity) {
            System.out.printf("Stok tidak mencukupi. Stok tersedia: %d.%n", menu.getStock() - existingQuantity);
            return false;
        }

        if (existingItem != null) {
            existingItem.setQuantity(existingQuantity + quantity);
        } else {
            items.add(new Cart(menu, quantity));
        }
        System.out.println("Menu berhasil ditambahkan ke keranjang.");
        return true;
    }

    public boolean updateQuantity(int itemNumber, int quantity) {
        if (quantity <= 0) {
            System.out.println("Jumlah harus lebih dari 0.");
            return false;
        }
        Cart item = findItem(itemNumber);
        if (item == null) {
            System.out.println("Nomor item tidak valid.");
            return false;
        }
        if (quantity > item.getMenu().getStock()) {
            System.out.printf("Stok tidak mencukupi. Stok tersedia: %d.%n", item.getMenu().getStock());
            return false;
        }
        item.setQuantity(quantity);
        System.out.println("Jumlah pesanan berhasil diperbarui.");
        return true;
    }

    public boolean removeItem(int itemNumber) {
        Cart item = findItem(itemNumber);
        if (item == null) {
            System.out.println("Nomor item tidak valid.");
            return false;
        }
        items.remove(item);
        System.out.println("Pesanan berhasil dihapus.");
        return true;
    }

    private Cart findItem(int itemNumber) {
        return itemNumber < 1 || itemNumber > items.size() ? null : items.get(itemNumber - 1);
    }

    public void showCart() {
        System.out.println("\n======= KERANJANG =======");
        if (items.isEmpty()) {
            System.out.println("Keranjang masih kosong.");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            Cart item = items.get(i);
            System.out.printf("%d. %-30s x %d | Rp.%,d%n", i + 1, item.getMenu().getName(),
                    item.getQuantity(), item.getMenu().getPrice() * item.getQuantity());
        }
        System.out.printf("Total: Rp.%,d%n", getTotal());
    }

    public long getTotal() {
        return items.stream()
                .mapToLong(item -> item.getMenu().getPrice() * item.getQuantity())
                .sum();
    }

    public boolean hasSufficientStock() {
        return items.stream().allMatch(item -> item.getQuantity() <= item.getMenu().getStock());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clearCart() {
        items.clear();
    }
}
