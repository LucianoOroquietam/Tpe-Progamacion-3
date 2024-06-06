package tpe;

import java.util.ArrayList;
import java.util.HashMap;

public class Solucion {

    private HashMap<String, ArrayList<Tarea>> asignaciones;

    public Solucion() {
        this.asignaciones = new HashMap<>();
    }


    //asigna una tarea a un procesador (solucion parcial)
    public void asignarTarea(Procesador p, Tarea t){
        //si el procesador no tiene lista de areas (osea es un procesador nuevo) creo una lista para ese procesador
        if(!asignaciones.containsKey(p)){

            asignaciones.put(p.getCodigo_procesador(),new ArrayList<>());
        }
        //si el procesador ya tiene una lista , directamente agrego la tarea a su lista
        this.asignaciones.get(p.getCodigo_procesador()).add(t);
    }
    //quita una tarea a un procesador (solucion parcial)
    public void removerTarea(Procesador p, Tarea t){
        this.asignaciones.get(p.getCodigo_procesador()).remove(t);
    }

    //calcula el tiempo maximo de ejecucion que hubo  en el procesador
    private float calcularTiempoTotal(){
        float tiempoTotal = 0, maximoTiempo=0;
        for(String key : asignaciones.keySet()){
            //resetea tiempo total para que el tiempo sea individual por cada procesador
            tiempoTotal = 0;
            tiempoTotal += calcularTiempoTareas(key);
            if (tiempoTotal > maximoTiempo){
                maximoTiempo = tiempoTotal;
            }
        }

        return tiempoTotal;
    }


    private float calcularTiempoTareas(String id_proce){
        ArrayList<Tarea>tareas = this.asignaciones.get(id_proce);
        float tiempo = 0.0f;
        for(Tarea tarea: tareas){
            tiempo += tarea.getTiempo_ejecucion();
        }
        return tiempo;
    }
}
