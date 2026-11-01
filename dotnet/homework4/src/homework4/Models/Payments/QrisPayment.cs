namespace homework4.Models.Payments;

public class QrisPayment : PaymentMethod
{
    public override string Name => "QRIS";

    public override bool Pay(long total, out long paymentAmount, out long change)
    {
        if (total <= 0)
        {
            Console.WriteLine("Total pembayaran tidak valid.");
            paymentAmount = 0;
            change = 0;
            return false;
        }

        Console.WriteLine("\nMemproses pembayaran QRIS...");
        paymentAmount = total;
        change = 0;
        Console.Write("Konfirmasi pembayaran QRIS (Y/N): ");
        bool confirmed = string.Equals(Console.ReadLine()?.Trim(), "Y", StringComparison.OrdinalIgnoreCase);
        if (confirmed)
        {
            Console.WriteLine("Pembayaran berhasil.");
        }
        else
        {
            Console.WriteLine("Pembayaran QRIS dibatalkan.");
        }
        return confirmed;
    }
}