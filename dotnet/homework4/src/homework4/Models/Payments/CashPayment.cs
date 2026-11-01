namespace homework4.Models.Payments;

public class CashPayment : PaymentMethod
{
    public override string Name => "Cash";

    public override bool Pay(long total, out long paymentAmount, out long change)
    {
        if (total <= 0)
        {
            Console.WriteLine("Total pembayaran tidak valid.");
            paymentAmount = 0;
            change = 0;
            return false;
        }

        Console.Write("Masukkan uang pembayaran: Rp. ");

        if (!long.TryParse(Console.ReadLine(), out paymentAmount))
        {
            Console.WriteLine("Nominal tidak valid");
            change = 0;
            return false;
        }

        if (paymentAmount <= 0)
        {
            Console.WriteLine("Nominal harus diatas Rp. 0");
            change = 0;
            return false;
        }

        if (paymentAmount < total)
        {
            Console.WriteLine("Uang kurang dari total bayar.");
            Console.WriteLine($"Uang kurang: Rp. {total - paymentAmount:N0}");
            change = 0;
            return false;
        }

        change = paymentAmount - total;

        Console.WriteLine("Pembayaran berhasil.");

        return true;
    }
}