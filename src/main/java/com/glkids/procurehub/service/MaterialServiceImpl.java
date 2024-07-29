package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.dto.MaterialWarehouseDTO;
import com.glkids.procurehub.repository.MaterialRepository;
import com.glkids.procurehub.repository.MaterialWarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    private final MaterialWarehouseRepository materialWarehouseRepository;

    @Override
    public List<MaterialDTO> list() {
        return List.of();
    }

    @Override
    public MaterialDTO read(Long mtrlno) {
        return materialEntityToDTO(materialRepository.findById(mtrlno));
    }

    @Override
    @Transactional
    public void register(MaterialDTO materialDTO) {

        materialRepository.save(materialDTOToEntity(materialDTO));

    }

    @Override
    public int update(MaterialDTO materialDTO) {
        return 0;
    }

    @Override
    public int delete(Long mtrlno) {
        return 0;
    }

    @Override
    public List<MaterialWarehouseDTO> listWarehouse() {
        List<MaterialWarehouseDTO> warehouses = new ArrayList<>();
        materialWarehouseRepository.findAll().forEach(x->warehouses.add(warehouseEntityToDTO(x)));
        return warehouses;
    }
}
