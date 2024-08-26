package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ImportDTO;
import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.dto.StatisticsDTO;
import com.glkids.procurehub.entity.QImports;
import com.glkids.procurehub.repository.ExportRepository;
import com.glkids.procurehub.repository.ImportInspectionRepository;
import com.glkids.procurehub.repository.ImportRepository;
import com.glkids.procurehub.status.ImportStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final ImportRepository importRepository;
    private final ImportInspectionRepository importInspectionRepository;
    private final ImportService importService;
    private final ExportRepository exportRepository;

    @Deprecated
    @Override
    public List<ImportDTO> getMonthImportByMaterial(Long mtrlno) {
        List<ImportDTO> list = new ArrayList<>();

        QImports qImports = QImports.imports;

        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue() - 6;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression mtrlExp = qImports.order.material.mtrlno.eq(mtrlno);
        BooleanExpression statusExp = qImports.status.gt(ImportStatus.INSPECTING.ordinal()).and(qImports.status.ne(ImportStatus.RETURNED.ordinal()));
        BooleanExpression approvedateExp = qImports.approvedate.after(LocalDate.of(year, month, 1).atStartOfDay())
                .or(qImports.approvedate.eq(LocalDate.of(year, month, 1).atStartOfDay()));

        importRepository.findAll(builder.and(mtrlExp).and(statusExp).and(approvedateExp)).forEach(imports -> {list.add(importService.importEntityToDto(imports));});


        Map<LocalDate, Long> quantityMap = new HashMap<>();

        for (int i = 0; i < 6; i++) {
            quantityMap.put(LocalDate.of(year, LocalDate.now().getMonthValue() - i,1), 0L);
        }

        for (ImportDTO importDTO : list) {
            LocalDate dtoApproveDate = LocalDate.of(year, importDTO.getApprovedate().getMonthValue(), 1);
            Long mapQuantity = quantityMap.get(dtoApproveDate);
            mapQuantity = mapQuantity + importDTO.getQuantity();
            quantityMap.put(dtoApproveDate, mapQuantity);
        }

        List<ImportDTO> logList = new ArrayList<>();

        for (LocalDate localDate : quantityMap.keySet()) {
            ImportDTO dto = ImportDTO.builder().approvedate(localDate.atStartOfDay())
                    .quantity(quantityMap.get(localDate)).build();
            logList.add(dto);
        }
        return logList;
    }

    @Override
    public List<StatisticsDTO> getMonthImportInspectionByMaterial(Long mtrlno) {
        int year = LocalDate.now().getYear();

        TreeMap<String, Long> quantityMap = new TreeMap<>();

        for (int i = 0; i < 6; i++) {
            int month = LocalDate.now().getMonthValue() - i;
            // 월이 음수일 경우 12월을 넘어서는 경우 처리
            if (month <= 0) {
                month += 12;
                year -= 1; // 이전 년도로 변경
            }
            // 월을 두 자릿수 형식으로 포맷팅
            String monthStr = String.format("%02d", month);
            quantityMap.put(year + "-" + monthStr, 0L);
        }

        List<Object[]> objects = importInspectionRepository.findMonthlyInspectionData(mtrlno);
        if (objects != null && !objects.isEmpty()) {
            for (Object[] object : objects) {
                Long mapQuantity = quantityMap.get((String) object[0]);
                mapQuantity = mapQuantity + ((BigDecimal) object[1]).longValue() - ((BigDecimal) object[2]).longValue();
                quantityMap.put((String) object[0], mapQuantity);
            }
        }

        List<StatisticsDTO> list = new ArrayList<>();

        for (String string: quantityMap.keySet()) {
            list.add(StatisticsDTO.builder().month(string).quantity(quantityMap.get(string)).build());
        }

        return list;
    }

    @Override
    public List<StatisticsDTO> getMonthExportByMaterial(Long mtrlno) {
        int year = LocalDate.now().getYear();

        TreeMap<String, Long> quantityMap = new TreeMap<>();

        for (int i = 0; i < 6; i++) {
            int month = LocalDate.now().getMonthValue() - i;
            // 월이 음수일 경우 12월을 넘어서는 경우 처리
            if (month <= 0) {
                month += 12;
                year -= 1; // 이전 년도로 변경
            }
            // 월을 두 자릿수 형식으로 포맷팅
            String monthStr = String.format("%02d", month);
            quantityMap.put(year + "-" + monthStr, 0L);
        }

        List<Object[]> objects = exportRepository.findMonthlyExportData(mtrlno);
        if (objects != null && !objects.isEmpty()) {
            for (Object[] object : objects) {
                Long mapQuantity = quantityMap.get((String) object[0]);
                mapQuantity = mapQuantity + ((BigDecimal) object[1]).longValue();
                quantityMap.put((String) object[0], mapQuantity);
            }
        }

        List<StatisticsDTO> list = new ArrayList<>();

        for (String string: quantityMap.keySet()) {
            list.add(StatisticsDTO.builder().month(string).quantity(quantityMap.get(string)).build());
        }

        return list;
    }

    @Override
    public List<StatisticsDTO> getMaterialQuantityStatistics(MaterialDTO materialDTO, List<StatisticsDTO> importsStat, List<StatisticsDTO> exportsStat) {
        List<StatisticsDTO> list = new ArrayList<>();

        int size = importsStat.size();
        Long quantity = materialDTO.getQuantity();
        for (int i = 1; i <= size; i++) {
            StatisticsDTO dto = StatisticsDTO.builder().month(importsStat.get(i - 1).getMonth())
                    .quantity(quantity)
                    .build();
            quantity = quantity - importsStat.get(size - i).getQuantity() + exportsStat.get(size - i).getQuantity();
            list.add(dto);
        }
        Collections.reverse(list);
        return list;
    }
}
