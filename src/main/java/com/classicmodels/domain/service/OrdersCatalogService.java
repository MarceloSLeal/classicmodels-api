package com.classicmodels.domain.service;

import com.classicmodels.domain.model.Orders;
import com.classicmodels.domain.repository.OrdersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public void excluir(Long id) {
        ordersRepository.deleteById(id);
    }

    public List<Orders> buscarPorOrderDate(String date) {

        LocalDate dateReceived = LocalDate.parse(date);
        LocalDateTime startOfDay = dateReceived.atStartOfDay();
        LocalDateTime endOfDay = dateReceived.atTime(23, 59, 59);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime formattedStartOfDay = LocalDateTime.parse(startOfDay.format(formatter));
        LocalDateTime formattedEndOfDay = LocalDateTime.parse(endOfDay.format(formatter));

        return ordersRepository.findByDate(formattedStartOfDay, formattedEndOfDay);
    }

}
