package simulacion;

import java.util.ArrayList;

public class DatosGenerados {
    private ArrayList<Double> valores;
    private ArrayList<Long> enteros;
    private String detalle;
    private String error;

    public DatosGenerados(ArrayList<Double> valores, ArrayList<Long> enteros, String detalle) {
        this.valores = valores;
        this.enteros = enteros;
        this.detalle = detalle;
        this.error = "";
    }

    public DatosGenerados(String error) {
        this.valores = new ArrayList<Double>();
        this.enteros = new ArrayList<Long>();
        this.detalle = "";
        this.error = error;
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

    public String getError() {
        return error;
    }
}
