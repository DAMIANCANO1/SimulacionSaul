package simulacion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class PruebaKolmogorov {
    public static String ejecutar(ArrayList<Double> datos, int opcion) {
        if (datos == null || datos.isEmpty()) {
            return "No hay datos para realizar la prueba.";
        }
        ArrayList<Double> ordenados = new ArrayList<Double>(datos);
        Collections.sort(ordenados);
        double dPositiva = 0.0;
        double dNegativa = 0.0;
        DecimalFormat formato = new DecimalFormat("0.000000");
        StringBuilder texto = new StringBuilder();
        texto.append("PRUEBA KOLMOGOROV-SMIRNOV\n\nDatos originales:\n");
        texto.append(PruebaMedias.formatoLista(datos, formato));
        texto.append("\n\ni\ti/n\tri\t(i-1)/n\tD+\tD-\n");
        int n = ordenados.size();
        for (int i = 0; i < n; i++) {
            if (ordenados.get(i) < 0 || ordenados.get(i) > 1) {
                return "Todos los datos deben estar entre 0 y 1.";
            }
            double iSobreN = (double) (i + 1) / n;
            double anteriorSobreN = (double) i / n;
            double dp = iSobreN - ordenados.get(i);
            double dm = ordenados.get(i) - anteriorSobreN;
            if (dp > dPositiva) {
                dPositiva = dp;
            }
            if (dm > dNegativa) {
                dNegativa = dm;
            }
            texto.append(i + 1).append("\t").append(formato.format(iSobreN)).append("\t")
                    .append(formato.format(ordenados.get(i))).append("\t")
                    .append(formato.format(anteriorSobreN)).append("\t")
                    .append(formato.format(dp)).append("\t").append(formato.format(dm)).append("\n");
        }
        double calculada = dPositiva;
        if (dNegativa > calculada) {
            calculada = dNegativa;
        }
        double critica = obtenerCritica(n, opcion);
        String[] niveles = {"80%", "85%", "90%", "95%", "99%"};
        texto.append("\nD+ = ").append(formato.format(dPositiva));
        texto.append("\nD- = ").append(formato.format(dNegativa));
        texto.append("\nD calculada = ").append(formato.format(calculada));
        texto.append("\nNivel = ").append(niveles[opcion]);
        texto.append("\nD crítica = ").append(formato.format(critica));
        texto.append("\n\nH0: los números siguen una distribución uniforme.");
        if (calculada >= critica) {
            texto.append("\nSe rechaza H0.");
        } else {
            texto.append("\nNo se rechaza H0.");
        }
        return texto.toString();
    }

    private static double obtenerCritica(int n, int opcion) {
        double[] constantes = {1.07, 1.14, 1.22, 1.36, 1.63};
        return constantes[opcion] / Math.sqrt(n);
    }
}
