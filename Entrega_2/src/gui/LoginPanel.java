package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import galeria.usuarios.ManejoSesion;
import galeria.usuarios.CompradorPropietario;
import galeria.usuarios.Empleado;

public class LoginPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        add(loginButton);

        JButton registerButton = new JButton("Registrarse");
        registerButton.addActionListener(e -> mainFrame.showPanel("register"));
        add(registerButton);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        ManejoSesion.loginEmpleado(username, password);
        Empleado empleado = ManejoSesion.getEmpleadoActual();

        if (empleado != null) {
            switch (empleado.getRole()) {
                case "Administrador":
                    mainFrame.showPanel("admin");
                    break;
                case "Cajero":
                    mainFrame.showPanel("cajero");
                    break;
                case "Operador":
                    mainFrame.showPanel("operador");
                    break;
                default:
                    JOptionPane.showMessageDialog(mainFrame, "Rol desconocido.", "Error", JOptionPane.ERROR_MESSAGE);
                    ManejoSesion.logout();
                    break;
            }
        } else {
            ManejoSesion.loginCompradorPropietario(username, password);
            CompradorPropietario comprador = ManejoSesion.getCompradorPropietarioActual();
            if (comprador != null) {
                mainFrame.showPanel("comprador");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Credenciales inválidas.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
