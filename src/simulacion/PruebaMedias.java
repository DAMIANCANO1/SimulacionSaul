package simulacion;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PruebaMedias {
    public static String ejecutar(ArrayList<Double> datos, int opcion) {
        if (datos == null || datos.isEmpty()) {
            return "No hay datos para realizar la prueba.";
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
        resultado.append("\nLímite inferior = ").append(formato.format(limiteInferior));
        resultado.append("\nLímite superior = ").append(formato.format(limiteSuperior));
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
}
