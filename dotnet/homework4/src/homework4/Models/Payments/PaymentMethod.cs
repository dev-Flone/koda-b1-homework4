using homework4.Interfaces;

namespace homework4.Models.Payments;

public abstract class PaymentMethod : IPayment
{
    public abstract string Name { get; }

    public abstract bool Pay(long total, out long paymentAmount, out long change);
}