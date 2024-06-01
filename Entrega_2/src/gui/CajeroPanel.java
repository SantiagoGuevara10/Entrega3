package gui;

import javax.swing.*;
import java.awt.*;
import galeria.usuarios.*;

public class CajeroPanel extends JPanel {
    private MainFrame mainFrame;

    public CajeroPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        JButton processPaymentButton = new JButton("Procesar Pago");
        processPaymentButton.addActionListener(e -> new PaymentDialog(mainFrame).setVisible(true));
        add(processPaymentButton, BorderLayout.CENTER);

        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> mainFrame.showPanel("login"));
        add(backButton, BorderLayout.SOUTH);
    }
}
