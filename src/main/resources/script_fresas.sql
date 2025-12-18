create database if not exists fresa_calatrava;
use fresa_calatrava;

create or replace table finca (
	id int auto_increment primary key,
	nombre varchar(150),
	latitud float , 
	longitud float, 
	supercie int not null
);

create or replace table invernadero(
	id int auto_increment primary key,
	codigo varchar(50) unique  not null,
	capacidad int , 
	tipo int(2),
	id_finca int not null, 
	constraint fk_invernadero_finca foreign key (id_finca) references finca(id)
);


create or replace table fresa(
	id int auto_increment primary key,
	codigo varchar(50) unique  not null,
	nombre varchar(150),
	tipo int(2),
	temporada int(2), 
	rendimiento float
);

create or replace table recoleccion(
	id int auto_increment primary key,
	fecha date,
	id_invernadero int not null,
	constraint fk_recoleccion_invernadero
	foreign key (id_invernadero)
	references invernadero(id)
);


create or replace table invernadero_fresa(
	id_invernadero int not null, 
	id_fresa int not null,
	constraint fk_invernadero_fresa_invernadero
	foreign key (id_invernadero)
	references invernadero(id),
	constraint fk_invernadero_fresa_fresa
	foreign key (id_fresa)
	references fresa(id)
);

create or replace table recoleccion_fresa(
	id int auto_increment primary key,
	id_recoleccion int not null, 
	id_fresa int not null,
	cantidad int, 
	constraint fk_recoleccion_fresa_recoleccion
	foreign key (id_recoleccion)
	references recoleccion(id),
	constraint fk_recoleccion_fresa_fresa
	foreign key (id_fresa)
	references fresa(id)
);

create or replace table cliente (
	id int auto_increment primary key,
	nombre varchar(150),
	direccion varchar(150),
	telefono varchar(20)
);


create or replace table pedido (
	id int auto_increment primary key,
	codigo varchar(50),
	fecha date, 
	estado int(2), 
	importe_total float,
	id_cliente int not null, 
	constraint fk_pedido_cliente
	foreign key (id_cliente)
	references cliente(id)
);


create or replace table pedido_fresa(
	id int auto_increment primary key,
	id_pedido int not null, 
	id_fresa int not null,
	cantidad int, 
	constraint fk_pedido_fresa_pedido
	foreign key (id_pedido)
	references pedido(id),
	constraint fk_pedido_fresa_fresa
	foreign key (id_fresa)
	references fresa(id)
);


create or replace table valoracion (
	id int auto_increment primary key,
	fecha date, 
	puntuacion int,
	comentario varchar(500),
	id_pedido int not null,
	constraint fk_valoracion_pedido
	foreign key (id_pedido)
	references pedido(id)
);




















