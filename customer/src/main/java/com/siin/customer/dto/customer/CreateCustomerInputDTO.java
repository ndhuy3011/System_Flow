package com.siin.customer.dto.customer;

import java.util.Date;
import java.util.UUID;

public record CreateCustomerInputDTO(
        UUID uuid, String lastName,
        String fristName, Date birthday, String numberPhone) {

}
