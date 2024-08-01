package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.dto.QuotationFileDTO;
import com.glkids.procurehub.dto.QuotationMtrlDTO;
import com.glkids.procurehub.entity.*;

public interface QuotationService {

    Quotation saveQuotation(QuotationDTO quotationDTO);

    QuotationMtrl saveQuotationMtrl(QuotationMtrlDTO quotationMtrlDTO);

    QuotationFile saveQuotationFile(QuotationFileDTO quotationFileDTO);

    // DTO to Entity 변환 메서드
    default Quotation quotationDtoToEntity(QuotationDTO quotationDTO) {
        return Quotation.builder()
                .qtno(quotationDTO.getQtno())
                .contractor(Contractor.builder().corno(quotationDTO.getContractor().getCorno()).build())
                .emp(Emp.builder().empno(quotationDTO.getEmp().getEmpno()).build())
                .title(quotationDTO.getTitle())
                .content(quotationDTO.getContent())
                .status(quotationDTO.getStatus())
                .build();
    }

    // Entity to DTO 변환 메서드
    default QuotationDTO quotationEntityToDTO(Quotation entity) {
        return QuotationDTO.builder()
                .qtno(entity.getQtno())
                .contractor(entity.getContractor())
                .emp(entity.getEmp())
                .title(entity.getTitle())
                .content(entity.getContent())
                .status(entity.getStatus())
                .moddate(entity.getModdate())
                .build();
    }

    // QuotationMtrl DTO to Entity 변환 메서드
    default QuotationMtrl quotationMtrlDtoToEntity(QuotationMtrlDTO quotationMtrlDTO) {
        return QuotationMtrl.builder()
                .quotation(Quotation.builder().qtno(quotationMtrlDTO.getQuotationId()).build())
                .material(Material.builder().mtrlno(quotationMtrlDTO.getMaterialId()).build())
                .emp(Emp.builder().empno(201758030L).build())
                .quantity(quotationMtrlDTO.getQuantity())
                .unitprice(quotationMtrlDTO.getUnitprice())
                .totalprice(quotationMtrlDTO.getTotalprice())
                .leadtime(quotationMtrlDTO.getLeadtime())
                .build();
    }

    // QuotationMtrl Entity to DTO 변환 메서드
    default QuotationMtrlDTO quotationMtrlEntityToDTO(QuotationMtrl entity) {
        return QuotationMtrlDTO.builder()
                .quotationId(entity.getQuotation().getQtno())
                .materialId(entity.getMaterial().getMtrlno())
                .quantity(entity.getQuantity())
                .unitprice(entity.getUnitprice())
                .totalprice(entity.getTotalprice())
                .leadtime(entity.getLeadtime())
                .build();
    }

    // QuotationFile DTO to Entity 변환 메서드
    default QuotationFile quotationFileDtoToEntity(QuotationFileDTO quotationFileDTO) {
        return QuotationFile.builder()
                .quotation(Quotation.builder().qtno(quotationFileDTO.getQtno()).build())
                .uuid(quotationFileDTO.getUuid())
                .name(quotationFileDTO.getName())
                .url(quotationFileDTO.getUrl())
                .build();
    }

    // QuotationFile Entity to DTO 변환 메서드
    default QuotationFileDTO quotationFileEntityToDTO(QuotationFile entity) {
        return QuotationFileDTO.builder()
                .qtno(entity.getQuotation().getQtno())
                .uuid(entity.getUuid())
                .name(entity.getName())
                .url(entity.getUrl())
                .build();
    }
}
