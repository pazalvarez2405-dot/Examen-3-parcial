

CREATE DATABASE restaurante;

USE restaurante;
CREATE TABLE clientes(
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL
);
USE restaurante;
CREATE TABLE platillos(
    id_platillo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio DOUBLE NOT NULL
);

CREATE TABLE mesas(
    id_mesa INT AUTO_INCREMENT PRIMARY KEY,
    numero_mesa INT NOT NULL,
    estado VARCHAR(20) NOT NULL
);

CREATE TABLE pedidos(
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    cliente VARCHAR(100) NOT NULL,
    mesa INT NOT NULL,
    platillo VARCHAR(100) NOT NULL,
    total DOUBLE NOT NULL
);
Use restaurante;
INSERT INTO platillos(nombre, descripcion, precio)
VALUES
('Pizza Hawaiana','Pizza con jamón y piña',150),
('Hamburguesa Especial','Carne, queso y tocino',120),
('Tacos al Pastor','Orden de 5 tacos',80),
('Enchiladas Verdes','Pollo con salsa verde',95),
('Pasta Alfredo','Pasta con salsa cremosa',110),
('Refresco','Bebida de 600 ml',25),
('Agua de Horchata','Vaso de 500 ml',30);