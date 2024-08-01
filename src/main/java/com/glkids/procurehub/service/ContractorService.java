package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ContractorDTO;
import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.entity.Contractor;
import com.glkids.procurehub.entity.Quotation;

import java.util.List;

public interface ContractorService {

    //1. 업체 목록
    List<ContractorDTO> list();

    //2. 업체 상세 보기
    ContractorDTO read(Long corno);

    //3. 업체 정보 수정
    void update(ContractorDTO contractorDTO);

    //4. 업체 등록
    void register(ContractorDTO contractorDTO);

    //5. 견적 목록
    List<QuotationDTO> quoList();

    //6. 견적 등록
    void quoRegister(QuotationDTO quotationDTO);

    default Contractor contractorDtoToEntity(ContractorDTO contractorDTO) {
        Contractor contractor=Contractor.builder().corno(contractorDTO.getCorno())
                .name(contractorDTO.getName()).phone(contractorDTO.getPhone())
                .address1(contractorDTO.getAddress1()).address2(contractorDTO.getAddress2())
                .mngrName(contractorDTO.getMngrName()).mngrPhone(contractorDTO.getMngrPhone())
                .mngrAddress(contractorDTO.getMngrAddress()).bank(contractorDTO.getBank())
                .accountNum(contractorDTO.getAccountNum()).build();

        return contractor;
    }

    default ContractorDTO contractorEntityToDTO(Contractor entity) {
        return ContractorDTO.builder().corno(entity.getCorno()).name(entity.getName()).phone(entity.getPhone())
                .address1(entity.getAddress1()).address2(entity.getAddress2()).mngrName(entity.getMngrName())
                .mngrPhone(entity.getMngrPhone()).mngrAddress(entity.getMngrAddress()).bank(entity.getBank())
                .accountNum(entity.getAccountNum()).build();

    }

    default Quotation quotationDtoToEntity(QuotationDTO quotationDTO) {
        Quotation quotation=Quotation.builder().qtno(quotationDTO.getQtno()).contractor(quotationDTO.getContractor())
                .emp(quotationDTO.getEmp()).title(quotationDTO.getTitle()).content(quotationDTO.getContent())
                .status(quotationDTO.getStatus()).build();

        return quotation;
    }

    default QuotationDTO quotationEntityToDTO(Quotation entity) {
        return QuotationDTO.builder().qtno(entity.getQtno()).contractor(entity.getContractor())
                .emp(entity.getEmp()).title(entity.getTitle()).content(entity.getContent())
                .status(entity.getStatus()).moddate(entity.getModdate()).build();

    }
}
