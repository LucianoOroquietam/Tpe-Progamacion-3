package tpe;

import java.util.ArrayList;
import java.util.HashMap;
/*
* Primero, ningún procesador podrá ejecutar más de 2 tareas críticas.
*Segundo, los procesadores no refrigerados no podrán dedicar más de X tiempo de ejecución a
las tareas asignadas. El tiempo X será un parámetro establecido por el usuario al momento de
solicitar la asignación de las tareas a los procesadores.

*
* */
public class Solucion {

    private HashMap<String, ArrayList<Tarea>> asignaciones;

    public Solucion() {
        this.asignaciones = new HashMap<>();
    }


    //asigna una tarea a un procesador (solucion parcial)
    public void asignarTarea(Procesador p, Tarea t, int x){
        //si el procesador no tiene lista de areas (osea es un procesador nuevo) creo una lista para ese procesador
        if(!asignaciones.containsKey(p)){

            asignaciones.put(p.getCodigo_procesador(),new ArrayList<>());
        }
        // Si ese procesador ya tenia una lista de tareas asignadas, y es asignable, le agrego la tarea
        if (esAsignable(p, t, x)) {
            asignaciones.get(p.getCodigo_procesador()).add(t);
        }
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


    //Comprueba si a un procesador se le puede asignar cierta tarea
    public boolean esAsignable(Procesador procesador, Tarea tarea, int x){
        if (procesador.Esta_refrigerado() && tarea.getTiempo_ejecucion() > x){
            return false;
        }

        if (tarea.Es_critica()  &&  getCantCriticas(procesador.getCodigo_procesador()) >= 2){
            return false;
        }

        return true;
    }

    private int getCantCriticas(String id){
        int cantidad = 0;
        for (Tarea tarea : this.asignaciones.get(id)) {
            if (tarea.Es_critica()){
                cantidad++;
            }
        }

        return cantidad;
    }







}
