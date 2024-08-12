package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ExportDTO;
import com.glkids.procurehub.entity.Export;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface ExportService {

    List<ExportDTO> list();

    default ExportDTO exportEntityToDTO(Export entity) {
        return ExportDTO.builder().exportno(entity.getExportno()).emp(entity.getEmp())
                .prcr(entity.getPrcr()).quantity(entity.getQuantity()).shippeddate(entity.getShippeddate())
                .registerdate(entity.getDuedate()).shipper(entity.getShipper()).duedate(entity.getDuedate()).status(entity.getStatus()).build();
    }
}
