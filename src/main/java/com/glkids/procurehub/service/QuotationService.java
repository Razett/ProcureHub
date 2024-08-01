package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface QuotationService {
    Quotation saveQuotation(QuotationDTO quotationDTO);

    QuotationMtrl saveFileMaterial(QuotationMtrl quotationMtrl);

}