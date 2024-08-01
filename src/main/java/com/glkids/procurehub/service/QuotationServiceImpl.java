package com.glkids.procurehub.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class QuotationServiceImpl implements QuotationService {

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private QuotationFileRepository quotationFileRepository;

    @Autowired
    private QuotationMtrlRepository quotationMtrlRepository;

    @Override
    public Quotation saveQuotation(QuotationDTO quotationDTO) {
        Quotation quotation = Quotation.builder()
                .content(quotationDTO.getContent())
                .status(quotationDTO.getStatus())
                .title(quotationDTO.getTitle())
                .contractor(quotationDTO.getContractor())
                .emp(quotationDTO.getEmp())
                .build();
        return quotationRepository.save(quotation);
    }
    @Override
    public QuotationMtrl saveFileMaterial(QuotationMtrl quotationMtrlDTO) {
        QuotationMtrl quotationMtrl = QuotationMtrl.builder()
                .leadtime(quotationMtrlDTO.getLeadtime())
                .quantity(quotationMtrlDTO.getQuantity())
                .totalprice(quotationMtrlDTO.getTotalprice())
                .unitprice(quotationMtrlDTO.getUnitprice())
                .emp(quotationMtrlDTO.getEmp())
                .material(quotationMtrlDTO.getMaterial())
                .quotation(quotationMtrlDTO.getQuotation())
                .build();
        return quotationMtrlRepository.save(quotationMtrl);
    }
}
