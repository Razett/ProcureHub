package com.glkids.procurehub.service;

import com.glkids.procurehub.entity.Prdc;
import com.glkids.procurehub.entity.PrdcMtrl;

import java.util.List;

public interface PrdcService {

    List<PrdcMtrl> getPrdcmtrlByMtrl(Long mtrlno);
}
