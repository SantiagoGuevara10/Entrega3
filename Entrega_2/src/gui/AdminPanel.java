package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import galeria.pieza.Pieza;
import galeria.inventarios.InventarioGeneral;
import galeria.usuarios.Administrador;
import galeria.usuarios.ManejoSesion;
import java.util.Date;

public class AdminPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField piezaIdField;
    private JTextField piezaTituloField;
    private JTextField piezaAnioField;
    private JTextField piezaLugarField;
    private JTextField piezaValorField;
    private JComboBox<String> estadoPiezaCombo;
    private InventarioGeneral inventario;
    private Administrador admin;

    public AdminPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.inventario = new InventarioGeneral(); // Aquí debes pasar la instancia adecuada
        this.admin = (Administrador) ManejoSesion.getEmpleadoActual(); // Obtener el administrador actual de la sesión

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("ID de Pieza:"), gbc);
        
        gbc.gridx = 1;
        piezaIdField = new JTextField(20);
        add(piezaIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Título de Pieza:"), gbc);

        gbc.gridx = 1;
        piezaTituloField = new JTextField(20);
        add(piezaTituloField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Año de Creación:"), gbc);

        gbc.gridx = 1;
        piezaAnioField = new JTextField(20);
        add(piezaAnioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Lugar de Creación:"), gbc);

        gbc.gridx = 1;
        piezaLugarField = new JTextField(20);
        add(piezaLugarField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Valor de Pieza:"), gbc);

        gbc.gridx = 1;
        piezaValorField = new JTextField(20);
        add(piezaValorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Estado de Pieza:"), gbc);

        gbc.gridx = 1;
        estadoPiezaCombo = new JComboBox<>(new String[]{"bodega", "exhibida", "vendida"});
        add(estadoPiezaCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Agregar Pieza");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPieza();
            }
        });
        add(addButton, gbc);

        gbc.gridy = 7;
        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> mainFrame.showPanel("login"));
        add(backButton, gbc);
    }

    private void agregarPieza() {
        String idPieza = piezaIdField.getText();
        String titulo = piezaTituloField.getText();
        int anioCreacion = Integer.parseInt(piezaAnioField.getText());
        String lugarCreacion = piezaLugarField.getText();
        double valor = Double.parseDouble(piezaValorField.getText());
        String estadoPieza = (String) estadoPiezaCombo.getSelectedItem();

        // Validar que todos los campos estén llenos
        if (idPieza.isEmpty() || titulo.isEmpty() || piezaAnioField.getText().isEmpty() || piezaLugarField.getText().isEmpty() || piezaValorField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear una pieza y agregarla al inventario usando el administrador
        Pieza pieza = new Pieza(idPieza, titulo, anioCreacion, lugarCreacion, estadoPieza, true, true, null, valor, 0, 0, new Date(), true, "Descripción");

        admin.agregarPieza(pieza, inventario);

        JOptionPane.showMessageDialog(mainFrame, "Pieza agregada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        // Limpiar los campos después de agregar la pieza
        piezaIdField.setText("");
        piezaTituloField.setText("");
        piezaAnioField.setText("");
        piezaLugarField.setText("");
        piezaValorField.setText("");
        estadoPiezaCombo.setSelectedIndex(0);
    }
}
