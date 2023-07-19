package com.vibhugarg.cms.couponmanagement.domain;

import com.vibhugarg.cms.couponmanagement.domain.BatchState;
import com.vibhugarg.cms.couponmanagement.domain.Coupon;
import com.vibhugarg.cms.couponmanagement.domain.Period;
import com.vibhugarg.cms.couponmanagement.exceptions.BatchExhausted;
import com.vibhugarg.cms.couponmanagement.exceptions.BatchNotActive;
import com.vibhugarg.cms.couponmanagement.exceptions.InvalidTransition;

import java.util.Set;

public interface Batch {
    Coupon grantCoupon() throws BatchNotActive, BatchExhausted;

    void addCoupons(Set<String> couponIds);

    int getAvailableCouponCounts();

    void updateState(BatchState state) throws InvalidTransition;

    Period getValidityPeriod();
}
