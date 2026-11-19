package simulacion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PruebaMedias {
    public static String ejecutar(ArrayList<Double> datos) {
        if (datos == null || datos.isEmpty()) {
            return "No hay datos para realizar la prueba.";
        }
        int opcion = pedirNivel();
        if (opcion == -1) {
            return null;
        }
        double[] valoresZ = {1.28, 1.44, 1.64, 1.96, 2.58};
        String[] niveles = {"80%", "85%", "90%", "95%", "99%"};
        double suma = 0.0;
        for (int i = 0; i < datos.size(); i++) {
            suma += datos.get(i);
        }
        double media = suma / datos.size();
        double limiteInferior = 0.5 - valoresZ[opcion] / Math.sqrt(12.0 * datos.size());
        double limiteSuperior = 0.5 + valoresZ[opcion] / Math.sqrt(12.0 * datos.size());
        DecimalFormat formato = new DecimalFormat("0.000000");
        StringBuilder resultado = new StringBuilder();
        resultado.append("PRUEBA DE MEDIAS\n\nDatos utilizados:\n");
        resultado.append(formatoLista(datos, formato));
        resultado.append("\nMedia = suma(ri) / n = ").append(formato.format(media));
        resultado.append("\nLimite inferior = ").append(formato.format(limiteInferior));
        resultado.append("\nLimite superior = ").append(formato.format(limiteSuperior));
        resultado.append("\nNivel de confianza = ").append(niveles[opcion]);
        resultado.append("\nZ = ").append(valoresZ[opcion]);
        resultado.append("\n\nH0: la media poblacional es 0.5.");
        if (media >= limiteInferior && media <= limiteSuperior) {
            resultado.append("\nNo se rechaza H0.");
        } else {
            resultado.append("\nSe rechaza H0.");
        }
        return resultado.toString();
    }

    public static String formatoLista(ArrayList<Double> datos, DecimalFormat formato) {
        StringBuilder texto = new StringBuilder();
        for (int i = 0; i < datos.size(); i++) {
            texto.append(formato.format(datos.get(i)));
            if (i + 1 < datos.size()) {
                texto.append(", ");
            }
        }
        return texto.toString();
    }

    private static int pedirNivel() {
        String texto = JOptionPane.showInputDialog(null,
                "Seleccione el nivel de confianza:\n1: 80%\n2: 85%\n3: 90%\n4: 95%\n5: 99%");
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
