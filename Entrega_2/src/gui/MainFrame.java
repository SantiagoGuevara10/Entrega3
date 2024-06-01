package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import galeria.usuarios.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private LoginPanel loginPanel;
    private AdminPanel adminPanel;
    private CajeroPanel cajeroPanel;
    private CompradorPanel compradorPanel;
    private OperadorPanel operadorPanel;
    private RegisterPanel registerPanel;

    public MainFrame() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel(this);
        adminPanel = new AdminPanel(this);
        cajeroPanel = new CajeroPanel(this);
        compradorPanel = new CompradorPanel(this);
        operadorPanel = new OperadorPanel(this);
        registerPanel = new RegisterPanel(this);

        cardPanel.add(loginPanel, "login");
        cardPanel.add(adminPanel, "admin");
        cardPanel.add(cajeroPanel, "cajero");
        cardPanel.add(compradorPanel, "comprador");
        cardPanel.add(operadorPanel, "operador");
        cardPanel.add(registerPanel, "register");

        add(cardPanel);
        setTitle("Galería de Arte");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cargar usuarios registrados al inicio
        cargarUsuariosRegistrados();
    }

    private void cargarUsuariosRegistrados() {
        File archivo = new File("usuarios.dat");
        if (archivo.exists()) {
            try {
                UsuariosRegistrados usuariosRegistrados = UsuariosRegistrados.cargarEstado(archivo);
                ManejoSesion.setUsuariosRegistrados(usuariosRegistrados);
            } catch (IOException | ParseException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar usuarios registrados.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
