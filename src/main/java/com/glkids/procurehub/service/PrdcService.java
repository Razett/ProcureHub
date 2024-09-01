package com.glkids.procurehub.service;

import com.glkids.procurehub.entity.PrdcMtrl;
import com.glkids.procurehub.entity.QuotationMtrl;

import java.util.List;

public interface PrdcService {

    List<PrdcMtrl> getPrdcmtrlByMtrl(Long mtrlno);

    List<PrdcMtrl> getPrdcmtrlByPrdcno(Long prdcno);

    List<QuotationMtrl> getMaterial(List<PrdcMtrl> list);

}
