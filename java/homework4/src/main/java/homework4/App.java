package homework4;

import homework4.services.CartService;
import homework4.services.MenuService;
import homework4.ui.Home;
import homework4.services.CheckoutService;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            CartService cartService = new CartService();
            MenuService menuService = new MenuService();
            CheckoutService checkoutService = new CheckoutService(cartService);
            Home home = new Home(scanner, menuService, cartService, checkoutService);
            home.run();
        }
    }
}
