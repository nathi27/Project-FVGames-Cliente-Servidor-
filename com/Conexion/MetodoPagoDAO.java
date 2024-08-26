package com.Conexion;

import com.Modelo.MetodoPago;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MetodoPagoDAO {
    private ConexionMysql conexion;

    public MetodoPagoDAO() {
        this.conexion = new ConexionMysql();
    }

    public boolean insertar(MetodoPago metodoPago) {
        String sql = "INSERT INTO MetodosPago (nombre, detalles) VALUES (?, ?)";
        try (Connection conn = conexion.getConection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, metodoPago.getNombre());
            stmt.setString(2, metodoPago.getDetalles());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar método de pago: " + e.getMessage());
            return false;
        }
    }

    public MetodoPago obtenerPorCodigo(int codigo) {
        String sql = "SELECT * FROM MetodosPago WHERE codigo = ?";
        MetodoPago metodoPago = null;
        try (Connection conn = conexion.getConection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                metodoPago = new MetodoPago(
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getString("detalles")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener método de pago: " + e.getMessage());
        }
        return metodoPago;
    }

    public List<MetodoPago> obtenerTodos() {
        String sql = "SELECT * FROM MetodosPago";
        List<MetodoPago> metodosPago = new ArrayList<>();
        try (Connection conn = conexion.getConection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MetodoPago metodoPago = new MetodoPago(
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getString("detalles")
                );
                metodosPago.add(metodoPago);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener métodos de pago: " + e.getMessage());
        }
        return metodosPago;
    }

    public boolean actualizar(MetodoPago metodoPago) {
        String sql = "UPDATE MetodosPago SET nombre = ?, detalles = ? WHERE codigo = ?";
        try (Connection conn = conexion.getConection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, metodoPago.getNombre());
            stmt.setString(2, metodoPago.getDetalles());
            stmt.setInt(3, metodoPago.getCodigo());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar método de pago: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int codigo) {
        String sql = "DELETE FROM MetodosPago WHERE codigo = ?";
        try (Connection conn = conexion.getConection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar método de pago: " + e.getMessage());
            return false;
        }
    }
    
    public MetodoPago obtenerPorNombre(String nombre) {
        String sql = "SELECT * FROM MetodosPago WHERE nombre = ?";
        MetodoPago metodoPago = null;
        try (Connection conn = conexion.getConection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                metodoPago = new MetodoPago(
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getString("detalles")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener método de pago: " + e.getMessage());
        }
        return metodoPago;
    }
}
