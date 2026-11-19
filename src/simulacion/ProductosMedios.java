package simulacion;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProductosMedios {
    public static DatosGenerados generar() {
        String textoUno = JOptionPane.showInputDialog(null, "Ingrese la semilla 1 (2 a 9 dígitos)");
        if (textoUno == null) {
            return null;
        }
        String textoDos = JOptionPane.showInputDialog(null, "Ingrese la semilla 2 (2 a 9 dígitos)");
        if (textoDos == null) {
            return null;
        }
        String uno = textoUno.trim();
        String dos = textoDos.trim();
        if (!esSemillaValida(uno) || !esSemillaValida(dos)) {
            JOptionPane.showMessageDialog(null, "Las semillas deben tener de 2 a 9 dígitos y no comenzar con cero.");
            return null;
        }
        if (uno.length() != dos.length()) {
            JOptionPane.showMessageDialog(null, "Las semillas deben tener la misma longitud.");
            return null;
        }
        long s1 = Long.parseLong(uno);
        long s2 = Long.parseLong(dos);
        int d = uno.length();
        ArrayList<Double> valores = new ArrayList<Double>();
        ArrayList<Long> enteros = new ArrayList<Long>();
        StringBuilder detalle = new StringBuilder();
        detalle.append("PRODUCTOS MEDIOS\n");
        for (int i = 0; i < 10; i++) {
            long producto = s1 * s2;
            String resultado = String.valueOf(producto);
            while (resultado.length() < d * 2) {
                resultado = "0" + resultado;
            }
            int inicio = (resultado.length() - d) / 2;
            String centrales = resultado.substring(inicio, inicio + d);
            double ri = Double.parseDouble("0." + centrales);
            valores.add(ri);
            enteros.add(s1);
            detalle.append("Iteracion ").append(i + 1).append("\n")
                    .append("Producto: ").append(s1).append(" * ").append(s2).append(" = ").append(producto).append("\n")
                    .append("Resultado completo: ").append(resultado).append("\n")
                    .append("Digitos centrales: ").append(centrales).append("\n")
                    .append("ri: ").append(ri).append("\n\n");
            s1 = s2;
            s2 = Long.parseLong(centrales);
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
