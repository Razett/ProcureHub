package com.glkids.procurehub.repository.search;

import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.status.ImportStatus;
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

public class ImportsSearchRepositoryImpl extends QuerydslRepositorySupport implements ImportsSearchRepository {
    public ImportsSearchRepositoryImpl() {
        super(Imports.class);
    }

    @Override
    public Page<Object[]> searchImport(String type, String input, Pageable pageable) {
        QImports qImports = QImports.imports;
        QOrder qOrder  = QOrder.order;
        QEmp qReceiver = QEmp.emp;
        QEmp qApprover = QEmp.emp;

        JPQLQuery<Imports> query = from(qImports);
        query.leftJoin(qOrder).on(qImports.order.eq(qOrder));
        query.leftJoin(qReceiver).on(qImports.receiver.eq(qReceiver));
        query.leftJoin(qApprover).on(qImports.approver.eq(qApprover));

        JPQLQuery<Tuple> tuple = query.select(qImports, qOrder, qReceiver, qApprover);
        tuple.where(getSearchBuilder(type, input));
        tuple.groupBy(qImports);

        this.getQuerydsl().applyPagination(pageable, tuple);

        List<Tuple> result = tuple.fetch();
        Long count = tuple.fetchCount();

        return new PageImpl<Object[]>(result.stream().map(Tuple::toArray).collect(Collectors.toList()), pageable, count);
    }

    private BooleanBuilder getSearchBuilder(String type, String input) {
        QImports qImport = QImports.imports;
        QOrder qOrder = QOrder.order;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression searchExp = null;

        List<BooleanExpression> booleanExpressions = new ArrayList<>();

        if ((input == null || type == null) || input.isEmpty()) {
            return builder.and(qImport.importno.gt(0L));
        } else {
            if (type.contains("IC")) {
                booleanExpressions.add(qImport.importno.like(input));
            }
            if (type.contains("OC")) {
                booleanExpressions.add(qImport.order.orderno.like(input));
            }
            if (type.contains("MC")) {
                booleanExpressions.add(qImport.order.material.mtrlno.like(input));
            }
            if (type.contains("MN")) {
                booleanExpressions.add(qImport.order.material.name.contains(input));
            }
//            if (type.contains("CN")) {
//                booleanExpressions.add(qImport.order.quotationmtrl.quotation.contractor.corno.like(input));
//            }
//            if (type.contains("CM")) {
//                booleanExpressions.add(qImport.order.quotationmtrl.quotation.contractor.name.contains(input));
//            }

            for (BooleanExpression booleanExpression : booleanExpressions) {
                if (searchExp == null) {
                    searchExp = booleanExpression;
                } else {
                    searchExp = searchExp.or(booleanExpression);
                }
            }
        }
        return builder.and(searchExp);
    }
}
