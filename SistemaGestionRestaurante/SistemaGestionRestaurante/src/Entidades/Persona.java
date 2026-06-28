package Entidades;

public class Persona {

    protected String nombre;

    public Persona() {
    }

    public Persona(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void mostrarInformacion() {
        System.out.println("Nombre: " + nombre);

    }
}