package Entidades;

public class Pedido {

    private int idPedido;
    private String cliente;
    private int mesa;
    private String platillo;
    private double total;

    public Pedido() {
    }

    public Pedido(int idPedido,String cliente, int mesa, String platillo, double total) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.mesa = mesa;
        this.platillo = platillo;
        this.total = total;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public String getPlatillo() {
        return platillo;
    }

    public void setPlatillo(String platillo) {
        this.platillo = platillo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}