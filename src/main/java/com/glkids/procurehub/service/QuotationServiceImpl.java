package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.dto.QuotationFileDTO;
import com.glkids.procurehub.dto.QuotationMtrlDTO;
import com.glkids.procurehub.entity.Quotation;
import com.glkids.procurehub.entity.QuotationFile;
import com.glkids.procurehub.entity.QuotationMtrl;
import com.glkids.procurehub.repository.QuotationFileRepository;
import com.glkids.procurehub.repository.QuotationMtrlRepository;
import com.glkids.procurehub.repository.QuotationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuotationServiceImpl implements QuotationService {

    private final QuotationRepository quotationRepository;
    private final QuotationMtrlRepository quotationMtrlRepository;
    private final QuotationFileRepository quotationFileRepository;

    @Override
    @Transactional
    public Quotation saveQuotation(QuotationDTO quotationDTO) {
        Quotation quotation = quotationDtoToEntity(quotationDTO);
        return quotationRepository.save(quotation);
    }

    @Override
    @Transactional
    public QuotationMtrl saveQuotationMtrl(QuotationMtrlDTO quotationMtrlDTO) {
        QuotationMtrl quotationMtrl = quotationMtrlDtoToEntity(quotationMtrlDTO);
        return quotationMtrlRepository.save(quotationMtrl);
    }

    @Override
    @Transactional
    public QuotationFile saveQuotationFile(QuotationFileDTO quotationFileDTO) {
        QuotationFile quotationFile = quotationFileDtoToEntity(quotationFileDTO);
        return quotationFileRepository.save(quotationFile);
    }

    @Override
    public List<QuotationMtrlDTO> quotationMtrlList() {
        List<QuotationMtrl> quotationMtrls = quotationMtrlRepository.findAll();
        List<QuotationMtrlDTO> quotationMtrlDTO = new ArrayList<>();
        quotationMtrls.forEach(x-> quotationMtrlDTO.add(quotationMtrlEntityToDTO(x)));

        return  quotationMtrlDTO;
    }

    @Override
    public QuotationDTO read(Long qutno) {
        Optional<Quotation> opquo = quotationRepository.findById(qutno);
        if(opquo.isPresent()){
            return quotationEntityToDTO(opquo.get());
        }
        return null;
    }
}
