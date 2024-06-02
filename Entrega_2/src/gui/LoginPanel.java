package gui;

import javax.swing.*;

import galeria.inventarios.InventarioGeneral;
import galeria.usuarios.UsuariosRegistrados;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class LoginPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPanel(MainFrame mainFrame, InventarioGeneral inventario, UsuariosRegistrados usuariosDelPrograma, File archivoUsuarios, File archivoInventario) {
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
        try {
            mainFrame.autenticarUsuario(username, password);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error al autenticar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

