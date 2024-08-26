package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ImportDTO;
import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.dto.StatisticsDTO;
import com.glkids.procurehub.entity.Material;

import java.util.List;

public interface StatisticsService {

    @Deprecated
    List<ImportDTO> getMonthImportByMaterial(Long mtrlno);

    List<StatisticsDTO> getMonthImportInspectionByMaterial(Long mtrlno);

    List<StatisticsDTO> getMonthExportByMaterial(Long mtrlno);

    List<StatisticsDTO> getMaterialQuantityStatistics(MaterialDTO materialDTO, List<StatisticsDTO> importsStat, List<StatisticsDTO> exportsStat);
}
