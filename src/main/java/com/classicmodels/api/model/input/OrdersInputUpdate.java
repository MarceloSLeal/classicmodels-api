package com.classicmodels.api.model.input;

import com.classicmodels.domain.exception.BusinessException;
import com.classicmodels.domain.model.Orders;
import com.classicmodels.domain.model.OrdersStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration;

import java.time.OffsetDateTime;

@Getter
@Setter
public class OrdersInputUpdate {

    @FutureOrPresent
    @JsonProperty("shippedDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime shippedDate;

    @Enumerated(EnumType.STRING)
    private String status;

    @Nullable
    private String comments;

}
