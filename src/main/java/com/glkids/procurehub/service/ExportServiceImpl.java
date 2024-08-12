package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ExportDTO;
import com.glkids.procurehub.entity.Export;
import com.glkids.procurehub.repository.ExportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {

    private final ExportRepository exportRepository;

    @Override
    public List<ExportDTO> list() {
        List<Export> exports = exportRepository.findAll();
        List<ExportDTO> dtos = new ArrayList<>();
        exports.forEach(x ->dtos.add(exportEntityToDTO(x)));
        return dtos;
    }
}
