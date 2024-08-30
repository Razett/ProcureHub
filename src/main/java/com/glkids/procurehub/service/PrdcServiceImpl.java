package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.CaculatorDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.MaterialRepository;
import com.glkids.procurehub.repository.PrcrRepository;
import com.glkids.procurehub.repository.PrdcMtrlRepository;
import com.glkids.procurehub.repository.QuotationMtrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PrdcServiceImpl implements PrdcService {

    private final PrdcMtrlRepository prdcMtrlRepository;
    private final MaterialRepository materialRepository;
    private final QuotationMtrlRepository quotationMtrlRepository;
    private final PrcrRepository prcrRepository;

    @Override
    public List<PrdcMtrl> getPrdcmtrlByMtrl(Long mtrlno) {
        List<PrdcMtrl> list = new ArrayList<>();

        prdcMtrlRepository.findByMtrlno(mtrlno).forEach(prdc -> {
            list.add(prdc);
        });
        return list;
    }

    @Override
    public List<PrdcMtrl> getPrdcmtrlByPrdcno(Long prdcno) {
        return prdcMtrlRepository.findByPrdcno(prdcno);
    }

    @Override
    public List<QuotationMtrl> getMaterial(List<PrdcMtrl> list) {
        List<QuotationMtrl> resultList = new ArrayList<>();
        list.forEach(prdcMtrl -> {
            // PrdcMtrl에서 자재 번호를 가져옴
            Long mtrlno = prdcMtrl.getMaterial().getMtrlno();

            // 자재 번호를 사용하여 QuotationMtrl 엔티티를 조회
            List<QuotationMtrl> quotationMtrls = quotationMtrlRepository.findByMaterial(mtrlno);

            // 조회된 QuotationMtrl 리스트를 결과 리스트에 추가
            resultList.addAll(quotationMtrls);
        });

        return resultList;
    }


}

