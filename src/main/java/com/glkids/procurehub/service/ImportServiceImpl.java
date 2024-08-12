package com.glkids.procurehub.service;


import com.glkids.procurehub.dto.ImportDTO;
import com.glkids.procurehub.entity.Import;
import com.glkids.procurehub.repository.ImportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
