package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.dto.QuotationFileDTO;
import com.glkids.procurehub.dto.QuotationMtrlDTO;
import com.glkids.procurehub.entity.*;

import java.util.List;

public interface QuotationService {

    Quotation saveQuotation(QuotationDTO quotationDTO);

    QuotationMtrl saveQuotationMtrl(QuotationMtrlDTO quotationMtrlDTO);

    QuotationFile saveQuotationFile(QuotationFileDTO quotationFileDTO);

    List<QuotationMtrlDTO> readQuotationMtrlList(Long qtno); // 계약 추가화면

    QuotationDTO read(Long qutno);

    List<QuotationFileDTO> quotationFileList(Long qtno);

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
                .regdate(entity.getRegdate())
                .build();
    }

    // QuotationFile DTO to Entity 변환 메서드
    default QuotationFile quotationFileDtoToEntity(QuotationFileDTO quotationFileDTO) {
        return QuotationFile.builder()
                .quotation(Quotation.builder().qtno(quotationFileDTO.getQtno()).build())
                .uuid(quotationFileDTO.getUuid())
                .name(quotationFileDTO.getName())
                .url(quotationFileDTO.getUrl())
                .qtfno(quotationFileDTO.getQtfno())
                .build();
    }

    // QuotationFile Entity to DTO 변환 메서드
    default QuotationFileDTO quotationFileEntityToDTO(QuotationFile entity) {
        return QuotationFileDTO.builder()
                .qtno(entity.getQuotation().getQtno())
                .qtfno(entity.getQtfno())
                .uuid(entity.getUuid())
                .name(entity.getName())
                .url(entity.getUrl())
                .build();
    }
    // QuotationMtrl DTO to Entity 변환 메서드
    default QuotationMtrl quotationMtrlDtoToEntity(QuotationMtrlDTO quotationMtrlDTO) {
        return QuotationMtrl.builder()
                .quotation(Quotation.builder()
                        .qtno(quotationMtrlDTO.getQuotationId())
                        .build())
                .material(Material.builder()
                        .mtrlno(quotationMtrlDTO.getMaterialId())
                        .build())
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
                .qtmtno(entity.getQtmtno())
                .quotationId(entity.getQuotation().getQtno())
                .quotation(entity.getQuotation())
                .materialId(entity.getMaterial().getMtrlno())
                .material(entity.getMaterial())
                .quantity(entity.getQuantity())
                .unitprice(entity.getUnitprice())
                .totalprice(entity.getTotalprice())
                .leadtime(entity.getLeadtime())
                .build();
    }
}
