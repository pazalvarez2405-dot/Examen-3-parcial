package Vistas;
 
import Entidades.Platillo;
import CRUD.PlatilloBD;
 
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
 
/**
 * Formulario CRUD para la gestión de Platillos.
 */
public class FrmPlatillos extends JFrame {
 
    private final PlatilloBD platilloBD = new PlatilloBD();
 
    private JTextField txtId, txtNombre, txtDescripcion, txtPrecio;
    private JTable tabla;
    private DefaultTableModel modelo;
 
    public FrmPlatillos() {
        setTitle("Gestión de Platillos");
        setSize(720, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
 
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(Color.WHITE);
 
        JLabel lblTitulo = new JLabel("🍕  Gestión de Platillos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(46, 125, 50));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
 
        // ── Formulario ────────────────────────────────────────────
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Platillo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
 
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("ID:"), gbc);
        txtId = new JTextField(8); txtId.setEditable(false);
        txtId.setBackground(new Color(238, 238, 238));
        gbc.gridx = 1; panelForm.add(txtId, gbc);
 
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField(25);
        gbc.gridx = 1; panelForm.add(txtNombre, gbc);
 
        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Descripción:"), gbc);
        txtDescripcion = new JTextField(25);
        gbc.gridx = 1; panelForm.add(txtDescripcion, gbc);
 
        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(new JLabel("Precio ($):"), gbc);
        txtPrecio = new JTextField(10);
        gbc.gridx = 1; panelForm.add(txtPrecio, gbc);
 
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
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panelForm.add(panelBotones, gbc);
 
        // ── Tabla ─────────────────────────────────────────────────
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.setSelectionBackground(new Color(200, 230, 201));
        // Ancho de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(70);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Menú de Platillos"));
 
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelForm, scroll);
        split.setDividerSize(5);
        split.setResizeWeight(0.42);
        split.setBorder(null);
        panelPrincipal.add(split, BorderLayout.CENTER);
        add(panelPrincipal);
 
        cargarTabla();
 
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtId.setText(modelo.getValueAt(fila, 0).toString());
                txtNombre.setText(modelo.getValueAt(fila, 1).toString());
                txtDescripcion.setText(modelo.getValueAt(fila, 2).toString());
                txtPrecio.setText(modelo.getValueAt(fila, 3).toString());
            }
        });
 
        btnGuardar.addActionListener(e    -> guardar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e   -> eliminar());
        btnLimpiar.addActionListener(e    -> limpiar());
    }
 
    private void guardar() {
        if (!validarCampos()) return;
        Platillo p = new Platillo();
        p.setNombre(txtNombre.getText().trim());
        p.setDescripcion(txtDescripcion.getText().trim());
        p.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
        platilloBD.insertarPlatillo(p);
        JOptionPane.showMessageDialog(this, "Platillo registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        limpiar(); cargarTabla();
    }
 
    private void actualizar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un platillo de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) return;
        int confirm = JOptionPane.showConfirmDialog(this, "¿Actualizar este platillo?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            platilloBD.actualizarPlatillo(
                Integer.parseInt(txtId.getText()),
                txtNombre.getText().trim(),
                txtDescripcion.getText().trim(),
                Double.parseDouble(txtPrecio.getText().trim())
            );
            JOptionPane.showMessageDialog(this, "Platillo actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar(); cargarTabla();
        }
    }
 
    private void eliminar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un platillo de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este platillo?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            platilloBD.eliminarPlatillo(Integer.parseInt(txtId.getText()));
            JOptionPane.showMessageDialog(this, "Platillo eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar(); cargarTabla();
        }
    }
 
    private void cargarTabla() {
        modelo.setRowCount(0);
        ArrayList<Platillo> lista = platilloBD.mostrarPlatillos();
        for (Platillo p : lista) {
            modelo.addRow(new Object[]{p.getIdPlatillo(), p.getNombre(), p.getDescripcion(),
                String.format("$%.2f", p.getPrecio())});
        }
    }
 
    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            if (precio <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número mayor a 0.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
 
    private void limpiar() {
        txtId.setText(""); txtNombre.setText("");
        txtDescripcion.setText(""); txtPrecio.setText("");
        tabla.clearSelection();
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
