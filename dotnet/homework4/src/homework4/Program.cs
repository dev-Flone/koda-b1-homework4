using homework4.Models;
using homework4.Services;

internal class Program
{
    private static void Main(string[] args)
    {
        var menuService = new MenuService();
        var cartService = new CartService();
        var checkoutService = new CheckoutService(cartService);
        bool running = true;

        while (running)
        {
            Home.ShowHome();
            Console.Write("Silahkan pilih menu: ");
            string? input = Console.ReadLine();
            if (input == null)
            {
                break;
            }
            if (!int.TryParse(input, out int choice))
            {
                Console.WriteLine("Input harus berupa angka.");
                continue;
            }

            switch (choice)
            {
                case 1:
                    Cart? selectedCart = menuService.SelectMenu();
                    if (selectedCart != null)
                    {
                        cartService.AddItems(selectedCart.Menu, selectedCart.Quantity);
                    }
                    break;
                case 2:
                    Console.Write("Masukkan nama menu yang dicari: ");
                    menuService.ShowSearchResults(Console.ReadLine() ?? string.Empty);
                    break;
                case 3:
                    menuService.ShowMenusSortedByPrice();
                    break;
                case 4:
                    ManageCart(cartService);
                    break;
                case 5:
                    checkoutService.ProcessCheckout();
                    break;
                case 6:
                    running = false;
                    Console.WriteLine("Terima kasih sudah mengunjungi Koda Food.");
                    break;
                default:
                    Console.WriteLine("Pilihan menu tidak valid.");
                    break;
            }
        }
    }

    private static void ManageCart(CartService cartService)
    {
        cartService.ShowCart();
        if (cartService.IsEmpty())
        {
            return;
        }

        Console.WriteLine("1. Ubah jumlah\n2. Hapus pesanan\n0. Kembali");
        Console.Write("Pilih aksi: ");
        if (!int.TryParse(Console.ReadLine(), out int choice) || choice == 0)
        {
            return;
        }
        if (choice is not (1 or 2))
        {
            Console.WriteLine("Pilihan aksi tidak valid.");
            return;
        }

        Console.Write("Pilih nomor item: ");
        if (!int.TryParse(Console.ReadLine(), out int itemNumber))
        {
            Console.WriteLine("Nomor item harus berupa angka.");
            return;
        }
        if (choice == 1)
        {
            Console.Write("Masukkan jumlah baru: ");
            if (!int.TryParse(Console.ReadLine(), out int quantity))
            {
                Console.WriteLine("Jumlah harus berupa angka.");
                return;
            }
            cartService.UpdateQuantity(itemNumber, quantity);
        }
        else
        {
            cartService.RemoveItem(itemNumber);
        }
    }
}
