using homework4.Interfaces;
using homework4.Models;
using homework4.Models.Payments;

namespace homework4.Services;

public class CheckoutService(CartService cartService)
{
    private readonly CartService cartService = cartService;

    public void ProcessCheckout()
    {
        if (cartService.IsEmpty())
        {
            Console.WriteLine("Keranjang masih kosong.");
            return;
        }
        if (!cartService.HasSufficientStock())
        {
            Console.WriteLine("Stok tidak mencukupi. Perbarui keranjang sebelum checkout.");
            return;
        }

        IReadOnlyList<Cart> items = cartService.GetItems();
        long total = cartService.GetTotal();
        Console.WriteLine("\n========== CHECKOUT ==========");
        PrintItems(items);
        Console.WriteLine("------------------------------");
        Console.WriteLine($"Total Pembayaran: Rp. {total:N0}");

        Console.Write("Lanjutkan Checkout? Y/N: ");
        if (!string.Equals(Console.ReadLine()?.Trim(), "Y", StringComparison.OrdinalIgnoreCase))
        {
            Console.WriteLine("Checkout dibatalkan.");
            return;
        }

        Console.WriteLine("\nPilih metode pembayaran:\n1. Cash\n2. QRIS");
        Console.Write("Pilihan: ");
        if (!int.TryParse(Console.ReadLine(), out int paymentChoice))
        {
            Console.WriteLine("Pilihan harus berupa angka.");
            return;
        }

        IPayment? paymentMethod = paymentChoice switch
        {
            1 => new CashPayment(),
            2 => new QrisPayment(),
            _ => null
        };
        if (paymentMethod == null)
        {
            Console.WriteLine("Metode pembayaran tidak valid.");
            return;
        }

        if (!paymentMethod.Pay(total, out long paymentAmount, out long change))
        {
            Console.WriteLine("Pembayaran gagal. Keranjang tetap tersimpan.");
            return;
        }

        foreach (Cart item in items)
        {
            if (!item.Menu.ReduceStock(item.Quantity))
            {
                throw new InvalidOperationException("Stok berubah setelah pembayaran berhasil.");
            }
        }
        ShowReceipt(items, total, paymentMethod.Name, paymentAmount, change);
        cartService.ClearCart();
    }

    private static void PrintItems(IReadOnlyList<Cart> items)
    {
        for (int i = 0; i < items.Count; i++)
        {
            Cart item = items[i];
            long subtotal = item.Menu.Price * item.Quantity;
            Console.WriteLine($"{i + 1}. {item.Menu.Name} x {item.Quantity} | Subtotal: Rp. {subtotal:N0}");
        }
    }

    private static void ShowReceipt(
        IReadOnlyList<Cart> items,
        long total,
        string paymentMethod,
        long payment,
        long change)
    {
        Console.WriteLine("\n========== STRUK ==========");
        PrintItems(items);
        Console.WriteLine("------------------------------");
        Console.WriteLine($"Total pembayaran: Rp. {total:N0}");
        Console.WriteLine($"Metode pembayaran: {paymentMethod}");
        Console.WriteLine($"Dibayar: Rp. {payment:N0}");
        Console.WriteLine($"Kembalian: Rp. {change:N0}");
        Console.WriteLine("Pembayaran berhasil. Terima kasih sudah membeli.");
    }
}
