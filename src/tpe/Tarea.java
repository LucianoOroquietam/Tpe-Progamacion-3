package tpe;

public class Tarea {

    private String id_tarea;
    private String nombre_tarea;
    private float tiempo_ejecucion;
    private boolean es_critica;
    private int nivel_prioridad;

    public Tarea(String id_tarea, String nombre_tarea, float tiempo_ejecucion, boolean es_critica, int nivel_prioridad) {
        this.id_tarea = id_tarea;
        this.nombre_tarea = nombre_tarea;
        this.tiempo_ejecucion = tiempo_ejecucion;
        this.es_critica = es_critica;
        this.nivel_prioridad = nivel_prioridad;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id_tarea=").append(id_tarea).append('\n');
        sb.append("nombre_tarea=").append(nombre_tarea).append('\n');
        sb.append("tiempo_ejecucion=").append(tiempo_ejecucion).append('\n');;
        sb.append("es_critica=").append(es_critica).append('\n');;
        sb.append("nivel_prioridad=").append(nivel_prioridad).append('\n');;
        sb.append("");
        return sb.toString();
    }

    public String getId_tarea() {
        return id_tarea;
    }

    public void setId_tarea(String id_tarea) {
        this.id_tarea = id_tarea;
    }

    public String getNombre_tarea() {
        return nombre_tarea;
    }

    public void setNombre_tarea(String nombre_tarea) {
        this.nombre_tarea = nombre_tarea;
    }

    public float getTiempo_ejecucion() {
        return tiempo_ejecucion;
    }

    public void setTiempo_ejecucion(float tiempo_ejecucion) {
        this.tiempo_ejecucion = tiempo_ejecucion;
    }

    public boolean Es_critica() {
        return es_critica;
    }

    public void setEs_critica(boolean es_critica) {
        this.es_critica = es_critica;
    }

    public int getNivel_prioridad() {
        return nivel_prioridad;
    }

    public void setNivel_prioridad(int nivel_prioridad) {
        this.nivel_prioridad = nivel_prioridad;
    }
}
