package simulacion;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class CongruencialCuadratico {
    public static DatosGenerados generar() {
        String textoX = JOptionPane.showInputDialog(null, "Ingrese X0");
        if (textoX == null) {
            return null;
        }
        String textoM = JOptionPane.showInputDialog(null, "Ingrese m entre 2 y 100000");
        if (textoM == null) {
            return null;
        }
        String textoA = JOptionPane.showInputDialog(null, "Ingrese a par y no negativo");
        if (textoA == null) {
            return null;
        }
        String textoB = JOptionPane.showInputDialog(null, "Ingrese b no negativo");
        if (textoB == null) {
            return null;
        }
        String textoC = JOptionPane.showInputDialog(null, "Ingrese c impar y mayor o igual que 1");
        if (textoC == null) {
            return null;
        }
        long x;
        long m;
        long a;
        long b;
        long c;
        try {
            x = Long.parseLong(textoX.trim());
            m = Long.parseLong(textoM.trim());
            a = Long.parseLong(textoA.trim());
            b = Long.parseLong(textoB.trim());
            c = Long.parseLong(textoC.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Todas las entradas deben ser enteros.");
            return null;
        }
        if (x < 0 || m < 2 || m > 100000 || a < 0 || b < 0 || c < 1 || x >= m || a % 2 != 0 || c % 2 != 1) {
            JOptionPane.showMessageDialog(null, "Valores fuera de rango o condiciones incumplidas.");
            return null;
        }
        ArrayList<Double> valores = new ArrayList<Double>();
        ArrayList<Long> enteros = new ArrayList<Long>();
        StringBuilder detalle = new StringBuilder();
        detalle.append("CONGRUENCIAL CUADRATICO\n");
        for (int i = 0; i < m; i++) {
            x = (a * x * x + b * x + c) % m;
            double ri = (double) x / m;
            valores.add(ri);
            enteros.add(x);
            detalle.append("X").append(i + 1).append(" = ").append(x)
                    .append("  r = ").append(ri).append("\n");
        }
        detalle.append("Datos generados: ").append(valores.size()).append("\n");
        return new DatosGenerados(valores, enteros, detalle.toString());
    }
}
