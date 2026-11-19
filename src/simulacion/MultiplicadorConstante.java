package simulacion;

import java.util.ArrayList;

public class MultiplicadorConstante {
    public static DatosGenerados generar(String constanteTexto, String variableTexto) {
        String constanteCadena = constanteTexto.trim();
        String variableCadena = variableTexto.trim();
        if (!esSemillaValida(constanteCadena) || !esSemillaValida(variableCadena)) {
            return new DatosGenerados("Las semillas deben tener de 2 a 9 dígitos y no comenzar con cero.");
        }
        if (constanteCadena.length() != variableCadena.length()) {
            return new DatosGenerados("Las semillas deben tener la misma longitud.");
        }
        long constante = Long.parseLong(constanteCadena);
        long variable = Long.parseLong(variableCadena);
        int d = constanteCadena.length();
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
            detalle.append("Iteración ").append(i + 1).append("\n")
                    .append("Multiplicación: ").append(constante).append(" × ").append(variable).append(" = ").append(multiplicacion).append("\n")
                    .append("Resultado completo: ").append(resultado).append("\n")
                    .append("Dígitos centrales: ").append(centrales).append("\n")
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
