package CRUD;

import Entidades.Cliente;
import ConexionBD.Conexion;
import java.sql.*;
import java.util.ArrayList;

public class ClienteBD {

    public void insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes(nombre, telefono) VALUES (?, ?)";
        try ( Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.executeUpdate();
            System.out.println("Cliente registrado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public ArrayList<Cliente> mostrarClientes() {
        ArrayList<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try ( Connection con = Conexion.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNombre(rs.getString("nombre"));
                c.setTelefono(rs.getString("telefono"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lista;
    }

    public void actualizarCliente(int id, String nombre, String telefono) {
        String sql = "UPDATE clientes SET nombre=?, telefono=? WHERE id_cliente=?";
        try ( Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.setInt(3, id);
            ps.executeUpdate();
            System.out.println("Cliente actualizado.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void eliminarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id_cliente=?";
        try ( Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Cliente eliminado.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}