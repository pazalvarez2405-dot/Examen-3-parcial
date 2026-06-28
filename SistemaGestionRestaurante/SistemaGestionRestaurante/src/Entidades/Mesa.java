package Entidades;

public class Mesa {

    private int idMesa;
    private int numeroMesa;
    private String estado;

    public Mesa() {
    }

    public Mesa(int idMesa, int numeroMesa, String estado) {
        this.idMesa = idMesa;
        this.numeroMesa = numeroMesa;
        this.estado = estado;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}