package sistemagestionrestaurante;

public class Platillo {

    private int idPlatillo;
    private String nombre;
    private String descripcion;
    private double precio;

    public Platillo() {
    }

    public Platillo(int idPlatillo, String nombre, String descripcion, double precio) {
        this.idPlatillo = idPlatillo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public int getIdPlatillo() {
        return idPlatillo;
    }

    public void setIdPlatillo(int idPlatillo) {
        this.idPlatillo = idPlatillo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void mostrarInformacion() {
        System.out.println("ID: " + idPlatillo);
        System.out.println("Nombre: " + nombre);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Precio: $" + precio);
    }
}