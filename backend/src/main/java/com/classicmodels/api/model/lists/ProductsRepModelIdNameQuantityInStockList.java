package com.classicmodels.api.model.lists;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductsRepModelIdNameQuantityInStockList {

    private Long id;
    private String name;
    private Short quantityInStock;
    private Double msrp;
}
