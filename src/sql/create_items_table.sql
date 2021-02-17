CREATE TABLE items(
item_id INT auto_increment,
item_code varchar(10) NOT NULL UNIQUE,
description varchar(50),
price decimal(13,2) DEFAULT 0,
inventory_amount int DEFAULT 0,
primary key (item_id)
);