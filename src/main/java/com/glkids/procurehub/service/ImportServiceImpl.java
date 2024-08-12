package com.glkids.procurehub.service;


import com.glkids.procurehub.dto.ExportDTO;
import com.glkids.procurehub.dto.ImportDTO;
import com.glkids.procurehub.entity.Export;
import com.glkids.procurehub.entity.Import;
import com.glkids.procurehub.repository.ImportRepository;
import com.glkids.procurehub.status.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService {

    private final ImportRepository importRepository;

    @Override
    public List<ImportDTO> list() {
        List<Import> imports = importRepository.findAll();
        List<ImportDTO> importDTOList = new ArrayList<>();
        imports.forEach(x -> importDTOList.add(importEntityToDto(x)));
        return importDTOList;
    }

    @Override
    public List<ImportDTO> readImportForm(Long importno) {
        List<ImportDTO> list = new ArrayList<>();
        Import im = importRepository.findById(importno).orElseGet(null);

        if (im != null) {
            importRepository.getImportForm(im.getOrder().getQuotationmtrl().getQuotation().getContractor().getCorno(), im.getApprovedate().toLocalDate(), OrderStatus.OK.ordinal()).forEach(x -> {
                list.add(importEntityToDto(x));
            });
        }
        return list;
    }
}
