package simulacion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PruebaChiCuadrada {
    private static final int[] GRADOS = {
        1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,
        21,22,23,24,25,26,27,28,29,30,40,50,60,70,80,90,100
    };

    private static final double[][] TABLA = {
        {2.706,3.841,5.024,6.635,7.879,10.828},{4.605,5.991,7.378,9.210,10.597,13.816},
        {6.251,7.815,9.348,11.345,12.838,16.266},{7.779,9.488,11.143,13.277,14.860,18.467},
        {9.236,11.070,12.833,15.086,16.750,20.515},{10.645,12.592,14.449,16.812,18.548,22.458},
        {12.017,14.067,16.013,18.475,20.278,24.322},{13.362,15.507,17.535,20.090,21.955,26.124},
        {14.684,16.919,19.023,21.666,23.589,27.877},{15.987,18.307,20.483,23.209,25.188,29.588},
        {17.275,19.675,21.920,24.725,26.757,31.264},{18.549,21.026,23.337,26.217,28.300,32.909},
        {19.812,22.362,24.736,27.688,29.819,34.528},{21.064,23.685,26.119,29.141,31.319,36.123},
        {22.307,24.996,27.488,30.578,32.801,37.697},{23.542,26.296,28.845,32.000,34.267,39.252},
        {24.769,27.587,30.191,33.409,35.718,40.790},{25.989,28.869,31.526,34.805,37.156,42.312},
        {27.204,30.144,32.852,36.191,38.582,43.820},{28.412,31.410,34.170,37.566,39.997,45.315},
        {29.615,32.671,35.479,38.932,41.401,46.797},{30.813,33.924,36.781,40.289,42.796,48.268},
        {32.007,35.172,38.076,41.638,44.181,49.728},{33.196,36.415,39.364,42.980,45.559,51.179},
        {34.382,37.652,40.646,44.314,46.928,52.620},{35.563,38.885,41.923,45.642,48.290,54.052},
        {36.741,40.113,43.195,46.963,49.645,55.476},{37.916,41.337,44.461,48.278,50.993,56.892},
        {39.087,42.557,45.722,49.588,52.336,58.301},{40.256,43.773,46.979,50.892,53.672,59.703},
        {51.805,55.758,59.342,63.691,66.766,73.402},{63.167,67.505,71.420,76.154,79.490,86.661},
        {74.397,79.082,83.998,88.379,91.952,99.607},{85.527,90.531,95.023,100.425,104.215,112.317},
        {96.578,101.879,106.629,112.325,116.321,124.839},{107.565,113.145,118.136,124.116,128.299,137.708},
        {118.498,124.342,129.561,135.807,140.169,149.449}
    };

    public static String ejecutar(ArrayList<Double> datos) {
        if (datos == null || datos.size() < 2) {
            return "Se necesitan al menos 2 datos.";
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
        int[] columnas = {0, 1, 2, 4};
        String[] niveles = {"80%", "90%", "95%", "99%"};
        int n = datos.size();
        int intervalos = (int) Math.sqrt(n);
        if (intervalos < 2) {
            intervalos = 2;
        }
        int gl = intervalos - 1;
        int fila = buscarFila(gl);
        if (fila == -1) {
            return "No existe una fila para " + gl + " grados de libertad.";
        }
        int[] observados = new int[intervalos];
        for (int i = 0; i < datos.size(); i++) {
            int indice = (int) (datos.get(i) * intervalos);
            if (indice >= intervalos) {
                indice = intervalos - 1;
            }
            observados[indice]++;
        }
        double esperado = (double) n / intervalos;
        double calculada = 0.0;
        DecimalFormat formato = new DecimalFormat("0.000000");
        StringBuilder texto = new StringBuilder();
        texto.append("PRUEBA CHI-CUADRADA\n\nDatos utilizados:\n");
        texto.append(PruebaMedias.formatoLista(datos, formato));
        texto.append("\n\nIntervalo\tOi\tEi\tTermino\n");
        for (int i = 0; i < intervalos; i++) {
            double inicio = (double) i / intervalos;
            double fin = (double) (i + 1) / intervalos;
            double termino = (observados[i] - esperado) * (observados[i] - esperado) / esperado;
            calculada += termino;
            texto.append(formato.format(inicio)).append(" - ").append(formato.format(fin))
                    .append("\t").append(observados[i]).append("\t")
                    .append(formato.format(esperado)).append("\t")
                    .append(formato.format(termino)).append("\n");
        }
        double critica = TABLA[fila][columnas[opcion]];
        texto.append("\nIntervalos = raiz(n) = ").append(intervalos);
        texto.append("\nEsperado = n/intervalos = ").append(formato.format(esperado));
        texto.append("\nGrados de libertad = ").append(gl);
        texto.append("\nNivel = ").append(niveles[opcion]);
        texto.append("\nChi calculada = ").append(formato.format(calculada));
        texto.append("\nChi critica = ").append(formato.format(critica));
        texto.append("\n\nH0: los numeros siguen una distribucion uniforme.");
        if (calculada < critica) {
            texto.append("\nNo se rechaza H0.");
        } else {
            texto.append("\nSe rechaza H0.");
        }
        return texto.toString();
    }

    public static int buscarFila(int gl) {
        for (int i = 0; i < GRADOS.length; i++) {
            if (GRADOS[i] == gl) {
                return i;
            }
        }
        return -1;
    }

    private static int pedirNivel() {
        String texto = JOptionPane.showInputDialog(null,
                "Seleccione el nivel:\n1: 80%\n2: 90%\n3: 95%\n4: 99%");
        if (texto == null) {
            return -1;
        }
        try {
            int opcion = Integer.parseInt(texto.trim());
            if (opcion >= 1 && opcion <= 4) {
                return opcion - 1;
            }
        } catch (NumberFormatException e) {
        }
        JOptionPane.showMessageDialog(null, "Seleccione una opcion entre 1 y 4.");
        return -1;
    }
}
