package tpe;

public class Procesador {

    private String id_procesador;
    private String codigo_procesador;
    private boolean esta_refrigerado;
    private int anio_funcionamiento;


    public Procesador(String id_procesador, String codigo_procesador, boolean esta_refrigerado, int anio_funcionamiento) {
        this.id_procesador = id_procesador;
        this.codigo_procesador = codigo_procesador;
        this.esta_refrigerado = esta_refrigerado;
        this.anio_funcionamiento = anio_funcionamiento;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Procesador{");
        sb.append("id_procesador='").append(id_procesador).append('\'');
        sb.append(", codigo_procesador='").append(codigo_procesador).append('\'');
        sb.append(", esta_refrigerado=").append(esta_refrigerado);
        sb.append(", anio_funcionamiento=").append(anio_funcionamiento);
        sb.append('}');
        return sb.toString();
    }
    public String getId_procesador() {
        return id_procesador;
    }

    public void setId_procesador(String id_procesador) {
        this.id_procesador = id_procesador;
    }

    public String getCodigo_procesador() {
        return codigo_procesador;
    }

    public void setCodigo_procesador(String codigo_procesador) {
        this.codigo_procesador = codigo_procesador;
    }

    public boolean Esta_refrigerado() {
        return esta_refrigerado;
    }

    public void setEsta_refrigerado(boolean esta_refrigerado) {
        this.esta_refrigerado = esta_refrigerado;
    }

    public int getAnio_funcionamiento() {
        return anio_funcionamiento;
    }

    public void setAnio_funcionamiento(int anio_funcionamiento) {
        this.anio_funcionamiento = anio_funcionamiento;
    }
}
