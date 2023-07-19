package com.vibhugarg.cms.couponmanagement.service;

import com.vibhugarg.cms.couponmanagement.domain.BatchState;
import com.vibhugarg.cms.couponmanagement.domain.Coupon;
import com.vibhugarg.cms.couponmanagement.exceptions.*;

import java.util.Set;

public interface Batch {
    void createBatch(Batch batch, int maxAllowedGrants) throws InvalidRequest;

    void updateState(String batchId, BatchState state);

    Batch getBatch(String batchId) throws BatchNotFound;

    void ingestCoupons(String batchId, Set<String> couponCodes) throws BatchNotFound;

    Coupon grantCoupon(String batchId) throws BatchNotFound, BatchNotActive, BatchExhausted;

    Coupon getCoupon(String couponId) throws CouponNotFound;

    int getCouponsCount(String batchId) throws BatchNotFound;

    boolean isValidBatch(Batch batch);
}
