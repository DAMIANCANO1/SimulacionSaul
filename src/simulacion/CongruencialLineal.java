package simulacion;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class CongruencialLineal {
    public static DatosGenerados generar() {
        String textoX = JOptionPane.showInputDialog(null, "Ingrese X0");
        if (textoX == null) {
            return null;
        }
        String textoA = JOptionPane.showInputDialog(null, "Ingrese a");
        if (textoA == null) {
            return null;
        }
        String textoC = JOptionPane.showInputDialog(null, "Ingrese c");
        if (textoC == null) {
            return null;
        }
        String textoM = JOptionPane.showInputDialog(null, "Ingrese m (mayor que 1)");
        if (textoM == null) {
            return null;
        }
        long x;
        long a;
        long c;
        long m;
        try {
            x = Long.parseLong(textoX.trim());
            a = Long.parseLong(textoA.trim());
            c = Long.parseLong(textoC.trim());
            m = Long.parseLong(textoM.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Todas las entradas deben ser enteros.");
            return null;
        }
        if (x < 0 || a < 0 || c < 0 || m <= 1 || x >= m) {
            JOptionPane.showMessageDialog(null, "X0, a y c no pueden ser negativos; m debe ser mayor que 1 y X0 menor que m.");
            return null;
        }
        if (a > (Long.MAX_VALUE - c) / Math.max(1, m - 1)) {
            JOptionPane.showMessageDialog(null, "Los valores son demasiado grandes para realizar la operación segura.");
            return null;
        }
        a = a % m;
        c = c % m;
        ArrayList<Double> valores = new ArrayList<Double>();
        ArrayList<Long> enteros = new ArrayList<Long>();
        StringBuilder detalle = new StringBuilder();
        detalle.append("CONGRUENCIAL LINEAL\n");
        for (int i = 0; i < 10; i++) {
            x = (a * x + c) % m;
            double ri = (double) x / m;
            valores.add(ri);
            enteros.add(x);
            detalle.append("X").append(i + 1).append(" = ").append(x)
                    .append("  r = ").append(ri).append("\n");
        }
        return new DatosGenerados(valores, enteros, detalle.toString());
    }
}
