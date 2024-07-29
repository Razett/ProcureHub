package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.repository.MaterialRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    @Override
    public List<MaterialDTO> list() {
        return List.of();
    }

    @Override
    public MaterialDTO read(Long mtrlno) {
        return materialEntityToDTO(materialRepository.findById(mtrlno));
    }

    @Override
    public void register(MaterialDTO materialDTO) {

    }

    @Override
    public int update(MaterialDTO materialDTO) {
        return 0;
    }

    @Override
    public int delete(Long mtrlno) {
        return 0;
    }
}
