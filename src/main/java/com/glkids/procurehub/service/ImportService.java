package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ImportDTO;
import com.glkids.procurehub.dto.ImportInspectionDTO;
import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.dto.StatisticsDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.status.ImportStatus;

import java.util.List;

public interface ImportService {

    @Deprecated
    List<ImportDTO> list();

    List<ImportDTO> getImportListBefore();

    List<ImportDTO> getImportListAfter();

    List<ImportDTO> totalList(String type, String input);

    List<ImportDTO> readImportForm(Long importno);

    ImportDTO readRestful(Long importno);

    void update(List<ImportDTO> importDTOList);

    ImportInspectionDTO readInspection(Long importno);

    Boolean updateInspection(ImportInspectionDTO inspectionDTO, Emp emp);

    void changeStatus(Long importno, ImportStatus status);

    Boolean executeImport(List<ImportDTO> importDTOList, Emp receiver);

    default ImportDTO importEntityToDto(Imports entity) {
        return ImportDTO.builder().importno(entity.getImportno()).order(entity.getOrder())
                .quantity(entity.getQuantity()).arrivaldate(entity.getArrivaldate())
                .receiver(entity.getReceiver()).approvedate(entity.getApprovedate())
                .approver(entity.getApprover()).status(entity.getStatus()).build();
    }

    default Imports importDtoToEntity(ImportDTO importDTO) {
        Imports imports = Imports.builder()
                .importno(importDTO.getImportno())
                .order(importDTO.getOrder())
                .quantity(importDTO.getQuantity())
                .arrivaldate(importDTO.getArrivaldate())
                .receiver(importDTO.getReceiver())
                .approvedate(importDTO.getApprovedate())
                .approver(importDTO.getApprover())
                .status(importDTO.getStatus())
                .build();

        return imports;
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

    default ImportInspection importInspectionDTOToEntity (ImportInspectionDTO inspectionDTO) {
        ImportInspection inspection = ImportInspection.builder()
                .importNspcNo(inspectionDTO.getImportNspcNo())
                .imports(inspectionDTO.getImports())
                .duedate(inspectionDTO.getDuedate())
                .content(inspectionDTO.getContent())
                .dfcQuantity(inspectionDTO.getDfcQuantity())
                .emp(inspectionDTO.getEmp())
                .status(inspectionDTO.getStatus()).build();

        return inspection;
    }
}
