package homework4.models.payment;

import java.util.Scanner;

public class QrisPayment extends Payment {
    @Override
    public boolean pay(long total, Scanner scanner) {
        if (total <= 0) {
            System.out.println("Total pembayaran tidak valid.");
            return false;
        }
        System.out.print("Konfirmasi pembayaran QRIS (Y/N): ");
        if (!scanner.hasNextLine() || !"y".equalsIgnoreCase(scanner.nextLine().trim())) {
            System.out.println("Pembayaran QRIS dibatalkan.");
            return false;
        }
        System.out.println("Memproses pembayaran QRIS....");
        System.out.println("Pembayaran berhasil");
        return true;
    }

    @Override
    public String getReceiptDetails(long total) {
        return String.format("Metode: QRIS%n%nTotal: Rp. %,d%nTotal dibayar: Rp. %,d", total, total);
    }
}
