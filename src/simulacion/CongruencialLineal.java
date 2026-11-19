package simulacion;

import java.util.ArrayList;

public class CongruencialLineal {
    public static DatosGenerados generar(String xTexto, String aTexto, String cTexto, String mTexto) {
        long x;
        long a;
        long c;
        long m;
        try {
            x = Long.parseLong(xTexto.trim());
            a = Long.parseLong(aTexto.trim());
            c = Long.parseLong(cTexto.trim());
            m = Long.parseLong(mTexto.trim());
        } catch (NumberFormatException e) {
            return new DatosGenerados("Todas las entradas deben ser enteros.");
        }
        if (x < 0 || a < 0 || c < 0 || m <= 1 || x >= m) {
            return new DatosGenerados("X0, a y c no pueden ser negativos; m debe ser mayor que 1 y X0 menor que m.");
        }
        if (a > (Long.MAX_VALUE - c) / Math.max(1, m - 1)) {
            return new DatosGenerados("Los valores son demasiado grandes para realizar la operación segura.");
        }
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
