package com.vibhugarg.cms.couponmanagement.domain;

import com.vibhugarg.cms.couponmanagement.exceptions.BatchExhausted;
import com.vibhugarg.cms.couponmanagement.exceptions.BatchNotActive;
import com.vibhugarg.cms.couponmanagement.exceptions.InvalidTransition;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class ClosedBatch implements Batch {
    private final String id;
    private BatchState state;
    private Period validityPeriod;
    private String distributorName;
    private CouponType couponType;
    private Set<String> availableCouponCodes;
    private Set<String> grantedCouponCodes;

    public ClosedBatch(String id, Period validityPeriod, CouponType couponType, String distributor) {
        this.id = id;
        this.state = BatchState.CREATED;
        this.validityPeriod = validityPeriod;
        this.distributorName = distributor;
        this.couponType = couponType;
        this.availableCouponCodes = new HashSet<>();
        this.grantedCouponCodes = new HashSet<>();
    }

    public boolean isValid() {
        Instant now = Instant.now();
        return this.validityPeriod.getStart().toInstant().isBefore(now)
                && this.validityPeriod.getEnd().toInstant().isAfter(now);
    }

    public boolean isTransitionAllowed(BatchState initial, BatchState finalState) {
        return Transitions.transitionMap.containsKey(initial) &&
                Transitions.transitionMap.get(initial).contains(finalState);
    }

    public Coupon createNewCoupon() {
        String id = this.availableCouponCodes.iterator().next();
        return new Coupon(id, CouponStatus.GRANTED);
    }

    @Override
    public synchronized Coupon grantCoupon() throws BatchNotActive, BatchExhausted {
        // check if batch is active
        if (this.state != BatchState.ACTIVE) {
            throw new BatchNotActive();
        }
        // check if any coupons are available to grant
        if (this.isCouponAvailable()) {
            // Create a new coupon and assign to the userid
            Coupon coupon = createNewCoupon();
            grantedCouponCodes.add(coupon.getCode());
            availableCouponCodes.remove(coupon.getCode());
            return coupon;
        }
        throw new BatchExhausted();
    }

    @Override
    public void addCoupons(Set<String> couponIds) {
        this.availableCouponCodes.addAll(couponIds);
    }

    @Override
    public int getAvailableCouponCounts() {
        return this.availableCouponCodes.size();
    }

    protected boolean isCouponAvailable() {
        return this.availableCouponCodes.size() > 0;
    }

    @Override
    public void updateState(BatchState state) throws InvalidTransition {
        if (this.isTransitionAllowed(this.state, state)) {
            this.state = state;
            return;
        }
        throw new InvalidTransition();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Batch ID: ").append(this.id).append("\n").
                append("Current State: ").append(this.state).append("\n").
                append("Expires on: ").append(this.validityPeriod.getEnd().toString()).append("\n").
                append("Available coupons: ").append(this.availableCouponCodes.size()).append("\n");
        return sb.toString();
    }
}
