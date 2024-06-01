package gui;

import javax.swing.*;
import java.awt.*;
import galeria.usuarios.*;
import java.util.Map;

public class OperadorPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextArea ofertasArea;
    private JTextField idPiezaField;
    private JTextField montoOfertaField;

    public OperadorPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        ofertasArea = new JTextArea();
        ofertasArea.setEditable(false);
        add(new JScrollPane(ofertasArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("ID de Pieza:"));
        idPiezaField = new JTextField();
        inputPanel.add(idPiezaField);

        inputPanel.add(new JLabel("Monto de Oferta:"));
        montoOfertaField = new JTextField();
        inputPanel.add(montoOfertaField);

        add(inputPanel, BorderLayout.NORTH);

        JButton registrarOfertaButton = new JButton("Registrar Oferta");
        registrarOfertaButton.addActionListener(e -> registrarOferta());
        add(registrarOfertaButton, BorderLayout.SOUTH);

        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> mainFrame.showPanel("login"));
        add(backButton, BorderLayout.NORTH);
    }

    private void registrarOferta() {
        String idPieza = idPiezaField.getText();
        double monto;
        try {
            monto = Double.parseDouble(montoOfertaField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame, "Monto de oferta inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Operador operador = (Operador) ManejoSesion.getEmpleadoActual();
        if (operador != null) {
            operador.getOfertasRegistradas().put(idPieza, monto);
            ofertasArea.append("Oferta registrada para la pieza " + idPieza + " con monto $" + monto + "\n");
        } else {
            JOptionPane.showMessageDialog(mainFrame, "No hay un operador activo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
