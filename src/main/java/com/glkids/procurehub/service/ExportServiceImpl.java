package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ExportDTO;
import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.dto.UserDTO;
import com.glkids.procurehub.entity.Export;
import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.entity.Prcr;
import com.glkids.procurehub.entity.QExport;
import com.glkids.procurehub.repository.ExportRepository;
import com.glkids.procurehub.repository.MaterialRepository;
import com.glkids.procurehub.repository.PrcrRepository;
import com.glkids.procurehub.status.ExportStatus;
import com.glkids.procurehub.status.PrcrStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {

    private final ExportRepository exportRepository;
    private final MaterialRepository materialRepository;
    private final MaterialService materialService;
    private final PrcrRepository prcrRepository;

    @Deprecated
    @Override
    public List<ExportDTO> list() {
        List<Export> exports = exportRepository.findAll();
        List<ExportDTO> dtos = new ArrayList<>();
        exports.forEach(x -> dtos.add(exportEntityToDTO(x)));
        return dtos;
    }

    @Override
    public List<ExportDTO> getExportListBefore() {
        List<ExportDTO> list = new ArrayList<>();

        QExport qExport = QExport.export;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression statusExp = qExport.status.lt(ExportStatus.CONTINUING.ordinal());

        exportRepository.findAll(builder.and(statusExp)).forEach(x -> list.add(exportEntityToDTO(x)));
        return list;
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

    @Override
    public void Shipment(List<ExportDTO> list, UserDTO userDTO) throws Exception {
        for (ExportDTO exportDTO : list) {

            Export export = exportRepository.findById(exportDTO.getExportno())
                    .orElseThrow(() -> new Exception("출고번호를 찾지못함"));


            // 상태 코드 업데이트
            export.setStatus(ExportStatus.OK.ordinal());
            export.setShippeddate(LocalDateTime.now());

            // 조달계획 상태 코드 업데이트
            Prcr procurementPlan = prcrRepository.findById(export.getPrcr().getPrcrno())
                    .orElseThrow(() -> new Exception("조달계획을 찾지못함"));
            procurementPlan.setStatus(PrcrStatus.FINISHED.ordinal());

            // 자재 수량 차감
            Material material = export.getPrcr().getMaterial();
            MaterialDTO materialDTO = materialService.materialEntityToDTO(Optional.of(material));
            materialDTO.setQuantity(materialDTO.getQuantity() - export.getQuantity());
            material = materialService.materialDTOToEntity(materialDTO);


            // 출고 처리한 사원 기록
            export.setShipper(userDTO.getEmp());

            // 데이터 저장
            exportRepository.save(export);
            prcrRepository.save(procurementPlan);
            materialRepository.save(material);
        }
    }
}
