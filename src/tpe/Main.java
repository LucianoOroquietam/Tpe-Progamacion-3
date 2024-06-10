package tpe;

public class Main {

	public static void main(String args[]) {
		Servicios servicios = new Servicios("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas.csv");


		//System.out.println(servicios.servicio1("T5"));
		//System.out.println(servicios.servicio1("T8"));

		//System.out.println(servicios.servicio2(false));
		//System.out.println(servicios.servicio3(1,20));

		//System.out.println(servicios.servicio2(false));

		System.out.println(servicios.back(500));

		System.out.println(servicios.greedy(500));


	}
}
