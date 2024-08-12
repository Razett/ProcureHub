package com.glkids.procurehub.repository;

import com.glkids.procurehub.dto.ImportDTO;
import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.entity.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ImportRepository extends JpaRepository<Import, Long> {

    @Query("SELECT i FROM Import i WHERE i.order.quotationmtrl.quotation.contractor.corno = :corno AND FUNCTION('DATE', i.approvedate) = :approvedate AND i.status = :status")
    List<Import> getImportForm(@Param("corno") Long corno, @Param("approvedate") LocalDate approvedate, @Param("status") Integer status);
}
