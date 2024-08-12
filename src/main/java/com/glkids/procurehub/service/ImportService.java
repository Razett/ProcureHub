package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ImportDTO;
import com.glkids.procurehub.entity.Import;

import java.util.List;

public interface ImportService {

    List<ImportDTO> list();

    default ImportDTO importEntityToDto(Import entity) {
        return ImportDTO.builder().importno(entity.getImportno()).order(entity.getOrder())
                .quantity(entity.getQuantity()).arrivaldate(entity.getArrivaldate())
                .receiver(entity.getReceiver()).approvedate(entity.getApprovedate())
                .approver(entity.getApprover()).status(entity.getStatus()).build();
    }
}
