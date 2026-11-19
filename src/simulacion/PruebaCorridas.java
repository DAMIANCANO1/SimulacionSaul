package simulacion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PruebaCorridas {
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
        StringBuilder secuencia = new StringBuilder();
        int corridas = 1;
        for (int i = 1; i < datos.size(); i++) {
            int actual = datos.get(i) > datos.get(i - 1) ? 1 : 0;
            secuencia.append(actual);
            if (i > 1 && secuencia.charAt(i - 1) != secuencia.charAt(i - 2)) {
                corridas++;
            }
        }
        int n = datos.size();
        double media = (2.0 * n - 1) / 3.0;
        double varianza = (16.0 * n - 29) / 90.0;
        double calculada = Math.abs(corridas - media) / Math.sqrt(varianza);
        double[] criticos = {1.282, 1.440, 1.645, 1.960, 2.576};
        String[] niveles = {"80%", "85%", "90%", "95%", "99%"};
        DecimalFormat formato = new DecimalFormat("0.000000");
        StringBuilder texto = new StringBuilder();
        texto.append("CORRIDAS ARRIBA Y ABAJO\n\nDatos originales:\n");
        texto.append(PruebaMedias.formatoLista(datos, formato));
        texto.append("\nSecuencia: ").append(secuencia);
        texto.append("\nCorridas Co = ").append(corridas);
        texto.append("\nMedia Mco = (2n-1)/3 = ").append(formato.format(media));
        texto.append("\nVarianza = (16n-29)/90 = ").append(formato.format(varianza));
        texto.append("\nZ calculada = ").append(formato.format(calculada));
        texto.append("\nNivel = ").append(niveles[opcion]);
        texto.append("\nZ critica = ").append(formato.format(criticos[opcion]));
        texto.append("\n\nH0: los numeros son independientes.");
        if (calculada < criticos[opcion]) {
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
