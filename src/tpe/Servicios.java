package tpe;

import tpe.utils.CSVReader;

import java.util.ArrayList;
import java.util.List;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

	/*
     * Expresar la complejidad temporal del constructor.
     */

	private List<Procesador> procesadores;
	private List<Tarea>tareas;

	public Servicios(String pathProcesadores, String pathTareas)//Complejidad O(n) n siendo la cantidad de tareas y procesadores
	{
		this.procesadores=new ArrayList<>();//O(1)
		this.tareas=new ArrayList<>();//O(1)
		CSVReader reader = new CSVReader();//O(1)
		procesadores.addAll(reader.readProcessors(pathProcesadores));//O(p)
		tareas.addAll(reader.readTasks(pathTareas));//O(t)

	}
	
	/*
     * Expresar la complejidad temporal del servicio 1.
     */
	public Tarea servicio1(String ID) {//O(n) donde n son las tareas y en el peor de los casos recorres toda la lista
		for (Tarea t:tareas) {
			if (t.getId_tarea().equalsIgnoreCase(ID)){
				return t;
			}
		}
        return null;
    }
    
    /*
     * Expresar la complejidad temporal del servicio 2.
     */
	public List<Tarea> servicio2(boolean esCritica) {//O(n) donde n son las tareas y puede q todas sean criticas o no criticas segun lo q se busca

		List<Tarea>tareasBuscadas=new ArrayList<>();

		for (Tarea t:tareas) {
			if (t.Es_critica()==esCritica){
				tareasBuscadas.add(t);
			}
		}
        return tareasBuscadas;
    }

    /*
     * Expresar la complejidad temporal del servicio 3.
     */
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {

		List<Tarea>tareasBuscadas=new ArrayList<>();

		for (Tarea t:tareas) {
			if (t.getNivel_prioridad()>=prioridadInferior && t.getNivel_prioridad()<=prioridadSuperior){
				tareasBuscadas.add(t);
			}
		}
        return tareasBuscadas;
    }

	public void agregarTarea(Tarea t){
		if (t!=null && !tareas.contains(t))
			tareas.add(t);
	}
	public void agregarProcesador(Procesador p){
		if (p!=null && !procesadores.contains(p))
			procesadores.add(p);
	}

}
