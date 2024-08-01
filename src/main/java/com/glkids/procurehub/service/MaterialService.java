package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.dto.MaterialGroupListDTO;
import com.glkids.procurehub.dto.MaterialWarehouseDTO;
import com.glkids.procurehub.dto.MaterialGroupDTO;
import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.entity.MaterialGroup;
import com.glkids.procurehub.entity.MaterialWarehouse;

import java.util.List;
import java.util.Optional;

public interface MaterialService {

    List<MaterialDTO> list();

    MaterialDTO read(Long mtrlno);

    MaterialDTO readByFetch(Long mtrlno);

    Material register(MaterialDTO materialDTO);

    void update(MaterialDTO materialDTO);

    int delete(Long mtrlno);

    List<MaterialWarehouseDTO> getWarehouses();

    MaterialGroupDTO readMaterialGroup(String grpcode);

    List<MaterialGroupDTO> getMaterialGroupsByDepth(Integer depth);

    List<MaterialGroupDTO> getChildMaterialGroups(String pGrpcode);

    List<MaterialGroupListDTO> getMaterialGroupLists(MaterialGroup materialGroup);

    List<MaterialGroupDTO> getMaterialGroupDirection(MaterialGroup materialGroup);

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
                    .status(material.getStatus())
                    .regdate(material.getRegdate())
                    .moddate(material.getModdate()).build();
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

    default MaterialWarehouseDTO warehouseEntityToDTO(MaterialWarehouse materialWarehouse) {
        return MaterialWarehouseDTO.builder().wrhscode(materialWarehouse.getWrhscode()).wrhsname(materialWarehouse.getName()).build();
    }

    default MaterialGroupDTO groupEntityToDTO(MaterialGroup materialGroup) {
        return MaterialGroupDTO.builder().grpcode(materialGroup.getGrpcode())
                .name(materialGroup.getName())
                .pGrpcode(materialGroup.getPGrpcode())
                .depth(materialGroup.getDepth()).build();
    }

}
