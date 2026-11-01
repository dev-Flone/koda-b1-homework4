namespace homework4.Interfaces;

public interface IPayment
{
    string Name { get; }

    bool Pay(long total, out long paymentAmount, out long change);
}