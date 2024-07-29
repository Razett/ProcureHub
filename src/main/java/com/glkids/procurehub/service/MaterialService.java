package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.entity.Material;

import java.util.List;
import java.util.Optional;

public interface MaterialService {

    List<MaterialDTO> list();

    MaterialDTO read(Long mtrlno);

    void register(MaterialDTO materialDTO);

    int update(MaterialDTO materialDTO);

    int delete(Long mtrlno);

    default MaterialDTO materialEntityToDTO(Optional<Material> opt) {
        if (opt.isPresent()) {
            Material material = opt.get();

            return MaterialDTO.builder().mtrlno(material.getMtrlno())
                    .name(material.getName())
                    .description(material.getDescription())
                    .standard(material.getStandard())
                    .quantity(material.getQuantity())
                    .materialGroup(material.getMaterialGroup())
                    .materialWarehouse(material.getMaterialWarehouse())
                    .status(material.getStatus()).build();
        } else {
            return null;
        }
    }

    default Material materialDTOToEntity(MaterialDTO materialDTO) {
        return Material.builder().mtrlno(materialDTO.getMtrlno())
                .name(materialDTO.getName())
                .description(materialDTO.getDescription())
                .standard(materialDTO.getStandard())
                .quantity(materialDTO.getQuantity())
                .materialGroup(materialDTO.getMaterialGroup())
                .materialWarehouse(materialDTO.getMaterialWarehouse())
                .status(materialDTO.getStatus()).build();
    }
}
