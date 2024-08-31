package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.AgreementDTO;
import com.glkids.procurehub.dto.ContractorDTO;
import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.dto.QuotationMtrlDTO;
import com.glkids.procurehub.entity.*;
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
    Boolean register(ContractorDTO contractorDTO);

    //5. 견적 목록
    List<QuotationDTO> quoList(String type, String input);

    List<QuotationDTO> quoListByContractor(Long corno, Integer pageNum);

    //6. 견적 등록
    Boolean quoRegister(QuotationDTO quotationDTO);

    //7. 이름 검색 기능
    ContractorDTO findByName(String name);

    //8. 회사명 자동 완성 기능 검색
    List<ContractorDTO> findByNameContaining(String name);

    //9. 견적 상세 보기
    QuotationDTO quoread(Long qtno);

    //10. 견적 수정하기
    void quoupdate(QuotationDTO quotationDTO);

    /**
     * 견적에 추가된 자재가 없으면 상태를 {@code QuotationStatus.NEED_MATERIAL}로 변경합니다.
     */
    void quoStatusNoMtrl(Long qtno);

    default Contractor contractorDtoToEntity(ContractorDTO contractorDTO) {
        Contractor contractor=Contractor.builder().corno(contractorDTO.getCorno())
                .name(contractorDTO.getName()).phone(contractorDTO.getPhone())
                .postcode(contractorDTO.getPostcode()).address1(contractorDTO.getAddress1()).address2(contractorDTO.getAddress2())
                .mngrName(contractorDTO.getMngrName()).mngrPhone(contractorDTO.getMngrPhone())
                .mngrAddress(contractorDTO.getMngrAddress()).bank(contractorDTO.getBank())
                .accountNum(contractorDTO.getAccountNum()).build();

        return contractor;
    }

    default ContractorDTO contractorEntityToDTO(Contractor entity) {
        return ContractorDTO.builder().corno(entity.getCorno()).name(entity.getName()).phone(entity.getPhone()).postcode(entity.getPostcode())
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

    default QuotationDTO quotationObjectToDTO(Object entityObject) {
        QuotationDTO dto = new QuotationDTO();
        if (entityObject instanceof Object[] objectArray) {
            for (Object object : objectArray) {
                if (object instanceof Quotation quotation) {
                    dto.setQtno(quotation.getQtno());
                    dto.setTitle(quotation.getTitle());
                    dto.setContent(quotation.getContent());
                    dto.setStatus(quotation.getStatus());
                    dto.setRegdate(quotation.getRegdate());
                    dto.setModdate(quotation.getModdate());
                } else if (object instanceof QuotationMtrl quotationMtrl) {
                    dto.getQuotationMtrlList().add(quotationMtrlEntityToDTO(quotationMtrl));
                } else if (object instanceof Contractor contractor) {
                    dto.setContractor(contractor);
                } else if (object instanceof Long quotationMtrlCount) {
                    dto.setQuotationMtrlCount(quotationMtrlCount);
                }
            }
            return dto;
        } else {
            return null;
        }
    }

}
