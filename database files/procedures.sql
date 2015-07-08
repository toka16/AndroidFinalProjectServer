-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The procedure inserts new user into 'users' table.
-- --------------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `insert_user` $$
CREATE PROCEDURE `insert_user` (userName nvarchar(32), userSurname nvarchar(32), email varchar(32), 
							userPassword varchar(16), phone varchar(32), card_number text, primary_number text)
BEGIN

	insert into users(user_name, user_surname, user_email, user_password, user_phone, user_card_number, user_primary_number)
		value(userName, userSurname, email, userPassword, phone, card_number, primary_number);

END $$


-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The procedure saves category into categories table. Also it finds out category id and adds it into map_category_product table.
-- --------------------------------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS `insert_category` $$
CREATE PROCEDURE `insert_category` (categoryName nvarchar(32))
BEGIN

	insert into categories(category_name)
		value(categoryName);

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The procedure inserts new product into products table. Also makes new entry into map_category_product
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE PROCEDURE `insert_product` (productName nvarchar(32), productDescription text, productPrice double, 
									productImageLink text, categoryName nvarchar(32))
BEGIN
	
	declare productID int;
	declare categoryID int;

	insert into products(product_name, description, price, image_link)
		value(productName, productDescription, productPrice, productImageLink);

	set productID = (select max(product_id) from products);
	set categoryID = (select category_id from categories where category_name = categoryName);
	
	insert into map_category_product(id_of_category, id_of_product)
		value(categoryID, productID);
END