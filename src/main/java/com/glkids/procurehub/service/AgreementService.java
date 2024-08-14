package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.AgreementDTO;
import com.glkids.procurehub.dto.ContractorDTO;
import com.glkids.procurehub.entity.Agreement;
import com.glkids.procurehub.status.AgreementStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface AgreementService {

    //등록하기
    Boolean register(AgreementDTO agreementdto);

    // 글가져오기
    AgreementDTO read(Long grmo);

    void changeStatus(Long grmno, AgreementStatus agreementStatus);

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

    default AgreementDTO agreementEntitytoDTO (Agreement entity){
        return AgreementDTO.builder().grmno(entity.getGrmno()).contractor(entity.getContractor())
                .quotation(entity.getQuotation()).emp(entity.getEmp()).title(entity.getTitle())
                .content(entity.getContent()).startdate(entity.getStartdate()).enddate(entity.getEnddate())
                .startdate(entity.getStartdate()).status(entity.getStatus()).build();
    }
}