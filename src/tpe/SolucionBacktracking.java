package tpe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* Primero, ningún procesador podrá ejecutar más de 2 tareas críticas.
*Segundo, los procesadores no refrigerados no podrán dedicar más de X tiempo de ejecución a
las tareas asignadas. El tiempo X será un parámetro establecido por el usuario al momento de
solicitar la asignación de las tareas a los procesadores.

*
* */
public class SolucionBacktracking {

    private List<Procesador> procesadores;
    private HashMap<Procesador, ArrayList<Tarea>> solucion;


    public SolucionBacktracking(List<Procesador> procesadores) {
        this.solucion = new HashMap<>();
        this.procesadores = new ArrayList<>(procesadores);

        for (Procesador p : procesadores) {
            this.solucion.put(p, new ArrayList<Tarea>());
        }

    }


    //asigna una tarea a un procesador (solucion parcial)
    public void asignarTarea(Procesador p, Tarea t, int x) {
        // Si ese procesador ya tenia una lista de tareas asignadas, y es asignable, le agrego la tarea
        if (esAsignable(p, t, x)) {
            solucion.get(p).add(t);
        }
    }

    //quita una tarea a un procesador (solucion parcial)
    public void removerTarea(Procesador p, Tarea t) {
        this.solucion.get(p).remove(t);
    }

    //calcula el tiempo maximo de ejecucion de la solucion
    public float calcularTiempoTotal() {
        float tiempoTotal = 0, maximoTiempo = 0;

        for (Procesador key : solucion.keySet()) {
            //resetea tiempo total para que el tiempo sea individual por cada procesador
            tiempoTotal = 0;
            tiempoTotal += calcularTiempoTareas(key);
            if (tiempoTotal > maximoTiempo) {
                maximoTiempo = tiempoTotal;
            }
        }

        return tiempoTotal;
    }


    private float calcularTiempoTareas(Procesador p) {
        ArrayList<Tarea> tareas = this.solucion.get(p);
        float tiempo = 0.0f;
        for (Tarea tarea : tareas) {
            tiempo += tarea.getTiempo_ejecucion();
        }
        return tiempo;
    }


    //Comprueba si a un procesador se le puede asignar cierta tarea
    public boolean esAsignable(Procesador procesador, Tarea tarea, int x) {
        if (!procesador.Esta_refrigerado() && tarea.getTiempo_ejecucion() > x) {
            return false;
        }

        if (tarea.Es_critica() && getCantCriticas(procesador) >= 2) {
            return false;
        }

        return true;
    }

    private int getCantCriticas(Procesador p) {
        int cantidad = 0;
        List<Tarea> tareas = solucion.get(p);
        for (Tarea t : tareas) {
            if (t.Es_critica()) {
                cantidad++;
            }
        }
        return cantidad;
    }


    public SolucionBacktracking copiarSolucion() {
        List<Procesador> nuevosProcesadores = new ArrayList<>(this.procesadores);
        SolucionBacktracking nuevaSolucion = new SolucionBacktracking(nuevosProcesadores);

        for (Map.Entry<Procesador, ArrayList<Tarea>> entry : this.solucion.entrySet()) {
            Procesador procesador = entry.getKey();
            ArrayList<Tarea> tareas = entry.getValue();
            ArrayList<Tarea> nuevasTareas = new ArrayList<>(tareas);
            nuevaSolucion.solucion.put(procesador, nuevasTareas);
        }

        return nuevaSolucion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Procesador, ArrayList<Tarea>> entry : solucion.entrySet()) {
            Procesador procesador = entry.getKey();
            ArrayList<Tarea> tareas = entry.getValue();
            sb.append("Procesador: ").append(procesador.getId_procesador()).append("\n");
            sb.append("Tareas asignadas:\n");
            for (Tarea tarea : tareas) {
                sb.append("\t").append(tarea.getId_tarea()).append(" (Tiempo: ").append(tarea.getTiempo_ejecucion()).append(", Crítica: ").append(tarea.Es_critica()).append(")\n");
            }
            sb.append("Tiempo total de la solucion: ").append(calcularTiempoTotal());
            sb.append("\n");
        }
        return sb.toString();
    }





}
