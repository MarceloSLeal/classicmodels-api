package com.classicmodels.domain.service;

import com.classicmodels.domain.model.Orders;
import com.classicmodels.domain.repository.OrdersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
