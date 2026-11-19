package simulacion;

import java.util.ArrayList;

public class DatosGenerados {
    private ArrayList<Double> valores;
    private ArrayList<Long> enteros;
    private String detalle;

    public DatosGenerados(ArrayList<Double> valores, ArrayList<Long> enteros, String detalle) {
        this.valores = valores;
        this.enteros = enteros;
        this.detalle = detalle;
    }

    public ArrayList<Double> getValores() {
        return valores;
    }

    public ArrayList<Long> getEnteros() {
        return enteros;
    }

    public String getDetalle() {
        return detalle;
    }
}
