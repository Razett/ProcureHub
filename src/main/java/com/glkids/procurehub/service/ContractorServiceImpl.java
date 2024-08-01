package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ContractorDTO;
import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.entity.Contractor;
import com.glkids.procurehub.entity.Quotation;
import com.glkids.procurehub.repository.ContractorRepository;
import com.glkids.procurehub.repository.QuotationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository contractorRepository;
    private final QuotationRepository quotationRepository;

    @Override
    public List<ContractorDTO> list() {
        List<Contractor> contractors = contractorRepository.findAll();
        List<ContractorDTO> contractorDTOList = new ArrayList<>();
        contractors.forEach(x->contractorDTOList.add(contractorEntityToDTO(x)));
        return contractorDTOList;
    }

    @Override
    public ContractorDTO read(Long corno) {
        Optional<Contractor> opCon = contractorRepository.findById(corno);
        if (opCon.isPresent()) {
            return contractorEntityToDTO(opCon.get());
        }
        else {
            return null;
        }
    }

    @Override
    public void update(ContractorDTO contractorDTO) {
        contractorRepository.save(contractorDtoToEntity(contractorDTO));
    }

    @Override
    public void register(ContractorDTO contractorDTO) {
        Contractor conEntity = contractorDtoToEntity(contractorDTO);
        contractorRepository.save(conEntity);
    }

    @Override
    public List<QuotationDTO> quoList() {
        List<Quotation> quotation = quotationRepository.findAll();
        List<QuotationDTO> quoDTOList = new ArrayList<>();
        quotation.forEach(x->quoDTOList.add(quotationEntityToDTO(x)));
        return quoDTOList;
    }

    @Override
    public void quoRegister(QuotationDTO quotationDTO) {
        Quotation quoEntity = quotationDtoToEntity(quotationDTO);
        quotationRepository.save(quoEntity);
    }

    @Override
    public ContractorDTO findByName(String name) {
        Optional<Contractor> contractorOpt = contractorRepository.findByName(name);
        if (contractorOpt.isPresent()) {
            return contractorEntityToDTO(contractorOpt.get());
        } else {
            throw new IllegalArgumentException("No contractor found with name: " + name);
        }
    }
}
