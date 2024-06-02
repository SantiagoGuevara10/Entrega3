package gui;

import galeria.inventarios.InventarioGeneral;
import galeria.inventarios.PiezaEscultura;
import galeria.inventarios.PiezaFotografia;
import galeria.inventarios.PiezaPintura;
import galeria.inventarios.PiezaVideo;
import galeria.pieza.Pieza;
import galeria.usuarios.CompradorPropietario;
import galeria.usuarios.UsuariosRegistrados;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CompradorPanel extends JPanel {
    private MainFrame mainFrame;
    private CompradorPropietario comprador;
    private InventarioGeneral inventario;
    private UsuariosRegistrados usuariosRegistrados;
    private File archivoUsuarios;
    private File archivoInventario;
    private String username;

    public CompradorPanel(MainFrame mainFrame, InventarioGeneral inventario, UsuariosRegistrados usuariosRegistrados, File archivoUsuarios, File archivoInventario, String username, String password) {
        this.mainFrame = mainFrame;
        this.inventario = inventario;
        this.usuariosRegistrados = usuariosRegistrados;
        this.archivoUsuarios = archivoUsuarios;
        this.archivoInventario = archivoInventario;
        this.username = username;

        setLayout(new BorderLayout());

        // Panel superior para la imagen
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("./datos/galeria1.png");

        // Redimensionar la imagen si es necesario
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(512, 256, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        imageLabel.setIcon(imageIcon);
        imagePanel.add(imageLabel);
        add(imagePanel, BorderLayout.NORTH);
        
        // Panel central para los botones
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton verPiezasButton = new JButton("Ver Piezas");
        verPiezasButton.addActionListener(e -> mostrarPiezas());
        buttonPanel.add(verPiezasButton);

        JButton registrarPiezaButton = new JButton("Registrar Pieza");
        registrarPiezaButton.addActionListener(e -> registrarPieza());
        buttonPanel.add(registrarPiezaButton);

        JButton realizarCompraButton = new JButton("Realizar Compra");
        realizarCompraButton.addActionListener(e -> realizarCompra());
        buttonPanel.add(realizarCompraButton);

        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> mainFrame.showPanel("login"));
        buttonPanel.add(backButton);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(buttonPanel);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void mostrarPiezas() {
        List<CompradorPropietario> compradores = usuariosRegistrados.getCompradoresEnPrograma();
        CompradorPropietario comprador = usuariosRegistrados.getComprador(mainFrame.getUsernameProfile(), compradores);
        if (comprador != null) {
            StringBuilder sb = new StringBuilder();
            for (Pieza pieza : comprador.getPiezas()) {
                sb.append(pieza.getTitulo()).append(" - ").append(pieza.getEstadoPieza()).append("\n");
            }
            JOptionPane.showMessageDialog(mainFrame, sb.toString(), "Piezas", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "No hay un comprador activo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarPieza() {
        List<CompradorPropietario> compradores = usuariosRegistrados.getCompradoresEnPrograma();
        CompradorPropietario comprador = usuariosRegistrados.getComprador(mainFrame.getUsernameProfile(), compradores);
        if (comprador != null) {
            try {
                Random random = new Random();
                List<Integer> numeros = usuariosRegistrados.getAllUserIds();

                int numeroAleatorio;
                do {
                    numeroAleatorio = random.nextInt(1000);
                } while (numeros.contains(numeroAleatorio));

                String idPieza = String.valueOf(numeroAleatorio);
                String titulo = JOptionPane.showInputDialog("Ingrese el título de la pieza");
                int anioCreacion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el año de la creación de la pieza"));
                String lugarCreacion = JOptionPane.showInputDialog("Ingrese el lugar donde la pieza fue creada");
                String estadoPieza = JOptionPane.showInputDialog("Condiciones en las que se encuentra la pieza");
                boolean estaExhibida = false;
                boolean disponibleVenta = false;
                List<String> autores = Arrays.asList(JOptionPane.showInputDialog("Ingrese los autores de la obra y separe por , cada uno").split(","));
                double valorFijo = 0;
                int valorMinimo = 0;
                int valorInicial = 0;
                String descripcion = "";
                Pieza pieza = null;
                int opcion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tipo de pieza: 1.Escultura  2.Fotografía  3.Pintura  4.Video"));

                switch (opcion) {
                    case 1:
                        descripcion = "Escultura";
                        float peso = Integer.parseInt(JOptionPane.showInputDialog("Indique el peso de la pieza"));
                        boolean usaElectricidad = JOptionPane.showConfirmDialog(null, "La escultura usa electricidad?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                        pieza = new PiezaEscultura(idPieza, titulo, anioCreacion, lugarCreacion, estadoPieza, estaExhibida, disponibleVenta, autores, valorFijo, valorMinimo, valorInicial, new Date(), true, descripcion, peso, usaElectricidad);
                        break;
                    case 2:
                        descripcion = "Fotografía";
                        boolean esDigital = JOptionPane.showConfirmDialog(null, "La fotografía es digital?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                        pieza = new PiezaFotografia(idPieza, titulo, anioCreacion, lugarCreacion, estadoPieza, estaExhibida, disponibleVenta, autores, valorFijo, valorMinimo, valorInicial, new Date(), true, descripcion, esDigital);
                        break;
                    case 3:
                        descripcion = "Pintura";
                        int pesoPintura = Integer.parseInt(JOptionPane.showInputDialog("Indique el peso de la pieza"));
                        String tecnica = JOptionPane.showInputDialog("Técnica que tiene la pintura");
                        pieza = new PiezaPintura(idPieza, titulo, anioCreacion, lugarCreacion, estadoPieza, estaExhibida, disponibleVenta, autores, valorFijo, valorMinimo, valorInicial, new Date(), true, descripcion, pesoPintura, tecnica);
                        break;
                    case 4:
                        descripcion = "Video";
                        String calidad = JOptionPane.showInputDialog("Ingrese la calidad del video");
                        int duracion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la duración del video en minutos"));
                        pieza = new PiezaVideo(idPieza, titulo, anioCreacion, lugarCreacion, estadoPieza, estaExhibida, disponibleVenta, autores, valorFijo, valorMinimo, valorInicial, new Date(), true, descripcion, calidad, duracion);
                        break;
                    default:
                        JOptionPane.showMessageDialog(mainFrame, "Opción no válida.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                }

                comprador.agregarPieza(pieza);
                usuariosRegistrados.guardarUsuarios(archivoUsuarios);
                inventario.guardarUsuarios(archivoInventario);

                JOptionPane.showMessageDialog(mainFrame, "Pieza registrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(mainFrame, "Error al registrar la pieza.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "No hay un comprador activo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarCompra() {
        List<CompradorPropietario> compradores = usuariosRegistrados.getCompradoresEnPrograma();
        CompradorPropietario comprador = usuariosRegistrados.getComprador(mainFrame.getUsernameProfile(), compradores);
        if (comprador != null) {
            String idPieza = JOptionPane.showInputDialog("Ingrese el ID de la pieza que desea comprar:");

            Pieza pieza = inventario.getPiezaInventarioBodega(idPieza);
            if (pieza == null) {
                pieza = inventario.getPiezaInventarioExhibido(idPieza);
            }

            if (pieza != null && pieza.getDisponibleVenta()) {
                double precio = pieza.getValorFijo();
                if (comprador.getDinero() >= precio) {
                    comprador.setDinero(comprador.getDinero() - precio);
                    comprador.getPiezas().add(pieza);
                    inventario.removeInventarioBodega(pieza.getIdPieza());
                    inventario.removeInventarioExhibido(pieza.getIdPieza());
                    inventario.addInventarioPasado(pieza.getIdPieza(), pieza);
                    inventario.addInventarioDinero((int) precio);
                    try {
                        usuariosRegistrados.guardarUsuarios(archivoUsuarios);
                        inventario.guardarUsuarios(archivoInventario);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(mainFrame, "Error al guardar la compra.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    JOptionPane.showMessageDialog(mainFrame, "Compra realizada exitosamente. Pieza agregada a tu colección.");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "No tienes suficiente dinero para realizar esta compra.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Pieza no disponible para la venta o no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "No hay un comprador activo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
