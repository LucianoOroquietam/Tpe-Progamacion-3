package tpe;

import tpe.utils.CSVReader;

import java.util.*;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

	private List<Procesador> procesadores;
	private HashMap<String,Tarea>tareas;

	private List<Tarea> tareasCriticas;
	private List<Tarea> tareasNoCriticas;

	/*
	 * Complejidad O(n) n siendo la cantidad de tareas y procesadores
	 */

	public Servicios(String pathProcesadores, String pathTareas)
	{
		this.procesadores=new ArrayList<>();//O(1)
		this.tareasCriticas = new ArrayList<>();
		this.tareasNoCriticas = new ArrayList<>();
		this.tareas = new HashMap<>();
		CSVReader reader = new CSVReader();//O
		procesadores.addAll(reader.readProcessors(pathProcesadores));//O(p)
		tareas.putAll(reader.readTasks(pathTareas));
		addCriticasyNocriticas(tareas);


	}
	public void addCriticasyNocriticas(HashMap<String,Tarea>t){
		for (Map.Entry<String, Tarea> entry : tareas.entrySet()){
			Tarea tarea = entry.getValue();
			if (tarea.Es_critica()){
				tareasCriticas.add(tarea);
			}
			else {
				tareasNoCriticas.add(tarea);
			}
		}
	}
	
	/*
     * Expresar la complejidad temporal del servicio 1.
     */
	public Tarea servicio1(String ID) {//O(1)
		if(tareas.containsKey(ID)) {
			return tareas.get(ID);
		}
		return null;
    }
    
    /*
     * //o(1)
     * donde si es critica o no critica directamente la retorna
     */
	public List<Tarea> servicio2(boolean esCritica) {

		if(esCritica){
			return this.tareasCriticas;
		}
        return this.tareasNoCriticas;
    }

    /*
     * o(n)
     */
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {

		List<Tarea>tareasBuscadas=new ArrayList<>();

		for (Map.Entry<String, Tarea> entry : tareas.entrySet()){
			Tarea t = entry.getValue();
			if (t.getNivel_prioridad()>=prioridadInferior && t.getNivel_prioridad()<=prioridadSuperior){
				tareasBuscadas.add(t);
			}
		}
        return tareasBuscadas;
    }


}
