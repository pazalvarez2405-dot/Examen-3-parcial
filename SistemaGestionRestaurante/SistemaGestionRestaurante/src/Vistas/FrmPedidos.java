package Vistas;
 
import  Entidades.Pedido;
import CRUD.PedidoBD;
import Entidades.Platillo;
import CRUD.PlatilloBD;
import Entidades.Mesa;
import CRUD.MesaBD;
 
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
 
/**
 * Formulario CRUD para la gestión de Pedidos.
 * Carga dinámicamente las mesas y platillos desde la BD.
 */
public class FrmPedidos extends JFrame {
 
    private final PedidoBD   pedidoBD   = new PedidoBD();
    private final PlatilloBD platilloBD = new PlatilloBD();
    private final MesaBD     mesaBD     = new MesaBD();
 
    private JTextField txtId, txtCliente, txtTotal;
    private JComboBox<String> cmbMesa, cmbPlatillo;
    private JTable tabla;
    private DefaultTableModel modelo;
 
    // Para guardar IDs reales de combos
    private ArrayList<Mesa>    listaMesas;
    private ArrayList<Platillo> listaPlatillos;
 
    public FrmPedidos() {
        setTitle("Gestión de Pedidos");
        setSize(760, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
 
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(Color.WHITE);
 
        JLabel lblTitulo = new JLabel("📋  Gestión de Pedidos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(230, 81, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
 
        // ── Formulario ────────────────────────────────────────────
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Pedido"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
 
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("ID:"), gbc);
        txtId = new JTextField(8); txtId.setEditable(false);
        txtId.setBackground(new Color(238, 238, 238));
        gbc.gridx = 1; panelForm.add(txtId, gbc);
 
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Cliente:"), gbc);
        txtCliente = new JTextField(25);
        gbc.gridx = 1; panelForm.add(txtCliente, gbc);
 
        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Mesa:"), gbc);
        cmbMesa = new JComboBox<>();
        gbc.gridx = 1; panelForm.add(cmbMesa, gbc);
 
        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(new JLabel("Platillo:"), gbc);
        cmbPlatillo = new JComboBox<>();
        gbc.gridx = 1; panelForm.add(cmbPlatillo, gbc);
 
        gbc.gridx = 0; gbc.gridy = 4; panelForm.add(new JLabel("Total ($):"), gbc);
        txtTotal = new JTextField(10);
        txtTotal.setEditable(false);
        txtTotal.setBackground(new Color(238, 238, 238));
        gbc.gridx = 1; panelForm.add(txtTotal, gbc);
 
        // ── Botones ───────────────────────────────────────────────
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
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        panelForm.add(panelBotones, gbc);
 
        // ── Tabla ─────────────────────────────────────────────────
        String[] columnas = {"ID", "Cliente", "Mesa", "Platillo", "Total"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.setSelectionBackground(new Color(255, 224, 178));
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(70);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Pedidos"));
 
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelForm, scroll);
        split.setDividerSize(5);
        split.setResizeWeight(0.48);
        split.setBorder(null);
        panelPrincipal.add(split, BorderLayout.CENTER);
        add(panelPrincipal);
 
        cargarCombos();
        cargarTabla();
 
        // Cuando cambia el platillo, actualizar el precio automáticamente
        cmbPlatillo.addActionListener(e -> actualizarPrecio());
 
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtId.setText(modelo.getValueAt(fila, 0).toString());
                txtCliente.setText(modelo.getValueAt(fila, 1).toString());
                // Buscar mesa en combo
                String mesaVal = modelo.getValueAt(fila, 2).toString();
                for (int i = 0; i < cmbMesa.getItemCount(); i++) {
                    if (cmbMesa.getItemAt(i).startsWith(mesaVal + " ") || cmbMesa.getItemAt(i).equals("Mesa " + mesaVal)) {
                        cmbMesa.setSelectedIndex(i); break;
                    }
                }
                // Buscar platillo en combo
                String platVal = modelo.getValueAt(fila, 3).toString();
                for (int i = 0; i < cmbPlatillo.getItemCount(); i++) {
                    if (cmbPlatillo.getItemAt(i).contains(platVal)) {
                        cmbPlatillo.setSelectedIndex(i); break;
                    }
                }
                txtTotal.setText(modelo.getValueAt(fila, 4).toString());
            }
        });
 
        btnGuardar.addActionListener(e    -> guardar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e   -> eliminar());
        btnLimpiar.addActionListener(e    -> limpiar());
    }
 
    /** Carga mesas y platillos disponibles en los ComboBox. */
    private void cargarCombos() {
        cmbMesa.removeAllItems();
        listaMesas = mesaBD.mostrarMesas();
        for (Mesa m : listaMesas) {
            cmbMesa.addItem("Mesa " + m.getNumeroMesa() + " [" + m.getEstado() + "]");
        }
 
        cmbPlatillo.removeAllItems();
        listaPlatillos = platilloBD.mostrarPlatillos();
        for (Platillo p : listaPlatillos) {
            cmbPlatillo.addItem(p.getNombre() + " ($" + String.format("%.2f", p.getPrecio()) + ")");
        }
        actualizarPrecio();
    }
 
    /** Actualiza el campo Total según el platillo seleccionado. */
    private void actualizarPrecio() {
        int idx = cmbPlatillo.getSelectedIndex();
        if (idx >= 0 && idx < listaPlatillos.size()) {
            txtTotal.setText(String.format("%.2f", listaPlatillos.get(idx).getPrecio()));
        }
    }
 
    private void guardar() {
        if (!validarCampos()) return;
        Pedido p = new Pedido();
        p.setCliente(txtCliente.getText().trim());
        p.setMesa(listaMesas.get(cmbMesa.getSelectedIndex()).getNumeroMesa());
        p.setPlatillo(listaPlatillos.get(cmbPlatillo.getSelectedIndex()).getNombre());
        p.setTotal(listaPlatillos.get(cmbPlatillo.getSelectedIndex()).getPrecio());
        pedidoBD.insertarPedido(p);
        JOptionPane.showMessageDialog(this, "Pedido registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        limpiar(); cargarTabla();
    }
 
    private void actualizar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) return;
        int confirm = JOptionPane.showConfirmDialog(this, "¿Actualizar este pedido?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            pedidoBD.actualizarPedido(
                Integer.parseInt(txtId.getText()),
                txtCliente.getText().trim(),
                listaMesas.get(cmbMesa.getSelectedIndex()).getNumeroMesa(),
                listaPlatillos.get(cmbPlatillo.getSelectedIndex()).getNombre(),
                listaPlatillos.get(cmbPlatillo.getSelectedIndex()).getPrecio()
            );
            JOptionPane.showMessageDialog(this, "Pedido actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar(); cargarTabla();
        }
    }
 
    private void eliminar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este pedido?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            pedidoBD.eliminarPedido(Integer.parseInt(txtId.getText()));
            JOptionPane.showMessageDialog(this, "Pedido eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar(); cargarTabla();
        }
    }
 
    private void cargarTabla() {
        modelo.setRowCount(0);
        ArrayList<Pedido> lista = pedidoBD.mostrarPedidos();
        for (Pedido p : lista) {
            modelo.addRow(new Object[]{
                p.getIdPedido(), p.getCliente(), p.getMesa(),
                p.getPlatillo(), String.format("$%.2f", p.getTotal())
            });
        }
    }
 
    private boolean validarCampos() {
        if (txtCliente.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del cliente no puede estar vacío.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cmbMesa.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay mesas registradas. Registra una mesa primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (cmbPlatillo.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay platillos registrados.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
 
    private void limpiar() {
        txtId.setText(""); txtCliente.setText("");
        cargarCombos(); tabla.clearSelection();
    }
 
    private JButton boton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color); btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false); btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(110, 32));
        return btn;
    }
}
