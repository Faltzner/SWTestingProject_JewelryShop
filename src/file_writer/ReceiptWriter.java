package file_writer;

import collections.NewReceipt;

import java.io.File;
import java.io.PrintWriter;

public class ReceiptWriter {
    private PrintWriter writer;
    private NewReceipt newReceipt;
    private String receiptID;

    public ReceiptWriter(NewReceipt newReceipt, String receiptID) throws Exception {
        this.newReceipt = newReceipt;
        this.receiptID = receiptID;
        createFile();
    }

    private void createFile() throws Exception {
        final File folder = new File(System.getProperty("user.home"), "gemshop");
        if (!folder.exists() && !folder.mkdirs()) {
            throw new RuntimeException("Failed to create save directory.");
        }
        final File myFile = new File(folder, this.receiptID + ".txt");
        this.writer = new PrintWriter(myFile, "UTF-8");
    }

    public void createReceipt() {
        writer.println("                Receipt                ");
        writer.println("---------------------------------------");
        writer.println("---------------------------------------");
        writer.println(" Receipt ID:   " + receiptID);
        writer.println(" Order ID:     " + newReceipt.getBuyOrderID());
        writer.println(" Product Name: " + newReceipt.getProductName());
        writer.println(" Price/Unit:   " + newReceipt.getPrice() + " Baht.");
        writer.println(" Quantity:     " + newReceipt.getQuantity());
        writer.println(" Date:         " + newReceipt.getDate());
        writer.println("---------------------------------------");
        writer.println(" Total:        " + (newReceipt.getQuantity() * newReceipt.getPrice()) + " Baht.");
        writer.println("---------------------------------------");
        writer.close();
    }

}