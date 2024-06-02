package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

import galeria.inventarios.InventarioGeneral;
import galeria.pieza.Pieza;
import galeria.usuarios.*;
import subasta.Oferta;

public class OperadorPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField idPiezaField;
    private JTextField montoOfertaField;
	private InventarioGeneral inventario;
	private UsuariosRegistrados usuariosDelPrograma;

    public OperadorPanel(MainFrame mainFrame, InventarioGeneral inventario, UsuariosRegistrados usuariosDelPrograma, File archivoUsuarios, File archivoInventario) {
        this.mainFrame = mainFrame;
        this.inventario = inventario;
        this.usuariosDelPrograma = usuariosDelPrograma;
        
        
        
        setLayout(new BorderLayout());

        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("./datos/galeria3.jpg");

        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(480, 270, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        imageLabel.setIcon(imageIcon);
        imagePanel.add(imageLabel);
        add(imagePanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 2));
      

        JButton registrarOfertaButton = new JButton("Registrar Oferta");
        registrarOfertaButton.addActionListener(e -> registrarOferta());
        buttonPanel.add(registrarOfertaButton);

        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> mainFrame.showPanel("login"));
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void registrarOferta() {
        String idPieza = pedirCadenaAlUsuario("Ingrese el ID de la pieza:");
        Pieza pieza = inventario.getPiezaInventarioBodega(idPieza); 
        if (pieza == null) pieza = inventario.getPiezaInventarioExhibido(idPieza);
        CompradorPropietario comprador = usuariosDelPrograma.getCompradorporid(pedirCadenaAlUsuario("Ingrese el ID del comprador:"), usuariosDelPrograma.getCompradoresEnPrograma());
        
        int dinero = pedirEnteroAlUsuario("Ingrese el monto de la oferta:");
        
        Oferta oferta = new Oferta(comprador.getIdUsuario(), idPieza, dinero);
        
        // Aquí puedes agregar la lógica para registrar la oferta en tu sistema
        // Por ejemplo, podrías agregarla a una lista de ofertas o procesarla de alguna otra manera
        
        System.out.println("Oferta registrada exitosamente.");
    }

    // Método para solicitar una cadena de texto al usuario
    private String pedirCadenaAlUsuario(String mensaje) {
        return JOptionPane.showInputDialog(mainFrame, mensaje);
    }

    // Método para solicitar un entero al usuario
    private int pedirEnteroAlUsuario(String mensaje) {
        String input = JOptionPane.showInputDialog(mainFrame, mensaje);
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame, "Por favor ingrese un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return pedirEnteroAlUsuario(mensaje);
        }
    }
}

