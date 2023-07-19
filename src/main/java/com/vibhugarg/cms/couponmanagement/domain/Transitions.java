package com.vibhugarg.cms.couponmanagement.domain;

import javax.naming.ldap.HasControls;
import java.util.*;

// Transitions defines the map for possible transitions for each transition
public class Transitions {
    public static Map<BatchState, HashSet<BatchState>> transitionMap = new HashMap<>();

    static {
        transitionMap.put(BatchState.CREATED, new HashSet<>(List.of(BatchState.SUSPENDED, BatchState.TERMINATED, BatchState.ACTIVE)));
        transitionMap.put(BatchState.ACTIVE, new HashSet<>(List.of(BatchState.SUSPENDED, BatchState.TERMINATED)));
        transitionMap.put(BatchState.SUSPENDED, new HashSet<>(List.of(BatchState.ACTIVE, BatchState.TERMINATED)));
        transitionMap.put(BatchState.TERMINATED, new HashSet<>());
        transitionMap.put(BatchState.EXPIRED, new HashSet<>());
    }
}
