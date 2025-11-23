
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