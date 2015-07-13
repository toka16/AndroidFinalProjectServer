-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The procedure inserts new user into 'users' table.
-- --------------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `insert_user` $$
CREATE PROCEDURE `insert_user` (userName nvarchar(32), userSurname nvarchar(32), email varchar(128), 
							userPassword text, phone varchar(32), card_number text, primary_number text, isAdmin int)
BEGIN

	insert into users(user_name, user_surname, user_email, user_password, user_phone, user_card_number, user_primary_number, user_is_admin)
		value(userName, userSurname, email, userPassword, phone, card_number, primary_number, isAdmin);

END $$


-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The procedure saves category into categories table. Also it finds out category id and adds it into map_category_product table.
-- --------------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `insert_category` $$
CREATE PROCEDURE `insert_category` (out categoryID int, categoryName nvarchar(32))
BEGIN

	insert into categories(category_name)
		value(categoryName);

	set categoryID = (select category_id from categories where category_name = categoryName);
END $$

-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The procedure inserts new product into products table. Also makes new entry into map_category_product
-- --------------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `insert_product` $$
CREATE PROCEDURE `insert_product` (out productID int, productName nvarchar(32), productDescription text, productPrice double, 
									productImageLink text)
BEGIN

	insert into products(product_name, description, price, image_link)
		value(productName, productDescription, productPrice, productImageLink);

	set productID = (select product_id from products where product_name = productName);

END $$


-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The storage procedure fills map_category_product table.
-- --------------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `fill_map_category_product` $$
CREATE PROCEDURE `android_final_project`.`fill_map_category_product` (categoryID int, productID int)
BEGIN
	
	declare tableID int;

	set tableID = (select id from map_category_product where id_of_category = categoryID and id_of_product = productID);
	select tableID;
	if tableID is null then
		insert into map_category_product(id_of_category, id_of_product)
			value(categoryID, productID);
	end if;

END $$


-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The procedure fills fill_map_menu_product table.
-- --------------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `fill_map_menu_product` $$
CREATE PROCEDURE `android_final_project`.`fill_map_menu_product` (menuID int, categoryID int)
BEGIN

	insert into map_menu_product(id_of_menu, id_of_product)
		value(menuID, categoryID);

END $$


-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The procedure gets all products from products table for appropriate category id.
-- --------------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `all_products_by_category` $$
CREATE PROCEDURE `android_final_project`.`all_products_by_category` (categoryID int)
BEGIN

	select product_id, product_name, description, price, image_link from products
	inner join map_category_product 
	on products.product_id = map_category_product.id_of_product
	where map_category_product.id_of_category = categoryID;

END $$


-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The procedure gives all products which are not in appropriate category. 
-- If category is removed, maping also removed from map_category_product. The procedure return correct
-- products, because it uses left join statement on products table and map_category_product table.
-- --------------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `all_products_out_of_category` $$
CREATE PROCEDURE `android_final_project`.`all_products_out_of_category` (categoryID int)
BEGIN
	
	select product_id, product_name, description, price, image_link from products
	left join map_category_product 
	on products.product_id = map_category_product.id_of_product
	where map_category_product.id_of_category != categoryID
	group by products.product_id; 

END $$

-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The procedure inserts new menu into menus table and saves menu id in menuID parameter.
-- --------------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `insert_menu` $$
CREATE PROCEDURE `insert_menu` (out menuID int, menuName nvarchar(32), menuDescription text, menuPrice double, menuImageLink text)
BEGIN

	insert into menus(menu_name, description, price, image_link)
		values(menuName, menuDescription, menuPrice, menuImageLink);

	set menuID = (select menu_id from menus where menu_name = menuName);
	
END $$


-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The procedure minsert news entry into appropriate table.
-- --------------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `insert_news` $$
CREATE PROCEDURE `android_final_project`.`insert_news` (out newsID int, newsName nvarchar(32), newsDescription text, newsFromDate long, 
														newsToDate long)
BEGIN
	
	insert into news(news_name, description, from_date, to_date)
		value(newsName, newsDescription, newsFromDate, newsToDate);

	set newsID = (select id from news where news_name = newsName);

END $$


-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The procedure updates product by product id.
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE PROCEDURE `android_final_project`.`update_product` (poductID int, productName nvarchar(32), productDescription text,
															productPrice double, productImageLink text)
BEGIN

	update products
	set product_name = productName, description = productDescription, price = productPrice, image_link = productImageLink
	where product_id = poductID;

END $$


-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The procedure updates menus by menu id.
-- --------------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `update_menu` $$
CREATE PROCEDURE `android_final_project`.`update_menu` (menuID int, menuName nvarchar(32), menuDescription text, 
														menuPrice double, menuImageLink text)
BEGIN

	update menus
	set menu_name = menuName, description = menuDescription, price = menuPrice, image_link = menuImageLink
	where menu_id = menuID;

END $$


-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The procedure updates category by id.
-- --------------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `update_category` $$
CREATE PROCEDURE `android_final_project`.`update_category` (categoryID int, categoryName nvarchar(32))
BEGIN

	update categories
	set category_name = categoryName
	where category_id = categoryID;

END $$

-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The procedure updates news table.
-- --------------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `update_news` $$
CREATE PROCEDURE `android_final_project`.`update_news` (newsID int, newsName nvarchar(32), newsDescription text, 
														newsFromDate long, newsToDate long)
BEGIN

	update news
	set news_name = newsName, description = newsDescription, from_date = newsFromDate, to_date = newsToDate
	where id = newsID;

END $$


DELIMITER $$

CREATE PROCEDURE `android_final_project`.`increase_version_number` (itemName nvarchar(32))
BEGIN
	declare newVersionNumber int;
	set newVersionNumber = (select version_number from versions where version_item_name = itemName) + 1;

	update versions
	set version_number = newVersionNumber
	where version_item_name = itemName;
END $$




# selects procedures:

DELIMITER $$
DROP PROCEDURE IF EXISTS `select_user` $$
CREATE PROCEDURE `android_final_project`.`select_user` (userEmail varchar(32), userPassword varchar(16))
BEGIN

	select * from users where user_email = userEmail and user_password = userPassword;

END $$


DELIMITER $$
DROP PROCEDURE IF EXISTS `select_categories` $$
CREATE PROCEDURE `android_final_project`.`select_categories` ()
BEGIN
	select * from categories;
END $$


DELIMITER $$
DROP PROCEDURE IF EXISTS `select_news` $$
CREATE PROCEDURE `android_final_project`.`select_news` ()
BEGIN
	select * from news;
END $$


DELIMITER $$
DROP PROCEDURE IF EXISTS `select_menus` $$
CREATE PROCEDURE `android_final_project`.`select_menus` ()
BEGIN
	select * from menus;
END $$


DELIMITER $$
DROP PROCEDURE IF EXISTS `select_menus_products` $$
CREATE PROCEDURE `android_final_project`.`select_menus_products` (menuID int)
BEGIN
	
	select product_id, product_name, description, price, image_link from products
	inner join map_menu_product 
	on products.product_id = map_menu_product.id_of_product
	where map_menu_product.id_of_menu = menuID;

END $$



DELIMITER $$
DROP PROCEDURE IF EXISTS `select_products_by_menu` $$
CREATE PROCEDURE `select_products_by_menu` (menuID int)
BEGIN
	select product_name, description, price, image_link from
	products inner join map_menu_productproduct
	on product_id = id_of_product
	where id_of_menu = menuID;

END $$

DELIMITER $$
DROP PROCEDURE IF EXISTS `select_products_out_of_menu` $$
CREATE PROCEDURE `select_products_out_of_menu` (menuID int)
BEGIN

	select product_name, description, price, image_link from
	products inner join map_menu_product
	on product_id = id_of_product
	where id_of_menu != menuID;

END $$


DELIMITER $$
DROP PROCEDURE IF EXISTS `select_categories_by_product` $$
CREATE PROCEDURE `select_categories_by_product` (productID int)
BEGIN

	select category_id, category_name from
	categories inner join map_category_product
	on category_id = id_of_category
	where id_of_product = productID;

END $$


DELIMITER $$
DROP PROCEDURE IF EXISTS `select_categories_out_of_product` $$
CREATE PROCEDURE `select_categories_out_of_product` (productID int)
BEGIN

	select category_id, category_name from
	categories inner join map_category_product
	on category_id = id_of_category
	where id_of_product != productID
	group by category_id;

END $$

DELIMITER $$
DROP PROCEDURE IF EXISTS `select_produts` $$
CREATE PROCEDURE `android_final_project`.`select_produts` ()
BEGIN
	select * from products;
END

#  deletes:

DELIMITER $$
DROP PROCEDURE IF EXISTS `remove_product_from_category` $$
CREATE PROCEDURE `remove_product_from_category` (categoryID int, productID int)
BEGIN

	delete from map_category_product
	where id_of_category = categoryID and id_of_product = productID;

END $$


DELIMITER $$
DROP PROCEDURE IF EXISTS `remove_product_from_menu` $$
CREATE PROCEDURE `remove_product_from_menu` (menuID int, productID int)
BEGIN

	delete from map_menu_product
	where id_of_menu = menuID and id_of_product = productID;

END $$


DELIMITER $$
DROP PROCEDURE IF EXISTS `remove_user` $$
CREATE PROCEDURE `android_final_project`.`remove_user` (userID int)
BEGIN
	
	delete from users
	where user_id = userID;

END $$


DELIMITER $$
DROP PROCEDURE IF EXISTS `remove_product` $$
CREATE PROCEDURE `android_final_project`.`remove_product` (productID int)
BEGIN
	
	delete from map_category_product
	where id_of_product = productID;

	delete from map_menu_product
	where id_of_product = productID;

	delete from products
	where product_id = productID;

END $$


DELIMITER $$
DROP PROCEDURE IF EXISTS `remove_category` $$
CREATE PROCEDURE `android_final_project`.`remove_category` (categoryID int)
BEGIN
	
	delete from map_category_product
	where id_of_category = categoryID;

	delete from categories
	where category_id = categoryID;

END $$


DELIMITER $$
DROP PROCEDURE IF EXISTS `remove_menu` $$
CREATE PROCEDURE `android_final_project`.`remove_menu` (menuID int)
BEGIN
	
	delete from map_menu_product
	where id_of_menu = menuID;
	
	delete from menus
	where menu_id = menuID;

END $$


DELIMITER $$
DROP PROCEDURE IF EXISTS `remove_news` $$
CREATE PROCEDURE `android_final_project`.`remove_news` (newsID int)
BEGIN
	
	delete from news
	where id = newsID;

END $$