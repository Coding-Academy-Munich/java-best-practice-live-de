package invoice.v2;

import invoice.data.InvoiceData;
import invoice.data.Order;

public class Main {
    public static void main(String[] args) {
        for (Order order : InvoiceData.orders) {
            System.out.println(InvoiceGenerator.generateInvoice(order, InvoiceData.products));
            System.out.println("=========================================");
        }
    }
}