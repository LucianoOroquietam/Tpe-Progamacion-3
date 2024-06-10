package tpe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolucionGreedy {
    private List<Procesador> procesadores;
    private HashMap<Procesador, ArrayList<Tarea>> solucion;
    //asigna una tarea a un procesador (solucion parcial)\

    private List<Procesador>candidatos;

    public SolucionGreedy(List<Procesador> procesadores) {
        this.solucion = new HashMap<>();
        this.procesadores = new ArrayList<>(procesadores);

        for (Procesador p : procesadores) {
            this.solucion.put(p, new ArrayList<Tarea>());
        }
        this.candidatos=new ArrayList<>();
    }
    
    public void asignarTarea(Procesador p, Tarea t) {
        // Si ese procesador ya tenia una lista de tareas asignadas, y es asignable, le agrego la tarea
        solucion.get(p).add(t);
    }

    //quita una tarea a un procesador (solucion parcial)

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
    public List<Procesador> getCandidatos(){
        return  candidatos;
    }
    public  Procesador seleccionarProcesador(Tarea t, int tiempoMax){
        Procesador seleccionado = null;
        for(Procesador procesador : this.procesadores){
           if(seleccionado==null){
              if(this.esAsignable(procesador,t,tiempoMax)) {
                  seleccionado = procesador;
              }
           }else if (this.getTiempoAcumulado(procesador)<this.getTiempoAcumulado(seleccionado)) {
               if (this.esAsignable(procesador, t, tiempoMax)) ;
               seleccionado = procesador;
           }
           candidatos.add(seleccionado);
        }
        return seleccionado;
    }
    private int getTiempoAcumulado(Procesador p){
        int tiempoAcumulado = 0;
        for(Tarea t : this.solucion.get(p)){
            tiempoAcumulado += t.getTiempo_ejecucion();
        }
        return tiempoAcumulado;
    }

    //Comprueba si a un procesador se le puede asignar cierta tarea

    public boolean esAsignable(Procesador p,Tarea t,int tiempoMaximo){
        if (t.Es_critica() && getCantCriticas(p) >= 2){
            return false;         }
        if (!p.Esta_refrigerado() && t.getTiempo_ejecucion()+calcularTiempoTotal()>=tiempoMaximo) {
            //si el tiempo q toma hacer esta tarea mas el tiempo ya acumulado es mayor al tiempo total no la puedo agregar
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



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Solucion Greedy: ");
        sb.append("\n");
        for (Map.Entry<Procesador, ArrayList<Tarea>> entry : solucion.entrySet()) {
            Procesador procesador = entry.getKey();
            ArrayList<Tarea> tareas = entry.getValue();
            sb.append("Procesador: ").append(procesador.getId_procesador()).append("\n");
            sb.append("Tareas asignadas:\n");
            for (Tarea tarea : tareas) {
                sb.append("\t").append(tarea.getId_tarea()).append(" (Tiempo: ").append(tarea.getTiempo_ejecucion()).append(", Crítica: ").append(tarea.Es_critica()).append(")\n");
            }
            sb.append("Tiempo total de tareas : ").append(calcularTiempoTotal());
            sb.append("\n");
        }
        return sb.toString();
    }

}
