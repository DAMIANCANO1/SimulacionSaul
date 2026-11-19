package simulacion;

import java.util.ArrayList;

public class CongruencialMultiplicativo {
    public static DatosGenerados generar(String xTexto, String kTexto, String gTexto) {
        long x;
        long k;
        int g;
        try {
            x = Long.parseLong(xTexto.trim());
            k = Long.parseLong(kTexto.trim());
            g = Integer.parseInt(gTexto.trim());
        } catch (NumberFormatException e) {
            return new DatosGenerados("Todas las entradas deben ser enteros.");
        }
        if (x <= 0 || k < 0 || g < 1 || g > 30) {
            return new DatosGenerados("X debe ser mayor que 0, K no negativo y G entre 1 y 30.");
        }
        long m = 1L << g;
        if (x >= m) {
            return new DatosGenerados("X debe ser menor que m = 2^G.");
        }
        if (k > (Long.MAX_VALUE - 3L) / 8L) {
            return new DatosGenerados("El valor de K es demasiado grande.");
        }
        long a = 3L + 8L * k;
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
