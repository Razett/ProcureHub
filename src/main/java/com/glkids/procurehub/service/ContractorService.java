package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.AgreementDTO;
import com.glkids.procurehub.dto.ContractorDTO;
import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.dto.QuotationMtrlDTO;
import com.glkids.procurehub.entity.Agreement;
import com.glkids.procurehub.entity.Contractor;
import com.glkids.procurehub.entity.Quotation;
import com.glkids.procurehub.entity.QuotationMtrl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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

    List<QuotationDTO> quoListByContractor(Long corno, Integer pageNum);

    //6. 견적 등록
    void quoRegister(QuotationDTO quotationDTO);

    //7. 이름검색기능
    ContractorDTO findByName(String name);

    //8. 회사명 자동완성 기능검색
    List<ContractorDTO> findByNameContaining(String name);

    // 견적상세보기
     QuotationDTO quoread(Long qtno);

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
                .status(entity.getStatus()).regdate(entity.getRegdate()).moddate(entity.getModdate()).build();

    }

    // QuotationMtrl Entity to DTO 변환 메서드
    default QuotationMtrlDTO quotationMtrlEntityToDTO(QuotationMtrl entity) {
        return QuotationMtrlDTO.builder()
                .qtmtno(entity.getQtmtno())
                .quotation(entity.getQuotation())
                .quotationId(entity.getQuotation().getQtno())
                .material(entity.getMaterial())
                .materialId(entity.getMaterial().getMtrlno())
                .quantity(entity.getQuantity())
                .unitprice(entity.getUnitprice())
                .totalprice(entity.getTotalprice())
                .leadtime(entity.getLeadtime())
                .build();
    }

    default AgreementDTO agreementEntityToDTO(Agreement agreement) {
        return AgreementDTO.builder().grmno(agreement.getGrmno())
                .contractor(agreement.getContractor())
                .quotation(agreement.getQuotation())
                .emp(agreement.getEmp())
                .title(agreement.getTitle())
                .content(agreement.getContent())
                .startdate(agreement.getStartdate())
                .enddate(agreement.getEnddate())
                .regdate(agreement.getRegdate())
                .modedate(agreement.getModdate()).build();
    }

}
