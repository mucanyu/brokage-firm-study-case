package com.mucanyu.brokage.order.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record MatchOrderDto(@NotEmpty List<Long> pendingOrders) {}
