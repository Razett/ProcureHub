package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotationRepository extends JpaRepository<Quotation, Long> {
}
