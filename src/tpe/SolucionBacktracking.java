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
    public void asignarTarea(Procesador p, Tarea t) {
        // Si ese procesador ya tenia una lista de tareas asignadas, y es asignable, le agrego la tarea
            solucion.get(p).add(t);
    }

    //quita una tarea a un procesador (solucion parcial)
    public void removerTarea(Procesador p, Tarea t) {
        this.solucion.get(p).remove(t);
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

    public boolean estaVacia(){
        for (Procesador p:procesadores) {
            if (!solucion.get(p).isEmpty()){
                return false;
            }
        }
       return true;
    }
    //Comprueba si a un procesador se le puede asignar cierta tarea
    public boolean esValida(int x) {
        for(Procesador p : this.procesadores){

            List<Tarea> tareas = this.solucion.get(p);
            float tiempoAcumulado = 0;

            for(Tarea t : tareas){

                tiempoAcumulado += t.getTiempo_ejecucion();

                if(t.Es_critica() && getCantCriticas(p) >= 2) {
                    return false;
                }
                if(!p.Esta_refrigerado() && tiempoAcumulado >= x) {
                    return false;
                }
            }
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
        sb.append("posible solucion: ");
        sb.append("\n");
        for (Map.Entry<Procesador, ArrayList<Tarea>> entry : solucion.entrySet()) {
            Procesador procesador = entry.getKey();
            ArrayList<Tarea> tareas = entry.getValue();
            sb.append("Procesador: ").append(procesador.getId_procesador()).append("\n");
            sb.append("Tareas asignadas:\n");
            for (Tarea tarea : tareas) {
                sb.append("\t").append(tarea.getId_tarea()).append(" (Tiempo: ").append(tarea.getTiempo_ejecucion()).append(", Crítica: ").append(tarea.Es_critica()).append(")\n");
            }

        }
        sb.append("Tiempo total de tareas : ").append(calcularTiempoTotal());
        sb.append("\n");
        return sb.toString();
    }





}
