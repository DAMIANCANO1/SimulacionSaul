package simulacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal extends JFrame implements ActionListener {
    private JComboBox<String> selectorGenerador;
    private JComboBox<String> selectorPrueba;
    private JTable tablaNumeros;
    private DefaultTableModel modeloTabla;
    private JTextArea areaCalculos;
    private JLabel etiquetaEstado;
    private JButton botonGenerar;
    private JButton botonProbar;
    private JButton botonLimpiar;
    private JButton botonSalir;
    private ArrayList<Double> datosGenerados;
    private ArrayList<Long> enterosGenerados;

    public VentanaPrincipal() {
        super("Simulación Estadística");
        datosGenerados = new ArrayList<Double>();
        enterosGenerados = new ArrayList<Long>();
        construirInterfaz();
    }

    private void construirInterfaz() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1120, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(238, 245, 240));

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearPie(), BorderLayout.SOUTH);
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(new Color(10, 92, 56));
        encabezado.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        JLabel titulo = new JLabel("Laboratorio de Simulación");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel subtitulo = new JLabel("Generadores pseudoaleatorios y pruebas estadísticas");
        subtitulo.setForeground(new Color(210, 238, 221));
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel textos = new JPanel(new BorderLayout());
        textos.setOpaque(false);
        textos.add(titulo, BorderLayout.NORTH);
        textos.add(subtitulo, BorderLayout.SOUTH);
        encabezado.add(textos, BorderLayout.WEST);

        JLabel identificador = new JLabel("SIMULACIÓN");
        identificador.setForeground(new Color(210, 238, 221));
        identificador.setFont(new Font("Segoe UI", Font.BOLD, 14));
        identificador.setHorizontalAlignment(SwingConstants.RIGHT);
        encabezado.add(identificador, BorderLayout.EAST);
        return encabezado;
    }

    private JPanel crearPanelCentral() {
        JPanel central = new JPanel(new GridBagLayout());
        central.setBackground(new Color(238, 245, 240));
        central.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        GridBagConstraints restricciones = new GridBagConstraints();
        restricciones.insets = new Insets(0, 0, 0, 12);
        restricciones.gridx = 0;
        restricciones.gridy = 0;
        restricciones.weightx = 0.48;
        restricciones.weighty = 1.0;
        restricciones.fill = GridBagConstraints.BOTH;
        central.add(crearPanelControl(), restricciones);

        restricciones.insets = new Insets(0, 0, 0, 0);
        restricciones.gridx = 1;
        restricciones.weightx = 0.52;
        central.add(crearPanelResultados(), restricciones);
        return central;
    }

    private JPanel crearPanelControl() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(196, 218, 205)),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)));

        GridBagConstraints restricciones = new GridBagConstraints();
        restricciones.gridx = 0;
        restricciones.gridy = 0;
        restricciones.weightx = 1.0;
        restricciones.fill = GridBagConstraints.HORIZONTAL;

        JLabel tituloDatos = new JLabel("Datos generados");
        tituloDatos.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tituloDatos.setForeground(new Color(21, 81, 50));
        panel.add(tituloDatos, restricciones);

        restricciones.gridy = 1;
        restricciones.insets = new Insets(8, 0, 4, 0);
        panel.add(crearEtiqueta("Método de generación"), restricciones);

        restricciones.gridy = 2;
        restricciones.insets = new Insets(0, 0, 10, 0);
        selectorGenerador = new JComboBox<String>();
        selectorGenerador.addItem("Congruencial aditivo");
        selectorGenerador.addItem("Congruencial multiplicativo");
        selectorGenerador.addItem("Congruencial cuadrático");
        selectorGenerador.addItem("Productos medios");
        selectorGenerador.addItem("Cuadrados medios");
        selectorGenerador.addItem("Multiplicador constante");
        selectorGenerador.addItem("Congruencial lineal");
        selectorGenerador.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(selectorGenerador, restricciones);

        restricciones.gridy = 3;
        restricciones.insets = new Insets(2, 0, 4, 0);
        panel.add(crearEtiqueta("Prueba estadística"), restricciones);

        restricciones.gridy = 4;
        restricciones.insets = new Insets(0, 0, 12, 0);
        selectorPrueba = new JComboBox<String>();
        selectorPrueba.addItem("Prueba de medias");
        selectorPrueba.addItem("Prueba de varianza");
        selectorPrueba.addItem("Prueba Chi-cuadrada");
        selectorPrueba.addItem("Kolmogorov-Smirnov");
        selectorPrueba.addItem("Corridas arriba y abajo");
        selectorPrueba.addItem("Corridas arriba y abajo de la media");
        selectorPrueba.addItem("Prueba de Póker");
        selectorPrueba.addItem("Prueba de huecos");
        selectorPrueba.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(selectorPrueba, restricciones);

        JPanel botonesAccion = new JPanel(new GridBagLayout());
        botonesAccion.setOpaque(false);
        GridBagConstraints botones = new GridBagConstraints();
        botones.gridx = 0;
        botones.gridy = 0;
        botones.weightx = 1.0;
        botones.fill = GridBagConstraints.HORIZONTAL;
        botones.insets = new Insets(0, 0, 0, 6);
        botonGenerar = crearBoton("Generar", new Color(18, 125, 72));
        botonesAccion.add(botonGenerar, botones);

        botones.gridx = 1;
        botones.insets = new Insets(0, 6, 0, 0);
        botonProbar = crearBoton("Ejecutar prueba", new Color(32, 112, 84));
        botonesAccion.add(botonProbar, botones);

        restricciones.gridy = 5;
        restricciones.insets = new Insets(0, 0, 12, 0);
        panel.add(botonesAccion, restricciones);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("#");
        modeloTabla.addColumn("Valor interno");
        modeloTabla.addColumn("ri");
        tablaNumeros = new JTable(modeloTabla);
        tablaNumeros.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaNumeros.setRowHeight(24);
        tablaNumeros.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaNumeros.getTableHeader().setBackground(new Color(221, 235, 227));
        tablaNumeros.getTableHeader().setForeground(new Color(21, 81, 50));
        DefaultTableCellRenderer alineacionCentro = new DefaultTableCellRenderer();
        alineacionCentro.setHorizontalAlignment(SwingConstants.CENTER);
        tablaNumeros.getColumnModel().getColumn(0).setCellRenderer(alineacionCentro);

        JScrollPane desplazamientoTabla = new JScrollPane(tablaNumeros);
        desplazamientoTabla.setBorder(BorderFactory.createLineBorder(new Color(196, 218, 205)));
        restricciones.gridy = 6;
        restricciones.weighty = 1.0;
        restricciones.fill = GridBagConstraints.BOTH;
        restricciones.insets = new Insets(0, 0, 0, 0);
        panel.add(desplazamientoTabla, restricciones);
        return panel;
    }

    private JPanel crearPanelResultados() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(196, 218, 205)),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)));

        JLabel titulo = new JLabel("Detalle de operaciones");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(new Color(21, 81, 50));
        panel.add(titulo, BorderLayout.NORTH);

        areaCalculos = new JTextArea();
        areaCalculos.setEditable(false);
        areaCalculos.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        areaCalculos.setBackground(new Color(248, 251, 249));
        areaCalculos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane desplazamientoCalculos = new JScrollPane(areaCalculos);
        desplazamientoCalculos.setBorder(BorderFactory.createLineBorder(new Color(210, 225, 216)));
        panel.add(desplazamientoCalculos, BorderLayout.CENTER);
        return panel;
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 12));
        etiqueta.setForeground(new Color(65, 92, 76));
        return etiqueta;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(9, 12, 9, 12));
        boton.addActionListener(this);
        return boton;
    }

    private JPanel crearPie() {
        JPanel pie = new JPanel(new BorderLayout());
        pie.setBackground(new Color(225, 238, 229));
        pie.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        etiquetaEstado = new JLabel("Listo. Genere datos para comenzar.");
        etiquetaEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        etiquetaEstado.setForeground(new Color(54, 90, 68));
        pie.add(etiquetaEstado, BorderLayout.WEST);

        JPanel botones = new JPanel();
        botones.setOpaque(false);
        botonLimpiar = crearBoton("Limpiar", new Color(103, 135, 118));
        botonSalir = crearBoton("Salir", new Color(90, 104, 96));
        botones.add(botonLimpiar);
        botones.add(botonSalir);
        pie.add(botones, BorderLayout.EAST);
        return pie;
    }

    public void actionPerformed(ActionEvent evento) {
        Object origen = evento.getSource();
        if (origen == botonGenerar) {
            generarDatos();
        } else if (origen == botonProbar) {
            ejecutarPrueba();
        } else if (origen == botonLimpiar) {
            limpiar();
        } else if (origen == botonSalir) {
            dispose();
        }
    }

    private void generarDatos() {
        DatosGenerados resultado = null;
        int opcion = selectorGenerador.getSelectedIndex();
        if (opcion == 0) {
            resultado = CongruencialAditivo.generar();
        } else if (opcion == 1) {
            resultado = CongruencialMultiplicativo.generar();
        } else if (opcion == 2) {
            resultado = CongruencialCuadratico.generar();
        } else if (opcion == 3) {
            resultado = ProductosMedios.generar();
        } else if (opcion == 4) {
            resultado = CuadradosMedios.generar();
        } else if (opcion == 5) {
            resultado = MultiplicadorConstante.generar();
        } else if (opcion == 6) {
            resultado = CongruencialLineal.generar();
        }
        if (resultado == null) {
            etiquetaEstado.setText("La generación fue cancelada o contiene datos inválidos.");
            return;
        }
        datosGenerados = resultado.getValores();
        enterosGenerados = resultado.getEnteros();
        mostrarDatos(resultado);
        etiquetaEstado.setText(datosGenerados.size() + " datos generados. Listo para pruebas.");
    }

    private void mostrarDatos(DatosGenerados resultado) {
        modeloTabla.setRowCount(0);
        for (int i = 0; i < datosGenerados.size(); i++) {
            String interno = "";
            if (i < enterosGenerados.size()) {
                interno = String.valueOf(enterosGenerados.get(i));
            }
            modeloTabla.addRow(new Object[]{
                i + 1,
                interno,
                String.format(java.util.Locale.US, "%.10f", datosGenerados.get(i))
            });
        }
        areaCalculos.setText(resultado.getDetalle());
        areaCalculos.setCaretPosition(0);
    }

    private void ejecutarPrueba() {
        if (datosGenerados == null || datosGenerados.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Primero debe generar datos.",
                    "Sin datos",
                    JOptionPane.WARNING_MESSAGE);
            etiquetaEstado.setText("No hay datos disponibles para ejecutar una prueba.");
            return;
        }
        String resultado = null;
        int opcion = selectorPrueba.getSelectedIndex();
        if (opcion == 0) {
            resultado = PruebaMedias.ejecutar(datosGenerados);
        } else if (opcion == 1) {
            resultado = PruebaVarianza.ejecutar(datosGenerados);
        } else if (opcion == 2) {
            resultado = PruebaChiCuadrada.ejecutar(datosGenerados);
        } else if (opcion == 3) {
            resultado = PruebaKolmogorov.ejecutar(datosGenerados);
        } else if (opcion == 4) {
            resultado = PruebaCorridas.ejecutar(datosGenerados);
        } else if (opcion == 5) {
            resultado = PruebaCorridasMedia.ejecutar(datosGenerados);
        } else if (opcion == 6) {
            resultado = PruebaPoker.ejecutar(datosGenerados);
        } else if (opcion == 7) {
            resultado = PruebaHuecos.ejecutar(datosGenerados);
        }
        if (resultado != null) {
            areaCalculos.setText(resultado);
            areaCalculos.setCaretPosition(0);
            etiquetaEstado.setText("Prueba completada. Los datos siguen disponibles.");
        } else {
            etiquetaEstado.setText("La prueba fue cancelada.");
        }
    }

    private void limpiar() {
        datosGenerados = new ArrayList<Double>();
        enterosGenerados = new ArrayList<Long>();
        modeloTabla.setRowCount(0);
        areaCalculos.setText("");
        etiquetaEstado.setText("Pantalla limpiada. Genere nuevos datos.");
    }
}
