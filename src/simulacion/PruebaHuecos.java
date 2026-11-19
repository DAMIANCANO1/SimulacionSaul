package simulacion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PruebaHuecos {
    public static String ejecutar(ArrayList<Double> datos) {
        if (datos == null || datos.size() < 3) {
            return "Se necesitan al menos 3 datos.";
        }
        double alfa = pedirNumero("Ingrese el limite inferior alfa");
        if (alfa == -2.0) {
            return null;
        }
        double beta = pedirNumero("Ingrese el limite superior beta");
        if (beta == -2.0) {
            return null;
        }
        if (alfa < 0 || beta > 1 || alfa >= beta) {
            return "El intervalo debe cumplir 0 <= alfa < beta <= 1.";
        }
        int opcion = pedirNivel();
        if (opcion == -1) {
            return null;
        }
        StringBuilder secuencia = new StringBuilder();
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i) < 0 || datos.get(i) > 1) {
                return "Todos los datos deben estar entre 0 y 1.";
            }
            if (datos.get(i) > alfa && datos.get(i) < beta) {
                secuencia.append("1");
            } else {
                secuencia.append("0");
            }
        }
        ArrayList<Integer> huecos = obtenerHuecos(secuencia);
        if (huecos.isEmpty()) {
            return "No se encontraron dos unos consecutivos dentro del intervalo.";
        }
        int[] observados = new int[6];
        for (int i = 0; i < huecos.size(); i++) {
            int cantidad = huecos.get(i);
            if (cantidad >= 5) {
                observados[5]++;
            } else {
                observados[cantidad]++;
            }
        }
        double probabilidad = beta - alfa;
        double probabilidadCero = 1 - probabilidad;
        double[] esperados = new double[6];
        double calculada = 0.0;
        DecimalFormat formato = new DecimalFormat("0.000000");
        StringBuilder tabla = new StringBuilder();
        tabla.append("Tamaño\tOi\tEi\tTermino\n");
        for (int i = 0; i < 6; i++) {
            esperados[i] = huecos.size() * probabilidad;
            if (i == 5) {
                esperados[i] = huecos.size();
                for (int j = 0; j < 5; j++) {
                    esperados[i] *= probabilidadCero;
                }
            } else {
                for (int j = 0; j < i; j++) {
                    esperados[i] *= probabilidadCero;
                }
            }
            double termino = (observados[i] - esperados[i]) * (observados[i] - esperados[i]) / esperados[i];
            calculada += termino;
            if (i < 5) {
                tabla.append(i);
            } else {
                tabla.append("5 o más");
            }
            tabla.append("\t").append(observados[i]).append("\t")
                    .append(formato.format(esperados[i])).append("\t")
                    .append(formato.format(termino)).append("\n");
        }
        int gl = 5;
        double critica = criticaChi(gl, opcion);
        String[] niveles = {"80%", "90%", "95%", "99%"};
        StringBuilder texto = new StringBuilder();
        texto.append("PRUEBA DE HUECOS\n\n");
        texto.append("Datos utilizados:\n");
        texto.append(PruebaMedias.formatoLista(datos, formato));
        texto.append("\nIntervalo abierto = (").append(formato.format(alfa))
                .append(", ").append(formato.format(beta)).append(")");
        texto.append("\nProbabilidad p = beta-alfa = ").append(formato.format(probabilidad));
        texto.append("\nSecuencia: ").append(secuencia);
        texto.append("\nHuecos encontrados: ").append(huecos.size());
        texto.append("\n").append(tabla);
        texto.append("\nGrados de libertad = ").append(gl);
        texto.append("\nNivel = ").append(niveles[opcion]);
        texto.append("\nChi calculada = ").append(formato.format(calculada));
        texto.append("\nChi critica = ").append(formato.format(critica));
        texto.append("\n\nH0: los numeros son independientes.");
        if (calculada < critica) {
            texto.append("\nNo se rechaza H0.");
        } else {
            texto.append("\nSe rechaza H0.");
        }
        return texto.toString();
    }

    private static ArrayList<Integer> obtenerHuecos(StringBuilder secuencia) {
        ArrayList<Integer> huecos = new ArrayList<Integer>();
        int anterior = -1;
        for (int i = 0; i < secuencia.length(); i++) {
            if (secuencia.charAt(i) == '1') {
                if (anterior != -1) {
                    huecos.add(i - anterior - 1);
                }
                anterior = i;
            }
        }
        return huecos;
    }

    private static double pedirNumero(String mensaje) {
        String texto = JOptionPane.showInputDialog(null, mensaje);
        if (texto == null) {
            return -2.0;
        }
        try {
            return Double.parseDouble(texto.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un numero valido.");
            return -2.0;
        }
    }

    private static double criticaChi(int gl, int opcion) {
        double[] valores80 = {7.289};
        double[] valores90 = {9.236};
        double[] valores95 = {11.070};
        double[] valores99 = {15.086};
        if (opcion == 0) {
            return valores80[0];
        }
        if (opcion == 1) {
            return valores90[0];
        }
        if (opcion == 2) {
            return valores95[0];
        }
        return valores99[0];
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
