package homework4.models.payment;

import java.util.Scanner;

public class CashPayment extends Payment {
    private long payment;
    private long change;

    public long getPayment() {
        return payment;
    }

    public long getChange() {
        return change;
    }

    @Override
    public boolean pay(long total, Scanner scanner) {
        if (total <= 0) {
            System.out.println("Total pembayaran tidak valid.");
            return false;
        }

        System.out.print("Masukkan uang: Rp. ");

        if (!scanner.hasNextLine()) {
            return false;
        }

        long inputPayment;
        try {
            inputPayment = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException exception) {
            System.out.println("Jumlah harus angka.");
            return false;
        }

        if (inputPayment <= 0) {
            System.out.println("Nominal harus diatas Rp. 0");
            return false;
        }

        if (inputPayment < total) {
            System.out.println("Uang anda kurang.");
            System.out.printf("Uang kurang Rp. %,d%n", total - inputPayment);
            return false;
        }

        this.payment = inputPayment;
        this.change = inputPayment - total;
        return true;
    }

    @Override
    public String getReceiptDetails(long total) {
        return String.format("Metode: Cash%n%nTotal: Rp. %,d%nUang diterima: Rp. %,d%nKembalian: Rp. %,d", total,
                payment,
                change);
    }
}