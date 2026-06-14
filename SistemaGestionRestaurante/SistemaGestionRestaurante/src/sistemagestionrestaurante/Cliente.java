package sistemagestionrestaurante;

public class Cliente extends Persona {

    private int idCliente;
    private String telefono;

    public Cliente() {
    }

    public Cliente(int idCliente, String nombre, String telefono) {
        super(nombre);
        this.idCliente = idCliente;
        this.telefono = telefono;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("ID: " + idCliente);
        System.out.println("Nombre: " + nombre);
        System.out.println("Teléfono: " + telefono);
    }
}