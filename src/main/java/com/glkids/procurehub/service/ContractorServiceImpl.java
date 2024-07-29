package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ContractorDTO;
import com.glkids.procurehub.entity.Contractor;
import com.glkids.procurehub.repository.ContractorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository contractorRepository;

    @Override
    public List<ContractorDTO> list() {
        List<Contractor> contractors = contractorRepository.findAll();
        List<ContractorDTO> contractorDTOList = new ArrayList<>();
        contractors.forEach(x->contractorDTOList.add(entityToDTO(x)));
        return contractorDTOList;
    }

    @Override
    public ContractorDTO read(Long corno) {
        Optional<Contractor> opCon = contractorRepository.findById(corno);
        if (opCon.isPresent()) {
            return entityToDTO(opCon.get());
        }
        else {
            return null;
        }
    }

    @Override
    public void update(ContractorDTO contractorDTO) {
        contractorRepository.save(dtoToEntity(contractorDTO));
    }

    @Override
    public void register(ContractorDTO contractorDTO) {
        Contractor conEntity = dtoToEntity(contractorDTO);
        contractorRepository.save(conEntity);
    }

    @Override
    public List<ContractorDTO> quoList() {
        List<Contractor> quotation = contractorRepository.findAll();
        List<ContractorDTO> quoDTOList = new ArrayList<>();
        quotation.forEach(x->quoDTOList.add(entityToDTO(x)));
        return quoDTOList;
    }

    @Override
    public void quoRegister(ContractorDTO contractorDTO) {
        Contractor quoEntity = dtoToEntity(contractorDTO);
        contractorRepository.save(quoEntity);
    }
}
