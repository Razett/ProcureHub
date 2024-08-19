package com.glkids.procurehub.repository.search;

import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.status.ExportStatus;
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

public class ExportSearchRepositoryImpl extends QuerydslRepositorySupport implements ExportSearchRepository {
    public ExportSearchRepositoryImpl() {
        super(Export.class);
    }

    @Override
    public Page<Object[]> searchExport(String type, String input, Pageable pageable) {
        QExport qExport = QExport.export;
        QPrcr qPrcr = QPrcr.prcr;
        QEmp qEmp = QEmp.emp;

        JPQLQuery<Export> query = from(qExport);
        query.leftJoin(qPrcr).on(qExport.prcr.eq(qPrcr));
        query.leftJoin(qEmp).on(qExport.shipper.eq(qEmp));

        JPQLQuery<Tuple> tuple = query.select(qExport, qPrcr, qEmp);
        tuple.where(getSearchBuilder(type, input));
        tuple.groupBy(qExport);

        this.getQuerydsl().applyPagination(pageable, tuple);

        List<Tuple> result = tuple.fetch();
        Long count = tuple.fetchCount();

        return new PageImpl<Object[]>(result.stream().map(Tuple::toArray).collect(Collectors.toList()), pageable, count);
    }

    private BooleanBuilder getSearchBuilder(String type, String input) {
        QExport export = QExport.export;
        QEmp emp = QEmp.emp;
        QPrcr prcr = QPrcr.prcr;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression searchExp = null;

        List<BooleanExpression> booleanExpressions = new ArrayList<>();

        if ((input == null || type == null) || input.isEmpty()) {
            return builder.and(export.exportno.gt(0L));
        } else {
            if (type.contains("EC")) {
                booleanExpressions.add(export.exportno.like(input));
            }
            if (type.contains("MC")) {
                booleanExpressions.add(export.prcr.material.mtrlno.like(input));
            }
            if (type.contains("MN")) {
                booleanExpressions.add(export.prcr.material.name.contains(input));
            }
            if (type.contains("EN")) {
                booleanExpressions.add(export.emp.empno.like(input));
            }
            if (type.contains("EM")) {
                booleanExpressions.add(export.emp.name.contains(input));
            }

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
