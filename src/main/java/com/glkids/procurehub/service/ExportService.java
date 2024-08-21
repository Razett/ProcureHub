package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ExportDTO;
import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.dto.UserDTO;
import com.glkids.procurehub.entity.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface ExportService {

    @Deprecated
    List<ExportDTO> list();

    List<ExportDTO> getExportListBefore();

    ExportDTO read(Long exportno);

    List<ExportDTO> totalList(String type, String input);

    void Shipment(List<ExportDTO> list , UserDTO userDTO) throws Exception;

    default ExportDTO exportEntityToDTO(Export entity) {
        if (entity != null) {
            return ExportDTO.builder().exportno(entity.getExportno()).emp(entity.getEmp())
                .prcr(entity.getPrcr()).quantity(entity.getQuantity()).shippeddate(entity.getShippeddate())
                .registerdate(entity.getRegdate()).shipper(entity.getShipper()).duedate(entity.getDuedate()).status(entity.getStatus())
                    .moddate(entity.getModdate()).build();
        } else {
            return new ExportDTO();
        }
    }

    default ExportDTO exportObjectToDTO(Object entityObject) {
        ExportDTO dto = new ExportDTO();
        if (entityObject instanceof Object[] objectArray) {
            for (Object object : objectArray) {
                if (object instanceof Export export) {
                    dto.setExportno(export.getExportno());
                    dto.setDuedate(export.getDuedate());
                    dto.setQuantity(export.getQuantity());
                    dto.setRegisterdate(export.getRegdate());
                    dto.setShippeddate(export.getShippeddate());
                    dto.setStatus(export.getStatus());
                } else if (object instanceof Emp shipper) {
                    dto.setShipper(shipper);
                } else if (object instanceof Prcr prcr) {
                    dto.setPrcr(prcr);
                }
            }
            return dto;
        } else {
            return null;
        }
    }
}
