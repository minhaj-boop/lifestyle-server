package com.server.lifestyle.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateCouponRequest {
    private String code;

    private int discountPercentage = 0;

    private LocalDate validityStartDate;

    private LocalDate validityEndDate;

    private Long minimumOrderValue;
}
