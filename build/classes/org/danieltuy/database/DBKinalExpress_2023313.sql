-- drop database if exists DBKinalExpress_2023313;
create database DBKinalExpress_2023313;
use DBKinalExpress_2023313;
set global time_zone = '-6:00';

-- Clientes
create table Clientes
(
    codigoCliente int not null,
    NITCliente varchar (10),
    nombresCliente varchar(50),
    apellidosCliente varchar(50),
    direccionCliente varchar(150),
    telefonoCliente varchar(8),
    correoCliente varchar(45),
    primary key PK_codigoCliente (codigoCliente)
);

-- TipoProducto
create table TipoProducto
(
    codigoTipoProducto int not null,
    descripcion varchar(45),
    primary key PK_codigoTipoProducto (codigoTipoProducto)
);

-- Compras
create table Compras
(
    numeroDocumento int not null,
    fechaDocumento date,
    descripcion varchar(60),
    totalDocumento decimal(10,2),
    primary key PK_numeroDocumento (numeroDocumento)
);

-- Proveedores
create table Proveedores
(
    codigoProveedor int not null,
    NITProveedor varchar (10),
    nombresProveedor varchar(60),
    apellidosProveedor varchar(60),
    direccionProveedor varchar(150),
    razonSocial varchar(60),
    contactoPrincipal varchar(100),
    paginaWeb varchar(50),
    primary key PK_codigoProveedor (codigoProveedor)
);

-- cargo empleado
create table CargoEmpleado
(
    codigoCargoEmpleado int not null,
    nombreCargo varchar(45),
    descripcionCargo varchar(45),
    primary key PK_codigoCargoEmpleado (codigoCargoEmpleado)
);

-- TelefonoProveedor
create table TelefonoProveedor
(
	codigoTelefonoProveedor int not null,
    numeroPrincipal varchar(8),
    numeroSecundario varchar(8),
    observaciones varchar(45),
    codigoProveedor int not null,
    primary key PK_codigoTelefonoProveedor (codigoTelefonoProveedor),
	foreign key (codigoProveedor) references Proveedores(codigoProveedor) on delete cascade
);

-- Empleados
create table Empleados
(
	codigoEmpleado int not null,
    nombresEmpleados varchar(50),
    apellidosEmpleados varchar(50),
    sueldo decimal(10,2),
    direccion varchar(150),
    turno varchar(15),
    codigoCargoEmpleado int,
    primary key PK_codigoEmpleado (codigoEmpleado),
    foreign key (codigoCargoEmpleado) references CargoEmpleado(codigoCargoEmpleado) on delete cascade
);

-- EmailProveedor
create table EmailProveedor
(
	codigoEmailProveedor int not null,
    emailProveedor varchar(50),
    descripcion varchar(100),
    codigoProveedor int not null,
    primary key PK_codigoEmailProveedor (codigoEmailProveedor),
	foreign key (codigoProveedor) references Proveedores(codigoProveedor) on delete cascade

);

-- Factura
create table Factura
(
	numeroFactura int not null,
	estado varchar(50),
    totalFactura decimal(10,2),
    fechaFactura varchar(45),
    codigoCliente int not null,
    codigoEmpleado int not null,
    primary key PK_numeroFactura (numeroFactura),
	foreign key (codigoCliente) references Clientes(codigoCliente) on delete cascade,
	foreign key (codigoEmpleado) references Empleados(codigoEmpleado) on delete cascade
);

-- Productos
create table Productos
(
	codigoProducto varchar(15),
	descripcionProducto varchar(45),
    precioUnitario decimal(10,2),
    precioDocena decimal(10,2),
    precioMayor decimal(10,2),
    existencia int not null,
    codigoTipoProducto int not null,
    codigoProveedor int not null,
    primary key PK_codigoProducto (codigoProducto),
	foreign key (codigoTipoProducto) references TipoProducto(codigoTipoProducto) on delete cascade,
	foreign key (codigoProveedor) references Proveedores(codigoProveedor) on delete cascade
);

-- DetalleFactura
create table DetalleFactura
(
	codigoDetalleFactura int not null,
    precioUnitario decimal(10,2),
    cantidad int,
    numeroFactura int not null,
    codigoProducto varchar(15),
    primary key PK_codigoDetalleFactura (codigoDetalleFactura),
	foreign key (numeroFactura) references Factura(numeroFactura) on delete cascade,
    foreign key (codigoProducto) references Productos(codigoProducto) on delete cascade
);

-- DetalleCompra
create table DetalleCompra
(
	codigoDetalleCompra int not null,
    costoUnitario decimal(10,2),
    cantidad int not null,
    codigoProducto varchar(15),
    numeroDocumento int not null,
    primary key PK_codigoDetalleCompra (codigoDetalleCompra),
	foreign key (codigoProducto) references Productos(codigoProducto) on delete cascade,
    foreign key (numeroDocumento) references Compras(numeroDocumento) on delete cascade
);

 
-- Entidad Clientes procedimientos almacenados --

-- Agregar 

delimiter $$
create procedure sp_agregarClientes (in codClie int, in nitClie varchar(10), in nombresClie varchar(50),
in apellidosClie varchar(50), in direccionClie varchar(150), in teleClie varchar(8), in correoClie varchar(45))
begin
	insert into Clientes (Clientes.codigoCliente, Clientes.NITCliente, Clientes.nombresCliente,
    Clientes.apellidosCliente, Clientes.direccionCliente, Clientes.telefonoCliente, Clientes.correoCliente)
    values (codClie, nitClie, nombresClie, apellidosClie, direccionClie, teleClie, correoClie);
end$$
delimiter ;

call sp_agregarClientes(123456789, '65465467-9', 'Daniel Andres', 'Tuy Tuchez', 'Avenida 16-37 CAYALA', '12345678', 'danieltuy@gmail.com');
call sp_agregarClientes(161481461, '56464899-8', 'Javier Eduardo', 'Herrera Perez', 'Avenida 97-19 Guatemala', '56567898', 'jherrera@gmail.com');
call sp_agregarClientes(154664846, '15161655-1', 'Andres Miguel', 'Tuy Tuchez', 'Avenida 16-33 Zona 10', '15645616', 'amtt100@gmail.com');
call sp_agregarClientes(564980398, '19498494-2', 'Luis pedro', 'Alay Lopez', 'Avenida 97-14 Majaditas', '79893567', 'luisAlay@gmail.com');
call sp_agregarClientes(454894988, '65465467-9', 'Erick Jahir', 'Socop Colindres', 'Avenida 97-45 San Miguel Petapa', '45645678', 'erickSocop@gmail.com');
call sp_agregarClientes(114151415, '12345678-9', 'Ana María', 'Pérez González', 'Calle 123, Zona 1, Ciudad de Guatemala', '12345678', 'ana.perez@gmail.com');
call sp_agregarClientes(565651111, '98765432-1', 'Carlos Enrique', 'López Martínez', 'Boulevard Los Próceres 10-50, Zona 10, Ciudad de Guatemala', '87654321', 'carlos.lopez@gmail.com');
call sp_agregarClientes(515151518, '56473829-2', 'María Fernanda', 'Rodríguez Ramírez', 'Avenida Las Américas 5-67, Zona 13, Ciudad de Guatemala', '56473829', 'maria.rodriguez@gmail.com');


-- Listar 

delimiter $$
	create procedure sp_listarClientes ()
    begin
		select Clientes.codigoCliente, Clientes.NITCliente, Clientes.nombresCliente,
    Clientes.apellidosCliente, Clientes.direccionCliente, Clientes.telefonoCliente, Clientes.correoCliente  from Clientes;
    end$$
delimiter ;

call sp_listarClientes();

-- Buscar 

delimiter $$
	create procedure sp_buscarClientes(in codClie int)
    begin
	select Clientes.codigoCliente, Clientes.NITCliente, Clientes.nombresCliente, Clientes.apellidosCliente, Clientes.direccionCliente, 
    Clientes.telefonoCliente, Clientes.correoCliente from Clientes where Clientes.codigoCliente = codClie;
	end$$
delimiter ;

call sp_buscarClientes(123456789);	
call sp_buscarClientes(161481461);
call sp_buscarClientes(154664846);
call sp_buscarClientes(564980398);
call sp_buscarClientes(454894988);

-- Actualizar

delimiter $$
	create procedure sp_actualizarClientes(in codClie int, in nitClie varchar(10), in nombresClie varchar(50),
in apellidosClie varchar(50), in direccionClie varchar(150), in teleClie varchar(8), in correoClie varchar(45))
    begin
		update Clientes 
		set
        Clientes.NITCliente = nitClie,
        Clientes.nombresCliente = nombresClie,
        Clientes.apellidosCliente = apellidosClie,
        Clientes.direccionCliente = direccionClie, 
        Clientes.correoCliente = correoClie
        where 
        Clientes.codigoCliente = codClie;
    end$$
delimiter ;

call sp_actualizarClientes(564980398, '12347856-3', 'Luis', 'Alay', 'Avenida 98-15 Majaditas1', '78995566', 'pedroLopez@gmail.com');

-- Eliminar

delimiter $$
	create procedure sp_eliminarCliente (in codClie int)
    begin
		delete from Clientes where Clientes.codigoCliente = codClie;
    end$$
delimiter ;

-- call sp_eliminarCliente();

-- Entidad tipoProducto procedimientos almacenados --

-- agregar

delimiter $$
create procedure sp_agregarTipoProducto (in codTiPro int, in descri varchar(45))
begin
	insert into TipoProducto (TipoProducto.codigoTipoProducto, TipoProducto.descripcion)
    values (codTiPro, descri);
end$$
delimiter ;

call sp_agregarTipoProducto(1,'Aguas');
call sp_agregarTipoProducto(2,'Cremas para el cuerpo');
call sp_agregarTipoProducto(3,'Gaseosas');
call sp_agregarTipoProducto(4,'Filetes');
call sp_agregarTipoProducto(5,'Cepillos dentales');

-- Listar 

delimiter $$
	create procedure sp_listarTipoProducto ()
    begin
		select TipoProducto.codigoTipoProducto, TipoProducto.descripcion from TipoProducto;
    end$$
delimiter ;

call sp_listarTipoProducto();

-- Buscar 

delimiter $$
	create procedure sp_buscarTipoProducto(in codTiPro int)
    begin
	select TipoProducto.codigoTipoProducto, TipoProducto.descripcion from TipoProducto where TipoProducto.codigoTipoProducto = codTiPro;
	end$$
delimiter ;

call sp_buscarTipoProducto(1);
call sp_buscarTipoProducto(2);
call sp_buscarTipoProducto(3);
call sp_buscarTipoProducto(4);
call sp_buscarTipoProducto(5);

-- Actualizar

delimiter $$
	create procedure sp_actualizarTipoProducto(in codTiPro int, in descri varchar(45))
    begin
		update TipoProducto 
		set
        TipoProducto.descripcion = descri
        where 
        TipoProducto.codigoTipoProducto = codTiPro;
    end$$
delimiter ;

call sp_actualizarTipoProducto(1,'agua salvavidas');

-- Eliminar

delimiter $$
	create procedure sp_eliminarTipoProducto(in codTiPro int)
    begin
		delete from TipoProducto where TipoProducto.codigoTipoProducto  = codTiPro;
    end$$
delimiter ;

-- call sp_eliminarTipoProducto();

-- Entidad Compras procedimientos almacenados --

-- Agregar 

delimiter $$
create procedure sp_agregarCompras (in numDo int, in feDo date, in des varchar(60), in toDo decimal(10,0))
begin
	insert into Compras (Compras.numeroDocumento, Compras.fechaDocumento, Compras.descripcion,
    Compras.totalDocumento)
    values (numDo, feDo, des, toDo);
end$$
delimiter ;	

call sp_agregarCompras(1,'2023-05-21','Hoy 21 de Mayo se realizo una compra de', 100.00);
call sp_agregarCompras(2,'2023-06-30','Hoy 30 de Junio se realizo una compra de', 150.00);
call sp_agregarCompras(3,'2023-01-07','Hoy 7 de Enero se realizo una compra de', 50.00);
call sp_agregarCompras(4,'2023-02-21','Hoy 21 de Febrero se realizo una compra de', 200.00);
call sp_agregarCompras(5,'2023-12-24','Hoy 24 de Diciembre se realizo una compra de', 400.00);

-- Listar 

delimiter $$
	create procedure sp_listarCompras ()
    begin
		select Compras.numeroDocumento, Compras.fechaDocumento, Compras.descripcion,
    Compras.totalDocumento from Compras;
    end$$
delimiter ;

call sp_listarCompras();


-- Buscar 

delimiter $$
	create procedure sp_buscarCompras(in numDo int)
    begin
	select Compras.numeroDocumento, Compras.fechaDocumento, Compras.descripcion, Compras.totalDocumento 
    from Compras where Compras.numeroDocumento = numDo;
	end$$
delimiter ;

call sp_buscarCompras(1);
call sp_buscarCompras(2);
call sp_buscarCompras(3);
call sp_buscarCompras(4);
call sp_buscarCompras(5);

-- Actualizar

delimiter $$
	create procedure sp_actualizarCompras(in numDo int, in feDo date, in des varchar(60), in toDo decimal(10,0))
    begin
		update Compras
		set
        Compras.fechaDocumento = feDo,
        Compras.descripcion = des,
        Compras.totalDocumento = toDo
        where 
        Compras.numeroDocumento = numDo;
    end$$
delimiter ;

call sp_actualizarCompras(1,'2023-05-21','Hoy 21 de Mayo se realizo una compra de', 90.00);

-- Eliminar

delimiter $$
	create procedure sp_eliminarCompras(in numDo int)
    begin
		delete from Compras where Compras.numeroDocumento = numDo;
    end$$
delimiter ;

-- call sp_eliminarCompras();

-- Entidad Proveedores procedimientos almacenados --

-- Agregar 

delimiter $$
create procedure sp_agregarProveedores (in codPro int, in NPro varchar(10), in nomPro varchar(60), in apePro varchar(60), 
in direPro varchar(150), in rsPro varchar(60), in conprinPro varchar(100), in pwPro varchar(50))
begin
	insert into Proveedores (Proveedores.codigoProveedor, Proveedores.NITProveedor, Proveedores.nombresProveedor,
    Proveedores.apellidosProveedor, Proveedores.direccionProveedor, Proveedores.razonSocial, Proveedores.contactoPrincipal,
    Proveedores.paginaWeb)
    values (codPro, NPro, nomPro, apePro, direPro, rsPro, conprinPro, pwPro);
end$$
delimiter ;	

call sp_agregarProveedores(1,'12347894-9','Carlos José','Méndez Oliva','Almacén 18 carretera al Salvador', 'aguas', 'aguas12@gmail.com', 'www.aguas.com');
call sp_agregarProveedores(2,'56448494-7','José Luis','Martínez López','Km8 carretera el Atlántico', 'Cremas Nivea', 'cremasNIVEA@gmail.com', 'www.CREMASNIVEA.com');
call sp_agregarProveedores(3,'79849416-4','Juan Pablo','Pérez Ramírez','Zona1 10a avenida', 'Gasesoas el mirador', 'gaseosamirados@gmail.com','www.gasesosasMirador.com');
call sp_agregarProveedores(4,'98748651-1','Miguel Ángel','González Fernández','Almacén zona 16 ciudad Cayalá', 'Filetes y carnes', 'filetesCarnes@gmail.com', 'www.Tiposdecarnes.com');
call sp_agregarProveedores(5,'56415454-3','Andrés Felipe','Rodríguez Martínez','zona 10 almacen la Estación', 'Empresa colgate', 'colgate@gmail.com', 'www.colgate.com');

-- Listar 

delimiter $$
	create procedure sp_listarProveedores()
    begin
		select Proveedores.codigoProveedor, Proveedores.NITProveedor, Proveedores.nombresProveedor,Proveedores.apellidosProveedor, 
	Proveedores.direccionProveedor, Proveedores.razonSocial, Proveedores.contactoPrincipal, Proveedores.paginaWeb 
    from Proveedores;
    end$$
delimiter ;

call sp_listarProveedores();

-- Buscar 

delimiter $$
	create procedure sp_buscarProveedores(in codPro int)
    begin
	select Proveedores.codigoProveedor, Proveedores.codigoProveedor ,Proveedores.NITProveedor, Proveedores.nombresProveedor,Proveedores.apellidosProveedor, 
	Proveedores.direccionProveedor, Proveedores.razonSocial, Proveedores.contactoPrincipal, Proveedores.paginaWeb  
    from Proveedores where Proveedores.codigoProveedor = codPro;
	end$$
delimiter ;

call sp_buscarProveedores(1);
call sp_buscarProveedores(2);
call sp_buscarProveedores(3);
call sp_buscarProveedores(4);
call sp_buscarProveedores(5);

-- Actualizar

delimiter $$
	create procedure sp_actualizarProveedores(in codPro int, in NPro varchar(10), in nomPro varchar(60), 
    in apePro varchar(60), in direPro varchar(150), in rsPro varchar(60), in conprinPro varchar(100), 
    in pwPro varchar(50))
    begin
		update Proveedores
		set
		Proveedores.NITProveedor = NPro,
        Proveedores.nombresProveedor = nomPro,
        Proveedores.apellidosProveedor = apePro,
		Proveedores.direccionProveedor = direPro,
        Proveedores.razonSocial = rsPro,
        Proveedores.contactoPrincipal = conprinPro,
        Proveedores.paginaWeb = pwPro
        where 
        Proveedores.codigoProveedor = codPro;
    end$$
delimiter ;

call sp_actualizarProveedores(1,'12347894-9','Carlos José','Méndez Oliva','Almacén 18 carretera al Salvador', 'aguas Tuchez', 'aguas12@gmail.com', 'www.aguas.com');

-- Eliminar

delimiter $$
	create procedure sp_eliminarProveedores(in codPro int)
    begin
		delete from Proveedores where Proveedores.codigoProveedor = codPro;
    end$$
delimiter ;

-- call sp_eliminarProveedores();

-- Entidad cargoEmpleado procedimientos almacenados --

-- Agregar 

delimiter $$
create procedure sp_agregarCargoEmpleado (in codCargEmp int, in nomCarg varchar(45), in desCarg varchar(45))
begin
	insert into cargoEmpleado (cargoEmpleado.codigoCargoEmpleado, cargoEmpleado.nombreCargo, cargoEmpleado.descripcionCargo)
    values (codCargEmp, nomCarg, desCarg);
end$$
delimiter ;

call sp_agregarCargoEmpleado(1,  'Encargado de cuenta', 'Lleva la contabilidad de empresa');
call sp_agregarCargoEmpleado(2, 'Supervisor de Tics', 'Supervisa el area de Tics');
call sp_agregarCargoEmpleado(3, 'Gerente General', 'Encargado de ver por el trabajo de todos');
call sp_agregarCargoEmpleado(4, 'Ordenador', 'Acomodar estantes');
call sp_agregarCargoEmpleado(5, 'Empacador de productos', 'Empaca los productos');

-- Listar 

delimiter $$
	create procedure sp_listarCargoEmpleado ()
    begin
		select cargoEmpleado.codigoCargoEmpleado, cargoEmpleado.nombreCargo, cargoEmpleado.descripcionCargo 
        from cargoEmpleado;
    end$$
delimiter ;

call sp_listarCargoEmpleado();

-- Buscar 

delimiter $$
	create procedure sp_buscarCargoEmpleado(in codCargEmp int)
    begin
	select cargoEmpleado.codigoCargoEmpleado, cargoEmpleado.nombreCargo, cargoEmpleado.descripcionCargo from cargoEmpleado 
    where cargoEmpleado.codigoCargoEmpleado = codCargEmp;
	end$$
delimiter ;

call sp_buscarCargoEmpleado(1);
call sp_buscarCargoEmpleado(2);
call sp_buscarCargoEmpleado(3);
call sp_buscarCargoEmpleado(4);
call sp_buscarCargoEmpleado(5);

-- Actualizar

delimiter $$
	create procedure sp_actualizarCargoEmpleado(in codCargEmp int, in nomCarg varchar(45), 
    in desCarg varchar(45))
    begin
		update cargoEmpleado 
		set
        cargoEmpleado.nombreCargo = nomCarg, 
        cargoEmpleado.descripcionCargo = desCarg
        where 
        cargoEmpleado.codigoCargoEmpleado = codCargEmp;
    end$$
delimiter ;

call sp_actualizarCargoEmpleado(1,  'Encargado de cuentas de la empresa', 'Llevar la contabilidad de empresa');

-- Eliminar

delimiter $$
	create procedure sp_eliminarCargoEmpleado (in codCargEmp int)
    begin
		delete from cargoEmpleado where cargoEmpleado.codigoCargoEmpleado = codCargEmp;
    end$$
delimiter ;

-- call sp_eliminarCargoEmpleado();

-- Entidad TelefonoProveedor procedimientos almacenados

-- Agregar 

delimiter $$
	create procedure sp_agregarTelefonoProveedor (in codTelPro int, in numPrinPro varchar(8), in numSecPro varchar(8), in obsPro varchar(45),
											  in codPro int)
	begin
	insert into TelefonoProveedor (TelefonoProveedor.codigoTelefonoProveedor, TelefonoProveedor.numeroPrincipal, TelefonoProveedor.numeroSecundario,
								  TelefonoProveedor.observaciones, TelefonoProveedor.codigoProveedor)
    values (codTelPro,numPrinPro, numSecPro, obsPro, codPro);
end$$
delimiter ;

call sp_agregarTelefonoProveedor(1, '12345678', '98765421', 'Entregar las aguas', 1);
call sp_agregarTelefonoProveedor(2, '23456789', '87654321', 'Nos ofrecen cremas', 2);
call sp_agregarTelefonoProveedor(3, '34567890', '76543210', 'La mejor empresa de gaseosas', 3);
call sp_agregarTelefonoProveedor(4, '45678901', '65432109', 'De exportar las carnes y filetes', 4);
call sp_agregarTelefonoProveedor(5, '56789012', '54321098', 'Ofrecen productos dentales', 5);

-- Listar 

delimiter $$
	create procedure sp_listarTelefonoProveedor ()
    begin
		select TelefonoProveedor.codigoTelefonoProveedor, TelefonoProveedor.numeroPrincipal, TelefonoProveedor.numeroSecundario,
			   TelefonoProveedor.observaciones, TelefonoProveedor.codigoProveedor 
        from TelefonoProveedor;
    end$$
delimiter ;

call sp_listarTelefonoProveedor();

-- Buscar 

delimiter $$
	create procedure sp_buscarTelefonoProveedor(in codTelPro int)
    begin
	select TelefonoProveedor.codigoTelefonoProveedor ,TelefonoProveedor.numeroPrincipal, TelefonoProveedor.numeroSecundario,
		   TelefonoProveedor.observaciones, TelefonoProveedor.codigoProveedor from TelefonoProveedor 
    where TelefonoProveedor.codigoTelefonoProveedor = codTelPro;
	end$$
delimiter ;

call sp_buscarTelefonoProveedor(1);
call sp_buscarTelefonoProveedor(2);
call sp_buscarTelefonoProveedor(3);
call sp_buscarTelefonoProveedor(4);
call sp_buscarTelefonoProveedor(5);

-- Actualizar

delimiter $$
	create procedure sp_actualizarTelefonoProveedor(in codTelPro int, in numPrinPro varchar(8), in numSecPro varchar(8), in obsPro varchar(45),
in codPro int)
    begin
		update TelefonoProveedor 
		set
        TelefonoProveedor.numeroPrincipal = numPrinPro,
        TelefonoProveedor.numeroSecundario = numSecPro,
		TelefonoProveedor.observaciones = obsPro,
        TelefonoProveedor.codigoProveedor = codPro
        where 
        TelefonoProveedor.codigoTelefonoProveedor = codTelPro;
    end$$
delimiter ;

call sp_actualizarTelefonoProveedor(1, '12345678', '98765421', 'Entregar aguas salvavidas', 1);

-- Eliminar

delimiter $$
	create procedure sp_eliminarTelefonoProveedor (in codTelPro int)
    begin
		delete from TelefonoProveedor where TelefonoProveedor.codigoTelefonoProveedor = codTelPro;
    end$$
delimiter ;

-- call sp_eliminarTelefonoProveedor();

-- Entidad Empleados procedimientos almacenados

-- Agregar 

delimiter $$
	create procedure sp_agregarEmpleados (in codEmple int, in nomEmple varchar(50), in apeEmple varchar(50), in sueldo decimal(10,2),
									  in direEmple varchar(150), in turEmple varchar(15), in codCarEmple int)
	begin
	insert into Empleados (Empleados.codigoEmpleado, Empleados.nombresEmpleados, Empleados.apellidosEmpleados,
						   Empleados.sueldo, Empleados.direccion, Empleados.turno, Empleados.codigoCargoEmpleado)
    values (codEmple, nomEmple, apeEmple, sueldo, direEmple, turEmple, codCarEmple);
	end$$
delimiter ;

call sp_agregarEmpleados(1, 'Juan Jose', 'Pérez Lainez', 5200.00, 'Zona 6 Villa nueva', 'Matutina', 1);
call sp_agregarEmpleados(2, 'María Laura', 'García Veliz', 6500.00, 'zona 14', 'Matutina', 2);
call sp_agregarEmpleados(3, 'Pedro Rafael', 'López Hernandez', 9800.00, 'zona 10 las majaditas', 'Vespertina', 3);
call sp_agregarEmpleados(4, 'Ana Sofia', 'Ramírez Gaitan', 8000.00, 'Carretera Salvador', 'Vespertina', 4);
call sp_agregarEmpleados(5, 'Carlos Andres', 'Torres Socop', 10000.00, 'Cayala', 'Nocturna', 5);

-- Listar 

delimiter $$
	create procedure sp_listarEmpleados ()
    begin
		select Empleados.codigoEmpleado, Empleados.nombresEmpleados, Empleados.apellidosEmpleados,
			   Empleados.sueldo, Empleados.direccion, Empleados.turno, Empleados.codigoCargoEmpleado
        from Empleados;
    end$$
delimiter ;

call sp_listarEmpleados();

-- Buscar 

delimiter $$
	create procedure sp_buscarEmpleados(in codEmple int)
    begin
	select Empleados.codigoEmpleado, Empleados.nombresEmpleados, Empleados.apellidosEmpleados,
		   Empleados.sueldo, Empleados.direccion, Empleados.turno, Empleados.codigoCargoEmpleado from Empleados 
    where Empleados.codigoEmpleado = codEmple;
	end$$
delimiter ;

call sp_buscarEmpleados(1);
call sp_buscarEmpleados(2);
call sp_buscarEmpleados(3);
call sp_buscarEmpleados(4);
call sp_buscarEmpleados(5);

-- Actualizar

delimiter $$
	create procedure sp_actualizarEmpleados(in codEmple int, in nomEmple varchar(50), in apeEmple varchar(50), in sueldo decimal(10,2),
											in direEmple varchar(150), in turEmple varchar(15), in codCarEmple int)
    begin
		update Empleados 
		set
        Empleados.nombresEmpleados = nomEmple,
        Empleados.apellidosEmpleados = apeEmple,
	    Empleados.sueldo = sueldo, 
        Empleados.direccion = direEmple, 
        Empleados.turno = turEmple, 
        Empleados.codigoCargoEmpleado = codCarEmple
        where 
        Empleados.codigoEmpleado = codEmple;
    end$$
delimiter ;

call sp_actualizarEmpleados(1, 'Juan Jose', 'Pérez Lainez', 5580.00, 'Zona 6 Villa nueva', 'Vespertina', 1);

-- Eliminar

delimiter $$
	create procedure sp_eliminarEmpleados (in codEmple int)
    begin
		delete from Empleados where  Empleados.codigoEmpleado = codEmple;
    end$$
delimiter ;

-- call sp_eliminarEmpleados();

-- Entidad EmailProveedor procedimientos almacenados

-- Agregar 

delimiter $$
create procedure sp_agregarEmailProveedor (in codEmPro int, in emaPro varchar(50), in descri varchar(100), in codPro int)
begin
	insert into EmailProveedor (EmailProveedor.codigoEmailProveedor, EmailProveedor.emailProveedor, EmailProveedor.descripcion,
						        EmailProveedor.codigoProveedor)
    values (codEmPro, emaPro, descri, codPro);
end$$
delimiter ;

call sp_agregarEmailProveedor(1, 'aguas12@gmail.com', 'Entregar las aguas', 1);
call sp_agregarEmailProveedor(2, 'cremasNIVEA@gmail.com', 'Nos ofrecen cremas', 2);
call sp_agregarEmailProveedor(3, 'gaseosamirados@gmail.com','Las mejor empresa de gaseosas', 3);
call sp_agregarEmailProveedor(4, 'filetesCarnes@gmail.com', 'De exportar las carnes y filetes', 4);
call sp_agregarEmailProveedor(5, 'colgate@gmail.com', 'Ofrecen productos dentales', 5);

-- Listar 

delimiter $$
	create procedure sp_listarEmailProveedor ()
    begin
		select EmailProveedor.codigoEmailProveedor, EmailProveedor.emailProveedor, EmailProveedor.descripcion,
			   EmailProveedor.codigoProveedor
        from EmailProveedor;
    end$$
delimiter ;

call sp_listarEmailProveedor();

-- Buscar 

delimiter $$
	create procedure sp_buscarEmailProveedor(in codEmPro int)
    begin
	select EmailProveedor.codigoEmailProveedor, EmailProveedor.emailProveedor, EmailProveedor.descripcion, EmailProveedor.codigoProveedor from EmailProveedor 
    where EmailProveedor.codigoEmailProveedor = codEmPro;
	end$$
delimiter ;

call sp_buscarEmailProveedor(1);
call sp_buscarEmailProveedor(2);
call sp_buscarEmailProveedor(3);
call sp_buscarEmailProveedor(4);
call sp_buscarEmailProveedor(5);

-- Actualizar

delimiter $$
	create procedure sp_actualizarEmailProveedor(in codEmPro int, in emaPro varchar(50), in descri varchar(100), in codPro int)
    begin
		update EmailProveedor 
		set
        EmailProveedor.emailProveedor = emaPro, 
        EmailProveedor.descripcion = descri,
		EmailProveedor.codigoProveedor = codPro
        where 
        EmailProveedor.codigoEmailProveedor = codEmPro;
    end$$
delimiter ;

call sp_actualizarEmailProveedor(1, 'aguas12@gmail.com', 'Entregar el producto', 1);

-- Eliminar

delimiter $$
	create procedure sp_eliminarEmailProveedor (in codEmPro int)
    begin
		delete from EmailProveedor where  EmailProveedor.codigoEmailProveedor = codEmPro;
    end$$
delimiter ;

-- call sp_eliminarEmailProveedor();

-- Entidad Factura procedimientos almacenados

-- Agregar 

delimiter $$
create procedure sp_agregarFactura (in numFac int, in estadFac varchar(50), in totalFac decimal(10,2), in fecFac varchar(45),
									in codCliFac int, in codEmpleFac int)
	begin
	insert into Factura (Factura.numeroFactura, Factura.estado, Factura.totalFactura, Factura.fechaFactura,
						 Factura.codigoCliente, Factura.codigoEmpleado)
    values (numFac, estadFac, totalFac, fecFac, codCliFac, codEmpleFac);
	end$$
delimiter ;

call sp_agregarFactura(1, 'Pagado', 16.00, '2023-05-21', 123456789, 1 );
call sp_agregarFactura(2, 'Pagado', 40.00, '2023-02-14', 161481461, 2 );
call sp_agregarFactura(3, 'No Pagado', 108.00, '2023-02-14', 154664846, 3);
call sp_agregarFactura(4, 'No Pagado', 45.00, '2023-04-18', 564980398, 4 );
call sp_agregarFactura(5, 'Pagado', 100.00, '2023-06-20', 454894988, 5 );

-- Listar 

delimiter $$
	create procedure sp_listarFactura ()
    begin
		select Factura.numeroFactura, Factura.estado, Factura.totalFactura, Factura.fechaFactura,
			   Factura.codigoCliente, Factura.codigoEmpleado
        from Factura;
    end$$
delimiter ;

call sp_listarFactura();

-- Buscar 

delimiter $$
	create procedure sp_buscarFactura(in numFac int)
    begin
	select Factura.numeroFactura, Factura.estado, Factura.totalFactura, Factura.fechaFactura,
		   Factura.codigoCliente, Factura.codigoEmpleado from Factura 
    where Factura.numeroFactura = numFac;
	end$$
delimiter ;

call sp_buscarFactura(1);
call sp_buscarFactura(2);
call sp_buscarFactura(3);
call sp_buscarFactura(4);
call sp_buscarFactura(5);

-- Actualizar

delimiter $$
	create procedure sp_actualizarFactura(in numFac int, in estadFac varchar(50), in totalFac decimal(10,2), in fecFac varchar(45),
									     in codCliFac int, in codEmpleFac int)
    begin
		update Factura 
		set
        Factura.estado = estadFac, 
        Factura.totalFactura = totalFac, 
        Factura.fechaFactura = fecFac,
		Factura.codigoCliente = codCliFac, 
        Factura.codigoEmpleado = codEmpleFac
        where 
        Factura.numeroFactura = numFac;
    end$$
delimiter ;

call sp_actualizarFactura(3, 'Pagado', 250.00, '2023-02-14', 154664846, 3);

-- Eliminar

delimiter $$
	create procedure sp_eliminarFactura (in numFac int)
    begin
		delete from Factura where Factura.numeroFactura = numFac;
    end$$
delimiter ;

-- call sp_eliminarFactura();

-- Entidad Productos procedimientos almacenados

-- Agregar 

delimiter $$
create procedure sp_agregarProductos (in codPro varchar(45), in desPro varchar(45), in precioUniPro decimal(10,2), in precioDocPro decimal(10,2),
									  in precioMaPro decimal(10,2), in existencia int, in codTipPro int,
                                      in codProvPro int )
	begin 
	insert into Productos (Productos.codigoProducto, Productos.descripcionProducto, Productos.precioUnitario, Productos.precioDocena,
						   Productos.precioMayor, Productos.existencia, Productos.codigoTipoProducto, 
                           Productos.codigoProveedor)
    values (codPro, desPro, precioUniPro, precioDocPro, precioMaPro, existencia, codTipPro, codProvPro);
	end$$
delimiter ;

call sp_agregarProductos('1', 'Agua Rica roja 400 ml', 4.00, 48.00, 60.00, 100, 1, 1);
call sp_agregarProductos('2', 'Crema NIVEA para protegernos del sol', 8.00, 96.00, 120.00, 100, 2, 2);
call sp_agregarProductos('3', 'Gaseosa CocaCola 2.5 litros', 18.00, 216.00, 250.00, 100, 3, 3);
call sp_agregarProductos('4', 'Filete de pechuga 3.20LB la bandeja', 15.00, 180.00, 210.00, 100, 4, 4 );
call sp_agregarProductos('5', 'Cepillo dental Colgate', 10.00, 120.00, 160.00, 100, 5, 5 );

-- Listar 

delimiter $$
	create procedure sp_listarProductos ()
    begin
		select Productos.codigoProducto, Productos.descripcionProducto, Productos.precioUnitario, Productos.precioDocena,
			   Productos.precioMayor, Productos.existencia, Productos.codigoTipoProducto, 
			   Productos.codigoProveedor
        from Productos;
    end$$
delimiter ;

call sp_listarProductos();

-- Buscar 

delimiter $$
	create procedure sp_buscarProductos(in codPro varchar(45))
    begin
	select Productos.codigoProducto, Productos.descripcionProducto, Productos.precioUnitario, Productos.precioDocena,
		   Productos.precioMayor, Productos.existencia, Productos.codigoTipoProducto, 
		   Productos.codigoProveedor from Productos 
    where Productos.codigoProducto = codPro;
	end$$
delimiter ;

call sp_buscarProductos('1');
call sp_buscarProductos('2');
call sp_buscarProductos('3');
call sp_buscarProductos('4');
call sp_buscarProductos('5');

-- Actualizar

delimiter $$
	create procedure sp_actualizarProductos(in codPro varchar(45), in desPro varchar(45), in precioUniPro decimal(10,2), in precioDocPro decimal(10,2),
									        in precioMaPro decimal(10,2), in existencia int, in codTipPro int,
                                            in codProvPro int)
    begin
		update Productos
		set
        Productos.descripcionProducto = desPro, 
        Productos.precioUnitario = precioUniPro, 
        Productos.precioDocena = precioDocPro,
		Productos.precioMayor = precioMaPro, 
        Productos.existencia = existencia, 
        Productos.codigoTipoProducto = codTipPro, 
		Productos.codigoProveedor = codProvPro
        where 
        Productos.codigoProducto = codPro;
    end$$
delimiter ;

call sp_actualizarProductos('1', 'Agua Rica roja 400 ml', 6.00, 72.00, 90.00, 100, 1, 1);

-- Eliminar

delimiter $$
	create procedure sp_eliminarProductos (in codPro varchar(45))
    begin
		delete from Productos where Productos.codigoProducto = codPro;
    end$$
delimiter ;

-- call sp_eliminarProductos();

-- JOIN PRODUCTOS

delimiter $$
	create procedure sp_listarProductosJoin()
	begin
    select 
        pro.codigoProducto,
        tipoPro.descripcion as descripcionTipoProducto,
        pro.descripcionProducto,
        pro.precioUnitario,
        pro.existencia
    from
        Productos pro
    join 
        Proveedores prov on pro.codigoProveedor = prov.codigoProveedor
	join
		TipoProducto tipoPro on pro.codigoTipoProducto = tipoPro.codigoTipoProducto;
end$$
delimiter ;

call sp_listarProductosJoin();


-- Entidad DetalleFactura procedimientos almacenados

-- Agregar 

delimiter $$
create procedure sp_agregarDetalleFactura (in codDetaFac int, in precioUniFac decimal(10,2), in cantiFac int, in numFac int, 
									       in codProFac varchar(15))
	begin 
	insert into DetalleFactura (DetalleFactura.codigoDetalleFactura, DetalleFactura.precioUnitario, DetalleFactura.cantidad,
						        DetalleFactura.numeroFactura, DetalleFactura.codigoProducto)
    values (codDetaFac, precioUniFac, cantiFac, numFac, codProFac);
	end$$
delimiter ;


call sp_agregarDetalleFactura(1, 4.00, 1, 1, 1);
call sp_agregarDetalleFactura(2, 8.00, 5, 2, 2);
call sp_agregarDetalleFactura(3, 18.00, 6, 3, 3);
call sp_agregarDetalleFactura(4, 15.00, 3, 4, 4 );
call sp_agregarDetalleFactura(5, 10.00, 10, 5, 5 );

-- Listar 

delimiter $$
	create procedure sp_listarDetalleFactura ()
    begin
		select DetalleFactura.codigoDetalleFactura, DetalleFactura.precioUnitario, DetalleFactura.cantidad,
			   DetalleFactura.numeroFactura, DetalleFactura.codigoProducto
        from DetalleFactura;
    end$$
delimiter ;

call sp_listarDetalleFactura();

-- Buscar 

delimiter $$
	create procedure sp_buscarDetalleFactura(in codDetaFac int)
    begin
	select DetalleFactura.codigoDetalleFactura, DetalleFactura.precioUnitario, DetalleFactura.cantidad,
		   DetalleFactura.numeroFactura, DetalleFactura.codigoProducto from DetalleFactura 
    where DetalleFactura.codigoDetalleFactura = codDetaFac;
	end$$
delimiter ;

call sp_buscarDetalleFactura(1);
call sp_buscarDetalleFactura(2);
call sp_buscarDetalleFactura(3);
call sp_buscarDetalleFactura(4);
call sp_buscarDetalleFactura(5);

-- Actualizar

delimiter $$
	create procedure sp_actualizarDetalleFactura(in codDetaFac int, in precioUniFac decimal(10,2), in cantiFac int, in numFac int, 
									             in codProFac varchar(15))
    begin
		update DetalleFactura
		set
		   DetalleFactura.precioUnitario = precioUniFac, 
           DetalleFactura.cantidad = cantiFac,
		   DetalleFactura.numeroFactura = numFac, 
           DetalleFactura.codigoProducto = codProFac
        where 
        DetalleFactura.codigoDetalleFactura = codDetaFac;
    end$$
delimiter ;

call sp_actualizarDetalleFactura(1, 4.00, 4, 1, 1);

-- Eliminar

delimiter $$
	create procedure sp_eliminarDetalleFactura (in codDetaFac int)
    begin
		delete from DetalleFactura where DetalleFactura.codigoDetalleFactura = codDetaFac;
    end$$
delimiter ;

-- call sp_eliminarDetalleFactura();

-- Entidad DetalleCompra procedimientos almacenados

-- Agregar 

delimiter $$
create procedure sp_agregarDetalleCompra (in codDetaComp int, in cosUniDetaComp decimal(10,2), in cantDetaComp int,
									      in codProDetaComp varchar(15), in numDocDetaComp int)
	begin 
	insert into DetalleCompra (DetalleCompra.codigoDetalleCompra, DetalleCompra.costoUnitario, DetalleCompra.cantidad,
						       DetalleCompra.codigoProducto, DetalleCompra.numeroDocumento)
    values (codDetaComp, cosUniDetaComp, cantDetaComp, codProDetaComp, numDocDetaComp);
	end$$
delimiter ;

call sp_agregarDetalleCompra(1, 4.00, 4, 1, 1);
call sp_agregarDetalleCompra(2, 8.00, 100, 2, 2);
call sp_agregarDetalleCompra(3, 18.00, 100, 3, 3);
call sp_agregarDetalleCompra(4, 15.00, 100, 4, 4 );
call sp_agregarDetalleCompra(5, 10.00, 100, 5, 5 );

-- Listar 

delimiter $$
	create procedure sp_listarDetalleCompra ()
    begin
		select DetalleCompra.codigoDetalleCompra, DetalleCompra.costoUnitario, DetalleCompra.cantidad,
			   DetalleCompra.codigoProducto, DetalleCompra.numeroDocumento
        from DetalleCompra;
    end$$
delimiter ;

call sp_listarDetalleCompra();

-- Buscar 

delimiter $$
	create procedure sp_buscarDetalleCompra(in codDetaComp int)
    begin
	select DetalleCompra.codigoDetalleCompra, DetalleCompra.costoUnitario, DetalleCompra.cantidad,
		   DetalleCompra.codigoProducto, DetalleCompra.numeroDocumento from DetalleCompra 
    where DetalleCompra.codigoDetalleCompra = codDetaComp;
	end$$
delimiter ;

call sp_buscarDetalleCompra(1);
call sp_buscarDetalleCompra(2);
call sp_buscarDetalleCompra(3);
call sp_buscarDetalleCompra(4);
call sp_buscarDetalleCompra(5);

-- Actualizar

delimiter $$
	create procedure sp_actualizarDetalleCompra(in codDetaComp int, in cosUniDetaComp decimal(10,2), in cantDetaComp int,
											in codProDetaComp varchar(15), in numDocDetaComp int)
    begin
		update DetalleCompra
		set
        DetalleCompra.costoUnitario = cosUniDetaComp, 
        DetalleCompra.cantidad = cantDetaComp,
		DetalleCompra.codigoProducto = codProDetaComp, 
        DetalleCompra.numeroDocumento = numDocDetaComp
        where 
        DetalleCompra.codigoDetalleCompra = codDetaComp;
    end$$
delimiter ;

call sp_actualizarDetalleCompra(5, 10.00, 5, 5, 5 );

-- Eliminar

delimiter $$
	create procedure sp_eliminarDetalleCompra (in codDetaComp int)
    begin
		delete from DetalleCompra where DetalleCompra.codigoDetalleCompra = codDetaComp;
    end$$
delimiter ;

-- call sp_eliminarDetalleCompra();

-- JOIN FACTURA

select * from detallefactura
	join factura on detallefactura.numeroFactura = factura.numeroFactura
    join clientes on factura.codigoCliente = clientes.codigoCliente
    join productos on detallefactura.codigoProducto = productos.codigoProducto
    where factura.numeroFactura = 1;
-- Triggers


ALTER USER 'Trabajos Daniel Tuy_IN5BM** '@'localhost' IDENTIFIED WITH mysql_native_password BY 'abc123**';