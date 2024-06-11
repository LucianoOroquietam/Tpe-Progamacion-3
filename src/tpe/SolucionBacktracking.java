package tpe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolucionBacktracking extends Solucion{

    public SolucionBacktracking(List<Procesador> procesadores){
        super(procesadores);
    }

    //quita una tarea a un procesador (solucion parcial)
    public void removerTarea(Procesador p, Tarea t) {
        this.solucion.get(p).remove(t);
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
}
