package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.dto.QuotationFileDTO;
import com.glkids.procurehub.dto.QuotationMtrlDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.QuotationFileRepository;
import com.glkids.procurehub.repository.QuotationMtrlRepository;
import com.glkids.procurehub.repository.QuotationRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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
    public List<QuotationMtrlDTO> readQuotationMtrlList(Long qtno) {
        List<QuotationMtrlDTO> quotationMtrlDTOList = new ArrayList<>();

        QQuotationMtrl qQuotationMtrl = QQuotationMtrl.quotationMtrl;

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression quoExp = qQuotationMtrl.quotation.qtno.eq(qtno);

        quotationMtrlRepository.findAll(builder.and(quoExp)).forEach(x -> quotationMtrlDTOList.add(quotationMtrlEntityToDTO(x)));
        return  quotationMtrlDTOList;
    }

    @Override
    public QuotationDTO read(Long qtno) {
        Optional<Quotation> opquo = quotationRepository.findById(qtno);
        if(opquo.isPresent()){
            return quotationEntityToDTO(opquo.get());
        }
        return null;
    }

    @Override
    public List<QuotationFileDTO> quotationFileList(Long qtno) {
        List<QuotationFileDTO> quotationFileDTOList = new ArrayList<>();

        QQuotationFile qQuotationFile = QQuotationFile.quotationFile;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression quotationExp = qQuotationFile.quotation.qtno.eq(qtno)
;
        quotationFileRepository.findAll(builder.and(quotationExp)).forEach(x -> quotationFileDTOList.add(quotationFileEntityToDTO(x)));
        return quotationFileDTOList;
    }
}
