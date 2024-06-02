package payment;

import java.io.FileWriter;
import java.io.IOException;

public class PayUGateway implements PaymentGateway {

    @Override
    public boolean processPayment(String cardNumber, String cardHolderName, String expirationDate, String cvv, double amount) {
        boolean success = true;

        try (FileWriter writer = new FileWriter("PayU.txt", true)) {
            writer.write("Transaction: " + cardNumber + ", " + cardHolderName + ", " + expirationDate + ", " + cvv + ", " + amount + " - " + (success ? "Success" : "Failure") + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public String getName() {
        return "PayU";
    }
}
