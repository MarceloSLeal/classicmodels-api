package com.classicmodels.domain.service;

import com.classicmodels.domain.model.OrderDetails;
import com.classicmodels.domain.repository.OrderDetailsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderDetailsCatalogService {

    private OrderDetailsRepository orderDetailsRepository;

    @Transactional
    public List<OrderDetails> salvar(List<OrderDetails> orderDetails) {

        return orderDetailsRepository.saveAll(orderDetails);
    }

}