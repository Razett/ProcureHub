package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.*;
import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.entity.MaterialFile;
import com.glkids.procurehub.entity.MaterialGroup;
import com.glkids.procurehub.entity.MaterialWarehouse;

import java.util.List;
import java.util.Optional;

public interface MaterialService {

    List<MaterialDTO> list(String input);

    MaterialDTO read(Long mtrlno);

    MaterialDTO readByFetch(Long mtrlno);

    Material register(MaterialDTO materialDTO);

    void update(MaterialDTO materialDTO);

    int delete(Long mtrlno);

    List<MaterialWarehouseDTO> getWarehouses();

    MaterialWarehouseDTO registerMaterialWarehouse(MaterialWarehouseDTO materialWarehouseDTO);

    Boolean deleteMaterialWarehouse(String wrhscode);

    Boolean verifyWrhscode(String wrhscode);

    MaterialGroupDTO readMaterialGroup(String grpcode);

    List<MaterialGroupDTO> getMaterialGroupsByDepth(Integer depth);

    List<MaterialGroupDTO> getChildMaterialGroups(String pGrpcode);

    List<MaterialGroupListDTO> getMaterialGroupLists(MaterialGroup materialGroup);

    List<MaterialGroupDTO> getMaterialGroupDirection(MaterialGroup materialGroup);

    MaterialFile saveMaterialFile(MaterialFileDTO materialFileDTO);

    List<MaterialFileDTO> materialFileList(Long mtrlno);

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

    default MaterialDTO materialObjectToDto(Object entityObject) {
        MaterialDTO dto = new MaterialDTO();
        if (entityObject instanceof Object[] objectArray) {
            for (Object object : objectArray) {
                if (object instanceof Material material) {
                    dto.setMtrlno(material.getMtrlno());
                    dto.setName(material.getName());
                    dto.setDescription(material.getDescription());
                    dto.setStandard(material.getStandard());
                    dto.setQuantity(material.getQuantity());
                    dto.setStatus(material.getStatus());
                    dto.setRegdate(material.getRegdate());
                    dto.setModdate(material.getModdate());
                } else if (object instanceof MaterialGroup materialGroup) {
                    dto.setMaterialGroup(materialGroup);
                } else if (object instanceof MaterialWarehouse materialWarehouse) {
                    dto.setMaterialWarehouse(materialWarehouse);
                }
            }
            return dto;
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

    default MaterialWarehouse warehouseDtoToEntity(MaterialWarehouseDTO materialWarehouseDTO) {
        return MaterialWarehouse.builder().wrhscode(materialWarehouseDTO.getWrhscode()).name(materialWarehouseDTO.getWrhsname()).build();
    }

    default MaterialGroupDTO groupEntityToDTO(MaterialGroup materialGroup) {
        return MaterialGroupDTO.builder().grpcode(materialGroup.getGrpcode())
                .name(materialGroup.getName())
                .pGrpcode(materialGroup.getPGrpcode())
                .depth(materialGroup.getDepth()).build();
    }

    default MaterialFileDTO materialFileEntityToDTO(MaterialFile entity) {
        return MaterialFileDTO.builder()
                .mtrlfno(entity.getMtrlfno())
                .material(entity.getMaterial())
                .uuid(entity.getUuid())
                .name(entity.getName())
                .url(entity.getUrl())
                .build();

    }
        default MaterialFile materialFileDTOToEntity (MaterialFileDTO materialFileDTO){
            return MaterialFile.builder()
                    .material(materialFileDTO.getMaterial())
                    .uuid(materialFileDTO.getUuid())
                    .name(materialFileDTO.getName())
                    .url(materialFileDTO.getUrl())
                    .build();
        }
    }
