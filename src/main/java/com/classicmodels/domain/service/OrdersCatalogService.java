package com.classicmodels.domain.service;

import com.classicmodels.api.model.input.OrdersInputUpdate;
import com.classicmodels.domain.model.Orders;
import com.classicmodels.domain.model.OrdersStatus;
import com.classicmodels.domain.repository.OrdersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Service
public class OrdersCatalogService {

    private OrdersRepository ordersRepository;

    @Transactional
    public Orders salvar(Orders orders) {

        return ordersRepository.save(orders);
    }

    @Transactional
    public Orders atualizar(Orders orders, OrdersInputUpdate ordersInputUpdate) {

        if (orders.getShippedDate() == null) {
            orders.setShippedDate(ordersInputUpdate.getShippedDate());
        }

        orders.setComments(ordersInputUpdate.getComments());
        orders.setStatus(ordersInputUpdate.getStatus());

        return orders;
    }

    @Transactional
    public void excluir(Long id) {
        ordersRepository.deleteById(id);
    }

    public Orders salvarPost(Orders orders) {

        orders.setDate(OffsetDateTime.now());
        orders.setStatus(OrdersStatus.IN_PROCESS);

        System.out.println(orders.getDate());

        return this.salvar(orders);
    }

    public List<Orders> buscarPorOrderDate(String date) {

        return ordersRepository.findByDate(startOfDayFormatter(date), endOfDayFormatter(date));
    }

    public List<Orders> buscarPorOrderRequiredDate(String date) {

        return ordersRepository.findByRequiredDate(startOfDayFormatter(date), endOfDayFormatter(date));
    }

    public List<Orders> buscarPorOrderShippedDate(String date) {

        return ordersRepository.findByShippedDate(startOfDayFormatter(date), endOfDayFormatter(date));
    }

    static String startOfDayFormatter(String date) {

        LocalDate dateReceived = LocalDate.parse(date);
        LocalDateTime startOfDay = dateReceived.atStartOfDay();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return startOfDay.format(formatter);
    }

    static String endOfDayFormatter(String date) {

        LocalDate dateReceived = LocalDate.parse(date);
        LocalDateTime endOfDay = dateReceived.atTime(23, 59, 59);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return endOfDay.format(formatter);
    }

}
