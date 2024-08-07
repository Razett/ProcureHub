package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ContractorDTO;
import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.dto.QuotationMtrlDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.ContractorRepository;
import com.glkids.procurehub.repository.QuotationMtrlRepository;
import com.glkids.procurehub.repository.QuotationRepository;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository contractorRepository;
    private final QuotationRepository quotationRepository;

    @Override
    public List<ContractorDTO> list() {
        List<Contractor> contractors = contractorRepository.findAll();
        List<ContractorDTO> contractorDTOList = new ArrayList<>();
        contractors.forEach(x -> contractorDTOList.add(contractorEntityToDTO(x)));
        return contractorDTOList;
    }

    @Override
    public ContractorDTO read(Long corno) {
        Optional<Contractor> opCon = contractorRepository.findById(corno);
        if (opCon.isPresent()) {
            return contractorEntityToDTO(opCon.get());
        } else {
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
        quotation.forEach(x -> quoDTOList.add(quotationEntityToDTO(x)));
        return quoDTOList;
    }

    @Override
    public List<QuotationDTO> quoListByContractor(Long corno, Integer pageNum) {
        List<QuotationDTO> list = new ArrayList<>();

        Pageable pageable = PageRequest.of(pageNum, 50, Sort.by(Sort.Direction.DESC, "regdate"));
        Page<Object[]> pageObject = quotationRepository.findQuotationByCorno(corno, pageable);

        pageObject.getContent().forEach(object -> {
            Object[] array = object;
            if (array.length == 3) {
                if (array[0] instanceof Quotation quotation) {
                    QuotationDTO quotationDTO = quotationEntityToDTO(quotation);
                    if (list.isEmpty()) {
                        list.add(quotationDTO);
                    }
                    if (list.get(list.size() - 1).getQtno().longValue() != quotationDTO.getQtno().longValue()) {
                        if (array[1] instanceof QuotationMtrl quotationMtrl) {
                            quotationDTO.getQuotationMtrlList().add(quotationMtrlEntityToDTO(quotationMtrl));
                        }
                        if (array[2] instanceof Long agreementCount) {
                            quotationDTO.setAgreementCount(agreementCount);
                        }
                        list.add(quotationDTO);
                    } else {
                        if (array[1] instanceof QuotationMtrl quotationMtrl) {
                            list.get(list.size() - 1).getQuotationMtrlList().add(quotationMtrlEntityToDTO(quotationMtrl));
                        }
                        if (array[2] instanceof Long agreementCount) {
                            list.get(list.size() - 1).setAgreementCount(agreementCount);
                        }
                    }
                }
            }
        });
        return list;
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
            throw new IllegalArgumentException("회사명을 찾지 못햇습니다.: " + name);
        }
    }

    @Override
    public List<ContractorDTO> findByNameContaining(String name) {
        List<Contractor> contractors = contractorRepository.findByNameContaining(name);

        // 정렬 기준에 맞게 정렬
        List<Contractor> sortedContractors = contractors.stream()
                .sorted((c1, c2) -> {
                    // 이름에 대한 순서 정렬 예시 (단어의 위치에 따라 정렬)
                    String c1Name = c1.getName().toLowerCase();
                    String c2Name = c2.getName().toLowerCase();
                    return compareNames(c1Name, c2Name, name.toLowerCase());
                })
                .collect(Collectors.toList());

        return sortedContractors.stream()
                .map(this::contractorEntityToDTO)
                .collect(Collectors.toList());
    }


    private int compareNames(String name1, String name2, String query) {
        int index1 = getIndexOfQuery(name1, query);
        int index2 = getIndexOfQuery(name2, query);
        return Integer.compare(index1, index2);
    }

    private int getIndexOfQuery(String name, String query) {
        int index = 0;
        for (char ch : query.toCharArray()) {
            index = name.indexOf(ch, index);
            if (index == -1) {
                return Integer.MAX_VALUE; // 검색 쿼리가 포함되지 않는 경우 큰 값 반환
            }
            index++;
        }
        return index;
    }

    @Override
    public QuotationDTO quoread(Long qtno) {
        Optional<Quotation> quoOpt = quotationRepository.findById(qtno);
        if (quoOpt.isPresent()) {
            return quotationEntityToDTO(quoOpt.get());
        } else {
            return null;
        }
    }

    @Override
    public void quoupdate(QuotationDTO quotationDTO) {
        quotationRepository.save(quotationDtoToEntity(quotationDTO));
    }
}