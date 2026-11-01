using homework4.Models;

namespace homework4.Services;

public class CartService
{
    private readonly List<Cart> items = [];

    public IReadOnlyList<Cart> GetItems() => items.AsReadOnly();

    public bool AddItems(Menu? menu, int quantity)
    {
        if (menu == null || quantity <= 0)
        {
            Console.WriteLine("Menu atau jumlah tidak valid.");
            return false;
        }

        Cart? existingItem = items.FirstOrDefault(item => item.Menu == menu);
        int existingQuantity = existingItem?.Quantity ?? 0;
        if (quantity > menu.Stock - existingQuantity)
        {
            Console.WriteLine($"Stok tidak mencukupi. Stok tersedia: {menu.Stock - existingQuantity}.");
            return false;
        }

        if (existingItem != null)
        {
            existingItem.SetQuantity(existingQuantity + quantity);
        }
        else
        {
            items.Add(new Cart(menu, quantity));
        }

        Console.WriteLine("Menu berhasil ditambahkan ke keranjang.");
        return true;
    }

    public bool UpdateQuantity(int itemNumber, int quantity)
    {
        if (quantity <= 0)
        {
            Console.WriteLine("Jumlah harus lebih dari 0.");
            return false;
        }
        Cart? item = FindItem(itemNumber);
        if (item == null)
        {
            Console.WriteLine("Nomor item tidak valid.");
            return false;
        }
        if (quantity > item.Menu.Stock)
        {
            Console.WriteLine($"Stok tidak mencukupi. Stok tersedia: {item.Menu.Stock}.");
            return false;
        }

        item.SetQuantity(quantity);
        Console.WriteLine("Jumlah pesanan berhasil diperbarui.");
        return true;
    }

    public bool RemoveItem(int itemNumber)
    {
        Cart? item = FindItem(itemNumber);
        if (item == null)
        {
            Console.WriteLine("Nomor item tidak valid.");
            return false;
        }
        items.Remove(item);
        Console.WriteLine("Pesanan berhasil dihapus.");
        return true;
    }

    private Cart? FindItem(int itemNumber) =>
        itemNumber < 1 || itemNumber > items.Count ? null : items[itemNumber - 1];

    public void ShowCart()
    {
        Console.WriteLine("\n========== KERANJANG ==========");
        if (items.Count == 0)
        {
            Console.WriteLine("Keranjang masih kosong.");
            return;
        }

        for (int i = 0; i < items.Count; i++)
        {
            Cart item = items[i];
            long subtotal = item.Menu.Price * item.Quantity;
            Console.WriteLine($"{i + 1}. {item.Menu.Name} (x{item.Quantity}) - Rp. {subtotal:N0}");
        }
        Console.WriteLine($"Total: Rp. {GetTotal():N0}");
    }

    public long GetTotal() => items.Sum(item => item.Menu.Price * item.Quantity);

    public bool HasSufficientStock() => items.All(item => item.Quantity <= item.Menu.Stock);

    public bool IsEmpty() => items.Count == 0;

    public void ClearCart() => items.Clear();
}
