package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.dto.MaterialGroupDTO;
import com.glkids.procurehub.dto.MaterialWarehouseDTO;
import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.entity.MaterialGroup;
import com.glkids.procurehub.entity.QMaterialGroup;
import com.glkids.procurehub.repository.MaterialGroupRepository;
import com.glkids.procurehub.repository.MaterialRepository;
import com.glkids.procurehub.repository.MaterialWarehouseRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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
    private final MaterialGroupRepository materialGroupRepository;
    private final MaterialWarehouseRepository materialWarehouseRepository;

    @Override
    public List<MaterialDTO> list() {
        List<Material> materialList = materialRepository.findAll();
        List<MaterialDTO> materialDTOList = new ArrayList<>();

        for (Material material : materialList) {
            MaterialDTO materialDTO = new MaterialDTO();
            materialDTO.setMtrlno(material.getMtrlno()); // 필요한 속성들을 설정
            materialDTO.setName(material.getName());
            materialDTO.setDescription(material.getDescription());
            materialDTO.setQuantity(material.getQuantity());
            // 추가적인 속성 설정이 필요할 수 있음

            materialDTOList.add(materialDTO);
        }

        return materialDTOList;
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

    @Override
    public List<MaterialGroupDTO> getTopMaterialGroups() {
        QMaterialGroup qMaterialGroup = QMaterialGroup.materialGroup;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression pDepthExpr = qMaterialGroup.depth.eq(0);

        List<MaterialGroupDTO> materialGroups = new ArrayList<>();
        materialGroupRepository.findAll(builder.and(pDepthExpr)).forEach(x -> {materialGroups.add(groupEntityToDTO(x));});

        return materialGroups;
    }

    @Override
    public List<MaterialGroupDTO> getChildMaterialGroups(String pGrpcode) {
        QMaterialGroup qMaterialGroup = QMaterialGroup.materialGroup;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression pGrpcodeExpr = qMaterialGroup.pGrpcode.eq(pGrpcode);

        List<MaterialGroupDTO> materialGroups = new ArrayList<>();
        materialGroupRepository.findAll(builder.and(pGrpcodeExpr)).forEach(x -> {materialGroups.add(groupEntityToDTO(x));});

        return materialGroups;
    }
}
