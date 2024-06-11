package tpe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolucionGreedy extends Solucion{

    private int cantCandidatos;

    public SolucionGreedy(List<Procesador> procesadores) {
      super(procesadores);
      this.cantCandidatos=0;
    }

    public int getCantCandidatos(){
        return  cantCandidatos;
    }
    public  Procesador seleccionarProcesador(Tarea t, int tiempoMax){
        Procesador seleccionado = null;
        for(Procesador procesador : this.procesadores){
           if(seleccionado==null){
              if(this.esAsignable(procesador,t,tiempoMax)) {
                  seleccionado = procesador;
              }
           }else if (this.tiempoDeProcesador(procesador)<this.tiempoDeProcesador(seleccionado)) {
               if (this.esAsignable(procesador, t, tiempoMax)) {
                   seleccionado = procesador;
               }
           }
           cantCandidatos+=1;
        }
        return seleccionado;
    }
    private int tiempoDeProcesador(Procesador p){
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
        if (!p.Esta_refrigerado() && t.getTiempo_ejecucion()+tiempoDeProcesador(p)>tiempoMaximo) {
            //si el tiempo q toma hacer esta tarea mas el tiempo ya acumulado es mayor al tiempo total no la puedo agregar
             return false;
            }
        return true;
    }

}
