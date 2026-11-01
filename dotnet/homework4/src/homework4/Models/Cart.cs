namespace homework4.Models;

public class Cart(Menu menu, int quantity)
{
    public Menu Menu { get; } = menu ?? throw new ArgumentNullException(nameof(menu));
    public int Quantity { get; private set; } = quantity > 0
        ? quantity
        : throw new ArgumentOutOfRangeException(nameof(quantity), "Jumlah harus lebih dari 0.");

    public void SetQuantity(int quantity)
    {
        if (quantity <= 0)
        {
            throw new ArgumentOutOfRangeException(nameof(quantity), "Jumlah harus lebih dari 0.");
        }

        Quantity = quantity;
    }
}
