package homework4.services;

import homework4.interfaces.PaymentMethod;
import homework4.models.Cart;
import homework4.models.Menu;
import homework4.models.payment.CashPayment;
import homework4.models.payment.QrisPayment;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CheckoutService {
    private final CartService cartService;

    public CheckoutService(CartService cartService) {
        this.cartService = cartService;
    }

    public void checkout(Scanner scanner) {
        System.out.println("\n========== CHECKOUT ==========");
        if (cartService.isEmpty()) {
            System.out.println("Keranjang masih kosong.");
            return;
        }
        if (!cartService.hasSufficientStock()) {
            System.out.println("Stok menu berubah dan tidak mencukupi. Perbarui keranjang sebelum checkout.");
            return;
        }

        List<Cart> items = cartService.getItems();
        long total = cartService.getTotal();
        printOrderItems(items);
        System.out.printf("------------------------------------%nTotal Belanja: Rp.%,d%n", total);

        System.out.print("\nKonfirmasi Pesanan Y/N? ");
        if (!scanner.hasNextLine() || !"y".equals(scanner.nextLine().trim().toLowerCase(Locale.ROOT))) {
            System.out.println("Checkout dibatalkan.");
            return;
        }

        System.out.println("Metode Pembayaran\n1. Cash\n2. QRIS");
        System.out.print("Pilih Metode: ");
        if (!scanner.hasNextLine()) {
            return;
        }

        int method;
        try {
            method = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException exception) {
            System.out.println("Pilihan harus berupa angka.");
            return;
        }

        PaymentMethod paymentMethod = switch (method) {
            case 1 -> new CashPayment();
            case 2 -> new QrisPayment();
            default -> null;
        };
        if (paymentMethod == null) {
            System.out.println("Pilihan metode tidak tersedia.");
            return;
        }

        if (!paymentMethod.pay(total, scanner)) {
            System.out.println("Pembayaran gagal. Keranjang tetap tersimpan.");
            return;
        }

        for (Cart item : items) {
            if (!item.getMenu().reduceStock(item.getQuantity())) {
                throw new IllegalStateException("Stok berubah setelah pembayaran berhasil.");
            }
        }
        showReceipt(items, total, paymentMethod);
        cartService.clearCart();
    }

    private void printOrderItems(List<Cart> items) {
        for (int i = 0; i < items.size(); i++) {
            Cart item = items.get(i);
            Menu menu = item.getMenu();
            long subtotal = menu.getPrice() * item.getQuantity();
            System.out.printf("%d. %s x %d | Rp.%,d%n", i + 1, menu.getName(), item.getQuantity(), subtotal);
        }
    }

    private void showReceipt(List<Cart> items, long total, PaymentMethod paymentMethod) {
        System.out.println("\n========== RECEIPT ==========");
        printOrderItems(items);
        System.out.println("------------------------------------");
        System.out.printf("Total pembayaran: Rp.%,d%n", total);
        System.out.println(paymentMethod.getReceiptDetails(total));
        System.out.println("Pembayaran berhasil.");
        System.out.println("Terima kasih sudah membeli.");
    }
}
