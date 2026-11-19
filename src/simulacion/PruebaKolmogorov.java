package simulacion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

public class PruebaKolmogorov {
    private static final int[] TAMANOS = {
        1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,25,30,35,40,45
    };

    private static final double[][] TABLA = {
        {0.950,0.975,0.995},{0.776,0.842,0.929},{0.642,0.708,0.828},{0.564,0.624,0.733},
        {0.510,0.565,0.669},{0.470,0.521,0.618},{0.438,0.486,0.577},{0.411,0.457,0.543},
        {0.388,0.432,0.514},{0.368,0.410,0.490},{0.352,0.391,0.468},{0.338,0.375,0.450},
        {0.325,0.361,0.433},{0.314,0.349,0.418},{0.304,0.338,0.404},{0.295,0.328,0.392},
        {0.286,0.318,0.381},{0.278,0.309,0.371},{0.272,0.301,0.363},{0.264,0.294,0.356},
        {0.250,0.270,0.320},{0.220,0.240,0.290},{0.210,0.230,0.270},{0.193,0.215,0.258},
        {0.182,0.203,0.243}
    };

    public static String ejecutar(ArrayList<Double> datos) {
        if (datos == null || datos.isEmpty()) {
            return "No hay datos para realizar la prueba.";
        }
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i) < 0 || datos.get(i) > 1) {
                return "Todos los datos deben estar entre 0 y 1.";
            }
        }
        int opcion = pedirNivel();
        if (opcion == -1) {
            return null;
        }
        ArrayList<Double> ordenados = new ArrayList<Double>(datos);
        Collections.sort(ordenados);
        int n = ordenados.size();
        double dPositiva = 0.0;
        double dNegativa = 0.0;
        DecimalFormat formato = new DecimalFormat("0.000000");
        StringBuilder texto = new StringBuilder();
        texto.append("PRUEBA KOLMOGOROV-SMIRNOV\n\nDatos originales:\n");
        texto.append(PruebaMedias.formatoLista(datos, formato));
        texto.append("\n\ni\ti/n\tri\t(i-1)/n\tD+\tD-\n");
        for (int i = 0; i < n; i++) {
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
        texto.append("\nD critica = ").append(formato.format(critica));
        texto.append("\n\nH0: los numeros siguen una distribucion uniforme.");
        if (calculada >= critica) {
            texto.append("\nSe rechaza H0.");
        } else {
            texto.append("\nNo se rechaza H0.");
        }
        return texto.toString();
    }

    private static double obtenerCritica(int n, int opcion) {
        if (opcion <= 1) {
            double[] constantes = {1.07, 1.14};
            return constantes[opcion] / Math.sqrt(n);
        }
        for (int i = 0; i < TAMANOS.length; i++) {
            if (TAMANOS[i] == n) {
                return TABLA[i][opcion - 2];
            }
        }
        double[] constantes = {1.22, 1.36, 1.63};
        return constantes[opcion - 2] / Math.sqrt(n);
    }

    private static int pedirNivel() {
        String texto = JOptionPane.showInputDialog(null,
                "Seleccione el nivel:\n1: 80%\n2: 85%\n3: 90%\n4: 95%\n5: 99%");
        if (texto == null) {
            return -1;
        }
        try {
            int opcion = Integer.parseInt(texto.trim());
            if (opcion >= 1 && opcion <= 5) {
                return opcion - 1;
            }
        } catch (NumberFormatException e) {
        }
        JOptionPane.showMessageDialog(null, "Seleccione una opcion entre 1 y 5.");
        return -1;
    }
}
