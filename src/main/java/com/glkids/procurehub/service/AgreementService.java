package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.AgreementDTO;
import com.glkids.procurehub.entity.Agreement;

public interface AgreementService {

    void register(AgreementDTO agreementdto);

    default Agreement agreementDtoToEntity (AgreementDTO agreementDTO) {
        Agreement agreement = Agreement.builder()
                .grmno(agreementDTO.getGrmno())
                .contractor(agreementDTO.getContractor())
                .quotation(agreementDTO.getQuotation())
                .emp(agreementDTO.getEmp())
                .title(agreementDTO.getTitle())
                .content(agreementDTO.getContent())
                .startdate(agreementDTO.getStartdate())
                .enddate(agreementDTO.getEnddate())
                .status(agreementDTO.getStatus())
                .build();

        return agreement;
    }
}