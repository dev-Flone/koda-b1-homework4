package homework4.interfaces;

import java.util.Scanner;

public interface PaymentMethod {
    boolean pay(long total, Scanner scanner);

    String getReceiptDetails(long total);
}
