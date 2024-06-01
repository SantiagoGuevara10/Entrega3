package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import galeria.usuarios.*;

public class RegisterPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField roleField;
    private JTextField nombreField;
    private JTextField idField;

    public RegisterPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        add(nombreField);

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Role (Administrador/Cajero/Operador):"));
        roleField = new JTextField();
        add(roleField);

        JButton registerButton = new JButton("Registrar");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        add(registerButton);

        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> mainFrame.showPanel("login"));
        add(backButton);
    }

    private void registerUser() {
        String id = idField.getText();
        String nombre = nombreField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = roleField.getText();

        if (id.isEmpty() || nombre.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            UsuariosRegistrados usuariosRegistrados = ManejoSesion.getUsuariosRegistrados();
            if (usuariosRegistrados == null) {
                usuariosRegistrados = new UsuariosRegistrados();
                ManejoSesion.setUsuariosRegistrados(usuariosRegistrados);
            }
            if (role.equalsIgnoreCase("Administrador")) {
                usuariosRegistrados.addUsuario(new Administrador(id, nombre, username, password, role));
            } else if (role.equalsIgnoreCase("Cajero")) {
                usuariosRegistrados.addUsuario(new Cajero(id, nombre, username, password, role));
            } else if (role.equalsIgnoreCase("Operador")) {
                usuariosRegistrados.addUsuario(new Operador(id, nombre, username, password, role));
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Rol no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            File archivo = new File("usuarios.dat");
            usuariosRegistrados.guardarUsuarios(archivo);
            JOptionPane.showMessageDialog(mainFrame, "Usuario registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            mainFrame.showPanel("login");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error al registrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
