package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ContractorDTO;
import com.glkids.procurehub.entity.Contractor;

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
    List<ContractorDTO> quoList();

    //6. 견적 등록
    void quoRegister(ContractorDTO contractorDTO);

    default Contractor dtoToEntity(ContractorDTO contractorDTO) {
        Contractor contractor=Contractor.builder().corno(contractorDTO.getCorno())
                .name(contractorDTO.getName()).phone(contractorDTO.getPhone())
                .address1(contractorDTO.getAddress1()).address2(contractorDTO.getAddress2())
                .mngrName(contractorDTO.getMngrName()).mngrPhone(contractorDTO.getMngrPhone())
                .mngrAddress(contractorDTO.getMngrAddress()).bank(contractorDTO.getBank())
                .accountNum(contractorDTO.getAccountNum()).build();

        return contractor;
    }

    default ContractorDTO entityToDTO(Contractor entity) {
        return ContractorDTO.builder().corno(entity.getCorno()).name(entity.getName()).phone(entity.getPhone())
                .address1(entity.getAddress1()).address2(entity.getAddress2()).mngrName(entity.getMngrName())
                .mngrPhone(entity.getMngrPhone()).mngrAddress(entity.getMngrAddress()).build();

    }
}
