package gui;

import galeria.inventarios.InventarioGeneral;
import galeria.pieza.Pieza;
import galeria.usuarios.Administrador;
import galeria.usuarios.CompradorPropietario;
import galeria.usuarios.UsuariosRegistrados;
import subasta.Oferta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

public class AdminPanel extends JPanel {
    private MainFrame mainFrame;
    private InventarioGeneral inventario;
    private Administrador admin;
    private UsuariosRegistrados usuarios;

    public AdminPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.inventario = mainFrame.inventario;
        this.usuarios = mainFrame.usuariosDelPrograma;
        this.admin = (Administrador) mainFrame.usuariosDelPrograma.getUsuariosEnPrograma().stream()
                .filter(user -> user instanceof Administrador)
                .findFirst()
                .orElse(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adding buttons for administrative options
        addButton(gbc, "Agregar Pieza", this::agregarPieza, 0);
        addButton(gbc, "Devolver Pieza", this::devolverPieza, 1);
        addButton(gbc, "Verificar Usuario", this::verificarUsuario, 2);
        addButton(gbc, "Registrar Oferta", this::registrarOferta, 3);
        addButton(gbc, "Ver Historia de Compras", this::verHistoriaCompras, 4);
        addButton(gbc, "Calcular Valor de Colección", this::calcularValorColeccion, 5);
        addButton(gbc, "Volver", e -> mainFrame.showPanel("login"), 6);
    }

    private void addButton(GridBagConstraints gbc, String text, ActionListener action, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        JButton button = new JButton(text);
        button.addActionListener(action);
        add(button, gbc);
    }

    private void agregarPieza(ActionEvent e) {
        String idComprador = JOptionPane.showInputDialog(this, "Ingrese el ID del comprador:");
        CompradorPropietario comprador = buscarCompradorPorId(idComprador);

        if (comprador != null) {
            String piezaID = JOptionPane.showInputDialog(this, "Ingrese el ID de la pieza:");
            Pieza pieza = null;

            // Convertir piezaID a int si es necesario
            try {
                int piezaIdInt = Integer.parseInt(piezaID);
                pieza = comprador.getPieza(piezaIdInt);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID de la pieza debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (pieza != null) {
                int valor = JOptionPane.showConfirmDialog(this, "Indique en dónde va a colocar la pieza: Bodega (Sí) o Exhibición (No)");
                if (valor == JOptionPane.YES_OPTION) {
                    inventario.addInventarioBodega(piezaID, pieza);
                } else {
                    inventario.addInventarioExhibido(piezaID, pieza);
                }
                comprador.getPiezas().remove(pieza);

                JOptionPane.showMessageDialog(this, "Pieza con ID " + piezaID + " agregada exitosamente al inventario.");
                guardarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Pieza no encontrada en el inventario del comprador.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Comprador no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void devolverPieza(ActionEvent e) {
        String idPieza = JOptionPane.showInputDialog(this, "Ingrese el ID de la pieza:");
        String idUsuario = JOptionPane.showInputDialog(this, "Ingrese el ID de la persona a la cual se le devuelve la pieza:");
        Pieza pieza = inventario.getPieza(idPieza);

        if (pieza != null) {
            inventario.removeInventarioBodega(idPieza);
            inventario.removeInventarioExhibido(idPieza);
            CompradorPropietario comprador = buscarCompradorPorId(idUsuario);
            if (comprador != null) {
                comprador.agregarPieza(pieza);
                JOptionPane.showMessageDialog(this, "Pieza devuelta y actualizada en el inventario.");
                guardarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Comprador no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pieza no encontrada en el inventario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verificarUsuario(ActionEvent e) {
        String idUsuario = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario a verificar:");
        CompradorPropietario usuario = buscarCompradorPorId(idUsuario);

        if (usuario != null) {
            admin.verificarUsuario(usuario);
            JOptionPane.showMessageDialog(this, "Usuario verificado correctamente.");
            guardarDatos();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarOferta(ActionEvent e) {
        String idPieza = JOptionPane.showInputDialog(this, "Ingrese el ID de la pieza:");
        Pieza pieza = inventario.getPieza(idPieza);

        if (pieza != null) {
            String idComprador = JOptionPane.showInputDialog(this, "Ingrese el ID del comprador:");
            CompradorPropietario comprador = buscarCompradorPorId(idComprador);

            if (comprador != null) {
                int dinero = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el monto de la oferta:"));
                Oferta oferta = new Oferta(comprador.getIdUsuario(), idPieza, dinero);
                JOptionPane.showMessageDialog(this, "Oferta registrada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Comprador no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pieza no encontrada en el inventario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verHistoriaCompras(ActionEvent e) {
        String idUsuario = JOptionPane.showInputDialog(this, "Ingrese el ID del comprador:");
        CompradorPropietario comprador = buscarCompradorPorId(idUsuario);

        if (comprador != null) {
            String historial = admin.verHistoriaCompras(comprador);
            JOptionPane.showMessageDialog(this, "Historia de Compras:\n" + historial);
        } else {
            JOptionPane.showMessageDialog(this, "Comprador no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularValorColeccion(ActionEvent e) {
        String idUsuario = JOptionPane.showInputDialog(this, "Ingrese el ID del comprador:");
        CompradorPropietario comprador = buscarCompradorPorId(idUsuario);

        if (comprador != null) {
            double valorTotal = admin.calcularValorColeccion(comprador);
            JOptionPane.showMessageDialog(this, "El valor total de la colección del comprador es: $" + valorTotal);
        } else {
            JOptionPane.showMessageDialog(this, "Comprador no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private CompradorPropietario buscarCompradorPorId(String id) {
        return usuarios.getCompradoresEnPrograma().stream()
                .filter(comprador -> comprador.getIdUsuario().equals(id))
                .findFirst()
                .orElse(null);
    }

    private void guardarDatos() {
        try {
            usuarios.guardarUsuarios(mainFrame.archivoUsuarios);
            inventario.guardarUsuarios(mainFrame.archivoInventario);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}