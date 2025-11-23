
INSERT INTO TipoBanco (TipoBancoID, NombreTipo) VALUES (1, 'Banca Privada');
INSERT INTO TipoBanco (TipoBancoID, NombreTipo) VALUES (2, 'Banca Estatal');

INSERT INTO Bancos (BancoID, NombreBanco, TipoBancoID) VALUES (1, 'BCP', 1);
INSERT INTO Bancos (BancoID, NombreBanco, TipoBancoID) VALUES (2, 'Interbank', 1);
INSERT INTO Bancos (BancoID, NombreBanco, TipoBancoID) VALUES (3, 'BBVA', 1);
INSERT INTO Bancos (BancoID, NombreBanco, TipoBancoID) VALUES (4, 'Banco de la Nación', 2);

INSERT INTO MetodoPago (MetodoPagoID, NombreMetodo, Descripcion) VALUES (1, 'Tarjeta Crédito', 'Visa/Mastercard');
INSERT INTO MetodoPago (MetodoPagoID, NombreMetodo, Descripcion) VALUES (2, 'Tarjeta Débito', 'Saldo directo');

INSERT INTO Estados (EstadoID, Descripcion) VALUES (1, 'Pendiente');
INSERT INTO Estados (EstadoID, Descripcion) VALUES (2, 'Pagado');
INSERT INTO Estados (EstadoID, Descripcion) VALUES (3, 'Enviado');
INSERT INTO Estados (EstadoID, Descripcion) VALUES (4, 'Entregado');
INSERT INTO Estados (EstadoID, Descripcion) VALUES (5, 'Cancelado');

INSERT INTO DireccionesEntrega (ClienteID, Direccion, Ciudad, Departamento, Pais, EsPrincipal) VALUES (1, 'Av. Larco 123', 'Lima', 'Lima', 'Perú', 1);
INSERT INTO Productos (Nombre, Descripcion, Precio, Stock) VALUES ('Chifle Clásico', 'El sabor tradicional, crujiente y con el punto exacto de sal marina.', 3.50, 50);
INSERT INTO Productos (Nombre, Descripcion, Precio, Stock) VALUES ('Chifle Picante', 'Para los valientes. Con un toque de ají limo y especias.', 4.00, 45);
INSERT INTO Productos (Nombre, Descripcion, Precio, Stock) VALUES ('Chifle con Limón', 'Ácido y refrescante. La combinación perfecta para el plátano.', 4.50, 30);
INSERT INTO Productos (Nombre, Descripcion, Precio, Stock) VALUES ('Chifle Dulce (Madurito)', 'Hecho de plátano maduro, naturalmente dulce y caramelizado.', 4.20, 25);
INSERT INTO Productos (Nombre, Descripcion, Precio, Stock) VALUES ('Chifle al Ajo', 'Crujientes láminas con un intenso sabor a ajo tostado.', 3.80, 40);
INSERT INTO Productos (Nombre, Descripcion, Precio, Stock) VALUES ('Chifle Familiar', 'Bolsa gigante de 500g ideal para compartir en reuniones.', 8.50, 20);
INSERT INTO Productos (Nombre, Descripcion, Precio, Stock) VALUES ('Chifle con Cecina', 'Edición Premium con trocitos de cecina ahumada de la selva.', 6.00, 15);
INSERT INTO Productos (Nombre, Descripcion, Precio, Stock) VALUES ('Chifle Ondulado', 'Corte especial ondulado para mayor crocancia y dip.', 3.90, 60);
INSERT INTO Productos (Nombre, Descripcion, Precio, Stock) VALUES ('Mix Tropical', 'Mezcla de chifles, camote frito y yuca frita.', 5.00, 35);
INSERT INTO Productos (Nombre, Descripcion, Precio, Stock) VALUES ('Chifle Sin Sal', 'La opción saludable, 100% natural sin sodio añadido.', 3.50, 20);
INSERT INTO DireccionesEntrega (ClienteID, Direccion, Ciudad, Departamento, Pais, Referencia, EsPrincipal) VALUES (1, 'Calle Los Pinos 456', 'Lima', 'Lima', 'Perú', 'Puerta azul', 1);

UPDATE Productos SET Imagen = 'https://i.ibb.co/wNKx40z/chifle-clasico.jpg' WHERE Nombre LIKE '%Clásico%';
UPDATE Productos SET Imagen = 'https://i.ibb.co/xz806z9/chifle-picante.jpg' WHERE Nombre LIKE '%Picante%';
UPDATE Productos SET Imagen = 'https://i.ibb.co/0jvqW5S/chifle-limon.jpg' WHERE Nombre LIKE '%Limón%';
UPDATE Productos SET Imagen = 'https://i.ibb.co/3rT8XgD/chifle-dulce.jpg' WHERE Nombre LIKE '%Dulce%';
-- Puedes usar una imagen genérica para el resto si quieres
UPDATE Productos SET Imagen = 'https://via.placeholder.com/300x300.png?text=ChiflePop' WHERE Imagen IS NULL;