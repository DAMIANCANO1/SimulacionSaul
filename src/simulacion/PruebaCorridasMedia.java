package simulacion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PruebaCorridasMedia {
    public static String ejecutar(ArrayList<Double> datos) {
        if (datos == null) {
            return "No hay datos para realizar la prueba.";
        }
        ArrayList<Double> utilizados = new ArrayList<Double>();
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i) < 0 || datos.get(i) > 1) {
                return "Todos los datos deben estar entre 0 y 1.";
            }
            if (datos.get(i) != 0.5) {
                utilizados.add(datos.get(i));
            }
        }
        if (utilizados.size() < 3) {
            return "Se necesitan al menos 3 datos diferentes de 0.5.";
        }
        StringBuilder secuencia = new StringBuilder();
        int n1 = 0;
        int n0 = 0;
        for (int i = 0; i < utilizados.size(); i++) {
            if (utilizados.get(i) > 0.5) {
                secuencia.append("1");
                n1++;
            } else {
                secuencia.append("0");
                n0++;
            }
        }
        if (n1 == 0 || n0 == 0) {
            return "Debe existir al menos un numero arriba y uno abajo de 0.5.";
        }
        int opcion = pedirNivel();
        if (opcion == -1) {
            return null;
        }
        int corridas = 1;
        for (int i = 1; i < secuencia.length(); i++) {
            if (secuencia.charAt(i) != secuencia.charAt(i - 1)) {
                corridas++;
            }
        }
        int n = n1 + n0;
        double media = (2.0 * n1 * n0) / n + 0.5;
        double varianza = (2.0 * n1 * n0 * (2.0 * n1 * n0 - n)) / (n * n * (n - 1));
        double calculada = (corridas - media) / Math.sqrt(varianza);
        double[] criticos = {1.282, 1.440, 1.645, 1.960, 2.576};
        String[] niveles = {"80%", "85%", "90%", "95%", "99%"};
        DecimalFormat formato = new DecimalFormat("0.000000");
        StringBuilder texto = new StringBuilder();
        texto.append("CORRIDAS ARRIBA Y ABAJO DE LA MEDIA\n\nDatos originales:\n");
        texto.append(PruebaMedias.formatoLista(datos, formato));
        texto.append("\nDatos utilizados:\n");
        texto.append(PruebaMedias.formatoLista(utilizados, formato));
        texto.append("\nSecuencia: ").append(secuencia);
        texto.append("\nN1 = ").append(n1).append("  N0 = ").append(n0);
        texto.append("\nCorridas Co = ").append(corridas);
        texto.append("\nMedia = ").append(formato.format(media));
        texto.append("\nVarianza = ").append(formato.format(varianza));
        texto.append("\nZ = ").append(formato.format(calculada));
        texto.append("\nNivel = ").append(niveles[opcion]);
        texto.append("\nLimites = -").append(formato.format(criticos[opcion])).append(" y ")
                .append(formato.format(criticos[opcion]));
        texto.append("\n\nH0: los numeros son independientes.");
        if (calculada >= -criticos[opcion] && calculada <= criticos[opcion]) {
            texto.append("\nNo se rechaza H0.");
        } else {
            texto.append("\nSe rechaza H0.");
        }
        return texto.toString();
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
