package simulacion;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PruebaHuecos {
    public static String ejecutar(ArrayList<Double> datos, double alfa, double beta, int opcion) {
        if (datos == null || datos.size() < 3) {
            return "Se necesitan al menos 3 datos.";
        }
        if (alfa < 0 || beta > 1 || alfa >= beta) {
            return "El intervalo debe cumplir 0 <= alfa < beta <= 1.";
        }
        StringBuilder secuencia = new StringBuilder();
        for (int i = 0; i < datos.size(); i++) {
            double numero = datos.get(i);
            if (numero < 0 || numero > 1) {
                return "Todos los datos deben estar entre 0 y 1.";
            }
            if (numero > alfa && numero < beta) {
                secuencia.append("1");
            } else {
                secuencia.append("0");
            }
        }
        ArrayList<Integer> huecos = obtenerHuecos(secuencia);
        if (huecos.isEmpty()) {
            return "No se encontraron dos números dentro del intervalo.";
        }
        int[] observados = new int[6];
        for (int i = 0; i < huecos.size(); i++) {
            int cantidad = huecos.get(i);
            if (cantidad >= 5) observados[5]++;
            else observados[cantidad]++;
        }
        double probabilidad = beta - alfa;
        double cero = 1 - probabilidad;
        double calculada = 0.0;
        DecimalFormat formato = new DecimalFormat("0.000000");
        StringBuilder tabla = new StringBuilder();
        tabla.append("Tamaño\tOi\tEi\tTérmino\n");
        for (int i = 0; i < 6; i++) {
            double esperado = huecos.size() * probabilidad;
            if (i == 5) {
                esperado = huecos.size();
                for (int j = 0; j < 5; j++) esperado *= cero;
            } else {
                for (int j = 0; j < i; j++) esperado *= cero;
            }
            double termino = (observados[i] - esperado) * (observados[i] - esperado) / esperado;
            calculada += termino;
            if (i < 5) tabla.append(i);
            else tabla.append("5 o más");
            tabla.append("\t").append(observados[i]).append("\t")
                    .append(formato.format(esperado)).append("\t")
                    .append(formato.format(termino)).append("\n");
        }
        int[] columnas = {0, 1, 2, 4};
        double critica = TablasEstadisticas.obtenerChiCuadrada(5, columnas[opcion]);
        String[] niveles = {"80%", "90%", "95%", "99%"};
        StringBuilder texto = new StringBuilder();
        texto.append("PRUEBA DE HUECOS\n\nDatos utilizados:\n");
        texto.append(PruebaMedias.formatoLista(datos, formato));
        texto.append("\nIntervalo abierto = (").append(formato.format(alfa)).append(", ")
                .append(formato.format(beta)).append(")");
        texto.append("\nProbabilidad p = ").append(formato.format(probabilidad));
        texto.append("\nSecuencia: ").append(secuencia);
        texto.append("\nHuecos encontrados: ").append(huecos.size());
        texto.append("\n").append(tabla);
        texto.append("\nGrados de libertad = 5");
        texto.append("\nNivel = ").append(niveles[opcion]);
        texto.append("\nChi calculada = ").append(formato.format(calculada));
        texto.append("\nChi crítica = ").append(formato.format(critica));
        texto.append("\n\nH0: los números son independientes.");
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
}
