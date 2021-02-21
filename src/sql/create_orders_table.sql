
CREATE TABLE orders(
order_id INT auto_increment,
item_code varchar(10) NOT NULL,
quantity int NOT NULL,
order_timestamp timestamp DEFAULT CURRENT_TIMESTAMP,
order_total double,
primary key (order_id)
);
