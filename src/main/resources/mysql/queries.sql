Queries variadas.

-------------------------------------------------------------------------------------------------------------------------------
#Query para inserir order_id na tabela payments, a tabela originalmente não tinha esse campo e nenhuma referencia direta
#aos order_id da tabela orders, apenas o amount de cada pedido, tive que fazer algumas verificacoes para achar os valores
#certo somando o valor total dos pedidos na tabela orderdetails com o campo amount em payments
#da pra inserir uma lista de check_number e fazer essas verificacoes, o ideal seria inserir um select ali mas nao consegui
# e acabei inserindo uma lista manualmente, tive que inserir toda a tabela, só deixei algums registros aqui de exemplo
#caso precise fazer isso novamente.
UPDATE payments p
JOIN (
    SELECT
        o.id AS order_id,
        od.order_id AS orderdetails_order_id,
        SUM(od.quantity_ordered * od.price_each) AS total_order
    FROM orders o
    JOIN orderdetails od ON o.id = od.order_id
    GROUP BY o.id
) AS order_totals
ON p.customer_id = (
    SELECT customer_id
    FROM orders
    WHERE orders.id = order_totals.order_id
)
AND p.amount = order_totals.total_order
SET p.order_id = order_totals.order_id
WHERE p.check_number IN (
'6588d261-5120-11ee-8eaa-0242ac120002',
'6588d2b2-5120-11ee-8eaa-0242ac120002',
'6588d30f-5120-11ee-8eaa-0242ac120002',
'6588d357-5120-11ee-8eaa-0242ac120002',
'6588d39d-5120-11ee-8eaa-0242ac120002',
'6588d3e7-5120-11ee-8eaa-0242ac120002',
'6588d43a-5120-11ee-8eaa-0242ac120002',
'6588d487-5120-11ee-8eaa-0242ac120002'
);
-------------------------------------------------------------------------------------------------------------------------------
