package com.glkids.procurehub.service;

import com.glkids.procurehub.entity.QuotationMtrl;
import com.glkids.procurehub.repository.QuotationMtrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuotationMtrlServiceImpl implements QuotationMtrlService {

    @Autowired
    private QuotationMtrlRepository quotationMtrlRepository;

    @Override
    public QuotationMtrl saveQuotationMtrl(QuotationMtrl quotationMtrl) {
        return quotationMtrlRepository.save(quotationMtrl);
    }
}
