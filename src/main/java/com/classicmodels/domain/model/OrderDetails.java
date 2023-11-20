package com.classicmodels.domain.model;

import com.classicmodels.domain.model.modelId.OrderDetailsId;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@IdClass(OrderDetailsId.class)
@Table(name = "orderdetails")
public class OrderDetails {

    @Id
    private Long orderId;
    @Id
    private Long productId;
    private Integer quantityOrdered;
    private Double priceEach;
    private Integer orderLineNumber;

}
