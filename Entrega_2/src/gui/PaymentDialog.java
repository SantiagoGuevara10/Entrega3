package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import galeria.usuarios.CompradorPropietario;
import galeria.usuarios.ManejoSesion;
import galeria.usuarios.Cajero;
import galeria.usuarios.UsuariosRegistrados;
import galeria.pieza.Pieza;
import galeria.inventarios.InventarioGeneral;

public class PaymentDialog extends JDialog {
    private JTextField compradorField;
    private JTextField vendedorField;
    private JTextField montoField;
    private JTextField piezaField;

    public PaymentDialog(JFrame parentFrame) {
        super(parentFrame, "Procesar Pago", true);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("ID Comprador:"));
        compradorField = new JTextField(20);
        add(compradorField);

        add(new JLabel("ID Vendedor:"));
        vendedorField = new JTextField(20);
        add(vendedorField);

        add(new JLabel("Monto:"));
        montoField = new JTextField(20);
        add(montoField);

        add(new JLabel("ID Pieza:"));
        piezaField = new JTextField(20);
        add(piezaField);

        JButton processButton = new JButton("Procesar");
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarPago();
            }
        });
        add(processButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        pack();
        setLocationRelativeTo(parentFrame);
    }

    private void procesarPago() {
        String compradorId = compradorField.getText();
        String vendedorId = vendedorField.getText();
        double monto = Double.parseDouble(montoField.getText());
        String piezaId = piezaField.getText();

        // Obtener instancias adecuadas de CompradorPropietario y Pieza
        CompradorPropietario comprador = obtenerCompradorPorId(compradorId);
        CompradorPropietario vendedor = obtenerCompradorPorId(vendedorId);
        Pieza pieza = obtenerPiezaPorId(piezaId);

        if (comprador == null || vendedor == null || pieza == null) {
            JOptionPane.showMessageDialog(this, "Datos inválidos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cajero cajero = (Cajero) ManejoSesion.getEmpleadoActual();
        cajero.procesarPago(comprador, vendedor, monto, pieza);

        JOptionPane.showMessageDialog(this, "Pago procesado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private CompradorPropietario obtenerCompradorPorId(String id) {
        UsuariosRegistrados usuariosRegistrados = new UsuariosRegistrados();
        for (CompradorPropietario comprador : usuariosRegistrados.getCompradoresEnPrograma()) {
            if (comprador.getIdUsuario().equals(id)) {
                return comprador;
            }
        }
        return null;
    }

    private Pieza obtenerPiezaPorId(String id) {
        InventarioGeneral inventario = new InventarioGeneral(); // Aquí debes pasar la instancia adecuada
        return inventario.getPieza(id);
    }
}