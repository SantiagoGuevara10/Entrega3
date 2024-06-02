package gui;

import javax.swing.*;

import consolas.ConsolaAdministrador;
import consolas.ConsolaCajero;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import galeria.inventarios.InventarioGeneral;
import galeria.pieza.Pieza;
import galeria.usuarios.*;

public class RegisterPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField roleField;
    private JTextField nombreField;
    private JTextField idField;

    public RegisterPanel(MainFrame mainFrame, InventarioGeneral inventario, UsuariosRegistrados usuariosDelPrograma, File archivoUsuarios, File archivoInventario) {
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

        add(new JLabel("Role (Administrador/Cajero/Operador/CompradorPropietario):"));
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
            UsuariosRegistrados usuariosRegistrados = mainFrame.usuariosDelPrograma;
            
                switch (role) {
                case "Administrador":
                	Administrador admin = new Administrador(id, nombre, username, password, role);
                	mainFrame.usuariosDelPrograma.addUsuario(admin);
                    break;
                case "Cajero":
                	Cajero cajero = new Cajero(id, nombre, username, password, role);
                	mainFrame.usuariosDelPrograma.addUsuario(cajero);
                    break;
                case "CompradorPropietario":
                	CompradorPropietario comprador = new CompradorPropietario(id, nombre, username, password, "", 0, false, null, null);
                	mainFrame.usuariosDelPrograma.addComprador(comprador);
                    break;
                case "Operador":
                	Operador operador = new Operador(id, nombre, username, password, role);
                	mainFrame.usuariosDelPrograma.addUsuario(operador);
                    break;  
                }
            
       
        mainFrame.usuariosDelPrograma.guardarUsuarios(mainFrame.archivoUsuarios);

            JOptionPane.showMessageDialog(mainFrame, "Usuario registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            switch (role) {
            case "Administrador":
            	mainFrame.showPanel("admin");
                break;
            case "Cajero":
            	mainFrame.showPanel("cajero");
                break;
            case "Operador":
            	mainFrame.showPanel("operador");
                break;
                
            case "CompradorPropietario":
            	mainFrame.showPanel("comprador");
            	break;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error al registrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

