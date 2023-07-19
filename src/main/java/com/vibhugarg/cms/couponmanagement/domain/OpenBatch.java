package com.vibhugarg.cms.couponmanagement.domain;

import com.vibhugarg.cms.couponmanagement.exceptions.BatchExhausted;
import com.vibhugarg.cms.couponmanagement.exceptions.BatchNotActive;

public class OpenBatch extends ClosedBatch {
    private final int maxAllowedGrants;
    private int grantedCoupons = 0;

    public OpenBatch(String id, Period validityPeriod, CouponType couponType, String distributor, int maxAllowedGrants) {
        super(id, validityPeriod, couponType, distributor);
        this.maxAllowedGrants = maxAllowedGrants;
    }

    @Override
    public synchronized Coupon grantCoupon() throws BatchNotActive, BatchExhausted {

        if (this.getState() != BatchState.ACTIVE) {
            throw new BatchNotActive();
        }
        // The case where coupon type is open
        if (grantedCoupons >= maxAllowedGrants) {
            throw new BatchExhausted();
        }
        Coupon coupon = createNewCoupon();
        this.grantedCoupons += 1;
        return coupon;
    }

    @Override
    public int getAvailableCouponCounts() {
        return this.maxAllowedGrants - this.grantedCoupons;
    }
}
