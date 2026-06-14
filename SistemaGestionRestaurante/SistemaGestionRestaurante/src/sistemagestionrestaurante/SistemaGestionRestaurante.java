package sistemagestionrestaurante;

import java.util.ArrayList;
import java.util.Scanner;

public class SistemaGestionRestaurante {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ClienteBD clienteBD = new ClienteBD();
        MesaBD mesaBD = new MesaBD();
        PedidoBD pedidoBD = new PedidoBD();
        PlatilloBD platilloBD = new PlatilloBD();
        ExportarTXT exportarTXT = new ExportarTXT();

        int opcion;
        do {
            System.out.println("\n==============================");
            System.out.println("   SISTEMA DE RESTAURANTE");
            System.out.println("==============================");
            System.out.println("1. Gestionar Clientes");
            System.out.println("2. Gestionar Mesas");
            System.out.println("3. Gestionar Pedidos");
            System.out.println("4. Exportar Pedidos a TXT");
            System.out.println("5. Salir");
            System.out.print("Seleccione: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    menuClientes(sc, clienteBD);
                    break;
                case 2:
                    menuMesas(sc, mesaBD);
                    break;
                case 3:
                    menuPedidos(sc, pedidoBD, platilloBD);
                    break;
                case 4:
                    ArrayList<Pedido> pedidos = pedidoBD.mostrarPedidos();
                    exportarTXT.exportarPedidos(pedidos);
                    break;
                case 5:
                    System.out.println("Gracias por usar el sistema.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    // ========== MENÚ CLIENTES ==========
    public static void menuClientes(Scanner sc, ClienteBD clienteBD) {
        int opcion;
        do {
            System.out.println("\n===== CLIENTES =====");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Mostrar Clientes");
            System.out.println("3. Actualizar Cliente");
            System.out.println("4. Eliminar Cliente");
            System.out.println("5. Regresar");
            System.out.print("Seleccione: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    Cliente c = new Cliente();
                    System.out.print("Nombre: ");
                    c.setNombre(sc.nextLine());
                    System.out.print("Teléfono: ");
                    c.setTelefono(sc.nextLine());
                    clienteBD.insertarCliente(c);
                    break;
                case 2:
                    for (Cliente cl : clienteBD.mostrarClientes()) {
                        cl.mostrarInformacion();
                        System.out.println("---------------------");
                    }
                    break;
                case 3:
                    System.out.print("ID Cliente: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nuevo nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Nuevo teléfono: ");
                    String tel = sc.nextLine();
                    clienteBD.actualizarCliente(id, nombre, tel);
                    break;
                case 4:
                    System.out.print("ID a eliminar: ");
                    int elim = sc.nextInt();
                    clienteBD.eliminarCliente(elim);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    // ========== MENÚ MESAS ==========
    public static void menuMesas(Scanner sc, MesaBD mesaBD) {
        int opcion;
        do {
            System.out.println("\n===== MESAS =====");
            System.out.println("1. Registrar Mesa");
            System.out.println("2. Mostrar Mesas");
            System.out.println("3. Actualizar Mesa");
            System.out.println("4. Eliminar Mesa");
            System.out.println("5. Regresar");
            System.out.print("Seleccione: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    Mesa m = new Mesa();
                    System.out.print("Número de mesa: ");
                    m.setNumeroMesa(sc.nextInt());
                    sc.nextLine();
                    System.out.print("Estado (Disponible/Ocupada): ");
                    m.setEstado(sc.nextLine());
                    mesaBD.insertarMesa(m);
                    break;
                case 2:
                    for (Mesa me : mesaBD.mostrarMesas()) {
                        System.out.println("ID: " + me.getIdMesa() + " | Mesa: " + me.getNumeroMesa() + " | Estado: " + me.getEstado());
                        System.out.println("----------------------");
                    }
                    break;
                case 3:
                    System.out.print("ID Mesa: ");
                    int id = sc.nextInt();
                    System.out.print("Nuevo número: ");
                    int num = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nuevo estado: ");
                    String est = sc.nextLine();
                    mesaBD.actualizarMesa(id, num, est);
                    break;
                case 4:
                    System.out.print("ID a eliminar: ");
                    int elim = sc.nextInt();
                    mesaBD.eliminarMesa(elim);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    // ========== MENÚ PEDIDOS (MODIFICADO) ==========
    public static void menuPedidos(Scanner sc, PedidoBD pedidoBD, PlatilloBD platilloBD) {
        int opcion;
        do {
            System.out.println("\n===== PEDIDOS =====");
            System.out.println("1. Registrar Pedido");
            System.out.println("2. Mostrar Pedidos");
            System.out.println("3. Actualizar Pedido");
            System.out.println("4. Eliminar Pedido");
            System.out.println("5. Regresar");
            System.out.print("Seleccione: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    // Mostrar lista de platillos disponibles
                    System.out.println("\n--- PLATILLOS DISPONIBLES ---");
                    ArrayList<Platillo> platillos = platilloBD.mostrarPlatillos();
                    for (Platillo p : platillos) {
                        System.out.println("ID: " + p.getIdPlatillo() + " | " + p.getNombre() + " | $" + p.getPrecio());
                    }
                    System.out.print("\nID del platillo que desea: ");
                    int idPlatillo = sc.nextInt();
                    sc.nextLine();

                    // Obtener el platillo elegido
                    Platillo elegido = platilloBD.obtenerPlatilloPorId(idPlatillo);
                    if (elegido == null) {
                        System.out.println("Platillo no válido.");
                        break;
                    }

                    // Datos del pedido
                    Pedido pedido = new Pedido();
                    System.out.print("Nombre del cliente: ");
                    pedido.setCliente(sc.nextLine());
                    System.out.print("Número de mesa: ");
                    pedido.setMesa(sc.nextInt());
                    sc.nextLine();

                    // Asignamos el platillo y el total automático (precio del platillo)
                    pedido.setPlatillo(elegido.getNombre());
                    pedido.setTotal(elegido.getPrecio());

                    pedidoBD.insertarPedido(pedido);
                    break;

                case 2:
                    for (Pedido p : pedidoBD.mostrarPedidos()) {
                        System.out.println("ID Pedido: " + p.getIdPedido());
                        System.out.println("Cliente: " + p.getCliente());
                        System.out.println("Mesa: " + p.getMesa());
                        System.out.println("Platillo: " + p.getPlatillo());
                        System.out.println("Total: $" + p.getTotal());
                        System.out.println("----------------------");
                    }
                    break;

                case 3:
                    System.out.print("ID Pedido a actualizar: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nuevo cliente: ");
                    String cliente = sc.nextLine();
                    System.out.print("Nueva mesa: ");
                    int mesa = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nuevo platillo: ");
                    String platillo = sc.nextLine();
                    System.out.print("Nuevo total: ");
                    double total = sc.nextDouble();
                    pedidoBD.actualizarPedido(id, cliente, mesa, platillo, total);
                    break;

                case 4:
                    System.out.print("ID Pedido a eliminar: ");
                    int elim = sc.nextInt();
                    pedidoBD.eliminarPedido(elim);
                    break;

                case 5:
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }
}