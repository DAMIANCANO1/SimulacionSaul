package simulacion;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class CongruencialAditivo {
    public static DatosGenerados generar() {
        String textoM = JOptionPane.showInputDialog(null, "Ingrese m (mayor que 1)");
        if (textoM == null) {
            return null;
        }
        long m;
        try {
            m = Long.parseLong(textoM.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un entero valido.");
            return null;
        }
        if (m <= 1) {
            JOptionPane.showMessageDialog(null, "m debe ser mayor que 1.");
            return null;
        }
        ArrayList<Long> iniciales = new ArrayList<Long>();
        int contador = 0;
        while (true) {
            String texto = JOptionPane.showInputDialog(null, "Ingrese X" + contador + " o -1 para terminar");
            if (texto == null) {
                return null;
            }
            long numero;
            try {
                numero = Long.parseLong(texto.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un entero valido.");
                continue;
            }
            if (numero == -1) {
                if (iniciales.size() < 5) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar al menos 5 valores.");
                    continue;
                }
                break;
            }
            if (numero >= 0 && numero < m) {
                iniciales.add(numero);
                contador++;
            } else {
                JOptionPane.showMessageDialog(null, "El valor debe estar entre 0 y m - 1.");
            }
        }
        int n = iniciales.size();
        ArrayList<Double> valores = new ArrayList<Double>();
        ArrayList<Long> enteros = new ArrayList<Long>();
        StringBuilder detalle = new StringBuilder();
        detalle.append("CONGRUENCIAL ADITIVO\n");
        for (int i = 0; i < 10; i++) {
            long x = (iniciales.get(iniciales.size() - 1) + iniciales.get(iniciales.size() - n)) % m;
            double ri = (double) x / m;
            valores.add(ri);
            enteros.add(x);
            iniciales.add(x);
            detalle.append("X").append(contador + i).append(" = ").append(x)
                    .append("  r = ").append(ri).append("\n");
        }
        return new DatosGenerados(valores, enteros, detalle.toString());
    }
}
