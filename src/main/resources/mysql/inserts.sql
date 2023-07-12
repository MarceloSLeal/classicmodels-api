##query para inserir os dados do banco "classicmodels" para "classicmodels_api"

##productlines
insert into productlines (product_line, text_description, html_description, image)
select product_line, text_description, html_description, image from classicmodels.productlines;

##products
insert into products (id, name, product_line, scale, vendor, description,
quantity_in_stock, buy_price, msrp)
select id, name, product_line, scale, vendor, description,
quantity_in_stock, buy_price, msrp
from classicmodels.products;

##offices
insert into offices (id, city, phone, address_line1, address_line2, state, country, postal_code, territory)
select id, city, phone, address_line1, address_line2, state, country, postal_code, territory
from classicmodels.offices;

##employees
insert into employees (id, last_name, first_name, extension, email, office_id, reports_to, job_title)
select id, last_name, first_name, extension, email, office_id, reports_to, job_title
from classicmodels.employees;

##customers
insert into customers (id, email, name, contact_last_name, contact_first_name, phone,
address_line1, address_line2, city, state, postal_code, country, employee_id, credit_limit)
select id, email, name, contact_last_name, contact_first_name, phone, address_line1, address_line2,
city, state, postal_code, country, employee_id, credit_limit
from classicmodels.customers;

##orders
insert into orders (id, date, required_date, shipped_date, status, comments, customer_id)
select id, date, required_date, shipped_date, status, comments, customer_id
from classicmodels.orders;

##orderdetails
insert into orderdetails (order_id, product_id, quantity_ordered, price_each, order_line_number)
select order_id, product_id, quantity_ordered, price_each, order_line_number
from classicmodels.orderdetails;

##payments
insert into payments (customer_id, check_number, payment_date, amount)
select customer_id, check_number, payment_date, amount
from classicmodels.payments;