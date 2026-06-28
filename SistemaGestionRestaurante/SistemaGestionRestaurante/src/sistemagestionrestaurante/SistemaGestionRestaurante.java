package sistemagestionrestaurante;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import Vistas.MenuPrincipal;

public class SistemaGestionRestaurante {

    public static void main(String[] args) {
        // Usar el look and feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Error al cargar estilo: " + e.getMessage());
        }

        // Lanzar la interfaz gráfica en el hilo de Swing
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}