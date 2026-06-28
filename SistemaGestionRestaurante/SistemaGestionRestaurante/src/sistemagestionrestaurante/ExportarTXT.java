package sistemagestionrestaurante;

import Entidades.Pedido;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExportarTXT {
    public void exportarPedidos(ArrayList<Pedido> pedidos) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("pedidos.txt"));
            bw.write("===== PEDIDOS DEL RESTAURANTE =====");
            bw.newLine();
            bw.newLine();
            for (Pedido p : pedidos) {
                bw.write("ID Pedido: " + p.getIdPedido());
                bw.newLine();
                bw.write("Cliente: " + p.getCliente());
                bw.newLine();
                bw.write("Mesa: " + p.getMesa());
                bw.newLine();
                bw.write("Platillo: " + p.getPlatillo());
                bw.newLine();
                bw.write("Total: $" + p.getTotal());
                bw.newLine();
                bw.write("--------------------------------");
                bw.newLine();
                bw.newLine();
            }
            bw.close();
            System.out.println("Archivo pedidos.txt generado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al exportar: " + e.getMessage());
        }
    }
}