##query para inserir os dados do banco "classicmodels" para "classicmodels_api"

##productlines
insert into productlines (product_line, text_description, html_description, image)
select productLine, textDescription, htmlDescription, image from classicmodels.productlines;

##products
insert into products (product_code, product_name, product_line, product_scale, product_vendor, product_description,
quantity_in_stock, buy_price, msrp)
select productCode, productName, productLine, productScale, productVendor, productDescription,
quantityInStock, buyPrice, MSRP
from classicmodels.products;

##offices
insert into offices (office_code, city, phone, address_line1, address_line2, state, country, postal_code, territory)
select officeCode, city, phone, addressLine1, addressLine2, state, country, postalCode, territory
from classicmodels.offices;

##employees
insert into employees (employee_number, last_name, first_name, extension, email, office_code, reports_to, job_title)
select employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle
from classicmodels.employees;

##customers
insert into customers (customer_number, customer_name, contact_last_name, contact_first_name, phone,
address_line1, address_line2, city, state, postal_code, country, sales_rep_employee_number, credit_limit)
select customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, addressLine2,
city, state, postalCode, country, salesRepEmployeeNumber, creditLimit
from classicmodels.customers;

##orders
insert into orders (order_number, order_date, required_date, shipped_date, status, comments, customer_number)
select orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber
from classicmodels.orders;

##orderdetails
insert into orderdetails (order_number, product_code, quantity_ordered, price_each, order_line_number)
select orderNumber, productCode, quantityOrdered, priceEach, orderLineNumber
from classicmodels.orderdetails;

##payments
insert into payments (customer_number, check_number, payment_date, amount)
select customerNumber, checkNumber, paymentDate, amount
from classicmodels.payments;