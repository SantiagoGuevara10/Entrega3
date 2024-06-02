package gui;

import payment.PaymentGateway;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDialog extends JDialog {
    private JTextField cardNumberField;
    private JTextField cardHolderNameField;
    private JTextField expirationDateField;
    private JTextField cvvField;
    private JTextField amountField;
    private JComboBox<String> gatewayComboBox;
    private List<PaymentGateway> gateways;

    public PaymentDialog(JFrame parentFrame) {
        super(parentFrame, "Procesar Pago", true);
        setLayout(new GridLayout(7, 2, 10, 10));

        gateways = loadGateways("gateways.config");

        add(new JLabel("Número de Tarjeta:"));
        cardNumberField = new JTextField(20);
        add(cardNumberField);

        add(new JLabel("Nombre del Titular:"));
        cardHolderNameField = new JTextField(20);
        add(cardHolderNameField);

        add(new JLabel("Fecha de Expiración (MM/AA):"));
        expirationDateField = new JTextField(20);
        add(expirationDateField);

        add(new JLabel("CVV:"));
        cvvField = new JTextField(20);
        add(cvvField);

        add(new JLabel("Monto:"));
        amountField = new JTextField(20);
        add(amountField);

        add(new JLabel("Pasarela de Pago:"));
        gatewayComboBox = new JComboBox<>();
        for (PaymentGateway gateway : gateways) {
            gatewayComboBox.addItem(gateway.getName());
        }
        add(gatewayComboBox);

        JButton processButton = new JButton("Procesar");
        processButton.addActionListener(this::processPayment);
        add(processButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        pack();
        setLocationRelativeTo(parentFrame);
    }

    private void processPayment(ActionEvent e) {
        String cardNumber = cardNumberField.getText();
        String cardHolderName = cardHolderNameField.getText();
        String expirationDate = expirationDateField.getText();
        String cvv = cvvField.getText();
        double amount = Double.parseDouble(amountField.getText());
        String selectedGateway = (String) gatewayComboBox.getSelectedItem();

        for (PaymentGateway gateway : gateways) {
            if (gateway.getName().equals(selectedGateway)) {
                boolean success = gateway.processPayment(cardNumber, cardHolderName, expirationDate, cvv, amount);
                JOptionPane.showMessageDialog(this, success ? "Pago procesado correctamente" : "Error al procesar el pago", success ? "Éxito" : "Error", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
        dispose();
    }

    private List<PaymentGateway> loadGateways(String configFilePath) {
        List<PaymentGateway> gateways = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(configFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Class<?> clazz = Class.forName(line);
                    PaymentGateway gateway = (PaymentGateway) clazz.getDeclaredConstructor().newInstance();
                    gateways.add(gateway);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gateways;
    }
}