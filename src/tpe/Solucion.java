package tpe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Solucion {

    protected List<Procesador> procesadores;
    protected HashMap<Procesador, ArrayList<Tarea>> solucion;

    public Solucion(List<Procesador> procesadores) {
        this.solucion = new HashMap<>();
        this.procesadores = new ArrayList<>(procesadores);

        for (Procesador p : procesadores) {
            this.solucion.put(p, new ArrayList<Tarea>());
        }

    }

    //asigna una tarea a un procesador (solucion parcial)
    public void asignarTarea(Procesador p, Tarea t) {
        solucion.get(p).add(t);
    }

    protected int getCantCriticas(Procesador p) {
        int cantidad = 0;
        List<Tarea> tareas = solucion.get(p);
        for (Tarea t : tareas) {
            if (t.Es_critica()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    //calcula el tiempo maximo de ejecucion de la solucion
    public float calcularTiempoTotal(){

        float tiempoMax = 0;
        for(Procesador p : this.procesadores) {

            float tiempoAcumulado = 0;
            List<Tarea> tareas = this.solucion.get(p);
            for (Tarea t : tareas)
                tiempoAcumulado += t.getTiempo_ejecucion();

            if (tiempoAcumulado > tiempoMax)
                tiempoMax = tiempoAcumulado;
        }

        return tiempoMax;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Solucion: ");
        sb.append("\n");
        for (Map.Entry<Procesador, ArrayList<Tarea>> entry : solucion.entrySet()) {
            sb.append("\n");
            Procesador procesador = entry.getKey();
            ArrayList<Tarea> tareas = entry.getValue();
            sb.append("Procesador: ").append(procesador.getId_procesador()).append("\n");
            sb.append("Tareas asignadas:\n");
            for (Tarea tarea : tareas) {
                sb.append("\t").append(tarea.getId_tarea()).append(" (Tiempo: ").append(tarea.getTiempo_ejecucion()).append(", Crítica: ").append(tarea.Es_critica()).append(")\n");
            }

        }
        sb.append("\n");
        sb.append("Tiempo total de la Solucion : ").append(calcularTiempoTotal());
        sb.append("\n");
        return sb.toString();
    }

}
