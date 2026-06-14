package sistemagestionrestaurante;

import java.sql.*;
import java.util.ArrayList;

public class MesaBD {
    public void insertarMesa(Mesa mesa) {
        String sql = "INSERT INTO mesas(numero_mesa, estado) VALUES (?, ?)";
        try (Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, mesa.getNumeroMesa());
            ps.setString(2, mesa.getEstado());
            ps.executeUpdate();
            System.out.println("Mesa registrada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public ArrayList<Mesa> mostrarMesas() {
        ArrayList<Mesa> lista = new ArrayList<>();
        String sql = "SELECT * FROM mesas";
        try (Connection con = Conexion.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setIdMesa(rs.getInt("id_mesa"));
                mesa.setNumeroMesa(rs.getInt("numero_mesa"));
                mesa.setEstado(rs.getString("estado"));
                lista.add(mesa);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lista;
    }
    public void actualizarMesa(int id, int numeroMesa, String estado) {
        String sql = "UPDATE mesas SET numero_mesa=?, estado=? WHERE id_mesa=?";
        try (Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numeroMesa);
            ps.setString(2, estado);
            ps.setInt(3, id);
            ps.executeUpdate();
            System.out.println("Mesa actualizada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void eliminarMesa(int id) {
        String sql = "DELETE FROM mesas WHERE id_mesa=?";
        try (Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Mesa eliminada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}