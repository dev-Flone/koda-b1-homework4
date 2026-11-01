package homework4.models.payment;

import homework4.interfaces.PaymentMethod;

public abstract class Payment implements PaymentMethod {
    protected void showInvalidAmountMessage(String message) {
        System.out.println(message);
    }
}
