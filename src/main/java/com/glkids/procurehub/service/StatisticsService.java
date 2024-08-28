package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ImportDTO;
import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.dto.StatisticsDTO;
import com.glkids.procurehub.entity.Material;

import java.util.List;
import java.util.Map;

public interface StatisticsService {

    @Deprecated
    List<ImportDTO> getMonthImportByMaterial(Long mtrlno);

    List<StatisticsDTO> getMonthImportInspectionByMaterial(Long mtrlno);

    List<StatisticsDTO> getMonthExportByMaterial(Long mtrlno);

    List<StatisticsDTO> getMaterialQuantityStatistics(MaterialDTO materialDTO, List<StatisticsDTO> importsStat, List<StatisticsDTO> exportsStat);

    Map<String, Long> getMonthOrderCounts();

    Map<String, Long> getMonthImportsCounts();

    Map<String, Long> getMonthExportsCounts();
}
