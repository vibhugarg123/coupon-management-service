package com.vibhugarg.cms.couponmanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class Period {
    Date start;
    Date end;
}
