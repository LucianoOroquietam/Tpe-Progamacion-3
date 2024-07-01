package tpe;

public class Main {

	public static void main(String args[]) {
		Servicios servicios = new Servicios("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas.csv");


		//metodos comentados para que se puedan probar de a uno y verlos de forma mas clara

		//System.out.println(servicios.servicio1("T1"));
		//System.out.println(servicios.servicio2(false));
		//System.out.println(servicios.servicio3(1,20));


		System.out.println(servicios.back(200));

		System.out.println(servicios.greedy(50));


		/*-No encuentra la solucion optima para los casos de test:
P1;COD_P1;true;2010
P2;COD_P2;false;2020
P3;COD_P3;false;2022
P4;COD_P4;true;2015

T1;Tarea1;90;false;92
T2;Tarea2;15;true;31
T3;Tarea3;80;false;70
T4;Tarea4;20;true;58
T5;Tarea5;15;true;1
T6;Tarea6;10;true;31
deberia ser 90 tiempo max, y resuelve en 100.
-Tambien genera muy pocos estados, deberia generar alrededor de 4000 y genera 384. Solucionado
-No encuentra solucion para mismo dataset pero cuando el tiempo max es 10.*/

	}
}
