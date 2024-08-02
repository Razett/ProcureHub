package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.AgreementDTO;
import com.glkids.procurehub.entity.Agreement;
import com.glkids.procurehub.repository.AgreementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgreementServiceImpl implements AgreementService {

    private final AgreementRepository agreementRepository;

    @Override
    public void register(AgreementDTO agreementdto) {
        Agreement agreement = agreementDtoToEntity(agreementdto);
        agreementRepository.save(agreement);
    }
}
