package payment;

public interface PaymentGateway {
    boolean processPayment(String cardNumber, String cardHolderName, String expirationDate, String cvv, double amount);
    String getName();
}