package simulacion;

import java.util.ArrayList;

public class CongruencialCuadratico {
    public static DatosGenerados generar(String xTexto, String mTexto, String aTexto, String bTexto, String cTexto) {
        long x;
        long m;
        long a;
        long b;
        long c;
        try {
            x = Long.parseLong(xTexto.trim());
            m = Long.parseLong(mTexto.trim());
            a = Long.parseLong(aTexto.trim());
            b = Long.parseLong(bTexto.trim());
            c = Long.parseLong(cTexto.trim());
        } catch (NumberFormatException e) {
            return new DatosGenerados("Todas las entradas deben ser enteros.");
        }
        if (x < 0 || m < 2 || m > 100000 || a < 0 || b < 0 || c < 1 || x >= m || a % 2 != 0 || c % 2 != 1) {
            return new DatosGenerados("Valores fuera de rango o condiciones incumplidas.");
        }
        a = a % m;
        b = b % m;
        c = c % m;
        ArrayList<Double> valores = new ArrayList<Double>();
        ArrayList<Long> enteros = new ArrayList<Long>();
        StringBuilder detalle = new StringBuilder();
        detalle.append("CONGRUENCIAL CUADRÁTICO\n");
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
