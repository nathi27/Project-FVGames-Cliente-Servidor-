package com.Conexion;

import com.modelo.CarritoCompra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CarritoCompraDAO {
    private ConexionMysql conexion; //Conecta con la base de datos

    public CarritoCompraDAO() {
        this.conexion = new ConexionMysql();
    }

    public int insertar(CarritoCompra carrito) { //inserta un nuevo registro en la tabla del carritoCompras de la base de datos con los datos del objeto
        String sql = "INSERT INTO CarritoCompras (cliente_cedula, fecha, estado, metodo_pago, detalle, subtotal, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = conexion.getConection(); 
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
             
            stmt.setString(1, carrito.getClienteCedula());
            stmt.setTimestamp(2, carrito.getFecha());
            stmt.setString(3, carrito.getEstado());
            stmt.setInt(4, carrito.getMetodoPago());
            stmt.setString(5, carrito.getDetalle());
            stmt.setDouble(6, carrito.getSubtotal());
            stmt.setDouble(7, carrito.getTotal());
            stmt.executeUpdate();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar carrito de compras: " + e.getMessage());
        }

        return generatedId;
    }

    public CarritoCompra obtenerPorCodigo(int codigo) {
        String sql = "SELECT * FROM CarritoCompras WHERE codigo = ?";
        CarritoCompra carrito = null;
        try (Connection conn = conexion.getConection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                carrito = new CarritoCompra(
                    rs.getInt("codigo"),
                    rs.getString("cliente_cedula"),
                    rs.getTimestamp("fecha"),
                    rs.getString("estado"),
                    rs.getInt("metodo_pago"),
                    rs.getString("detalle"),
                    rs.getDouble("subtotal"),
                    rs.getDouble("total")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener carrito de compras: " + e.getMessage());
        }
        return carrito;
    }

    public List<CarritoCompra> obtenerTodos() {
        String sql = "SELECT * FROM CarritoCompras";
        List<CarritoCompra> carritos = new ArrayList<>();
        try (Connection conn = conexion.getConection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                CarritoCompra carrito = new CarritoCompra(
                    rs.getInt("codigo"),
                    rs.getString("cliente_cedula"),
                    rs.getTimestamp("fecha"),
                    rs.getString("estado"),
                    rs.getInt("metodo_pago"),
                    rs.getString("detalle"),
                    rs.getDouble("subtotal"),
                    rs.getDouble("total")
                );
                carritos.add(carrito);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener carritos de compras: " + e.getMessage());
        }
        return carritos;
    }

    public boolean actualizar(CarritoCompra carrito) {
        String sql = "UPDATE CarritoCompras SET cliente_cedula = ?, fecha = ?, estado = ?, metodo_pago = ?, detalle = ?, subtotal = ?, total = ? WHERE codigo = ?";
        try (Connection conn = conexion.getConection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, carrito.getClienteCedula());
            stmt.setTimestamp(2, carrito.getFecha());
            stmt.setString(3, carrito.getEstado());
            stmt.setInt(4, carrito.getMetodoPago());
            stmt.setString(5, carrito.getDetalle());
            stmt.setDouble(6, carrito.getSubtotal());
            stmt.setDouble(7, carrito.getTotal());
            stmt.setInt(8, carrito.getCodigo());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar carrito de compras: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int codigo) {
        String sql = "DELETE FROM CarritoCompras WHERE codigo = ?";
        try (Connection conn = conexion.getConection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar carrito de compras: " + e.getMessage());
            return false;
        }
    }

    public List<CarritoCompra> obtenerPorUsuario(String cedulaUsuario) {
        String sql = "SELECT * FROM CarritoCompras WHERE cliente_cedula = ?";
        List<CarritoCompra> carritos = new ArrayList<>();
        try (Connection conn = conexion.getConection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, cedulaUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CarritoCompra carrito = new CarritoCompra(
                    rs.getInt("codigo"),
                    rs.getString("cliente_cedula"),
                    rs.getTimestamp("fecha"),
                    rs.getString("estado"),
                    rs.getInt("metodo_pago"),
                    rs.getString("detalle"),
                    rs.getDouble("subtotal"),
                    rs.getDouble("total")
                );
                carritos.add(carrito);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener carritos por usuario: " + e.getMessage());
        }
        return carritos;
    }

    public List<CarritoCompra> obtenerPorIntervaloFecha(Timestamp fechaInicio, Timestamp fechaFin) {
        String sql = "SELECT * FROM CarritoCompras WHERE fecha BETWEEN ? AND ?";
        List<CarritoCompra> carritos = new ArrayList<>();
        try (Connection conn = conexion.getConection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setTimestamp(1, fechaInicio);
            stmt.setTimestamp(2, fechaFin);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CarritoCompra carrito = new CarritoCompra(
                    rs.getInt("codigo"),
                    rs.getString("cliente_cedula"),
                    rs.getTimestamp("fecha"),
                    rs.getString("estado"),
                    rs.getInt("metodo_pago"),
                    rs.getString("detalle"),
                    rs.getDouble("subtotal"),
                    rs.getDouble("total")
                );
                carritos.add(carrito);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener carritos por intervalo de fecha: " + e.getMessage());
        }
        return carritos;
    }
}
