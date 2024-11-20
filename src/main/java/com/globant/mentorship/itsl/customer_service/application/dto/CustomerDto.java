package com.globant.mentorship.itsl.customer_service.application.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private String name;
    private Boolean active;
}
