package homework4.services;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import homework4.models.Menu;

public class MenuService {
    private final List<Menu> menus = List.of(
            new Menu("Margherita Classic", "Saus tomat, mozzarella, dan basil.", 75000L, 10),
            new Menu("Pepperoni Supreme", "Pepperoni sapi, mozzarella, dan oregano.", 95000L, 8),
            new Menu("BBQ Chicken Blast", "Ayam panggang, saus BBQ, dan mozzarella.", 98000L, 7),
            new Menu("Meat Lovers", "Pepperoni, smoked beef, sosis, dan daging cincang.", 110000L, 6),
            new Menu("Mushroom Truffle Delight", "Jamur, saus krim truffle, dan parmesan.", 105000L, 5),
            new Menu("Hawaiian Paradise", "Ham sapi, nanas, mozzarella, dan saus tomat.", 90000L, 8),
            new Menu("Spicy Volcano", "Ayam pedas, jalapeño, paprika, dan saus sambal.", 99000L, 7),
            new Menu("Veggie Garden", "Jamur, paprika, tomat, zaitun, dan mozzarella.", 85000L, 9),
            new Menu("Cheese Overload", "Mozzarella, cheddar, parmesan, dan cream cheese.", 100000L, 6),
            new Menu("Rendang Fusion Pizza", "Rendang, bawang merah, cabai hijau, dan mozzarella.", 115000L, 4));

    public List<Menu> getMenus() {
        return menus;
    }

    public void showMenu() {
        showMenus(menus, true);
    }

    public void showMenusSortedByPrice() {
        showMenus(getMenusSortedByPrice(), false);
    }

    public List<Menu> getMenusSortedByPrice() {
        return menus.stream()
                .sorted(Comparator.comparingLong(Menu::getPrice))
                .toList();
    }

    public List<Menu> searchMenus(String query) {
        String normalizedQuery = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        return menus.stream()
                .filter(menu -> menu.getName().toLowerCase(Locale.ROOT).contains(normalizedQuery))
                .toList();
    }

    public void showSearchResults(String query) {
        showMenus(searchMenus(query), false);
    }

    private void showMenus(List<Menu> displayedMenus, boolean includeNumbers) {
        System.out.println("\n======= MENU =======");
        if (displayedMenus.isEmpty()) {
            System.out.println("Menu tidak ditemukan.");
            return;
        }
        for (int i = 0; i < displayedMenus.size(); i++) {
            Menu menu = displayedMenus.get(i);
            String number = includeNumbers ? (i + 1) + ". " : "";
            System.out.printf("%s%-30s Rp.%,d | Stok: %d%n",
                    number, menu.getName(), menu.getPrice(), menu.getStock());
        }
    }

    public Menu getMenuByNumber(int number) {
        return number < 1 || number > menus.size() ? null : menus.get(number - 1);
    }
}
