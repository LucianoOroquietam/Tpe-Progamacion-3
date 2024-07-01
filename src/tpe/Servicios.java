package tpe;

import tpe.utils.CSVReader;

import java.sql.SQLOutput;
import java.util.*;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

	private List<Procesador> procesadores;
	private HashMap<String,Tarea>tareas;

	private List<Tarea> listaTareas;

	private List<Tarea> tareasCriticas;
	private List<Tarea> tareasNoCriticas;
	private SolucionBacktracking mejorSolucionBack;
	private SolucionGreedy mejorSolucionGreedy;
	private List<Float>todosLostiempos;

	/*
	 * Complejidad O(n) n siendo la cantidad de tareas y procesadores
	 */

	public Servicios(String pathProcesadores, String pathTareas)
	{
		this.procesadores=new ArrayList<>();//O(1)
		this.tareasCriticas = new ArrayList<>();
		this.tareasNoCriticas = new ArrayList<>();
		this.tareas = new HashMap<>();
		this.listaTareas=new ArrayList<>();
		this.todosLostiempos=new ArrayList<>();

		CSVReader reader = new CSVReader();//O
		procesadores.addAll(reader.readProcessors(pathProcesadores));
		tareas.putAll(reader.readTasks(pathTareas));
		addCriticasyNocriticas(tareas);
		addListaTareas();

		this.mejorSolucionBack = new SolucionBacktracking(this.procesadores);
		this.mejorSolucionGreedy = new SolucionGreedy(this.procesadores);


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

	public void addListaTareas(){
		for (Map.Entry<String, Tarea> entry : tareas.entrySet()){
			Tarea tarea = entry.getValue();
			listaTareas.add(tarea);
		}
	}
	
	/*
     * Complejidad O(1) ya que le damos un id especifico para traer la tarea
     */
	public Tarea servicio1(String ID) {//O(1)
		if(tareas.containsKey(ID)) {
			return tareas.get(ID);
		}
		return null;
    }
    
    /*
     * Complejidad O(1) donde si es critica o no critica directamente la retorna
     */
	public List<Tarea> servicio2(boolean esCritica) {

		if(esCritica){
			return this.tareasCriticas;
		}
        return this.tareasNoCriticas;
    }

    /*
     * o(n) donde n es la cantidad de tareas.
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

	/**
	 * Explicacion Backtracking publico
	 * @param tiempoMaximo
	 * creamos el estado inicial de backtracking (Posicion=0) y una solucion parcial
	 * estos atributos los pasamos como parametros al metodo recursivo del back junto con el tiempoMaximo.
	 *una vez el metodo recursivo finaliza, se retorna la mejor solucion encontrada
	 * @return MejorSolucionBack
	 */
	public SolucionBacktracking back(int tiempoMaximo){

		System.out.println("Comienzo del Backtracking: ");
		int posicion=0;
		//crear solucion parcial para llamar a la recursividad

		SolucionBacktracking solucionParcial=new SolucionBacktracking(procesadores);
		backtracking(solucionParcial,posicion,tiempoMaximo);

		System.out.println("Metrica de estados generados: " + this.todosLostiempos);
		System.out.printf("Cantidad de estados generados: " + this.todosLostiempos.size()+ "\n \n");
		if (mejorSolucionBack.estaVacia()){
			System.out.println("No se encontro una solucion a travez de backtracking");
		}
        return mejorSolucionBack;//devuelve la solucion
	}

	/**
	 *Explicacion Backtracking privado
	 * @param solucion
	 * @param posicion
	 * @param tiempoMaximo
	 * por cada procesador iremos asignando distintas combinaciones de tareas a travez de la recursion.
	 * cada vez q asignamos una tarea a un procesador, llamamos recursivamente sumando 1 a la pocision de la lista para
	 * que haya condicion de corte, es decir q una vez q se hayan asignado todas las tareas esa solucion estara finalizada.
	 * por lo q se procede a chequear si la solucion es valida, (cumple con requisitos expresos en la consigna del TPE)
	 * si es valida, se compara el tiempo que tarda en darse esa solucion con la mejor solucion que tengamos guardada hasta el momento,
	 * en caso de que esta sea la primera, si el tiempo total de la mejor solucion es 0, entra al if y se guarda como mejor solucion actual
	 */

	private void backtracking(SolucionBacktracking solucion, int posicion, int tiempoMaximo){
		//si es valida comparar con mi mejor solucion
		//si es mejor q la q tenia, la copio y se vuelve mi mejor solucion
		if (posicion==listaTareas.size()){//si ya asigne todas mis tareas, empiezo a chequear las soluciones
			todosLostiempos.add(solucion.calcularTiempoTotal());
			if(solucion.esValida(tiempoMaximo)) {

				if (solucion.calcularTiempoTotal() < mejorSolucionBack.calcularTiempoTotal() || mejorSolucionBack.calcularTiempoTotal() == 0) {
					this.mejorSolucionBack = solucion.copiarSolucion();
				}
			}
			return;
		}
			for (Procesador p:procesadores) {
				solucion.asignarTarea(p,listaTareas.get(posicion));
				backtracking(solucion,posicion+1,tiempoMaximo);
				solucion.removerTarea(p,listaTareas.get(posicion));
			}

		}

	/**
	 * Explicacion Greedy
	 * @param tiempoMax
	 * recibe como parametro el tiempo maximo que un procesador puede funcionar si no esta refrigerado
	 * por cada tarea buscamos el mejor procesador posible, para esto chequeamos que el procesador cumpla con los requisitos.
	 * se guarda un candidato de procesador para esa tarea y se chequea si hay algun otro procesador que ademas de cumplir con los requisitos
	 * tenga mas tiempo libre que el candidato, si es encontrado entonces este se vuelve el procesador asignado final para esa tarea.
	 * este proceso se repite para todas las tareas
	 * @return SolucionGreedy
	 */

	public SolucionGreedy greedy(int tiempoMax){

		boolean error=false;

		System.out.println("Comienzo de Greedy");
		for (Tarea tarea:listaTareas) {
			Procesador p = mejorSolucionGreedy.seleccionarProcesador(tarea,tiempoMax);
			if (p!=null){
				mejorSolucionGreedy.asignarTarea(p,tarea);
			}else {
				error=true;
			}
		}
		if (error){
			System.out.println("No se ha encontrado solucion a travez de greedy");
		}
		if (listaTareas.isEmpty()){
			System.out.println("No se ha encontrado solucion a travez de greedy ya que no hay tareas");
		}
		System.out.println("La cantidad de candidatos es: " + mejorSolucionGreedy.getCantCandidatos());
		return mejorSolucionGreedy;
	}


}
