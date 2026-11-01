using homework4.Models;

namespace homework4.Services;

public class MenuService
{
    private readonly List<Menu> menus =
    [
        new Menu("Margherita Classic", 75000, 10),
        new Menu("Pepperoni Supreme", 95000, 8),
        new Menu("BBQ Chicken Blast", 98000, 7),
        new Menu("Meat Lovers", 110000, 6),
        new Menu("Mushroom Truffle Delight", 105000, 5),
        new Menu("Hawaiian Paradise", 90000, 8),
        new Menu("Spicy Volcano", 99000, 7),
        new Menu("Veggie Garden", 85000, 9),
        new Menu("Cheese Overload", 100000, 6),
        new Menu("Rendang Fusion Pizza", 115000, 4)
    ];

    public IReadOnlyList<Menu> GetMenus() => menus.AsReadOnly();

    public void ShowMenu() => ShowMenus(menus);

    public IReadOnlyList<Menu> GetMenusSortedByPrice() =>
        menus.OrderBy(menu => menu.Price).ToList();

    public void ShowMenusSortedByPrice() => ShowMenus(GetMenusSortedByPrice());

    public IReadOnlyList<Menu> SearchMenus(string query) =>
        menus.Where(menu => menu.Name.Contains(query.Trim(), StringComparison.OrdinalIgnoreCase)).ToList();

    public void ShowSearchResults(string query) => ShowMenus(SearchMenus(query));

    public Menu? GetMenuByIndex(int index) =>
        index < 0 || index >= menus.Count ? null : menus[index];

    public Cart? SelectMenu()
    {
        ShowMenu();
        Console.Write($"Pilih menu (1 - {menus.Count}, 0 untuk kembali): ");
        if (!int.TryParse(Console.ReadLine(), out int choice))
        {
            Console.WriteLine("Pilihan harus berupa angka.");
            return null;
        }
        if (choice == 0)
        {
            return null;
        }

        Menu? selectedMenu = GetMenuByIndex(choice - 1);
        if (selectedMenu == null)
        {
            Console.WriteLine("Menu tidak ditemukan.");
            return null;
        }

        Console.Write("Masukkan jumlah: ");
        if (!int.TryParse(Console.ReadLine(), out int quantity) || quantity <= 0)
        {
            Console.WriteLine("Jumlah harus berupa angka dan lebih dari 0.");
            return null;
        }

        return new Cart(selectedMenu, quantity);
    }

    private static void ShowMenus(IReadOnlyList<Menu> displayedMenus)
    {
        Console.WriteLine("\n========== MENU ==========");
        if (displayedMenus.Count == 0)
        {
            Console.WriteLine("Menu tidak ditemukan.");
            return;
        }
        for (int i = 0; i < displayedMenus.Count; i++)
        {
            Menu menu = displayedMenus[i];
            Console.WriteLine($"{i + 1}. {menu.Name} - Rp.{menu.Price:N0} | Stok: {menu.Stock}");
        }
    }
}
