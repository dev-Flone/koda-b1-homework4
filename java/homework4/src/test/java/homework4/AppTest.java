package homework4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import homework4.models.Menu;
import homework4.services.CartService;
import homework4.services.MenuService;

public class AppTest {
    @Test
    public void cartCombinesItemsAndEnforcesStock() {
        Menu menu = new Menu("Test Pizza", "Test", 10000, 5);
        CartService cart = new CartService();

        assertTrue(cart.addItem(menu, 2));
        assertTrue(cart.addItem(menu, 1));
        assertEquals(1, cart.getItems().size());
        assertEquals(3, cart.getItems().get(0).getQuantity());
        assertEquals(30000L, cart.getTotal());
        assertFalse(cart.addItem(menu, 3));
        assertFalse(cart.updateQuantity(1, 6));
        assertTrue(cart.updateQuantity(1, 4));
        assertTrue(cart.removeItem(1));
        assertTrue(cart.isEmpty());
    }

    @Test
    public void menuSearchAndSortedListingUseStreams() {
        MenuService menuService = new MenuService();
        List<Menu> result = menuService.searchMenus("pepperoni");
        assertEquals(1, result.size());
        assertEquals("Pepperoni Supreme", result.get(0).getName());

        List<Menu> sorted = menuService.getMenusSortedByPrice();
        assertEquals("Margherita Classic", sorted.get(0).getName());
        assertTrue(menuService.getMenus().stream().allMatch(menu -> menu.getStock() > 0));
    }
}
