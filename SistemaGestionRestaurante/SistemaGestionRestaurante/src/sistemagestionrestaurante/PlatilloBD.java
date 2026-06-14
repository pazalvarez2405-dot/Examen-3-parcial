package sistemagestionrestaurante;

import java.sql.*;
import java.util.ArrayList;

public class PlatilloBD {

    public void insertarPlatillo(Platillo platillo) {
        String sql = "INSERT INTO platillos(nombre, descripcion, precio) VALUES (?, ?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, platillo.getNombre());
            ps.setString(2, platillo.getDescripcion());
            ps.setDouble(3, platillo.getPrecio());
            ps.executeUpdate();
            System.out.println("Platillo registrado.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public ArrayList<Platillo> mostrarPlatillos() {
        ArrayList<Platillo> lista = new ArrayList<>();
        String sql = "SELECT * FROM platillos";
        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Platillo p = new Platillo();
                p.setIdPlatillo(rs.getInt("id_platillo"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lista;
    }

    // NUEVO MÉTODO: Obtener un platillo por su ID
    public Platillo obtenerPlatilloPorId(int id) {
        String sql = "SELECT * FROM platillos WHERE id_platillo = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Platillo p = new Platillo();
                p.setIdPlatillo(rs.getInt("id_platillo"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                return p;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public void actualizarPlatillo(int id, String nombre, String descripcion, double precio) {
        String sql = "UPDATE platillos SET nombre=?, descripcion=?, precio=? WHERE id_platillo=?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setDouble(3, precio);
            ps.setInt(4, id);
            ps.executeUpdate();
            System.out.println("Platillo actualizado.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void eliminarPlatillo(int id) {
        String sql = "DELETE FROM platillos WHERE id_platillo=?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Platillo eliminado.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}