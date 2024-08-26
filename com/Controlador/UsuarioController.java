package com.Controlador;

import com.Conexion.UsuarioDAO;
import com.Conexion.MetodoPagoDAO;
import com.Modelo.Usuario;
import com.Modelo.MetodoPago;
import com.Vista.UsuarioCRUDView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UsuarioController {

    private UsuarioCRUDView vista;
    private UsuarioDAO usuarioDAO;
    private MetodoPagoDAO metodoPagoDAO;

    public UsuarioController(UsuarioCRUDView vista) {
        this.vista = vista;
        this.usuarioDAO = new UsuarioDAO();
        this.metodoPagoDAO = new MetodoPagoDAO();
        inicializarVista();
        inicializarControladores();
    }

    private void inicializarVista() {
        cargarDatosEnTabla();
        vista.setVisible(true);
    }

    private void inicializarControladores() {
        vista.setInsertarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertarUsuario();
            }
        });

        vista.setActualizarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarUsuario();
            }
        });

        vista.setEliminarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
            }
        });

        vista.setLimpiarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.limpiarCampos();
            }
        });
    }

    private void cargarDatosEnTabla() {
        List<Usuario> usuarios = usuarioDAO.obtenerTodos();
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Cédula");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellidos");
        modeloTabla.addColumn("Dirección");
        modeloTabla.addColumn("Email");
        modeloTabla.addColumn("Dinero en Cuenta");
        modeloTabla.addColumn("Permiso");
        modeloTabla.addColumn("Método de Pago Preferido");

        for (Usuario usuario : usuarios) {
            Object[] fila = new Object[8];
            fila[0] = usuario.getCedula();
            fila[1] = usuario.getNombre();
            fila[2] = usuario.getApellidos();
            fila[3] = usuario.getDireccion();
            fila[4] = usuario.getEmail();
            fila[5] = usuario.getDineroCuenta();
            fila[6] = usuario.getPermiso();
            fila[7] = usuario.getMetodoPagoPreferido();
            modeloTabla.addRow(fila);
        }

        vista.setTableData(modeloTabla);
    }

    private void insertarUsuario() {
        String password = vista.getPassword();

        if (password == null || password.isEmpty()) {
            vista.showMessage("La contraseña no puede estar vacía.");
            return;
        }

        Usuario usuario = new Usuario(
                vista.getCedula(),
                vista.getNombre(),
                vista.getApellidos(),
                vista.getDireccion(),
                vista.getEmail(),
                password,
                vista.getDineroCuenta(),
                vista.getPermiso(),
                obtenerMetodoPagoID(vista.getMetodoPago())
        );

        if (usuarioDAO.insertar(usuario)) {
            vista.showMessage("Usuario insertado exitosamente.");
            cargarDatosEnTabla();
            vista.limpiarCampos();
        } else {
            vista.showMessage("Error al insertar el usuario.");
        }
    }

    private void actualizarUsuario() {
        String password = vista.getPassword();

        if (password == null || password.isEmpty()) {
            vista.showMessage("La contraseña no puede estar vacía.");
            return;
        }

        Usuario usuario = new Usuario(
                vista.getCedula(),
                vista.getNombre(),
                vista.getApellidos(),
                vista.getDireccion(),
                vista.getEmail(),
                password,
                vista.getDineroCuenta(),
                vista.getPermiso(),
                obtenerMetodoPagoID(vista.getMetodoPago())
        );

        if (usuarioDAO.actualizar(usuario)) {
            vista.showMessage("Usuario actualizado exitosamente.");
            cargarDatosEnTabla();
            vista.limpiarCampos();
        } else {
            vista.showMessage("Error al actualizar el usuario.");
        }
    }

    private void eliminarUsuario() {
        String cedula = vista.getCedula();

        if (usuarioDAO.eliminar(cedula)) {
            vista.showMessage("Usuario eliminado exitosamente.");
            cargarDatosEnTabla();
            vista.limpiarCampos();
        } else {
            vista.showMessage("Error al eliminar el usuario.");
        }
    }

    private int obtenerMetodoPagoID(String nombreMetodoPago) {
        MetodoPago metodoPago = metodoPagoDAO.obtenerPorNombre(nombreMetodoPago);
        return metodoPago != null ? metodoPago.getCodigo() : -1; // Retorna -1 si no encuentra el método de pago
    }
}
