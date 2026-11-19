package simulacion;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class CongruencialMultiplicativo {
    public static DatosGenerados generar() {
        String textoX = JOptionPane.showInputDialog(null, "Ingrese X mayor que 0");
        if (textoX == null) {
            return null;
        }
        String textoK = JOptionPane.showInputDialog(null, "Ingrese K no negativo");
        if (textoK == null) {
            return null;
        }
        String textoG = JOptionPane.showInputDialog(null, "Ingrese G entre 1 y 30");
        if (textoG == null) {
            return null;
        }
        long x;
        long k;
        int g;
        try {
            x = Long.parseLong(textoX.trim());
            k = Long.parseLong(textoK.trim());
            g = Integer.parseInt(textoG.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Todas las entradas deben ser enteros.");
            return null;
        }
        if (x <= 0 || k < 0 || g < 1 || g > 30) {
            JOptionPane.showMessageDialog(null, "X debe ser mayor que 0, K no negativo y G entre 1 y 30.");
            return null;
        }
        long m = 1L << g;
        if (x >= m) {
            JOptionPane.showMessageDialog(null, "X debe ser menor que m = 2^G.");
            return null;
        }
        if (k > (Long.MAX_VALUE - 3L) / 8L) {
            JOptionPane.showMessageDialog(null, "El valor de K es demasiado grande.");
            return null;
        }
        long a = 3L + 8L * k;
        a = a % m;
        ArrayList<Long> vistos = new ArrayList<Long>();
        ArrayList<Double> valores = new ArrayList<Double>();
        ArrayList<Long> enteros = new ArrayList<Long>();
        StringBuilder detalle = new StringBuilder();
        detalle.append("CONGRUENCIAL MULTIPLICATIVO\n");
        int indice = 0;
        while (true) {
            x = (a * x) % m;
            if (vistos.contains(x)) {
                break;
            }
            vistos.add(x);
            double ri = (double) x / m;
            valores.add(ri);
            enteros.add(x);
            detalle.append("X").append(indice).append(" = ").append(x)
                    .append("  r = ").append(ri).append("\n");
            indice++;
        }
        detalle.append("Datos generados: ").append(valores.size()).append("\n");
        return new DatosGenerados(valores, enteros, detalle.toString());
    }
}
