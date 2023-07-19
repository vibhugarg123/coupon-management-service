package com.vibhugarg.cms.couponmanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coupon {
    private String code;
    private CouponStatus status;
}
