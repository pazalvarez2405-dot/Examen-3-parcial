package Vistas;
 
import Entidades.Cliente;
import CRUD.ClienteBD;
 
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
 
/**
 * Formulario CRUD para la gestión de Clientes.
 */
public class FrmClientes extends JFrame {
 
    // ── BD ──────────────────────────────────────────────────────
    private final ClienteBD clienteBD = new ClienteBD();
 
    // ── Componentes ─────────────────────────────────────────────
    private JTextField txtId, txtNombre, txtTelefono;
    private JTable tabla;
    private DefaultTableModel modelo;
 
    public FrmClientes() {
        setTitle("Gestión de Clientes");
        setSize(700, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
 
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(Color.WHITE);
 
        // ── Encabezado ───────────────────────────────────────────
        JLabel lblTitulo = new JLabel("👤  Gestión de Clientes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(21, 101, 192));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
 
        // ── Panel de formulario ──────────────────────────────────
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
 
        // ID (solo lectura)
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("ID:"), gbc);
        txtId = new JTextField(8);
        txtId.setEditable(false);
        txtId.setBackground(new Color(238, 238, 238));
        gbc.gridx = 1; panelForm.add(txtId, gbc);
 
        // Nombre
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField(20);
        gbc.gridx = 1; panelForm.add(txtNombre, gbc);
 
        // Teléfono
        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Teléfono:"), gbc);
        txtTelefono = new JTextField(20);
        gbc.gridx = 1; panelForm.add(txtTelefono, gbc);
 
        // ── Botones ──────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.setBackground(Color.WHITE);
 
        JButton btnGuardar    = boton("Guardar",    new Color(46, 125, 50));
        JButton btnActualizar = boton("Actualizar", new Color(21, 101, 192));
        JButton btnEliminar   = boton("Eliminar",   new Color(183, 28, 28));
        JButton btnLimpiar    = boton("Limpiar",    new Color(97, 97, 97));
 
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
 
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panelForm.add(panelBotones, gbc);
 
        // ── Tabla ────────────────────────────────────────────────
        String[] columnas = {"ID", "Nombre", "Teléfono"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.setSelectionBackground(new Color(187, 222, 251));
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Clientes"));
 
        // ── Layout final ─────────────────────────────────────────
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelForm, scroll);
        split.setDividerSize(5);
        split.setResizeWeight(0.35);
        split.setBorder(null);
        panelPrincipal.add(split, BorderLayout.CENTER);
        add(panelPrincipal);
 
        cargarTabla();
 
        // ── Seleccionar fila → llenar formulario ─────────────────
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtId.setText(modelo.getValueAt(fila, 0).toString());
                txtNombre.setText(modelo.getValueAt(fila, 1).toString());
                txtTelefono.setText(modelo.getValueAt(fila, 2).toString());
            }
        });
 
        // ── Acciones de botones ───────────────────────────────────
        btnGuardar.addActionListener(e -> guardar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());
    }
 
    // ── Métodos CRUD ─────────────────────────────────────────────
 
    private void guardar() {
        if (!validarCampos()) return;
        Cliente c = new Cliente();
        c.setNombre(txtNombre.getText().trim());
        c.setTelefono(txtTelefono.getText().trim());
        clienteBD.insertarCliente(c);
        JOptionPane.showMessageDialog(this, "Cliente registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        limpiar();
        cargarTabla();
    }
 
    private void actualizar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) return;
        int id = Integer.parseInt(txtId.getText());
        int confirm = JOptionPane.showConfirmDialog(this, "¿Actualizar cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            clienteBD.actualizarCliente(id, txtNombre.getText().trim(), txtTelefono.getText().trim());
            JOptionPane.showMessageDialog(this, "Cliente actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar();
            cargarTabla();
        }
    }
 
    private void eliminar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            clienteBD.eliminarCliente(Integer.parseInt(txtId.getText()));
            JOptionPane.showMessageDialog(this, "Cliente eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar();
            cargarTabla();
        }
    }
 
    private void cargarTabla() {
        modelo.setRowCount(0);
        ArrayList<Cliente> lista = clienteBD.mostrarClientes();
        for (Cliente c : lista) {
            modelo.addRow(new Object[]{c.getIdCliente(), c.getNombre(), c.getTelefono()});
        }
    }
 
    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }
        if (txtTelefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El teléfono no puede estar vacío.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            txtTelefono.requestFocus();
            return false;
        }
        if (!txtTelefono.getText().trim().matches("[0-9\\-\\+\\s]{7,20}")) {
            JOptionPane.showMessageDialog(this, "Teléfono inválido. Usa solo dígitos (7-20 caracteres).", "Error de validación", JOptionPane.ERROR_MESSAGE);
            txtTelefono.requestFocus();
            return false;
        }
        return true;
    }
 
    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        tabla.clearSelection();
    }
 
    /** Crea un botón con estilo uniforme. */
    private JButton boton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(110, 32));
        return btn;
    }
}
