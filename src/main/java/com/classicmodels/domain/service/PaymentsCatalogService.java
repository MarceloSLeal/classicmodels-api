package com.classicmodels.domain.service;

import com.classicmodels.domain.model.Payments;
import com.classicmodels.domain.repository.PaymentsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class PaymentsCatalogService {

    private PaymentsRepository paymentsRepository;

    @Transactional
    public Payments salvar(Payments payments) {

        return paymentsRepository.save(payments);
    }

}
