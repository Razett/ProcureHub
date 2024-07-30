package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.dto.MaterialWarehouseDTO;
import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.repository.MaterialRepository;
import com.glkids.procurehub.repository.MaterialWarehouseRepository;
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
    public Material register(MaterialDTO materialDTO) {
        return materialRepository.save(materialDTOToEntity(materialDTO));
    }

    @Override
    public void update(MaterialDTO materialDTO) {
        materialRepository.save(materialDTOToEntity(materialDTO));
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
