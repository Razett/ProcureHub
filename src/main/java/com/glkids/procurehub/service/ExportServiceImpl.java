package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ExportDTO;
import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.entity.Export;
import com.glkids.procurehub.entity.Prcr;
import com.glkids.procurehub.repository.ExportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {

    private final ExportRepository exportRepository;

    @Deprecated
    @Override
    public List<ExportDTO> list() {
        List<Export> exports = exportRepository.findAll();
        List<ExportDTO> dtos = new ArrayList<>();
        exports.forEach(x -> dtos.add(exportEntityToDTO(x)));
        return dtos;
    }

    @Override
    public ExportDTO read(Long exportno) {
        ExportDTO exportDTO = new ExportDTO();
        Export export = exportRepository.findById(exportno).orElse(null);
        if (export != null) {
            exportDTO.setExportno(export.getExportno());
            exportDTO.setShipper(export.getShipper());
            exportDTO.setDuedate(export.getDuedate());
            exportDTO.setShippeddate(export.getShippeddate());
            exportDTO.setQuantity(export.getQuantity());
            exportDTO.setEmp(export.getEmp());
            exportDTO.setPrcr(Prcr.builder().prcrno(export.getPrcr().getPrcrno())
                            .reqdate(export.getPrcr().getReqdate())
                            .material(export.getPrcr().getMaterial())
                    .build());
            exportDTO.setStatus(export.getStatus());
        }
//        return exportEntityToDTO(exportRepository.findById(exportno).orElse(null));
        return exportDTO;
    }

    @Override
    public List<ExportDTO> totalList(String type, String input) {
        List<ExportDTO> exportDTOList = new ArrayList<>();

        Page<Object[]> result = exportRepository.searchExport(type, input, PageRequest.of(0, 500, Sort.by(Sort.Direction.DESC, "exportno")));

        result.getContent().forEach(object -> {
            exportDTOList.add(exportObjectToDTO(object));
        });

        return exportDTOList;
    }
}
