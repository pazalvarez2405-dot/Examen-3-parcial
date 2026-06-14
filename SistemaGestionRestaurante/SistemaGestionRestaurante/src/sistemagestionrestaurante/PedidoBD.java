package sistemagestionrestaurante;

import java.sql.*;
import java.util.ArrayList;

public class PedidoBD {
    public void insertarPedido(Pedido pedido) {
        String sql = "INSERT INTO pedidos(cliente, mesa, platillo, total) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pedido.getCliente());
            ps.setInt(2, pedido.getMesa());
            ps.setString(3, pedido.getPlatillo());
            ps.setDouble(4, pedido.getTotal());
            ps.executeUpdate();
            System.out.println("Pedido registrado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public ArrayList<Pedido> mostrarPedidos() {
        ArrayList<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";
        try (Connection con = Conexion.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)
        ) {
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("id_pedido"));
                pedido.setCliente(rs.getString("cliente"));
                pedido.setMesa(rs.getInt("mesa"));
                pedido.setPlatillo(rs.getString("platillo"));
                pedido.setTotal(rs.getDouble("total"));
                lista.add(pedido);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lista;
    }
    public void actualizarPedido(int id, String cliente, int mesa, String platillo, double total) {
        String sql = "UPDATE pedidos SET cliente=?, mesa=?, platillo=?, total=? WHERE id_pedido=?";
        try (Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, cliente);
            ps.setInt(2, mesa);
            ps.setString(3, platillo);
            ps.setDouble(4, total);
            ps.setInt(5, id);
            ps.executeUpdate();
            System.out.println("Pedido actualizado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void eliminarPedido(int id) {
        String sql = "DELETE FROM pedidos WHERE id_pedido=?";
        try (Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Pedido eliminado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}