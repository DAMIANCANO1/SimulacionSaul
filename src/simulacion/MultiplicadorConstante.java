package simulacion;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class MultiplicadorConstante {
    public static DatosGenerados generar() {
        String textoConstante = JOptionPane.showInputDialog(null, "Ingrese la semilla constante (2 a 9 dígitos)");
        if (textoConstante == null) {
            return null;
        }
        String textoVariable = JOptionPane.showInputDialog(null, "Ingrese la semilla variable (2 a 9 dígitos)");
        if (textoVariable == null) {
            return null;
        }
        String constanteTexto = textoConstante.trim();
        String variableTexto = textoVariable.trim();
        if (!esSemillaValida(constanteTexto) || !esSemillaValida(variableTexto)) {
            JOptionPane.showMessageDialog(null, "Las semillas deben tener de 2 a 9 dígitos y no comenzar con cero.");
            return null;
        }
        if (constanteTexto.length() != variableTexto.length()) {
            JOptionPane.showMessageDialog(null, "Las semillas deben tener la misma longitud.");
            return null;
        }
        long constante = Long.parseLong(constanteTexto);
        long variable = Long.parseLong(variableTexto);
        int d = constanteTexto.length();
        ArrayList<Double> valores = new ArrayList<Double>();
        ArrayList<Long> enteros = new ArrayList<Long>();
        StringBuilder detalle = new StringBuilder();
        detalle.append("MULTIPLICADOR CONSTANTE\n");
        for (int i = 0; i < 10; i++) {
            long multiplicacion = constante * variable;
            String resultado = String.valueOf(multiplicacion);
            while (resultado.length() < d || (resultado.length() - d) % 2 != 0) {
                resultado = "0" + resultado;
            }
            int inicio = (resultado.length() - d) / 2;
            String centrales = resultado.substring(inicio, inicio + d);
            double ri = Double.parseDouble("0." + centrales);
            valores.add(ri);
            enteros.add(variable);
            detalle.append("Iteracion ").append(i + 1).append("\n")
                    .append("Multiplicacion: ").append(constante).append(" * ").append(variable).append(" = ").append(multiplicacion).append("\n")
                    .append("Resultado completo: ").append(resultado).append("\n")
                    .append("Digitos centrales: ").append(centrales).append("\n")
                    .append("ri: ").append(ri).append("\n\n");
            variable = Long.parseLong(centrales);
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
