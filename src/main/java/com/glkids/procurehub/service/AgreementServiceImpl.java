package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.AgreementDTO;
import com.glkids.procurehub.entity.Agreement;
import com.glkids.procurehub.entity.Contractor;
import com.glkids.procurehub.entity.QAgreement;
import com.glkids.procurehub.repository.AgreementRepository;
import com.glkids.procurehub.repository.ContractorRepository;
import com.glkids.procurehub.status.AgreementStatus;
import com.glkids.procurehub.status.QuotationStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgreementServiceImpl implements AgreementService {

    private final AgreementRepository agreementRepository;
    private final ContractorRepository contractorRepository;
    private final QuotationService quotationService;


    @Transactional
    @Override
    public Boolean register(AgreementDTO agreementdto) {
        Agreement agreement = agreementDtoToEntity(agreementdto);
        agreementRepository.save(agreement);

        if (quotationService.isDuplicatedQuotationMtrl(agreementdto.getQtno(), agreementdto.getCorno())) {
            quotationService.changeStatus(agreementdto.getQtno(), QuotationStatus.DUPPLICATED_MTRL);
            changeStatus(agreement.getGrmno(), AgreementStatus.DUPPLICATED_MTRL);
        } else {
            quotationService.changeStatus(agreementdto.getQtno(), QuotationStatus.AGREEMENT);
        }

        return agreement.getGrmno() != null;
    }

    @Override
    public AgreementDTO read(Long grmo) {
        Optional<Agreement> agrop = agreementRepository.findById(grmo);
        if(agrop.isPresent()){
            return agreementEntitytoDTO(agrop.get());
        }
        return null;
    }

    @Override
    public List<AgreementDTO> readListByQtno(Long qtno) {
        List<AgreementDTO> list = new ArrayList<>();

        QAgreement qAgreement = QAgreement.agreement;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression qtnoExp = qAgreement.quotation.qtno.eq(qtno);

        agreementRepository.findAll(builder.and(qtnoExp), Sort.by(Sort.Direction.DESC, "regdate")).forEach(agreement -> {
            list.add(agreementEntitytoDTO(agreement));
        });
        return list;
    }

    @Transactional
    @Override
    public void changeStatus(Long grmno, AgreementStatus agreementStatus) {
        agreementRepository.changeStatus(grmno, agreementStatus.ordinal());
    }
}
