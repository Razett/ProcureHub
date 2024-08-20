package com.glkids.procurehub.scheduler;

import com.glkids.procurehub.service.ProcurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcurementScheduler {
    private final ProcurementService procurementService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void updatePrcrStatus(){
        procurementService.getProcurementDetailsGroupMtrl();
    }
}
