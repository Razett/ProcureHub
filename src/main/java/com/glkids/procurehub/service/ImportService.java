package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ImportDTO;
import com.glkids.procurehub.dto.ImportInspectionDTO;
import com.glkids.procurehub.entity.*;

import java.util.List;

public interface ImportService {

    @Deprecated
    List<ImportDTO> list();

    List<ImportDTO> totalList(String type, String input);

    List<ImportDTO> readImportForm(Long importno);

    ImportDTO readRestful(Long importno);

    ImportInspectionDTO readInspection(Long importno);

    default ImportDTO importEntityToDto(Imports entity) {
        return ImportDTO.builder().importno(entity.getImportno()).order(entity.getOrder())
                .quantity(entity.getQuantity()).arrivaldate(entity.getArrivaldate())
                .receiver(entity.getReceiver()).approvedate(entity.getApprovedate())
                .approver(entity.getApprover()).status(entity.getStatus()).build();
    }

    default ImportDTO importObjectToDTO(Object entityObject) {
        ImportDTO dto = new ImportDTO();
        if (entityObject instanceof Object[] objectArray) {
            if (objectArray[0] instanceof Imports imports$) {
                dto.setImportno(imports$.getImportno());
                dto.setApprovedate(imports$.getApprovedate());
                dto.setArrivaldate(imports$.getArrivaldate());
                dto.setQuantity(imports$.getQuantity());
                dto.setStatus(imports$.getStatus());
            } if (objectArray[1] instanceof Order order) {
                dto.setOrder(order);
            } if (objectArray[2] instanceof Emp receiver) {
                dto.setReceiver(receiver);
            } if (objectArray[3] instanceof Emp approver) {
                dto.setApprover(approver);
            }
            return dto;
        } else {
            return null;
        }
    }

    default ImportInspectionDTO importInspectionEntityToDTO(ImportInspection inspection) {
        ImportInspectionDTO dto = new ImportInspectionDTO();

        if (inspection != null) {
            dto.setImportNspcNo(inspection.getImportNspcNo());
            dto.setContent(inspection.getContent());
            dto.setDuedate(inspection.getDuedate());
            dto.setEmp(inspection.getEmp());
            dto.setStatus(inspection.getStatus());
            dto.setDfcQuantity(inspection.getDfcQuantity());
        }

        return dto;
    }
}
