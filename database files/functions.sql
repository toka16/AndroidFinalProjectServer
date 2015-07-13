-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The function returns appropriate product id by a given product name.
-- --------------------------------------------------------------------------------
DELIMITER $$
DROP FUNCTION IF EXISTS `get_product_id` $$
CREATE FUNCTION `get_product_id` (productName nvarchar(32))
RETURNS INTEGER
BEGIN
	RETURN (select product_id from products where product_name = productName);
END $$


-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: The function returns true if product is into database, false - otherwise.
-- --------------------------------------------------------------------------------
DELIMITER $$
DROP FUNCTION IF EXISTS `contains_product` $$
CREATE FUNCTION `contains_product` (productName nvarchar(32))
RETURNS BIT
BEGIN
	declare productID int;
	set productID = (select product_id from products where product_name = productName);
	RETURN productID is not null;
END