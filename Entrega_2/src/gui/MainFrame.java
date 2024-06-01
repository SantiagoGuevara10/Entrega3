package gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import galeria.inventarios.InventarioGeneral;
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

    private InventarioGeneral inventario;
    private UsuariosRegistrados usuariosDelPrograma;
    private File archivoUsuarios = new File("./datos/Usuarios");
    private File archivoInventario = new File("./datos/Inventario");

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

        // Cargar estado inicial
        try {
            this.inventario = InventarioGeneral.cargarEstado(archivoInventario);
            this.usuariosDelPrograma = UsuariosRegistrados.cargarEstado(archivoUsuarios);
        } catch (IOException | ParseException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el estado inicial.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }

    public void autenticarUsuario(String username, String password) throws IOException {
        boolean autenticado = false;
        String tipoUsuario = "";

        // Read the user data from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoUsuarios))) {
            String line;
            

            
            for (Empleado empleado : usuariosDelPrograma.getUsuariosEnPrograma()) {
                if (empleado.getUsername().equals(username) && empleado.getPasswordHash().equals(password)) {
                    autenticado = true;
                    tipoUsuario = empleado.getRole();
                    break;
                }
            }

            
            if (!autenticado) {
                for (CompradorPropietario comprador : usuariosDelPrograma.getCompradoresEnPrograma()) {
                    if (comprador.getUsername().equals(username) && comprador.getPasswordHash().equals(password)) {
                        autenticado = true;
                        tipoUsuario = "CompradorPropietario";
                        break;
                    }
                }
            }
            } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo de usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (autenticado) {
            JOptionPane.showMessageDialog(this, "Autenticación exitosa. Bienvenido " + tipoUsuario + ".", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Show the relevant panel based on user role
            
            switch (tipoUsuario) {
                case "Administrador":
                    showPanel("admin");
                    break;
                case "Cajero":
                    showPanel("cajero");
                    break;
                case "Comprador":
                    showPanel("comprador");
                    break;
                case "Operador":
                    showPanel("operador");
                    break;
                }
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas o rol incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
