package gui;

import javax.swing.*;
import java.awt.*;
import galeria.pieza.Pieza;
import galeria.usuarios.CompradorPropietario;
import galeria.usuarios.ManejoSesion;

public class CompradorPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextArea piezasArea;

    public CompradorPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        piezasArea = new JTextArea();
        piezasArea.setEditable(false);
        add(new JScrollPane(piezasArea), BorderLayout.CENTER);

        JButton verPiezasButton = new JButton("Ver Piezas");
        verPiezasButton.addActionListener(e -> mostrarPiezas());
        add(verPiezasButton, BorderLayout.SOUTH);

        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> mainFrame.showPanel("login"));
        add(backButton, BorderLayout.NORTH);
    }

    private void mostrarPiezas() {
        CompradorPropietario comprador = ManejoSesion.getCompradorPropietarioActual();
        if (comprador != null) {
            StringBuilder sb = new StringBuilder();
            for (Pieza pieza : comprador.getPiezas()) {
                sb.append(pieza.getTitulo()).append(" - ").append(pieza.getEstadoPieza()).append("\n");
            }
            piezasArea.setText(sb.toString());
        } else {
            JOptionPane.showMessageDialog(mainFrame, "No hay un comprador activo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
