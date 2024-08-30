package com.glkids.procurehub.repository.search;

import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.status.OrderStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderSearchRepositoryImpl extends QuerydslRepositorySupport implements OrderSearchRepository {
    public OrderSearchRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public Page<Object[]> searchOrder(String type, String input, Pageable pageable) {
        QOrder qOrder = QOrder.order;
        QMaterial qMaterial = QMaterial.material;
        QQuotationMtrl qQuotationMtrl = QQuotationMtrl.quotationMtrl;
        QEmp qEmp = QEmp.emp;

        JPQLQuery<Order> query = from(qOrder);
        query.leftJoin(qMaterial).on(qOrder.material.eq(qMaterial));
        query.leftJoin(qQuotationMtrl).on(qOrder.quotationmtrl.eq(qQuotationMtrl));
        query.leftJoin(qEmp).on(qOrder.emp.eq(qEmp));

        JPQLQuery<Tuple> tuple = query.select(qOrder, qMaterial, qQuotationMtrl, qEmp);
        tuple.where(getTotalSearchBuilder(type, input));
        tuple.groupBy(qOrder);

        this.getQuerydsl().applyPagination(pageable, tuple);

        List<Tuple> result = tuple.fetch();
        Long count = tuple.fetchCount();

        return new PageImpl<Object[]>(result.stream().map(Tuple::toArray).collect(Collectors.toList()), pageable, count);
    }

    private BooleanBuilder getTotalSearchBuilder(String type, String input) {
        QOrder order = QOrder.order;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression searchExp = null;

        List<BooleanExpression> booleanExpressions = new ArrayList<>();

        if ((input == null || type == null) || input.isEmpty()) {
            return builder.and(order.orderno.gt(0L));
        } else {
            if (type.contains("EC")) {
                booleanExpressions.add(order.orderno.like(input));
            }
            if (type.contains("CN")) {
                booleanExpressions.add(order.quotationmtrl.quotation.contractor.name.contains(input));
            }
            if (type.contains("MC")) {
                booleanExpressions.add(order.material.mtrlno.like(input));
            }
            if (type.contains("MN")) {
                booleanExpressions.add(order.material.name.contains(input));
            }
            if (type.contains("EN")) {
                booleanExpressions.add(order.emp.empno.like(input));
            }
            if (type.contains("EM")) {
                booleanExpressions.add(order.emp.name.contains(input));
            }

            for (BooleanExpression booleanExpression : booleanExpressions) {
                if (searchExp == null) {
                    searchExp = booleanExpression;
                } else {
                    searchExp = searchExp.or(booleanExpression);
                }
            }
        }
        return builder.and(order.status.gt(OrderStatus.INSPECTING.ordinal())).and(searchExp);
    }
}
