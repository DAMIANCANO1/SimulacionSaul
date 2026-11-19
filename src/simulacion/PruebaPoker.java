package simulacion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PruebaPoker {
    public static String ejecutar(ArrayList<Double> datos) {
        if (datos == null || datos.isEmpty()) {
            return "No hay datos para realizar la prueba.";
        }
        int decimales = pedirDecimales();
        if (decimales == -1) {
            return null;
        }
        int opcion = pedirNivel();
        if (opcion == -1) {
            return null;
        }
        String[] categorias = categorias(decimales);
        double[] probabilidades = probabilidades(decimales);
        int[] observados = new int[categorias.length];
        String patron = "0.";
        for (int i = 0; i < decimales; i++) {
            patron += "0";
        }
        DecimalFormat formatoNumero = new DecimalFormat(patron);
        DecimalFormat formato = new DecimalFormat("0.000000");
        StringBuilder detalle = new StringBuilder();
        detalle.append("Clasificacion individual:\n");
        for (int i = 0; i < datos.size(); i++) {
            double numero = datos.get(i);
            if (numero < 0 || numero >= 1) {
                return "Todos los datos deben estar entre 0 y 1.";
            }
            String texto = formatoNumero.format(numero);
            String digitos = texto.substring(2);
            int categoria = clasificar(digitos, decimales);
            observados[categoria]++;
            detalle.append(texto).append(" = ").append(categorias[categoria]).append("\n");
        }
        double calculada = 0.0;
        StringBuilder tabla = new StringBuilder();
        for (int i = 0; i < categorias.length; i++) {
            double esperado = probabilidades[i] * datos.size();
            double termino = (esperado - observados[i]) * (esperado - observados[i]) / esperado;
            calculada += termino;
            tabla.append(categorias[i]).append("\tOi=").append(observados[i])
                    .append("\tEi=").append(formato.format(esperado))
                    .append("\tTermino=").append(formato.format(termino)).append("\n");
        }
        int gl = categorias.length - 1;
        double critica = criticaChi(gl, opcion);
        if (critica == -1) {
            return "No existe una fila para " + gl + " grados de libertad.";
        }
        String[] niveles = {"80%", "90%", "95%", "99%"};
        StringBuilder texto = new StringBuilder();
        texto.append("PRUEBA DE POKER\n\n");
        texto.append(detalle);
        texto.append("\n").append(tabla);
        texto.append("\nDecimales = ").append(decimales);
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

    private static int clasificar(String digitos, int decimales) {
        int[] cantidades = new int[10];
        for (int i = 0; i < digitos.length(); i++) {
            cantidades[digitos.charAt(i) - '0']++;
        }
        int pares = 0;
        int mayor = 0;
        for (int i = 0; i < cantidades.length; i++) {
            if (cantidades[i] == 2) {
                pares++;
            }
            if (cantidades[i] > mayor) {
                mayor = cantidades[i];
            }
        }
        if (mayor == 1) {
            return 0;
        }
        if (mayor == 2 && pares == 1) {
            return 1;
        }
        if (mayor == 2 && pares == 2) {
            return 2;
        }
        if (mayor == 3 && pares == 0) {
            if (decimales == 3) {
                return 2;
            }
            return 3;
        }
        if (mayor == 3 && pares == 1) {
            return 4;
        }
        if (mayor == 4) {
            if (decimales == 4) {
                return 4;
            }
            return 5;
        }
        if (decimales == 5) {
            return 6;
        }
        return 4;
    }

    private static String[] categorias(int decimales) {
        if (decimales == 3) {
            return new String[]{"Todos diferentes", "Un par", "Tercia"};
        }
        if (decimales == 4) {
            return new String[]{"Todos diferentes", "Un par", "Dos pares", "Tercia", "Poker"};
        }
        return new String[]{"Todos diferentes", "Un par", "Dos pares", "Tercia", "Tercia y par", "Poker", "Quintilla"};
    }

    private static double[] probabilidades(int decimales) {
        if (decimales == 3) {
            return new double[]{0.72, 0.27, 0.01};
        }
        if (decimales == 4) {
            return new double[]{0.5040, 0.4320, 0.0270, 0.0360, 0.0016};
        }
        return new double[]{0.3024, 0.5040, 0.1080, 0.0720, 0.0090, 0.0045, 0.0001};
    }

    private static double criticaChi(int gl, int opcion) {
        if (gl == 2) {
            double[] valores = {3.219, 4.605, 5.991, 9.210};
            return valores[opcion];
        }
        if (gl == 4) {
            double[] valores = {5.989, 7.779, 9.488, 13.277};
            return valores[opcion];
        }
        if (gl == 6) {
            double[] valores = {8.558, 10.645, 12.592, 16.812};
            return valores[opcion];
        }
        return -1;
    }

    private static int pedirDecimales() {
        String texto = JOptionPane.showInputDialog(null,
                "Seleccione decimales:\n1: 3\n2: 4\n3: 5");
        if (texto == null) {
            return -1;
        }
        try {
            int opcion = Integer.parseInt(texto.trim());
            if (opcion == 1) {
                return 3;
            }
            if (opcion == 2) {
                return 4;
            }
            if (opcion == 3) {
                return 5;
            }
        } catch (NumberFormatException e) {
        }
        JOptionPane.showMessageDialog(null, "Seleccione una opcion entre 1 y 3.");
        return -1;
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
