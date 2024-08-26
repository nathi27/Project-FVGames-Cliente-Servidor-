package com.Conexion;

import com.Modelo.CarritoProducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarritoProductoDAO {

    private ConexionMysql conexion;

    public CarritoProductoDAO() {
        this.conexion = new ConexionMysql();
    }

    public boolean insertar(CarritoProducto carritoProducto) {
        String sql = "INSERT INTO Carrito_Productos (carrito_id, producto_id, cantidad) VALUES (?, ?, ?)";
        try (Connection conn = conexion.getConection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, carritoProducto.getCarritoId());
            stmt.setInt(2, carritoProducto.getProductoId());
            stmt.setInt(3, carritoProducto.getCantidad());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar CarritoProducto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPorCarritoIdYProductoId(int carritoId, int productoId) {
        String sql = "DELETE FROM Carrito_Productos WHERE carrito_id = ? AND producto_id = ?";
        try (Connection conn = conexion.getConection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, carritoId);
            stmt.setInt(2, productoId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el producto del carrito: " + e.getMessage());
            return false;
        }
    }

    // Este método ahora devuelve una lista de productos para un carrito específico
    public List<CarritoProducto> obtenerPorCarritoId(int carritoId) {
        String sql = "SELECT * FROM Carrito_Productos WHERE carrito_id = ?";
        List<CarritoProducto> productosDelCarrito = new ArrayList<>();
        try (Connection conn = conexion.getConection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, carritoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CarritoProducto carritoProducto = new CarritoProducto(
                        rs.getInt("id"),
                        rs.getInt("carrito_id"),
                        rs.getInt("producto_id"),
                        rs.getInt("cantidad")
                );
                productosDelCarrito.add(carritoProducto);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos del carrito: " + e.getMessage());
        }
        return productosDelCarrito;
    }

    public boolean actualizar(CarritoProducto carritoProducto) {
        String sql = "UPDATE Carrito_Productos SET carrito_id = ?, producto_id = ?, cantidad = ? WHERE id = ?";
        try (Connection conn = conexion.getConection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, carritoProducto.getCarritoId());
            stmt.setInt(2, carritoProducto.getProductoId());
            stmt.setInt(3, carritoProducto.getCantidad());
            stmt.setInt(4, carritoProducto.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar CarritoProducto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPorCarritoId(int carritoId) {
        String sql = "DELETE FROM Carrito_Productos WHERE carrito_id = ?";
        try (Connection conn = conexion.getConection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, carritoId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar productos del carrito: " + e.getMessage());
            return false;
        }
    }
    
    
}
