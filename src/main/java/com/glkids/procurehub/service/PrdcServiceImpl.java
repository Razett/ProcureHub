package com.glkids.procurehub.service;

import com.glkids.procurehub.entity.Prdc;
import com.glkids.procurehub.entity.PrdcMtrl;
import com.glkids.procurehub.repository.PrdcMtrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PrdcServiceImpl implements PrdcService {

    private final PrdcMtrlRepository prdcMtrlRepository;

    @Override
    public List<PrdcMtrl> getPrdcmtrlByMtrl(Long mtrlno) {
        List<PrdcMtrl> list = new ArrayList<>();

        prdcMtrlRepository.findByMtrlno(mtrlno).forEach(prdc -> {list.add(prdc);});
        return list;
    }
}
