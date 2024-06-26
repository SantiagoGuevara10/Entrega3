package gui;

import galeria.inventarios.InventarioGeneral;
import galeria.pieza.Pieza;
import galeria.usuarios.CompradorPropietario;
import galeria.usuarios.Cajero;
import galeria.usuarios.UsuariosRegistrados;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CajeroPanel extends JPanel {
    private MainFrame mainFrame;
    private InventarioGeneral inventario;
    private UsuariosRegistrados usuarios;
    private Cajero cajero;
    private List<String> transacciones = new ArrayList<>();

    public CajeroPanel(MainFrame mainFrame, InventarioGeneral inventario2, UsuariosRegistrados usuariosDelPrograma, File archivoUsuarios, File archivoInventario) {
        this.mainFrame = mainFrame;
        this.inventario = mainFrame.inventario;
        this.usuarios = mainFrame.usuariosDelPrograma;
        this.cajero = new Cajero("idEmpleado", "NombreCajero", "usernameCajero", "passwordHash", "Cajero");

        setLayout(new BorderLayout());

        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("./datos/galeria2.jpeg");

        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(480, 270, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        imageLabel.setIcon(imageIcon);
        imagePanel.add(imageLabel);
        add(imagePanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton processPaymentButton = new JButton("Procesar Pago");
        processPaymentButton.addActionListener(e -> new PaymentDialog(mainFrame).setVisible(true));
        buttonPanel.add(processPaymentButton);

        JButton issueReceiptsButton = new JButton("Emitir Recibos");
        issueReceiptsButton.addActionListener(e -> emitirRecibos());
        buttonPanel.add(issueReceiptsButton);

        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> mainFrame.showPanel("login"));
        buttonPanel.add(backButton);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(buttonPanel);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void emitirRecibos() {
        for (String transaccion : transacciones) {
            try {
                emitirRecibo(transaccion);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al emitir recibo: " + e.getMessage());
            }
        }
        transacciones.clear();
        JOptionPane.showMessageDialog(this, "Recibos emitidos exitosamente.");
    }

    private void emitirRecibo(String transaccion) throws IOException {
        try (FileWriter writer = new FileWriter("Recibo_" + transaccion.hashCode() + ".txt")) {
            writer.write("Recibo de la transacción: " + transaccion);
        }
    }

    private CompradorPropietario buscarCompradorPorId(String id) {
        for (CompradorPropietario comprador : usuarios.getCompradoresEnPrograma()) {
            if (comprador.getIdUsuario().equals(id)) {
                return comprador;
            }
        }
        return null;
    }
}
