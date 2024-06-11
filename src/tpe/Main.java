package tpe;

public class Main {

	public static void main(String args[]) {
		Servicios servicios = new Servicios("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas.csv");


		//metodos comentados para que se puedan probar de a uno y verlos de forma mas clara

		//System.out.println(servicios.servicio1("T1"));
		//System.out.println(servicios.servicio2(false));
		//System.out.println(servicios.servicio3(1,20));


		System.out.println(servicios.back(150));

		System.out.println(servicios.greedy(50));


	}
}
