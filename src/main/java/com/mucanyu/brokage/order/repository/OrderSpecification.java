package com.mucanyu.brokage.order.repository;

import com.mucanyu.brokage.order.constant.OrderStatus;
import com.mucanyu.brokage.order.model.Order;
import com.mucanyu.brokage.order.model.Order_;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderSpecification {

  public static Specification<Order> filterOrders(Long customerId, OrderStatus orderStatus, LocalDateTime startDate, LocalDateTime endDate) {
    return (root, query, criteriaBuilder) -> {
      var predicates = new ArrayList<Predicate>();

      if (Objects.nonNull(customerId)) {
        predicates.add(criteriaBuilder.equal(root.get(Order_.customerId), customerId));
      }

      if (Objects.nonNull(orderStatus)) {
        predicates.add(criteriaBuilder.equal(root.get(Order_.status), orderStatus));
      }

      if (Objects.nonNull(startDate)) {
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.createDate), startDate));
      }

      if (Objects.nonNull(endDate)) {
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Order_.createDate), endDate));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
