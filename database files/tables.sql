# create database android_final_project;

use android_final_project;

drop table if exists versions;
create table versions(
	version_id integer unsigned primary key auto_increment,
	version_item_name nvarchar(32),
	version_number int
);

insert into versions(version_item_name, version_number)
values ('product', 1), ('category', 1), ('menu', 1), ('news', 1);

drop table if exists users;
create table users(
	user_id integer unsigned primary key auto_increment,
	user_name nvarchar(32),
	user_surname nvarchar(32),
	user_email varchar(128) unique,
	user_password text,
	user_phone varchar(32),
	user_card_number text,
	user_primary_number text,
	user_is_admin int
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
	id_of_product int,
	constraint categories_unique unique(id_of_category, id_of_product)
);

drop table if exists menus;
create table menus(
	menu_id integer unsigned primary key auto_increment,
	menu_name nvarchar(32) unique,
	description text,
	price double,
	image_link text
);

drop table if exists map_menu_product;
create table map_menu_product(
	id integer unsigned primary key auto_increment,
	id_of_menu int,
	id_of_product int
);

drop table if exists news;
create table news(
	id integer unsigned primary key auto_increment,
	news_name nvarchar(32),
	description text,
	from_date long,
	to_date long
);