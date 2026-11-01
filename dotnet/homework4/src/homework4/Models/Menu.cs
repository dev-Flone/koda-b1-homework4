namespace homework4.Models;

public class Menu(string name, long price, int stock)
{
    public string Name { get; } = string.IsNullOrWhiteSpace(name)
        ? throw new ArgumentException("Nama menu tidak boleh kosong.", nameof(name))
        : name;
    public long Price { get; } = price > 0
        ? price
        : throw new ArgumentOutOfRangeException(nameof(price), "Harga harus lebih dari 0.");
    public int Stock { get; private set; } = stock >= 0
        ? stock
        : throw new ArgumentOutOfRangeException(nameof(stock), "Stok tidak boleh negatif.");

    public bool ReduceStock(int quantity)
    {
        if (quantity <= 0 || quantity > Stock)
        {
            return false;
        }

        Stock -= quantity;
        return true;
    }
}
