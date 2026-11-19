package simulacion;

import java.util.ArrayList;

public class CongruencialAditivo {
    public static DatosGenerados generar(String mTexto, String valoresTexto) {
        long m;
        try {
            m = Long.parseLong(mTexto.trim());
        } catch (NumberFormatException e) {
            return new DatosGenerados("Ingrese un entero válido para m.");
        }
        if (m <= 1) {
            return new DatosGenerados("m debe ser mayor que 1.");
        }
        ArrayList<Long> iniciales = new ArrayList<Long>();
        String[] partes = valoresTexto.split(",");
        for (int i = 0; i < partes.length; i++) {
            String parte = partes[i].trim();
            if (parte.length() == 0) {
                continue;
            }
            long numero;
            try {
                numero = Long.parseLong(parte);
            } catch (NumberFormatException e) {
                return new DatosGenerados("Todos los valores iniciales deben ser enteros.");
            }
            if (numero < 0 || numero >= m) {
                return new DatosGenerados("Cada valor inicial debe estar entre 0 y m - 1.");
            }
            iniciales.add(numero);
        }
        if (iniciales.size() < 5) {
            return new DatosGenerados("Debe ingresar al menos 5 valores separados por comas.");
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
            detalle.append("X").append(i + 1).append(" = ").append(x)
                    .append("  r = ").append(ri).append("\n");
        }
        return new DatosGenerados(valores, enteros, detalle.toString());
    }
}
