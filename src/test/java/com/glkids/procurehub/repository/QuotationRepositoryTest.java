package com.glkids.procurehub.repository;

import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.entity.Agreement;
import com.glkids.procurehub.entity.Quotation;
import com.glkids.procurehub.entity.QuotationMtrl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
public class QuotationRepositoryTest {

    @Autowired
    private QuotationRepository quotationRepository;

    @Test
    public void quoListByContractorTest() {
        Long corno = 123L;
        int pageNum = 0;
        Pageable pageable = PageRequest.of(pageNum, 50, Sort.by(Sort.Direction.DESC, "regdate"));
        Page<Object[]> pageObject = quotationRepository.findQuotationByCorno(corno, pageable);
        pageObject.getContent().forEach(object -> {
            Object[] array = object;
            if (array.length == 3) {
                if (array[0] instanceof Quotation quotation) {
                    System.out.println(quotation.getQtno());
                }
                if (array[1] instanceof QuotationMtrl quotationMtrl) {
                    System.out.println(quotationMtrl.getQtmtno());
                }
                if (array[2] instanceof Long agreementCount) {
                    System.out.println(agreementCount);
                }
                System.out.println();
            }
        });
    }
}
