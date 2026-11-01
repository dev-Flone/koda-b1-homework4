package homework4.ui;

import java.util.Scanner;

import homework4.models.Menu;
import homework4.services.CartService;
import homework4.services.CheckoutService;
import homework4.services.MenuService;

public class Home {
    private final Scanner scanner;
    private final MenuService menuService;
    private final CartService cartService;
    private final CheckoutService checkoutService;

    public Home(Scanner scanner, MenuService menuService, CartService cartService, CheckoutService checkoutService) {
        this.scanner = scanner;
        this.menuService = menuService;
        this.cartService = cartService;
        this.checkoutService = checkoutService;
    }

    public void run() {
        boolean running = true;
        while (running) {
            showHome();
            Integer choice = readInt("Silahkan pilih menu: ");
            if (choice == null) {
                break;
            }
            switch (choice) {
                case 1 -> chooseMenu();
                case 2 -> searchMenu();
                case 3 -> menuService.showMenusSortedByPrice();
                case 4 -> manageCart();
                case 5 -> checkoutService.checkout(scanner);
                case 6 -> {
                    running = false;
                    System.out.println("Terima kasih sudah mengunjungi Koda Food.");
                }
                default -> System.out.println("Pilihan menu tidak valid.");
            }
        }
    }

    private void showHome() {
        System.out.println("\n======= WELCOME TO KODA FOOD =======");
        System.out.println("1. Lihat menu dan tambah pesanan");
        System.out.println("2. Cari menu");
        System.out.println("3. Urutkan menu berdasarkan harga");
        System.out.println("4. Lihat/ubah/hapus keranjang");
        System.out.println("5. Checkout");
        System.out.println("6. Exit");
    }

    private void chooseMenu() {
        menuService.showMenu();
        Integer menuNumber = readInt("Pilih nomor menu (0 untuk kembali): ");
        Menu menu = menuService.getMenuByNumber(menuNumber);
        if (menuNumber == 0) {
            return;
        }

        if (menu == null) {
            System.out.println("Pilihan menu tidak valid.");
            return;
        }
        Integer quantity = readInt("Masukkan jumlah: ");
        if (quantity != null) {
            cartService.addItem(menu, quantity);
        }
    }

    private void searchMenu() {
        System.out.print("Masukkan nama menu: ");
        if (scanner.hasNextLine()) {
            menuService.showSearchResults(scanner.nextLine());
        }
    }

    private void manageCart() {
        cartService.showCart();
        if (cartService.isEmpty()) {
            return;
        }
        System.out.println("1. Ubah jumlah\n2. Hapus pesanan\n0. Kembali");
        Integer choice = readInt("Pilih aksi: ");
        if (choice == null || choice == 0) {
            return;
        }
        Integer itemNumber = readInt("Pilih nomor item: ");
        if (itemNumber == null) {
            return;
        }
        if (choice == 1) {
            Integer quantity = readInt("Masukkan jumlah baru: ");
            if (quantity != null) {
                cartService.updateQuantity(itemNumber, quantity);
            }
        } else if (choice == 2) {
            cartService.removeItem(itemNumber);
        } else {
            System.out.println("Pilihan aksi tidak valid.");
        }
    }

    private Integer readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                return null;
            }
            try {
                return Integer.valueOf(scanner.nextLine().trim());
            } catch (NumberFormatException exception) {
                System.out.println("Input harus berupa angka.");
            }
        }
    }
}
