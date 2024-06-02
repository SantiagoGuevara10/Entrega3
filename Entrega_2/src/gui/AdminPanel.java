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
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AdminPanel extends JPanel {
    private MainFrame mainFrame;
    private InventarioGeneral inventario;
    private Administrador admin;
    private UsuariosRegistrados usuarios;
	private File archivoUsuarios;
	private File archivoInventario;

    public AdminPanel(MainFrame mainFrame, InventarioGeneral inventario, UsuariosRegistrados usuariosDelPrograma, File archivoUsuarios, File archivoInventario) {
        this.mainFrame = mainFrame;
        this.inventario = mainFrame.inventario;
        this.usuarios = mainFrame.usuariosDelPrograma;
        this.archivoUsuarios = archivoUsuarios;
        this.archivoInventario = archivoInventario;
        this.admin = (Administrador) mainFrame.usuariosDelPrograma.getUsuariosEnPrograma().stream()
        
                .filter(user -> user instanceof Administrador)
                .findFirst()
                .orElse(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adding buttons for administrative options
        addButton(gbc, "Agregar Pieza", e -> {
			try {
				agregarPieza(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}, 0);
        addButton(gbc, "Devolver Pieza", e -> {
			try {
				devolverPieza(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}, 1);
        addButton(gbc, "Verificar Usuario", e -> {
			try {
				verificarUsuario(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}, 2);
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

    private void agregarPieza(ActionEvent e) throws IOException {
        String idComprador = JOptionPane.showInputDialog(this, "Ingrese el ID del comprador:");
        if (idComprador == null || idComprador.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID del comprador no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        CompradorPropietario comprador = buscarCompradorPorId(idComprador);
        if (comprador == null) {
            JOptionPane.showMessageDialog(this, "Comprador no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String piezaID = JOptionPane.showInputDialog(this, "Ingrese el ID de la pieza:");
        if (piezaID == null || piezaID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID de la pieza no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Pieza pieza = null;
        try {
            pieza = comprador.sacarPieza(piezaID);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID de la pieza debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pieza == null) {
            JOptionPane.showMessageDialog(this, "Pieza no encontrada en el inventario del comprador.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int valor = JOptionPane.showConfirmDialog(this, "Indique en dónde va a colocar la pieza: Bodega (Sí) o Exhibición (No)");
        if (valor == JOptionPane.YES_OPTION) {
            inventario.addInventarioBodega(piezaID, pieza);
        } else {
            inventario.addInventarioExhibido(piezaID, pieza);
        }
        comprador.getPiezas().remove(pieza);

        JOptionPane.showMessageDialog(this, "Pieza con ID " + piezaID + " agregada exitosamente al inventario.");
        usuarios.guardarUsuarios(archivoUsuarios);
        inventario.guardarUsuarios(archivoInventario);
    }

    private void devolverPieza(ActionEvent e) throws IOException {
        String idPieza = JOptionPane.showInputDialog(this, "Ingrese el ID de la pieza:");
        if (idPieza == null || idPieza.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID de la pieza no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String idUsuario = JOptionPane.showInputDialog(this, "Ingrese el ID de la persona a la cual se le devuelve la pieza:");
        if (idUsuario == null || idUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID del usuario no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Pieza pieza = inventario.getPieza(idPieza);
        if (pieza == null) {
            JOptionPane.showMessageDialog(this, "Pieza no encontrada en el inventario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        inventario.removeInventarioBodega(idPieza);
        inventario.removeInventarioExhibido(idPieza);
        CompradorPropietario comprador = buscarCompradorPorId(idUsuario);
        if (comprador == null) {
            JOptionPane.showMessageDialog(this, "Comprador no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        comprador.agregarPieza(pieza);

        JOptionPane.showMessageDialog(this, "Pieza devuelta y actualizada en el inventario.");
        usuarios.guardarUsuarios(archivoUsuarios);
        inventario.guardarUsuarios(archivoInventario);
    }

    private void verificarUsuario(ActionEvent e) throws IOException {
        String idUsuario = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario a verificar:");
        if (idUsuario == null || idUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID del usuario no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CompradorPropietario usuario = buscarCompradorPorId(idUsuario);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        admin.verificarUsuario(usuario);
        JOptionPane.showMessageDialog(this, "Usuario verificado correctamente.");
        usuarios.guardarUsuarios(archivoUsuarios);
        inventario.guardarUsuarios(archivoInventario);
    }

    private void registrarOferta(ActionEvent e) {
        String idPieza = JOptionPane.showInputDialog(this, "Ingrese el ID de la pieza:");
        if (idPieza == null || idPieza.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID de la pieza no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Pieza pieza = inventario.getPieza(idPieza);
        if (pieza == null) {
            JOptionPane.showMessageDialog(this, "Pieza no encontrada en el inventario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String idComprador = JOptionPane.showInputDialog(this, "Ingrese el ID del comprador:");
        if (idComprador == null || idComprador.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID del comprador no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CompradorPropietario comprador = buscarCompradorPorId(idComprador);
        if (comprador == null) {
            JOptionPane.showMessageDialog(this, "Comprador no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int dinero = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el monto de la oferta:"));
            Oferta oferta = new Oferta(comprador.getIdUsuario(), idPieza, dinero);
            JOptionPane.showMessageDialog(this, "Oferta registrada exitosamente.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El monto de la oferta debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verHistoriaCompras(ActionEvent e) {
        String idUsuario = JOptionPane.showInputDialog(this, "Ingrese el ID del comprador:");
        if (idUsuario == null || idUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID del comprador no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CompradorPropietario comprador = buscarCompradorPorId(idUsuario);
        if (comprador == null) {
            JOptionPane.showMessageDialog(this, "Comprador no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String historial = admin.verHistoriaCompras(comprador);
        JOptionPane.showMessageDialog(this, "Historia de Compras:\n" + historial);
    }

    private void calcularValorColeccion(ActionEvent e) {
        String idUsuario = JOptionPane.showInputDialog(this, "Ingrese el ID del comprador:");
        if (idUsuario == null || idUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID del comprador no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CompradorPropietario comprador = buscarCompradorPorId(idUsuario);
        if (comprador == null) {
            JOptionPane.showMessageDialog(this, "Comprador no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int valor = (int) admin.calcularValorColeccion(comprador);
        JOptionPane.showMessageDialog(this, "El valor de la colección del comprador es: " + valor);
    }

    private CompradorPropietario buscarCompradorPorId(String idComprador) {
        List<CompradorPropietario> listaCompradores = usuarios.getCompradoresEnPrograma();
        for (CompradorPropietario comprador : listaCompradores) {
            if (comprador.getIdUsuario().equals(idComprador)) {
                return comprador;
            }
        }
        return null;
    }

    
}
