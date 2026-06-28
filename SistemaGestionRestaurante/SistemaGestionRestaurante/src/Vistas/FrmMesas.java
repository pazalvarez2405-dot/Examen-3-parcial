package Vistas;
 
import Entidades.Mesa;
import CRUD.MesaBD;
 
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
 
/**
 * Formulario CRUD para la gestión de Mesas.
 */
public class FrmMesas extends JFrame {
 
    private final MesaBD mesaBD = new MesaBD();
 
    private JTextField txtId, txtNumeroMesa;
    private JComboBox<String> cmbEstado;
    private JTable tabla;
    private DefaultTableModel modelo;
 
    public FrmMesas() {
        setTitle("Gestión de Mesas");
        setSize(650, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
 
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(Color.WHITE);
 
        JLabel lblTitulo = new JLabel("🪑  Gestión de Mesas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(2, 119, 189));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
 
        // ── Formulario ────────────────────────────────────────────
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de la Mesa"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
 
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("ID:"), gbc);
        txtId = new JTextField(8);
        txtId.setEditable(false);
        txtId.setBackground(new Color(238, 238, 238));
        gbc.gridx = 1; panelForm.add(txtId, gbc);
 
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Número de Mesa:"), gbc);
        txtNumeroMesa = new JTextField(20);
        gbc.gridx = 1; panelForm.add(txtNumeroMesa, gbc);
 
        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Estado:"), gbc);
        cmbEstado = new JComboBox<>(new String[]{"disponible", "ocupada", "reservada"});
        gbc.gridx = 1; panelForm.add(cmbEstado, gbc);
 
        // ── Botones ───────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.setBackground(Color.WHITE);
        JButton btnGuardar    = boton("Guardar",    new Color(46, 125, 50));
        JButton btnActualizar = boton("Actualizar", new Color(2, 119, 189));
        JButton btnEliminar   = boton("Eliminar",   new Color(183, 28, 28));
        JButton btnLimpiar    = boton("Limpiar",    new Color(97, 97, 97));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panelForm.add(panelBotones, gbc);
 
        // ── Tabla ─────────────────────────────────────────────────
        String[] columnas = {"ID", "Número de Mesa", "Estado"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.setSelectionBackground(new Color(179, 229, 252));
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Mesas"));
 
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelForm, scroll);
        split.setDividerSize(5);
        split.setResizeWeight(0.38);
        split.setBorder(null);
        panelPrincipal.add(split, BorderLayout.CENTER);
        add(panelPrincipal);
 
        cargarTabla();
 
        // Selección de fila
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtId.setText(modelo.getValueAt(fila, 0).toString());
                txtNumeroMesa.setText(modelo.getValueAt(fila, 1).toString());
                cmbEstado.setSelectedItem(modelo.getValueAt(fila, 2).toString());
            }
        });
 
        btnGuardar.addActionListener(e    -> guardar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e   -> eliminar());
        btnLimpiar.addActionListener(e    -> limpiar());
    }
 
    private void guardar() {
        if (!validarCampos()) return;
        Mesa m = new Mesa();
        m.setNumeroMesa(Integer.parseInt(txtNumeroMesa.getText().trim()));
        m.setEstado(cmbEstado.getSelectedItem().toString());
        mesaBD.insertarMesa(m);
        JOptionPane.showMessageDialog(this, "Mesa registrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        limpiar(); cargarTabla();
    }
 
    private void actualizar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona una mesa de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) return;
        int confirm = JOptionPane.showConfirmDialog(this, "¿Actualizar esta mesa?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            mesaBD.actualizarMesa(
                Integer.parseInt(txtId.getText()),
                Integer.parseInt(txtNumeroMesa.getText().trim()),
                cmbEstado.getSelectedItem().toString()
            );
            JOptionPane.showMessageDialog(this, "Mesa actualizada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar(); cargarTabla();
        }
    }
 
    private void eliminar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona una mesa de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar esta mesa?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            mesaBD.eliminarMesa(Integer.parseInt(txtId.getText()));
            JOptionPane.showMessageDialog(this, "Mesa eliminada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar(); cargarTabla();
        }
    }
 
    private void cargarTabla() {
        modelo.setRowCount(0);
        ArrayList<Mesa> lista = mesaBD.mostrarMesas();
        for (Mesa m : lista) {
            modelo.addRow(new Object[]{m.getIdMesa(), m.getNumeroMesa(), m.getEstado()});
        }
    }
 
    private boolean validarCampos() {
        String num = txtNumeroMesa.getText().trim();
        if (num.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El número de mesa no puede estar vacío.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int n = Integer.parseInt(num);
            if (n <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El número de mesa debe ser un entero positivo.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
 
    private void limpiar() {
        txtId.setText(""); txtNumeroMesa.setText("");
        cmbEstado.setSelectedIndex(0); tabla.clearSelection();
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
