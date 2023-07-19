package com.vibhugarg.cms.couponmanagement.domain;


public enum BatchState {
    // initial state for batch
    CREATED,
    // marks the batch active
    ACTIVE,
    // suspend distribution for some time, can be again marked as DEFINED
    SUSPENDED,
    // marks the batch of coupons expired
    EXPIRED,
    // terminate the batch forever
    TERMINATED
}
