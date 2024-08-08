package com.glkids.procurehub.repository.search;

import com.glkids.procurehub.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuotationSearchRepositoryImpl extends QuerydslRepositorySupport implements QuotationSearchRepository {
    public QuotationSearchRepositoryImpl() {
        super(Quotation.class);
    }

    @Override
    public Page<Object[]> searchQuotation(String type, String input, Pageable pageable) {
        QQuotation qQuotation = QQuotation.quotation;
        QQuotationMtrl qQuotationMtrl = QQuotationMtrl.quotationMtrl;
        QContractor qContractor = QContractor.contractor;

        JPQLQuery<Quotation> query = from(qQuotation);
        query.leftJoin(qQuotationMtrl).on(qQuotationMtrl.quotation.eq(qQuotation));
        query.leftJoin(qContractor).on(qQuotation.contractor.eq(qContractor));

        JPQLQuery<Tuple> tuple = query.select(qQuotation, qQuotationMtrl, qContractor, qQuotationMtrl.count());
        tuple.where(getSearchBuilder(type, input));
        tuple.groupBy(qQuotation);

        this.getQuerydsl().applyPagination(pageable, tuple);

        List<Tuple> result = tuple.fetch();
        Long count = tuple.fetchCount();

        return new PageImpl<Object[]>(result.stream().map(Tuple::toArray).collect(Collectors.toList()), pageable, count);
    }

    private BooleanBuilder getSearchBuilder(String type, String input) {
        QQuotation qQuotation = QQuotation.quotation;
        QQuotationMtrl qQuotationMtrl = QQuotationMtrl.quotationMtrl;
        QContractor qContractor = QContractor.contractor;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression searchExp = null;

        List<BooleanExpression> booleanExpressions = new ArrayList<>();

//        if (type.equals("TODAY")) {
//            LocalDateTime today = LocalDate.now().atStartOfDay();
//            return builder.and(qGuestBook.regDate.after(today).or(qGuestBook.regDate.eq(today)));
//        }
//        else
        if ((input == null || type == null) || input.isEmpty()) {
            return builder.and(qQuotation.qtno.gt(0L));
        } else {
            if (type.contains("QC")) {
                booleanExpressions.add(qQuotation.qtno.like(input));
            }
            if (type.contains("QT")) {
                booleanExpressions.add(qQuotation.title.contains(input));
            }
            if (type.contains("MC")) {
                booleanExpressions.add(qQuotationMtrl.material.mtrlno.like(input));
            }
            if (type.contains("MN")) {
                booleanExpressions.add(qQuotationMtrl.material.name.contains(input));
            }
            if (type.contains("CN")) {
                booleanExpressions.add(qContractor.corno.like(input));
            }
            if (type.contains("CT")) {
                booleanExpressions.add(qContractor.name.contains(input));
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
