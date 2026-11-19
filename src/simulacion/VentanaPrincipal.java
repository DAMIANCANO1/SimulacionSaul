package simulacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal extends JFrame implements ActionListener {
    private JComboBox<String> selectorGenerador;
    private JComboBox<String> selectorPrueba;
    private JTable tablaNumeros;
    private DefaultTableModel modeloTabla;
    private JTextArea areaCalculos;
    private JButton botonGenerar;
    private JButton botonProbar;
    private JButton botonLimpiar;
    private JButton botonSalir;
    private ArrayList<Double> datosGenerados;
    private ArrayList<Long> enterosGenerados;

    public VentanaPrincipal() {
        super("Simulacion Estadistica");
        datosGenerados = new ArrayList<Double>();
        enterosGenerados = new ArrayList<Long>();
        construirInterfaz();
    }

    private void construirInterfaz() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 680);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        JPanel superior = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        superior.setBackground(new Color(20, 100, 55));
        JLabel etiquetaGenerador = new JLabel("Metodo:");
        JLabel etiquetaPrueba = new JLabel("Prueba:");
        etiquetaGenerador.setForeground(Color.WHITE);
        etiquetaPrueba.setForeground(Color.WHITE);
        selectorGenerador = new JComboBox<String>();
        selectorGenerador.addItem("Congruencial aditivo");
        selectorGenerador.addItem("Congruencial multiplicativo");
        selectorGenerador.addItem("Congruencial cuadratico");
        selectorGenerador.addItem("Productos medios");
        selectorGenerador.addItem("Cuadrados medios");
        selectorGenerador.addItem("Multiplicador constante");
        selectorGenerador.addItem("Congruencial lineal");
        selectorPrueba = new JComboBox<String>();
        selectorPrueba.addItem("Prueba de medias");
        selectorPrueba.addItem("Prueba de varianza");
        selectorPrueba.addItem("Prueba Chi-cuadrada");
        selectorPrueba.addItem("Kolmogorov-Smirnov");
        selectorPrueba.addItem("Corridas arriba y abajo");
        selectorPrueba.addItem("Corridas arriba y abajo de la media");
        selectorPrueba.addItem("Prueba de Poker");
        selectorPrueba.addItem("Prueba de huecos");

        botonGenerar = crearBoton("Generar numeros");
        botonProbar = crearBoton("Ejecutar prueba");
        superior.add(etiquetaGenerador);
        superior.add(selectorGenerador);
        superior.add(botonGenerar);
        superior.add(etiquetaPrueba);
        superior.add(selectorPrueba);
        superior.add(botonProbar);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Iteracion");
        modeloTabla.addColumn("Valor interno");
        modeloTabla.addColumn("ri");
        tablaNumeros = new JTable(modeloTabla);
        tablaNumeros.setRowHeight(22);
        JScrollPane tablaDesplazable = new JScrollPane(tablaNumeros);
        tablaDesplazable.setPreferredSize(new Dimension(380, 520));

        areaCalculos = new JTextArea();
        areaCalculos.setEditable(false);
        areaCalculos.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        areaCalculos.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane calculosDesplazable = new JScrollPane(areaCalculos);

        JPanel inferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        inferior.setBackground(new Color(225, 238, 229));
        botonLimpiar = crearBoton("Limpiar");
        botonSalir = crearBoton("Salir");
        inferior.add(botonLimpiar);
        inferior.add(botonSalir);

        add(superior, BorderLayout.NORTH);
        add(tablaDesplazable, BorderLayout.WEST);
        add(calculosDesplazable, BorderLayout.CENTER);
        add(inferior, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(32, 145, 78));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.addActionListener(this);
        return boton;
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
            return;
        }
        datosGenerados = resultado.getValores();
        enterosGenerados = resultado.getEnteros();
        mostrarDatos(resultado);
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
        }
    }

    private void limpiar() {
        datosGenerados = new ArrayList<Double>();
        enterosGenerados = new ArrayList<Long>();
        modeloTabla.setRowCount(0);
        areaCalculos.setText("");
    }
}
