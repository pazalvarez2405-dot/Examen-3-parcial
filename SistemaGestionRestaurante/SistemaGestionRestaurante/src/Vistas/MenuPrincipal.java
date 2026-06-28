package Vistas;
 
import javax.swing.*;
import java.awt.*;
 
/**
 * Ventana principal del Sistema de Gestión de Restaurante.
 * Permite navegar hacia los módulos de Clientes, Mesas, Platillos y Pedidos.
 */
public class MenuPrincipal extends JFrame {
 
    public MenuPrincipal() {
        setTitle("Sistema de Gestión de Restaurante");
        setSize(520, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
 
        // Panel principal con fondo degradado simulado
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(245, 245, 245));
 
        // ── Encabezado ──────────────────────────────────────────
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(183, 28, 28));   // rojo oscuro
        panelHeader.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
 
        JLabel lblTitulo = new JLabel("🍽  Restaurante", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
 
        JLabel lblSubtitulo = new JLabel("Sistema de Gestión", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(255, 205, 210));
 
        panelHeader.add(lblTitulo, BorderLayout.CENTER);
        panelHeader.add(lblSubtitulo, BorderLayout.SOUTH);
 
        // ── Botones del menú ─────────────────────────────────────
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 20, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));
        panelBotones.setBackground(new Color(245, 245, 245));
 
        JButton btnClientes  = crearBoton("👤  Clientes",  new Color(21, 101, 192));
        JButton btnMesas     = crearBoton("🪑  Mesas",     new Color(2, 119, 189));
        JButton btnPlatillos = crearBoton("🍕  Platillos", new Color(46, 125, 50));
        JButton btnPedidos   = crearBoton("📋  Pedidos",   new Color(230, 81, 0));
 
        panelBotones.add(btnClientes);
        panelBotones.add(btnMesas);
        panelBotones.add(btnPlatillos);
        panelBotones.add(btnPedidos);
 
        // ── Pie de página ────────────────────────────────────────
        JLabel lblFooter = new JLabel("Paradigmas de Programación II  •  UNSIJ", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
 
        panelPrincipal.add(panelHeader,  BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        panelPrincipal.add(lblFooter,    BorderLayout.SOUTH);
        add(panelPrincipal);
 
        // ── Acciones ─────────────────────────────────────────────
        btnClientes.addActionListener(e  -> new FrmClientes().setVisible(true));
        btnMesas.addActionListener(e     -> new FrmMesas().setVisible(true));
        btnPlatillos.addActionListener(e -> new FrmPlatillos().setVisible(true));
        btnPedidos.addActionListener(e   -> new FrmPedidos().setVisible(true));
    }
 
    /** Crea un botón con estilo uniforme. */
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 65));
 
        // Efecto hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            final Color original = color;
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(original.darker());
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(original);
            }
        });
        return btn;
    }
 
    public static void main(String[] args) {
        // Aspecto del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
 
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
