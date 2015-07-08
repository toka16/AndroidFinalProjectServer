# create database android_final_project;

use android_final_project;

drop table if exists users;
create table users(
	user_id integer unsigned primary key auto_increment,
	user_name nvarchar(32),
	user_surname nvarchar(32),
	user_email varchar(32) unique,
	user_password varchar(16),
	user_phone varchar(32),
	user_card_number text,
	user_primary_number text
);

drop table if exists categories;
create table categories(
	category_id integer unsigned primary key auto_increment,
	category_name nvarchar(32) unique
);

drop table if exists products;
create table products(
	product_id integer unsigned primary key auto_increment,
	product_name nvarchar(32) unique,
	description text,
	price double,
	image_link text
);

drop table if exists map_category_product;
create table map_category_product(
	id integer unsigned primary key auto_increment,
	id_of_category int,
	id_of_product int
);