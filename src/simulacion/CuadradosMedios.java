package simulacion;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class CuadradosMedios {
    public static DatosGenerados generar() {
        String texto = JOptionPane.showInputDialog(null, "Ingrese la semilla X0 (2 a 9 dígitos)");
        if (texto == null) {
            return null;
        }
        String semilla = texto.trim();
        if (!esSemillaValida(semilla)) {
            JOptionPane.showMessageDialog(null, "La semilla debe tener de 2 a 9 dígitos y no comenzar con cero.");
            return null;
        }
        long valor = Long.parseLong(semilla);
        int d = semilla.length();
        ArrayList<Double> valores = new ArrayList<Double>();
        ArrayList<Long> enteros = new ArrayList<Long>();
        StringBuilder detalle = new StringBuilder();
        detalle.append("CUADRADOS MEDIOS\n");
        for (int i = 0; i < 10; i++) {
            long cuadrado = valor * valor;
            String resultado = String.valueOf(cuadrado);
            while (resultado.length() < d * 2) {
                resultado = "0" + resultado;
            }
            int inicio = (resultado.length() - d) / 2;
            String centrales = resultado.substring(inicio, inicio + d);
            double ri = Double.parseDouble("0." + centrales);
            valores.add(ri);
            enteros.add(valor);
            detalle.append("Iteracion ").append(i + 1).append("\n")
                    .append("Cuadrado: ").append(cuadrado).append("\n")
                    .append("Resultado completo: ").append(resultado).append("\n")
                    .append("Digitos centrales: ").append(centrales).append("\n")
                    .append("ri: ").append(ri).append("\n\n");
            valor = Long.parseLong(centrales);
        }
        return new DatosGenerados(valores, enteros, detalle.toString());
    }

    private static boolean esSemillaValida(String texto) {
        if (texto.length() < 2 || texto.length() > 9 || texto.charAt(0) == '0') {
            return false;
        }
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) < '0' || texto.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
}
