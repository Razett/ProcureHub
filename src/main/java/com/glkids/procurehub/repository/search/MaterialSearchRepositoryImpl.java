package com.glkids.procurehub.repository.search;

import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.entity.QMaterial;
import com.glkids.procurehub.entity.QMaterialGroup;
import com.glkids.procurehub.entity.QMaterialWarehouse;
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

public class MaterialSearchRepositoryImpl extends QuerydslRepositorySupport implements MaterialSearchRepository {

    public MaterialSearchRepositoryImpl() {
        super(Material.class);
    }

    @Override
    public Page<Object[]> searchMaterial(String input, Pageable pageable) {

        QMaterial qMaterial = QMaterial.material;
        QMaterialGroup qMaterialGroup = QMaterialGroup.materialGroup;
        QMaterialWarehouse qMaterialWarehouse = QMaterialWarehouse.materialWarehouse;

        JPQLQuery<Material> query = from(qMaterial);
        query.leftJoin(qMaterialGroup).on(qMaterial.materialGroup.eq(qMaterialGroup));
        query.leftJoin(qMaterialWarehouse).on(qMaterial.materialWarehouse.eq(qMaterialWarehouse));

        JPQLQuery<Tuple> tuple = query.select(qMaterial, qMaterialGroup, qMaterialWarehouse);
        tuple.where(getSearchBuilder(input));
        tuple.groupBy(qMaterial);

        this.getQuerydsl().applyPagination(pageable, tuple);

        List<Tuple> result = tuple.fetch();
        Long count = tuple.fetchCount();

        return new PageImpl<Object[]>(result.stream().map(Tuple::toArray).collect(Collectors.toList()), pageable, count);
    }

    private BooleanBuilder getSearchBuilder(String keyword) {
        QMaterial qMaterial = QMaterial.material;
        QMaterialGroup qMaterialGroup = QMaterialGroup.materialGroup;
        QMaterialWarehouse qMaterialWarehouse = QMaterialWarehouse.materialWarehouse;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression searchExp = null;

        List<BooleanExpression> booleanExpressions = new ArrayList<>();

        if (keyword == null || keyword.isEmpty()) {
            return builder.and(qMaterial.mtrlno.gt(0L));
        } else {
            booleanExpressions.add(qMaterial.mtrlno.like(keyword));
            booleanExpressions.add(qMaterial.name.contains(keyword));
            booleanExpressions.add(qMaterialGroup.grpcode.contains(keyword));
            booleanExpressions.add(qMaterialGroup.name.contains(keyword));
            booleanExpressions.add(qMaterialWarehouse.wrhscode.contains(keyword));
            booleanExpressions.add(qMaterialWarehouse.name.contains(keyword));

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
